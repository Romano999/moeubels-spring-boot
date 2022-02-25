package nl.romano.moeubels.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Responses {
    private static String jsonStatus(String status) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("status", status);
        return node.toString();
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

    public static ResponseEntity<String> jsonNotFoundResponseEntityWithMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode status = mapper.createObjectNode();

        status.put("status" , "404");
        status.put("message", message);

        return new ResponseEntity<String>(
                status.toString(),
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
