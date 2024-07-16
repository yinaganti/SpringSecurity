package com.example.security.filter;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.security.model.User;

public class MyUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private String roles;
	public MyUserDetails(User user) {
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.roles = user.getRoles();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.stream(roles.split(",")).map(role-> role = "ROLE_".concat(role))
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return new BCryptPasswordEncoder().encode(this.password);
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

}
