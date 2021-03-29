package com.whoisxmlapi.whoisapi.model;

import com.whoisxmlapi.whoisapi.util.OptionalBuilder;

import java.util.ArrayList;
import java.util.Optional;

public class NameServers {
    protected ArrayList<String> hostNames;
    protected ArrayList<String> ips;
    protected String rawText;

    public ArrayList<String> getIps() {
        return ips;
    }

    public ArrayList<String> getHostNames() {
        return hostNames;
    }

    public Optional<String> getRawText() {
        return OptionalBuilder.buildOptionalString(this.rawText);
    }
}
