package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("LoginUserService")
public class LoginUserService implements UserDetailsService{

	@Autowired
	private LoginUserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//リポジトリからUserDetailsを取得
		UserDetails user=repository.selectOne(username);
		return user;
	}



}
