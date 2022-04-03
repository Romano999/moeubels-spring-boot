package nl.romano.moeubels.config;

import lombok.RequiredArgsConstructor;
import nl.romano.moeubels.filter.CustomAuthenticationFilter;
import nl.romano.moeubels.filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    //private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requiresChannel().antMatchers("**").requiresSecure();
        http.csrf().disable();
        http.cors().configurationSource(corsConfigurationSource());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Login related
        http.authorizeRequests().antMatchers("/login/**", "/token/refresh/**").permitAll();

        // Actor related
        http.authorizeRequests().antMatchers("/actors/**").hasAnyAuthority("Administrator");
        // Category related
        http.authorizeRequests().antMatchers(POST, "categories/**").hasAnyAuthority("Administrator");
        http.authorizeRequests().antMatchers(PUT, "categories/**").hasAnyAuthority("Administrator");
        http.authorizeRequests().antMatchers(DELETE, "categories/**").hasAnyAuthority("Administrator");
        http.authorizeRequests().antMatchers(PATCH, "categories/**").hasAnyAuthority("Administrator");
        http.authorizeRequests().antMatchers(TRACE, "categories/**").hasAnyAuthority("Administrator"); //
        // Product related
        http.authorizeRequests().antMatchers(POST, "products/**").hasAnyAuthority("Administrator");
        http.authorizeRequests().antMatchers(PUT, "products/**").hasAnyAuthority("Administrator");
        http.authorizeRequests().antMatchers(DELETE, "products/**").hasAnyAuthority("Administrator");
        http.authorizeRequests().antMatchers(PATCH, "products/**").hasAnyAuthority("Administrator");
        http.authorizeRequests().antMatchers(TRACE, "products/**").hasAnyAuthority("Administrator"); // Trace to prevent Cross Site Tracing
        // Favourites related
        http.authorizeRequests().antMatchers(POST, "products/**").hasAnyAuthority("Administrator");
        http.authorizeRequests().antMatchers(PUT, "products/**").hasAnyAuthority("Administrator");
        http.authorizeRequests().antMatchers(DELETE, "products/**").hasAnyAuthority("Administrator");
        http.authorizeRequests().antMatchers(PATCH, "products/**").hasAnyAuthority("Administrator");
        http.authorizeRequests().antMatchers(TRACE, "products/**").hasAnyAuthority("Administrator");
        // Review
        http.authorizeRequests().antMatchers(TRACE, "products/**").hasAnyAuthority("Administrator");
        // Shopping Cart

        // Role
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(List.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Accept"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
