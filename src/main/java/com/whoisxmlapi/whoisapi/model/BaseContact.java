package com.whoisxmlapi.whoisapi.model;

import com.whoisxmlapi.whoisapi.util.OptionalBuilder;

import java.util.Optional;

public abstract class BaseContact {
    protected String name;
    protected String organization;
    protected String street1;
    protected String street2;
    protected String city;
    protected String state;
    protected String postalCode;
    protected String country;
    protected String email;
    protected String telephone;
    protected String rawText;

    public Optional<String> getName() {
        return OptionalBuilder.buildOptionalString(this.name);
    }

    public Optional<String> getOrganization() {
        return OptionalBuilder.buildOptionalString(this.organization);
    }

    public Optional<String> getStreet1() {
        return OptionalBuilder.buildOptionalString(this.street1);
    }

    public Optional<String> getStreet2() {
        return OptionalBuilder.buildOptionalString(this.street2);
    }

    public Optional<String> getCity() {
        return OptionalBuilder.buildOptionalString(this.city);
    }

    public Optional<String> getState() {
        return OptionalBuilder.buildOptionalString(this.state);
    }

    public Optional<String> getPostCode() {
        return OptionalBuilder.buildOptionalString(this.postalCode);
    }

    public Optional<String> getCountry() {
        return OptionalBuilder.buildOptionalString(this.country);
    }

    public Optional<String> getEmail() {
        return OptionalBuilder.buildOptionalString(this.email);
    }

    public Optional<String> getTelephone() {
        return OptionalBuilder.buildOptionalString(this.telephone);
    }

    public Optional<String> getRawText() {
        return OptionalBuilder.buildOptionalString(this.rawText);
    }
}