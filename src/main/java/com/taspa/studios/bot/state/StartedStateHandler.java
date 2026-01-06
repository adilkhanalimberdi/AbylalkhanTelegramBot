package com.taspa.studios.bot.state;

import com.taspa.studios.bot.bot.BotProperties;
import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.Command;
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
	public void handle(Long chatId, Command command, Update update) {
		if (command == Command.ADMIN_PANEL) {
			if (botProperties.getAdmins().contains(chatId)) {
				userStateService.setState(chatId, BotState.WAITING_FOR_PASSWORD);
				messageService.sendText(chatId, "Please enter the password:\nYou can go back using command - /back");
				return;
			}

			messageService.sendText(chatId, "Sorry, you are not in the admins list.");
		}
	}

}
