package br.com.vivo.sfclient.domain.salesforce.valueObjects;

public class RecordType {

    private final String name;

    public RecordType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
