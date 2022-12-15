package nl.romano.moeubels.utils;

import java.util.Date;

public class ExpiryDate {
    public static Date getAccessTokenDate() {
        return new Date(System.currentTimeMillis() + 10 * 60 * 1000);
    }

    public static Date getRefreshTokenDate() {
        return new Date(System.currentTimeMillis() + 3600 * 60 * 1000);
    }
}
