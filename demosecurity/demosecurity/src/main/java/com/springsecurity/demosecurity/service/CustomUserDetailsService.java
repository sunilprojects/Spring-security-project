package com.springsecurity.demosecurity.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.springsecurity.demosecurity.entity.AppUser;
import com.springsecurity.demosecurity.repository.UserRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService{//uds is interface
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		AppUser user=userRepository.findByUsername(username)
				.orElseThrow(()->new UsernameNotFoundException("usernot found"));
		return User.withUsername(user.getUsername())
				   .password(user.getPassword())
				   .roles(user.getRole())
				   .build();
	}





}
