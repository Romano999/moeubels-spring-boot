package nl.romano.moeubels.utils;

public enum Roles {
    ADMINISTRATOR("Administrator"),
    ACTOR("Actor");

    public final String label;

    Roles(String label) {
        this.label = label;
    }
}
