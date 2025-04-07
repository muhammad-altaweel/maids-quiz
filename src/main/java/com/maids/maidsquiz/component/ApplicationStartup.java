package com.maids.maidsquiz.component;

import com.maids.maidsquiz.data.User;
import com.maids.maidsquiz.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public ApplicationStartup(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("Add two user admin with password: 12345678 and user with password: simple");
		User admin = new User();
		admin.setPassword(passwordEncoder.encode("12345678"));
		admin.setUsername("admin");
		User user = new User();
		user.setPassword(passwordEncoder.encode("simple"));
		user.setUsername("user");
		userRepository.save(admin);
		userRepository.save(user);
		System.out.println("Saved");
	}

}

