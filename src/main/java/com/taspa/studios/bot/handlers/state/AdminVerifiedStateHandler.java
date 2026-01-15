package com.taspa.studios.bot.handlers.state;

import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.command.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AdminVerifiedStateHandler implements StateHandler {

	@Override
	public BotState getState() {
		return BotState.ADMIN_VERIFIED;
	}

	@Override
	public void handle(Long chatId, Command command, Update update) {
		// admin has no dialog state
		// all commands are handled by dispatcher
	}

}
