package com.taspa.studios.bot.handlers.state;

import com.taspa.studios.bot.bot.BotProperties;
import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.UserStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class StartedStateHandler implements StateHandler {

	private final MessageService messageService;
	private final UserStateService userStateService;
	private final BotProperties botProperties;

	@Override
	public BotState getState() {
		return BotState.STARTED;
	}

	@Override
	public void handle(Long userId, Command command, Update update) {
		if (command == Command.ADMIN_PANEL) {
			if (botProperties.getAdminUsers().contains(userId)) {
				userStateService.setState(userId, BotState.WAITING_FOR_PASSWORD);
				messageService.sendText(userId, "Please enter the password:\nYou can go back using command - /back");
				return;
			}

			messageService.sendText(userId, "Sorry, you are not in the admins list.");
		}
	}

}
