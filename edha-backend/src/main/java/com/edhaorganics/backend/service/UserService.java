package com.edhaorganics.backend.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import com.edhaorganics.backend.beans.EdhaUser;
import com.edhaorganics.backend.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private MailService mailService;

	public EdhaUser register(@Valid EdhaUser user, String principalUser) {
		if (principalUser != null && checkUserName(principalUser)) {
			EdhaUser creationUser = new EdhaUser();
			creationUser.setUsername(principalUser);
			user.setCreatedBy(creationUser);
		}
		user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getEmailId()));
		EdhaUser newUserCreated = userRepo.save(user);
		mailService.sendWelcomeMail(newUserCreated.getUsername());
		return newUserCreated;
	}

	public boolean checkUserName(String userName) {
		return userRepo.existsById(userName);
	}

	public List<EdhaUser> listUsers() {
		return userRepo.findAll();
	}

	public EdhaUser getUser(String userName) {
		return userRepo.findTop1ByUsername(userName);
	}

	public List<EdhaUser> getActiveUsers() {
		return userRepo.findByActive(true);
	}

	public EdhaUser saveEditedUser(@Valid EdhaUser user) {
		EdhaUser userFromDb = getUser(user.getUsername());
		if (userFromDb != null) {
			user.setPassword(userFromDb.getPassword());
		}
		return userRepo.save(user);
	}

	public EdhaUser updatePassword(String name, String newPwd) {
		EdhaUser userFromDb = getUser(name);
		userFromDb.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(newPwd));
		return userRepo.save(userFromDb);
	}
}
