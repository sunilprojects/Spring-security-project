package com.springsecurity.demosecurity.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.springsecurity.demosecurity.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity//Enables the Spring Security filter chain and allows
//you to customize security behavior in your application.
public class DemoSecurity {
	   @Autowired
	    private CustomUserDetailsService userDetailsService;


//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User
//            .withUsername("sunil")
//            .password(passwordEncoder().encode("1234"))
//            .roles("USER")
//            .build();
//
//        UserDetails admin = User
//            .withUsername("admin")
//            .password(passwordEncoder().encode("admin123"))//converts plain text to hashed passord
//            .roles("ADMIN")
//            .build();
//        System.out.println("password:"+passwordEncoder().encode("1234"));
//
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	 http
    	   .csrf(csrf -> csrf.disable())
    	   
         .authorizeHttpRequests(auth -> auth
             .requestMatchers("/login.html","/register.html","/register", "/css/**", "/js/**").permitAll()
             .requestMatchers("/admin-home/**").hasRole("ADMIN")
             .requestMatchers("/user-home").hasRole("USER")
             .anyRequest().authenticated()
         )
         .formLogin(form -> form
             .loginPage("/login.html") // using static login page
             .loginProcessingUrl("/login") // Spring handles POST /login
             .defaultSuccessUrl("/default", true) // we'll handle redirect logic here
             .permitAll()
         )
         .logout(logout -> logout
             .logoutUrl("/logout")
             .logoutSuccessUrl("/login.html")
             .permitAll()
         )
       //"Enable Basic Authentication eg: postman
        .authenticationProvider(authenticationProvider());
//        .httpBasic(Customizer.withDefaults()); 
    	 

       
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance(); // 👈 Plain text password support
         return new BCryptPasswordEncoder();
    }

    
}
