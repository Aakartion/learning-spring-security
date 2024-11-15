package edu.miu.cs489.securitydemo.config;

import edu.miu.cs489.securitydemo.user.Permission;
import edu.miu.cs489.securitydemo.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtFilter jwtFilter;

    public SecurityConfiguration(AuthenticationProvider authenticationProvider,
                                 JwtFilter jwtFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(request->{
                    request.requestMatchers("/api/v1/auth/*")
                            .permitAll()
                            .requestMatchers("/api/v1/admin/**")
                            .hasRole(Role.ADMIN.name())
                            .requestMatchers("/api/v1/management/**")
                            .hasAnyRole(Role.ADMIN.name(), Role.MEMBER.name())
                            .requestMatchers("/api/v1/management/member-only")
                            .hasAnyRole("Role" + Permission.MEMBER_READ.getPermission(),
                                    "Role" + Permission.MEMBER_WRITE.getPermission())
                            .anyRequest()
                            .authenticated();
                })
                .authenticationProvider(authenticationProvider)
                .sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
