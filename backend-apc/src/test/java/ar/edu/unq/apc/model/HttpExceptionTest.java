package ar.edu.unq.apc.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class HttpExceptionTest {

    private String anyMessage = "message";
    private HttpStatus anyHttpStatus = HttpStatus.NOT_FOUND;


    @Test
    void testGetHttpStatus() {
        HttpException exception = new HttpException("Not Found", anyHttpStatus);

        assertEquals(exception.getMessage(), "Not Found");

    }

    @Test
    void contructorTest() {
        HttpException exception = new HttpException(anyMessage, anyHttpStatus);

        assertTrue((exception.getMessage() != null) && (exception.getHttpStatus() != null));
    }
}
