package nl.romano.moeubels.controller;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Responses {
    private static String jsonStatus(String status) {
        JSONObject json = new JSONObject();
        json.put("status", status);
        return json.toString();
    }

    public static ResponseEntity<String> jsonOkResponseEntity() {
        return new ResponseEntity<String>(
                jsonStatus("200"),
                new HttpHeaders(),
                HttpStatus.OK
        );
    }


    public static ResponseEntity<String> jsonNotFoundResponseEntity() {
        return new ResponseEntity<String>(
                jsonStatus("404"),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND
        );
    }

    public static <T> ResponseEntity<T> ResponseEntityOk(T response) {
        return new ResponseEntity<T>(
                response,
                new HttpHeaders(),
                HttpStatus.OK
        );
    }
}
