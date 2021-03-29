package com.whoisxmlapi.whoisapi.model;

import java.util.Optional;

public class Registrant extends BaseContact {
    protected String unparsable;

    @Override
    public Optional<String> getCountry() {
        throw new UnsupportedOperationException("There is no country.");
    }

    @Override
    public Optional<String> getEmail() {
        throw new UnsupportedOperationException("There is no email address.");
    }

    @Override
    public Optional<String> getTelephone() {
        throw new UnsupportedOperationException("There is no telephone number.");
    }

    public Optional<String> getUnparsable() {
        boolean isEmpty = this.unparsable == null || this.unparsable.isEmpty();
        return isEmpty ? Optional.empty() : Optional.of(this.unparsable);
    }
}
