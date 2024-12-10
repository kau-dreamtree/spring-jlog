package dreamtree.jlog.filter;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class MdcFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        String requestId = httpReq.getHeader("X-Request-ID");
        if (Objects.isNull(requestId) || requestId.isEmpty()) {
            requestId = length8RandomUUID();
        }
        MDC.put("requestId", requestId);
        chain.doFilter(request, response);
        MDC.clear();
    }

    private String length8RandomUUID() {
        return UUID.randomUUID()
                .toString()
                .substring(0, 8);
    }
}
