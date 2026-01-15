package com.taspa.studios.bot.handlers.state;

import com.taspa.studios.bot.bot.BotProperties;
import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.UserService;
import com.taspa.studios.bot.services.UserStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
@Slf4j
public class WaitingPasswordStateHandler implements StateHandler {

	private final UserService userService;
	private final MessageService messageService;
	private final UserStateService userStateService;
	private final BotProperties botProperties;

	@Override
	public BotState getState() {
		return BotState.WAITING_FOR_PASSWORD;
	}

	@Override
	public void handle(Long userId, Command command, Update update) {
		if (command == Command.BACK) {
			userStateService.setState(userId, BotState.STARTED);
			messageService.sendText(userId, "Now you are in default mode.");
			return;
		}

		// first, we delete last message in order to increase the security
		deleteMessage(userId, update.getMessage().getMessageId());

		// then we check if the password is correct
		String input = update.getMessage().getText().trim();
		String expected = botProperties.getAdminPassword();
		if (expected.equals(input)) {
			userService.toggleAdmin(userId);
			userStateService.setState(userId, BotState.ADMIN_VERIFIED);
			messageService.sendText(
					userId,
					"""
							Welcome to Admin Panel!\
							
							Use /admin_help command to see what you can do in admin mode.\
							
							Use /exit command to exit."""
			);
			return;
		}

		messageService.sendText(userId, "Password is incorrect, please try again.");
	}

	public void deleteMessage(Long userId, Integer messageId) {
		try {
			messageService.delete(userId, messageId);
		} catch (Exception e) {
			log.warn("Failed to delete password message for user {}", userId);
		}
	}
}
