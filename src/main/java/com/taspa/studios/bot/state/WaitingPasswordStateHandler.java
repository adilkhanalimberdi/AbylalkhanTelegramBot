package com.taspa.studios.bot.state;

import com.taspa.studios.bot.bot.BotProperties;
import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.Command;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.UserService;
import com.taspa.studios.bot.services.UserStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
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
	public void handle(Long chatId, Command command, Update update) {
		if (command == Command.BACK) {
			userStateService.setState(chatId, BotState.STARTED);
			messageService.sendText(chatId, "Now you are in default mode.");
			return;
		}

		if (update.getMessage().getText().equals(botProperties.getAdminPassword())) {
			userService.toggleAdmin(chatId);
			userStateService.setState(chatId, BotState.ADMIN_VERIFIED);
			messageService.sendText(chatId, "Welcome to Admin Panel!\nUse /admin_help command to see what you can do in admin mode.\nUse /exit command to exit.");
			messageService.delete(chatId, update.getMessage().getMessageId());
			return;
		}

		messageService.sendText(chatId, "Password is incorrect, please try again.");
		messageService.delete(chatId, update.getMessage().getMessageId());
	}
}
