package com.taspa.studios.bot.handlers.admin;

import com.taspa.studios.bot.enums.command.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface AdminCommandHandler {

	Command command();
	boolean handle(Long userId, Update update);

}
