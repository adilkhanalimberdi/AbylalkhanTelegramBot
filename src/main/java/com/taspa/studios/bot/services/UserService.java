package com.taspa.studios.bot.services;

import com.taspa.studios.bot.entities.User;
import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User getUser(Long chatId) {
		return userRepository.findByChatId(chatId)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}

	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));
	}

	public User getUserByUsernameOrNull(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	public boolean isRegistered(Long chatId) {
		return userRepository.findByChatId(chatId).isPresent();
	}

	public void createUser(
			Long chatId,
			String username
	) {
		User user = User.builder()
				.chatId(chatId)
				.username(username)
				.isAdmin(false)
				.state(BotState.STARTED)
				.createdDate(LocalDateTime.now())
				.lastModified(LocalDateTime.now())
				.build();

		userRepository.save(user);
	}

	public void updateUser(
			Long chatId,
			Boolean isAdmin,
			String instagramUsername
	) {
		User user = getUser(chatId);
		user.setAdmin(isAdmin);
		user.setInstagramUsername(instagramUsername);
		user.setLastModified(LocalDateTime.now());

		userRepository.save(user);
	}

	public void toggleAdmin(Long chatId) {
		User user = getUser(chatId);
		user.setAdmin(!user.isAdmin());
		userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
