package com.taspa.studios.bot.handlers.global;

import com.taspa.studios.bot.enums.command.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface GlobalCommandHandler {

	Command command();
	boolean handle(Long userId, Command command, Update update);

}
