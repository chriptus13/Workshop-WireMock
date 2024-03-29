package pt.andremartins.workshop.wiremock.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String method = request.getMethod();
        String path = request.getRequestURI();
        log.info("Received {} {}", method, path);
        long start = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        long responseTime = System.currentTimeMillis() - start;
        HttpStatusCode status = HttpStatusCode.valueOf(response.getStatus());
        if (status.isError()) {
            log.error("Response {} {} - {} ({} ms)", method, path, status, responseTime);
        } else {
            log.info("Response {} {} - {} ({} ms)", method, path, status, responseTime);
        }
    }
}
