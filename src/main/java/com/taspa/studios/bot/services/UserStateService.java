package com.taspa.studios.bot.services;

import com.taspa.studios.bot.entities.User;
import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStateService {

	private final UserRepository userRepository;

	public BotState getState(Long chatId) {
		return userRepository.findByChatId(chatId)
				.map(User::getState)
				.orElse(BotState.NOT_STARTED);
	}

	public void setState(Long chatId, BotState state) {
		User user = userRepository.findByChatId(chatId)
				.orElseThrow(() -> new IllegalArgumentException("Chat id " + chatId + " does not exist"));

		user.setState(state);
		userRepository.save(user);
	}

}
