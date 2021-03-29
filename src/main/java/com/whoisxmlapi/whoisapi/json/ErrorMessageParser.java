package com.whoisxmlapi.whoisapi.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.whoisxmlapi.whoisapi.model.ErrorMessage;

public class ErrorMessageParser {
    public static ErrorMessage parse(String json) {
        JsonElement element = JsonParser.parseString(json);
        return new Gson().fromJson(element.getAsJsonObject().get("ErrorMessage"), ErrorMessage.class);
    }
}
