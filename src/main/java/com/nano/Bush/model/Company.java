package com.nano.Bush.model;

import java.util.Optional;

public class Company {

    private final Optional<Integer> companyId;
    private final String name;
    private final String description;


    public Company(Optional<Integer> idCompany, String name, String description) {
        this.name = name;
        this.description = description;
        this.companyId = idCompany;
    }

    public Optional<Integer> getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

