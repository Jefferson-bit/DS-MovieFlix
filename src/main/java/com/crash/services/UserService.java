package com.crash.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crash.entities.User;
import com.crash.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repository.findByEmail(email);
		if(user == null) {
			LOG.error("User not found: " + email);
			throw new UsernameNotFoundException("User not found: " + email);
		}
		LOG.info("User Found: " + email);
		return user;
	}

}
