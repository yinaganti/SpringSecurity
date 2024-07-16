package com.example.security.filter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.model.User;
import com.example.security.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repo.finByUserName(username);
		user.orElseThrow(()->new RuntimeException("user not found"));
		Optional<UserDetails> userDetails = Optional.ofNullable(user.map(MyUserDetails::new).get());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return userDetails.filter(ud->encoder.matches(user.get().getPassword(), ud.getPassword()))
							.orElseThrow(()-> new RuntimeException("credentails are invalid"));
	}

}
