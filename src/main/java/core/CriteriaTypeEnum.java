package core;

public enum CriteriaTypeEnum {
    SEARCH("search"),
    STAT("stat"),
    ERROR("error");

    String value;

    public String getValue() {
        return value;
    }

    CriteriaTypeEnum(String value) {
        this.value = value;
    }
}
