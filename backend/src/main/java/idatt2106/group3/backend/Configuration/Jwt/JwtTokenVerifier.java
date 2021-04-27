package idatt2106.group3.backend.Configuration.Jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import idatt2106.group3.backend.Model.UserSecurityDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * Filter for verifying a token given in request header
 */
public class JwtTokenVerifier extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenVerifier.class);

    /**
     * Verifies the JWT token from request, and returns a 403 if it has expired
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // If request tries to create user, it will not try to verify token, but continue the through filters until it gets to registration endpoint
        if(request.getRequestURI().equals("/api/v1/users") && request.getMethod().equals("POST")){ 
            filterChain.doFilter(request, response); 
            return;
        }
        String token = request.getHeader("Authorization");
        try{
                //Parses and verifies token
                Jws<Claims> claimsJWs = Jwts.parserBuilder()
                    .setSigningKey(JwtSigningKey.getInstance())
                    .build().parseClaimsJws(token);
                Claims body = claimsJWs.getBody();

                String username = body.getSubject();

                int userId = (Integer)body.get("userId");
                
                List<Map<String, String>> authoritiesMapping = (List<Map<String, String>>) body.get("authorities");

                List<GrantedAuthority> grantedAuthorities = authoritiesMapping.stream()
                .map(auth -> new SimpleGrantedAuthority(auth.get("authority"))).collect(Collectors.toList());

                UserSecurityDetails userSecurityDetails = new UserSecurityDetails("", username, userId, grantedAuthorities);

                // Creates an authentication from the JWT token
                Authentication authentication = new UsernamePasswordAuthenticationToken(userSecurityDetails, token, grantedAuthorities);

                // Sets authentication
                SecurityContextHolder.getContext().setAuthentication(authentication);


            }catch(JwtException e){
                LOGGER.warn("Something went wrong trying to verify JWT token: {}\nException message: {}", token, e.getMessage(),e.fillInStackTrace());
            }
            // Sends the request to the next filter, which will be exception-handler and then the controller
            filterChain.doFilter(request, response);
        
    }
    
}
