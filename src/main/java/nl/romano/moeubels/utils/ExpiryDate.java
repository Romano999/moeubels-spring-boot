package nl.romano.moeubels.utils;

import java.util.Date;

public class ExpiryDate {
    public static Date getAccessTokenDate() {
        return new Date(System.currentTimeMillis() + 100 * 600);
    }

    public static Date getRefreshTokenDate() {
        return new Date(System.currentTimeMillis() + 100 * 600 * 10000);
    }
}
