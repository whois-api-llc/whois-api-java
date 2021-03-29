package com.whoisxmlapi.whoisapi;

import com.whoisxmlapi.whoisapi.exception.*;
import com.whoisxmlapi.whoisapi.json.ErrorMessageParser;
import com.whoisxmlapi.whoisapi.json.WhoisRecordParser;
import com.whoisxmlapi.whoisapi.model.*;
import com.whoisxmlapi.whoisapi.net.HttpClient;
import okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.Objects;

public class ApiClient {
    protected HttpClient client;
    protected RequestParameters rp;

    public ApiClient(String apiKey) {
        Objects.requireNonNull(apiKey);
        this.rp = new RequestParameters(apiKey);
        this.client = new HttpClient(this.rp);
    }

    public ApiClient(String apiKey, NetworkTimeouts networkTimeouts) {
        Objects.requireNonNull(apiKey);
        Objects.requireNonNull(networkTimeouts);
        this.rp = new RequestParameters(apiKey);
        this.client = new HttpClient(this.rp, networkTimeouts);
    }

    public ApiClient(String apiKey, OkHttpClient okHttpClient) {
        Objects.requireNonNull(apiKey);
        Objects.requireNonNull(okHttpClient);
        this.rp = new RequestParameters(apiKey);
        this.client = new HttpClient(this.rp);
        this.client.setClient(okHttpClient);
    }

    public ApiClient(RequestParameters rp) {
        Objects.requireNonNull(rp);
        this.rp = rp;
        this.client = new HttpClient(this.rp);
    }

    public ApiClient(RequestParameters rp, NetworkTimeouts networkTimeouts) {
        Objects.requireNonNull(rp);
        Objects.requireNonNull(networkTimeouts);
        this.rp = rp;
        this.client = new HttpClient(this.rp, networkTimeouts);
    }

    protected BaseRecord parseRecord(String record) throws UnparsableRecordException {
        if (record.contains("WhoisRecord")) {
            return WhoisRecordParser.parse(record);
        }
        if (record.contains("ErrorMessage")) {
            return ErrorMessageParser.parse(record);
        }
        throw new UnparsableRecordException(record);
    }

    protected String handleErrorResponse(String response) throws ApiErrorMessageException {
        if (response.contains("WhoisRecord")) {
            return response;
        }
        ErrorMessage error = ErrorMessageParser.parse(response);
        String msg = "";
        if (error.getErrorCode().isPresent()) {
            msg += "Code: " + error.getErrorCode().get();
        }
        if (error.getMsg().isPresent()) {
            msg += " Message: " + error.getMsg().get();
        }
        throw new ApiErrorMessageException(msg, error);
    }

    public WhoisRecord getWhois(String domain)
            throws EmptyApiKeyException, NetworkException, ApiEndpointException, ApiErrorMessageException, ApiAuthorizationException {
        Objects.requireNonNull(domain);
        RequestParameters temp = new RequestParameters(this.rp);
        temp.setOutputFormat(HttpClient.ResponseFormat.JSON.format());
        return WhoisRecordParser.parse(this.handleErrorResponse(this.client.get(domain, temp)));
    }

    public WhoisRecord getWhois(String domain, RequestParameters rp)
            throws EmptyApiKeyException, NetworkException, ApiEndpointException, ApiErrorMessageException, ApiAuthorizationException {
        Objects.requireNonNull(domain);
        Objects.requireNonNull(rp);
        rp.setOutputFormat(HttpClient.ResponseFormat.JSON.format());
        return WhoisRecordParser.parse(this.handleErrorResponse(this.client.get(domain, rp)));
    }

    public String getRawResponse(String domain)
            throws EmptyApiKeyException, NetworkException, ApiEndpointException, ApiAuthorizationException {
        Objects.requireNonNull(domain);
        return this.client.get(domain);
    }

    public String getRawResponse(String domain, RequestParameters rp)
            throws EmptyApiKeyException, NetworkException, ApiEndpointException, ApiAuthorizationException {
        Objects.requireNonNull(domain);
        Objects.requireNonNull(rp);
        return this.client.get(domain, rp);
    }

    public String getRawResponse(String domain, HttpClient.ResponseFormat dataFormat)
            throws EmptyApiKeyException, NetworkException, ApiEndpointException, ApiAuthorizationException {
        Objects.requireNonNull(domain);
        Objects.requireNonNull(dataFormat);
        RequestParameters temp = new RequestParameters(this.rp);
        temp.setOutputFormat(dataFormat.format());
        return this.client.get(domain, temp);
    }

    public BaseRecord[] getWhois(String[] domains) throws Exception {
        Objects.requireNonNull(domains);
        String[] records = this.client.get(domains);
        ArrayList<BaseRecord> recordsList = new ArrayList<>();
        for (String record : records) {
            recordsList.add(this.parseRecord(record));
        }
        return recordsList.toArray(new BaseRecord[0]);
    }

    public BaseRecord[] getWhois(String[] domains, RequestParameters rp) throws Exception {
        Objects.requireNonNull(domains);
        Objects.requireNonNull(rp);
        String[] records = this.client.get(domains, rp);
        ArrayList<BaseRecord> recordsList = new ArrayList<>();
        for (String record : records) {
            recordsList.add(this.parseRecord(record));
        }
        return recordsList.toArray(new BaseRecord[0]);
    }

    public String[] getRawResponse(String[] domains) throws Exception {
        Objects.requireNonNull(domains);
        return this.client.get(domains);
    }

    public String[] getRawResponse(String[] domains, RequestParameters rp) throws Exception {
        Objects.requireNonNull(domains);
        Objects.requireNonNull(rp);
        return this.client.get(domains, rp);
    }

    public String[] getRawResponse(String[] domains, HttpClient.ResponseFormat dataFormat) throws Exception {
        Objects.requireNonNull(domains);
        Objects.requireNonNull(dataFormat);
        RequestParameters temp = new RequestParameters(this.rp);
        temp.setOutputFormat(dataFormat.format());
        return this.client.get(domains, temp);
    }

    public void setRetries(int retries) {
        this.client.setRetries(retries);
    }

    public int getRetries() {
        return this.client.getRetries();
    }

    public void setPoolSize(int poolSize) {
        this.client.setPoolSize(poolSize);
    }

    public int getPoolSize() {
        return this.client.getPoolSize();
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.client.setClient(okHttpClient);
    }

    public void forceShutdown() {
        this.client.forceShutdown();
    }
}
