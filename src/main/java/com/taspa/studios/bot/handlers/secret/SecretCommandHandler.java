package com.taspa.studios.bot.handlers.secret;

import com.taspa.studios.bot.enums.command.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface SecretCommandHandler {

	Command command();
	boolean handle(Long userId, Command command, Update update);

}
