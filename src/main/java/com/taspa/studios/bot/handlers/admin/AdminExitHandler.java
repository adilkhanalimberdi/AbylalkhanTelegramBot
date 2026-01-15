package com.taspa.studios.bot.handlers.admin;

import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.UserService;
import com.taspa.studios.bot.services.UserStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class AdminExitHandler implements AdminCommandHandler {

	private final UserStateService userStateService;
	private final MessageService messageService;
	private final UserService userService;

	@Override
	public Command command() {
		return Command.EXIT;
	}

	@Override
	public boolean handle(Long userId, Update update) {
		userStateService.setState(userId, BotState.STARTED);
		userService.toggleAdmin(userId);
		messageService.sendText(userId, "Now, you are in normal mode.");
		return true;
	}

}
