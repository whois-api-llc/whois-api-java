package com.whoisxmlapi.whoisapi.model;

import com.whoisxmlapi.whoisapi.exception.EmptyApiKeyException;
import com.whoisxmlapi.whoisapi.net.HttpClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestParameters {
    protected String apiKey;

    protected String URL = "https://www.whoisxmlapi.com/whoisserver/WhoisService";
    protected String outputFormat = "JSON";
    protected String preferFresh = "0";
    protected String da = "0";
    protected String ip = "0";
    protected String ipWhois = "0";
    protected String checkProxyData = "0";
    protected String thinWhois = "0";
    protected String ignoreRawTexts = "0";
    protected String parse = "0";
    protected String registryRawText = "";
    protected String registrarRawText = "";

    public RequestParameters(String apiKey) throws IllegalArgumentException{
        this.setApiKey(apiKey);
    }

    public RequestParameters(String apiKey, String URL) throws IllegalArgumentException {
        this.setApiKey(apiKey);
        this.setURL(URL);
    }

    public RequestParameters(RequestParameters rp) {
        Objects.requireNonNull(rp);
        this.apiKey = rp.apiKey;
        this.URL = rp.URL;
        this.outputFormat = rp.outputFormat;
        this.preferFresh = rp.preferFresh;
        this.da = rp.da;
        this.ip = rp.ip;
        this.ipWhois = rp.ipWhois;
        this.checkProxyData = rp.checkProxyData;
        this.thinWhois = rp.thinWhois;
        this.ignoreRawTexts = rp.ignoreRawTexts;
        this.parse = rp.parse;
        this.registryRawText = rp.registryRawText;
        this.registrarRawText = rp.registrarRawText;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) throws IllegalArgumentException {
        Objects.requireNonNull(apiKey);
        if (! apiKey.startsWith("at_")) {
            throw new IllegalArgumentException("Invalid API key specified");
        }
        if (apiKey.length() < 32) {
            throw new IllegalArgumentException("Invalid API key specified");
        }
        this.apiKey = apiKey;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) throws IllegalArgumentException {
        Objects.requireNonNull(URL);
        this.URL = URL;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) throws IllegalArgumentException {
        Objects.requireNonNull(outputFormat);
        if (! (outputFormat.toUpperCase().equals("JSON") || outputFormat.toUpperCase().equals("XML"))) {
            throw new IllegalArgumentException("outputFormat should be JSON or XML");
        }
        this.outputFormat = outputFormat.toUpperCase();
    }

    public void setOutputFormat(HttpClient.ResponseFormat format) {
        Objects.requireNonNull(format);
        this.setOutputFormat(format.format());
    }

    public String getPreferFresh() {
        return preferFresh;
    }

    public void setPreferFresh(String preferFresh) throws IllegalArgumentException {
        Objects.requireNonNull(preferFresh);
        if (! (preferFresh.equals("0") || preferFresh.equals("1"))) {
            throw new IllegalArgumentException("preferFresh should ne 0 or 1");
        }
        this.preferFresh = preferFresh;
    }

    public String getDa() {
        return da;
    }

    public void setDa(String da) throws IllegalArgumentException {
        Objects.requireNonNull(da);
        if (! (da.equals("0") || da.equals("1") || da.equals("2"))) {
            throw new IllegalArgumentException("da should be 0|1|2");
        }
        this.da = da;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) throws IllegalArgumentException {
        Objects.requireNonNull(ip);
        if (! (ip.equals("0") || ip.equals("1"))) {
            throw new IllegalArgumentException("ip should be 0 or 1");
        }
        this.ip = ip;
    }

    public String getIpWhois() {
        return ipWhois;
    }

    public void setIpWhois(String ipWhois) throws IllegalArgumentException {
        Objects.requireNonNull(ipWhois);
        if (! (ipWhois.equals("0") || ipWhois.equals("1"))) {
            throw new IllegalArgumentException("ipWhois should be 0 or 1");
        }
        this.ipWhois = ipWhois;
    }

    public String getCheckProxyData() {
        return checkProxyData;
    }

    public void setCheckProxyData(String checkProxyData) throws IllegalArgumentException {
        Objects.requireNonNull(checkProxyData);
        if (! (checkProxyData.equals("0") || checkProxyData.equals("1"))) {
            throw new IllegalArgumentException("checkProxyData should be 0 or 1");
        }
        this.checkProxyData = checkProxyData;
    }

    public String getThinWhois() {
        return thinWhois;
    }

    public void setThinWhois(String thinWhois) throws IllegalArgumentException {
        Objects.requireNonNull(thinWhois);
        if (! (thinWhois.equals("0") || thinWhois.equals("1"))) {
            throw new IllegalArgumentException("thinWhois should be 0 or 1");
        }
        this.thinWhois = thinWhois;
    }

    public String getIgnoreRawTexts() {
        return ignoreRawTexts;
    }

    public void setIgnoreRawTexts(String ignoreRawTexts) throws IllegalArgumentException {
        Objects.requireNonNull(ignoreRawTexts);
        this.ignoreRawTexts = ignoreRawTexts;
    }

    public String getParse() {
        return parse;
    }

    public void setParse(String parse) throws IllegalArgumentException {
        Objects.requireNonNull(parse);
        if (! (parse.equals("0") || parse.equals("1"))) {
            throw new IllegalArgumentException("parse should be 0 or 1");
        }
        this.parse = parse;
    }

    public String getRegistryRawText() {
        return registryRawText;
    }

    public void setRegistryRawText(String registryRawText) {
        Objects.requireNonNull(registryRawText);
        this.registryRawText = registryRawText;
    }

    public String getRegistrarRawText() {
        return registrarRawText;
    }

    public void setRegistrarRawText(String registrarRawText) {
        Objects.requireNonNull(registrarRawText);
        this.registrarRawText = registrarRawText;
    }

    public Map<String, String> buildParametersMap() throws EmptyApiKeyException {
        Map<String, String> parameters = new HashMap<>();
        if (this.apiKey == null) {
            throw new EmptyApiKeyException();
        }
        parameters.put("apiKey", this.apiKey);
        parameters.putAll(Stream.of(new String[][] {
                {"outputFormat", this.outputFormat},
                {"preferFresh", this.preferFresh},
                {"da", this.da},
                {"ip", this.ip},
                {"ipWhois", this.ipWhois},
                {"checkProxyData", this.checkProxyData},
                {"thinWhois", this.thinWhois},
                {"ignoreRawTexts", this.ignoreRawTexts},
        }).collect(Collectors.toMap(data -> data[0], data -> data[1])));
        if (this.parse.equals("1")) {
            parameters.put("_parse", this.parse);
            if (this.registryRawText.length() > 2)
                parameters.put("registryRawText", this.registryRawText);
            if (this.registrarRawText.length() > 2)
                parameters.put("registrarRawText", this.registrarRawText);
        }

        return parameters;
    }
}