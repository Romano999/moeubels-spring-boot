package nl.romano.moeubels.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.romano.moeubels.utils.ExpiryDate;
import nl.romano.moeubels.utils.Roles;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Following data found: " + username + " and " + password);
        log.info("Following data found in header: " + request.getHeader("username") + " and " + request.getHeader("password"));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        User actor = (User) authentication.getPrincipal();
        List<String> authorities = actor.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String accessToken = JWT.create()
                .withSubject(actor.getUsername())
                .withExpiresAt(ExpiryDate.getAccessTokenDate())
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", actor.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(actor.getUsername())
                .withExpiresAt(ExpiryDate.getRefreshTokenDate())
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", actor.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        String role = authorities.get(1).replace("[", "").replace("]", "");
        String actorId = authorities.get(0);

        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        if (actorId.equals(Roles.ACTOR.label) || actorId.equals(Roles.ADMINISTRATOR.label)) {
            tokens.put("role", authorities.get(0));
            tokens.put("actorId", authorities.get(1).replace("[", "").replace("]", ""));
        } else {
            tokens.put("role", authorities.get(1).replace("[", "").replace("]", ""));
            tokens.put("actorId", authorities.get(0));
        }

        response.setContentType(APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    // Can be used to prevent brute force attacks
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
