package com.whoisxmlapi.whoisapi.model;

import java.time.ZonedDateTime;
import java.util.Optional;

import com.whoisxmlapi.whoisapi.util.OptionalBuilder;
import com.whoisxmlapi.whoisapi.util.DateParser;

public class Audit {
    protected String createdDate;
    protected String updatedDate;

    public Optional<String> getCreatedDateRawString() {
        return OptionalBuilder.buildOptionalString(this.createdDate);
    }

    public Optional<String> getUpdatedDateRawString() {
        return OptionalBuilder.buildOptionalString(this.updatedDate);
    }

    public Optional<ZonedDateTime> getCreatedDate() {
        Optional<String> date = this.getCreatedDateRawString();
        if (date.isPresent()) {
            return this.buildDateTimeObject(date.get());
        }
        return Optional.empty();
    }

    public Optional<ZonedDateTime> getUpdatedDate() {
        Optional<String> date = this.getUpdatedDateRawString();
        if (date.isPresent()) {
            return this.buildDateTimeObject(date.get());
        }
        return Optional.empty();
    }

    protected Optional<ZonedDateTime> buildDateTimeObject(String dateTime) {
        return Optional.ofNullable(DateParser.parseNormalizedDate(dateTime));
    }
}
