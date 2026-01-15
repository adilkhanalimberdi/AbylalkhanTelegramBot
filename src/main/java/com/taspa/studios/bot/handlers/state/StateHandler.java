package com.taspa.studios.bot.handlers.state;

import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.command.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface StateHandler {

	BotState getState();
	void handle(Long chatId, Command command, Update update);

}
