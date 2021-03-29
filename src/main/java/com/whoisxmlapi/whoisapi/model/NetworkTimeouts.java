package com.whoisxmlapi.whoisapi.model;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class NetworkTimeouts {
    private int connectTimeout = 5;
    private TimeUnit connectTimeUnits = TimeUnit.SECONDS;
    private int writeTimeout = 5;
    private TimeUnit writeTimeUnits = TimeUnit.SECONDS;
    private int readTimeout = 45;
    private TimeUnit readTimeUnits = TimeUnit.SECONDS;

    public NetworkTimeouts() {
    }

    public NetworkTimeouts(
            int connectTimeout, TimeUnit connectTimeUnits,
            int writeTimeout, TimeUnit writeTimeUnits,
            int readTimeout, TimeUnit readTimeUnits) {
        this.connectTimeout = connectTimeout;
        this.connectTimeUnits = connectTimeUnits;
        this.writeTimeout = writeTimeout;
        this.writeTimeUnits = writeTimeUnits;
        this.readTimeout = readTimeout;
        this.readTimeUnits = readTimeUnits;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout, TimeUnit tu) {
        Objects.requireNonNull(tu);
        this.connectTimeout = connectTimeout;
        this.connectTimeUnits = tu;
    }

    public TimeUnit getConnectTimeUnits() {
        return connectTimeUnits;
    }

    public void setConnectTimeUnits(TimeUnit connectTimeUnits) {
        Objects.requireNonNull(connectTimeUnits);
        this.connectTimeUnits = connectTimeUnits;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout, TimeUnit tu) {
        Objects.requireNonNull(tu);
        this.writeTimeout = writeTimeout;
        this.writeTimeUnits = tu;
    }

    public TimeUnit getWriteTimeUnits() {
        return writeTimeUnits;
    }

    public void setWriteTimeUnits(TimeUnit writeTimeUnits) {
        Objects.requireNonNull(writeTimeUnits);
        this.writeTimeUnits = writeTimeUnits;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setReadTimeout(int readTimeout, TimeUnit tu) {
        Objects.requireNonNull(tu);
        this.readTimeout = readTimeout;
        this.readTimeUnits = tu;
    }

    public TimeUnit getReadTimeUnits() {
        return readTimeUnits;
    }

    public void setReadTimeUnits(TimeUnit readTimeUnits) {
        Objects.requireNonNull(readTimeUnits);
        this.readTimeUnits = readTimeUnits;
    }
}
