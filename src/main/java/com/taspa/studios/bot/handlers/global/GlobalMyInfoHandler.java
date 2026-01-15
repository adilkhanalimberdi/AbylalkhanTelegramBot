package com.taspa.studios.bot.handlers.global;

import com.taspa.studios.bot.entities.User;
import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.message.MessageFormatter;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class GlobalMyInfoHandler implements GlobalCommandHandler {

	private final UserService userService;
	private final MessageService messageService;
	private final MessageFormatter messageFormatter;

	@Override
	public Command command() {
		return Command.MY_INFO;
	}

	@Override
	public boolean handle(Long userId, Command command, Update update) {
		if (!userService.isRegistered(userId)) {
			messageService.sendText(userId, "You are not registered. Use /start command to register.");
			return true;
		}

		User user = userService.getUser(userId);
		String info = messageFormatter.userInfo(user);
		messageService.sendHtml(userId, info);
		return true;
	}

}
