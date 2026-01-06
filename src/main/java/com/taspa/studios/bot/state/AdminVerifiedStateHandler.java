package com.taspa.studios.bot.state;

import com.taspa.studios.bot.entities.User;
import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.Command;
import com.taspa.studios.bot.message.MessageFormatter;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.UserService;
import com.taspa.studios.bot.services.UserStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminVerifiedStateHandler implements StateHandler {

	private final UserService userService;
	private final MessageService messageService;
	private final UserStateService userStateService;
	private final MessageFormatter messageFormatter;

	@Override
	public BotState getState() {
		return BotState.ADMIN_VERIFIED;
	}

	@Override
	public void handle(Long chatId, Command command, Update update) {
		if (command == Command.ADMIN_PANEL) {
			messageService.sendText(chatId,"You are already in admin mode.\nUse /exit command to exit.");
			return;
		}

		if (command == Command.INFO) {
			String messageText = update.getMessage().getText();

			if (messageText.split(" ").length == 2) {
				String username = messageText.split(" ")[1].replace("@", "");
				showInfoByUsername(chatId, username);
			} else {
				messageService.sendText(chatId, "Invalid usage of command.");
			}
			return;
		}

		switch (command) {
			case EXIT -> {
				userStateService.setState(chatId, BotState.STARTED);
				userService.toggleAdmin(chatId);
				messageService.sendText(chatId, "Now you are in default mode.");
			}
			case ALL_USERS -> {
				List<User> users = userService.getAllUsers();
				showAllUsers(chatId, users);
			}
			case UPLOAD -> {
				userStateService.setState(chatId, BotState.WAITING_FOR_UPLOAD);
				messageService.sendText(chatId, "Please, send file below:\nYou can cancel uploading via command - /back");
			}
			case ADMIN_HELP -> showAdminHelp(chatId);
		}
	}

	public void showAdminHelp(Long chatId) {
		String adminHelp = messageFormatter.adminHelp();
		messageService.sendHtml(chatId, adminHelp);
	}

	public void showAllUsers(Long chatId, List<User> users) {
		String allUsers = messageFormatter.allUsers(users);
		System.out.println(allUsers);
		messageService.sendHtml(chatId, allUsers);
	}

	public void showInfoByUsername(Long chatId, String username) {
		User user = userService.getUserByUsernameOrNull(username);

		if (user == null) {
			messageService.sendText(chatId, "User not found.");
			return;
		}

		String info = messageFormatter.userInfo(user);
		messageService.sendHtml(chatId, info);
	}

}
