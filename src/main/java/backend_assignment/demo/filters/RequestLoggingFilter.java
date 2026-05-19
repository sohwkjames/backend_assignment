package backend_assignment.demo.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

// Largely boilerplate.
// We can write to DB from here as well if needed.
@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws java.io.IOException, jakarta.servlet.ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        logger.info("Incoming request: {} {} from {}",
//                httpRequest.getMethod(),
//                httpRequest.getRequestURI(),
//                httpRequest.getRemoteAddr());
//
//        chain.doFilter(request, response); 
//
////        long duration = System.currentTimeMillis() - startTime;
//
//        logger.info("Completed request: {} {} -> {} ",
//                httpRequest.getMethod(),
//                httpRequest.getRequestURI(),
//                httpResponse.getStatus()
//                );
//    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

    	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    	HttpServletResponse httpServletResponse = (HttpServletResponse) response;

    	ContentCachingRequestWrapper wrappedRequest =
    	        new ContentCachingRequestWrapper(httpServletRequest, 1024);

//        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpServletRequest);
    	
//    	ContentCachingRequestWrapper wrappedRequest =
//    	        new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

        long startTime = System.currentTimeMillis();

        chain.doFilter(wrappedRequest, wrappedResponse); // controller runs here

        long duration = System.currentTimeMillis() - startTime;

        String requestBody = new String(wrappedRequest.getContentAsByteArray());
        String responseBody = new String(wrappedResponse.getContentAsByteArray());

        logger.info("Method: {} | URI: {} | Request Body: {} | Response Status: {} | Response Body: {} | Duration: {} ms",
                wrappedRequest.getMethod(),
                wrappedRequest.getRequestURI(),
                requestBody.isBlank() ? "(empty)" : requestBody,
                wrappedResponse.getStatus(),
                responseBody.isBlank() ? "(empty)" : responseBody,
                duration);

        wrappedResponse.copyBodyToResponse(); // critical — sends the response back to the client
    }

}
