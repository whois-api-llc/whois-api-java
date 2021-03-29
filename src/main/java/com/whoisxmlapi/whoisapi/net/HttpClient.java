package com.whoisxmlapi.whoisapi.net;

import com.whoisxmlapi.whoisapi.exception.*;
import com.whoisxmlapi.whoisapi.model.NetworkTimeouts;
import com.whoisxmlapi.whoisapi.model.RequestParameters;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

public class HttpClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

    protected static final int conTimeout = 5;
    protected static final int readTimeout = 45;
    protected static final int writeTimeout = 5;
    protected static final int maxAsyncPoolSize = 10;
    protected int poolSize = 5;
    protected static final int maxRetries = 3;
    protected int retries = 1;

    public enum ResponseFormat {
        JSON {
            @Override public String format() {
                return "JSON";
            }
        },

        XML {
            @Override public String format() {
                return "XML";
            }
        };

        public abstract String format();
    }

    protected static final String DOMAIN_NAME_PARAMETER = "domainName";

    protected OkHttpClient client;
    protected RequestParameters rp;

    public HttpClient(RequestParameters rp) {
        Objects.requireNonNull(rp);
        this.rp = rp;

        this.client = this.buildClient(
                conTimeout, TimeUnit.SECONDS, writeTimeout, TimeUnit.SECONDS, readTimeout, TimeUnit.SECONDS);
    }

    public HttpClient(RequestParameters rp, NetworkTimeouts timeouts) {
        Objects.requireNonNull(rp);
        this.rp = rp;

        this.client = this.buildClient(
                timeouts.getConnectTimeout(),
                timeouts.getConnectTimeUnits(),
                timeouts.getWriteTimeout(),
                timeouts.getWriteTimeUnits(),
                timeouts.getReadTimeout(),
                timeouts.getReadTimeUnits());
    }

    public String get(String domain)
            throws EmptyApiKeyException, NetworkException, ApiEndpointException, ApiAuthorizationException
    {
        return get(domain, this.rp);
    }

    public String get(String domain, RequestParameters rp)
            throws EmptyApiKeyException, NetworkException, ApiEndpointException, ApiAuthorizationException
    {
        Objects.requireNonNull(domain);
        Objects.requireNonNull(rp);

        return this.doRequest(rp.getURL(), domain, rp.buildParametersMap());
    }

    public String[] get(String[] domains) throws Exception{
        return this.get(domains, this.rp);
    }

    public String[] get(String[] domains, RequestParameters rp) throws Exception {
        Objects.requireNonNull(domains);
        Objects.requireNonNull(rp);

        return this.doRequests(rp.getURL(), domains, rp.buildParametersMap());
    }

    public void setClient(OkHttpClient client) {
        Objects.requireNonNull(client);
        this.client = client;
    }

    protected String doRequest(String endpoint, String domain, Map<String, String> parameters)
            throws NetworkException, ApiEndpointException, ApiAuthorizationException {
        Request request = new Request.Builder()
                .url(this.buildUrl(endpoint, domain, parameters))
                .build();
        try {
            Response response = this.client.newCall(request).execute();
            if (response.code() < 500) {
                String body = response.body().string();
                if (response.code() == 401) {
                    throw new ApiAuthorizationException(body);
                }
                if (body.contains("WhoisRecord") || body.contains("ErrorMessage")) {
                    return body;
                }
            }
            logger.error("API request failed with the following result: " + response.toString());
            throw new ApiEndpointException("Request failed with the following code: " + response.code());
        } catch (IOException exception) {
            logger.error("API request failed: " + exception.getMessage());
            throw new NetworkException("Could not complete the request for domain: " + domain, exception);
        }
    }

    protected String[] doRequests(String endpoint, String[] domains, Map<String, String> parameters) throws Exception {
        int retries = this.retries;
        String[] domainsToHandle = domains;
        ArrayList<String> failedDomains = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();

        this.client.dispatcher().setMaxRequests(this.poolSize);

        for(int i = 0; i <= retries; i++) {
            CountDownLatch countDownLatch = new CountDownLatch(domainsToHandle.length);
            Request.Builder builder = new Request.Builder();
            for (String domain: domainsToHandle) {
                Objects.requireNonNull(domain);
                Request request = builder
                        .url(this.buildUrl(endpoint, domain, parameters))
                        .build();
                try {
                    int finalI = i;
                    this.client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            logger.error("Failed request: " + domain + " Error: " + e.getMessage());
                            failedDomains.add(domain);
                            countDownLatch.countDown();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String body = response.body().string();
                            int code = response.code();
                            response.close();
                            if (code >= 200 && code < 300) {
                                if (body.contains("ErrorMessage") && finalI < retries) {
                                    logger.error("Error message: " + body);
                                    failedDomains.add(domain);
                                    countDownLatch.countDown();
                                    return;
                                }
                                if (body.contains("INCOMPLETE_DATA") && finalI < retries) {
                                    logger.warn("Got INCOMPLETE_DATA error for domain: " + domain);
                                    failedDomains.add(domain);
                                    countDownLatch.countDown();
                                    return;
                                }
                                result.add(body);
                                countDownLatch.countDown();
                            } else if (code >= 300) {
                                if (body.contains("ErrorMessage")) {
                                    result.add(body);
                                }
                                client.dispatcher().cancelAll();
                                countDownLatch.countDown();
                            }
                        }
                    });
                } catch (Exception e) {
                    throw e;
                }
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException exception) {
                logger.error(exception.getMessage());
            }
            this.client.dispatcher().cancelAll();
            domainsToHandle = failedDomains.toArray(new String[0]);
            failedDomains.clear();
        }
        return result.toArray(new String[0]);
    }

    protected HttpUrl buildUrl(String endpoint,  String domain, Map<String, String> parameters) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(endpoint).newBuilder();
        urlBuilder.addQueryParameter(HttpClient.DOMAIN_NAME_PARAMETER, domain);
        parameters.forEach((key, value) -> urlBuilder.addQueryParameter(key, value));
        return urlBuilder.build();
    }

    protected OkHttpClient buildClient(
            int connectTimeout, TimeUnit ctu, int writeTimeout, TimeUnit wtu, int readTimeout, TimeUnit rtu) {
        Objects.requireNonNull(ctu);
        Objects.requireNonNull(wtu);
        Objects.requireNonNull(rtu);
        return new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, ctu)
                .readTimeout(readTimeout, rtu)
                .writeTimeout(writeTimeout, wtu)
                .build();
    }

    public void setRetries(int retries) {
        if (retries >= 0 && retries <= maxRetries) {
            this.retries = retries;
        } else {
            throw new IllegalArgumentException("Value should be not less than 0 and not greater than " + maxRetries);
        }
    }

    public int getRetries() {
        return this.retries;
    }

    public void setPoolSize(int poolSize) {
        if (poolSize > 0 && poolSize <= maxAsyncPoolSize) {
            this.poolSize = poolSize;
        } else {
            throw new IllegalArgumentException("Value should be greater than 0 and less or equal to " + maxAsyncPoolSize);
        }
    }

    public int getPoolSize() {
        return this.poolSize;
    }

    public void forceShutdown() {
        this.client.dispatcher().executorService().shutdown();
    }
}
