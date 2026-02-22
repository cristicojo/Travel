package travel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BorderException.class)
    private ResponseEntity<Map<String, String>> handleBorderException(BorderException e) {

        Map<String, String> map = new HashMap<>();
        map.put("error", e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CountryNotFoundException.class)
    private ResponseEntity<Map<String, String>> handleCountryNotFoundException(CountryNotFoundException e) {

        Map<String, String> map = new HashMap<>();
        map.put("error", e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}
