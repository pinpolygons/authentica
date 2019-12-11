package com.pin.springjwt.security.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pin.springjwt.models.User;
import com.pin.springjwt.repository.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService{
	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User not found with username: "+ username));
		return UserDetailsImpl.build(user);
	}
}
/*Trong code tren, chung ta lay day du doi tuong User tuy chinh su dung UserRepository, sau khi cung ta
 * xau dung mot UserDetails su dung phuong thuc static build()*/
