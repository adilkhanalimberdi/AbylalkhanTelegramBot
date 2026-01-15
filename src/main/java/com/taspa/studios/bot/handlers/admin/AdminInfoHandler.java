package com.taspa.studios.bot.handlers.admin;

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
public class AdminInfoHandler implements AdminCommandHandler {

	private final MessageService messageService;
	private final UserService userService;
	private final MessageFormatter messageFormatter;

	@Override
	public Command command() {
		return Command.INFO;
	}

	@Override
	public boolean handle(Long userId, Update update) {
		String messageText = update.getMessage().getText();

		if (messageText.split(" ").length == 2) {
			String username = messageText.split(" ")[1].replace("@", "");
			showInfoByUsername(userId, username);
			return true;
		}

		messageService.sendText(userId, "Invalid usage of command.");
		return true;
	}

	public void showInfoByUsername(Long userId, String username) {
		User user = userService.getUserByUsernameOrNull(username);

		if (user == null) {
			messageService.sendText(userId, "User not found.");
			return;
		}

		String info = messageFormatter.userInfo(user);
		messageService.sendHtml(userId, info);
	}

}
