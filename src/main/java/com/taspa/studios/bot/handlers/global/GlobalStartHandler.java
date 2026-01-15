package com.taspa.studios.bot.handlers.global;

import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class GlobalStartHandler implements GlobalCommandHandler {

	private final UserService userService;
	private final MessageService messageService;

	@Override
	public Command command() {
		return Command.START;
	}

	@Override
	public boolean handle(Long userId, Command command, Update update) {
		String username = update.getMessage().getFrom().getUserName();
		if (!userService.isRegistered(userId)) {
			userService.createUser(userId, username);
			messageService.sendHtml(
					userId,
					"Hello, <b>%s!</b> You are successfully registered.".formatted(username)
			);
			return true;
		}

		messageService.sendHtml(
				userId,
				"Welcome back, <b>%s!</b> You already logged in.".formatted(username)
		);
		return true;
	}

}
