package pt.andremartins.workshop.wiremock.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Problem> handleError(Exception ex) {
        String msg = ex.getMessage();
        StringBuilder sb = new StringBuilder();
        if (msg != null) {
            sb.append(msg).append('\n');
        }
        sb.append(stackTraceToString(ex));
        return ResponseEntity.internalServerError().body(
                new Problem(ex.getClass().getSimpleName(), sb.toString())
        );
    }

    private static String stackTraceToString(Exception ex) {
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            ex.printStackTrace(pw);
            pw.flush();
            return sw.toString();
        } catch (IOException ioEx) {
            log.warn("Failed to get stacktrace", ioEx);
            return null;
        }
    }
}
