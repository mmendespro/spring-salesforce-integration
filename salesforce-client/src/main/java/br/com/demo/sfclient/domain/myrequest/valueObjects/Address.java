package br.com.demo.sfclient.domain.myrequest.valueObjects;

public class Address {

    private final String streetAddress;
    private final String locality;
    private final String region;
    private final String country;
    private final String postalCode;

    public Address() {
        this.streetAddress = null;
        this.locality = null;
        this.region = null;
        this.country = null;
        this.postalCode = null;
    }

    public Address(String streetAddress, String locality, String region, String country, String postalCode) {
        this.streetAddress = streetAddress;
        this.locality = locality;
        this.region = region;
        this.country = country;
        this.postalCode = postalCode;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getLocality() {
        return locality;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }
    
}