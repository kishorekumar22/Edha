package com.edhaorganics.backend.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

@Service
public class EdhaUserDetailsService implements UserDetailsService {
	private static Map<String, UserObject> users = new HashMap<>();

	@Autowired
	UserService userService;

	public EdhaUserDetailsService() {
		// in a real application, instead of using local data,
		// we will find user details by some other means e.g. from an external
		// system
		users.put("766", new UserObject("766", PasswordEncoderFactories.createDelegatingPasswordEncoder()
				.encode("766"), "ADMIN"));
	}

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
		if(users.get(username) != null){
			return User.withUsername(users.get(username).name)
	                .password(users.get(username).password)
	                .roles(users.get(username).role).build();
		}else if(userService.checkUserName(username)){
			com.edhaorganics.backend.beans.EdhaUser u = userService.getUser(username);
			return User.withUsername(u.getUsername())
                    .password(u.getPassword())
                    .roles(u.getRole().name()).build();
		}
        throw new UsernameNotFoundException("User not found by name: " + username);
        	
    }

	private static class UserObject {
		private String name;
		private String password;
		private String role;

		public UserObject(String name, String password, String role) {
			this.name = name;
			this.password = password;
			this.role = role;
		}
	}
}