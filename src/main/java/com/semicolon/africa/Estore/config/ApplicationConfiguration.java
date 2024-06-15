package com.semicolon.africa.Estore.config;


import com.cloudinary.utils.ObjectUtils;
import com.semicolon.africa.Estore.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.modelmapper.ModelMapper;
import com.cloudinary.Cloudinary;

import java.util.Map;


@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
    @Value("${cloud.api.key}")
    private String API_KEY ;
    @Value("${cloud.api.name}")
    private String API_NAME;
    @Value("${cloud.api.secret}")
    private String API_SECRET;

    private final CustomUserDetailsService service;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> service.loadUserByUsername(username);
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public Cloudinary cloudinary(){
        Map<?,?> map = ObjectUtils.asMap(
                "cloud_name", API_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET
        );
        return new Cloudinary(map);
    }



    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userDetailsService());
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
}
