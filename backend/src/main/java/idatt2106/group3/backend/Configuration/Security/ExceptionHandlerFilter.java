package idatt2106.group3.backend.Configuration.Security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
/**
 * Last filter in line for checking if any preceding filter has encountered any error/exception
 */
public class ExceptionHandlerFilter extends OncePerRequestFilter{

    @Autowired
    private ObjectMapper objectMapper;
    /**
     * Tries to continue through the filters, next step will be to the controller/endpoint
     * If exception it will send error
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch(RuntimeException ex){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(objectMapper.writeValueAsString(ex));
        }
        
    }
    
}
