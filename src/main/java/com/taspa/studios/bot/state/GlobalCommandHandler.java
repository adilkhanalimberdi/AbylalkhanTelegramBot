package com.taspa.studios.bot.state;

import com.taspa.studios.bot.enums.Command;
import com.taspa.studios.bot.message.MessageFormatter;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class GlobalCommandHandler {

	private final UserService userService;
	private final MessageService messageService;
	private final MessageFormatter messageFormatter;
	private final AdminVerifiedStateHandler adminVerifiedStateHandler;

	public boolean handle(Long chatId, Command command, Update update) {
		return switch(command) {
			case START -> handleStart(chatId, update);
			case HELP -> handleHelp(chatId, update);
			case ABOUT -> handleAbout(chatId, update);
			case MY_INFO -> handleMyInfo(chatId, update);
			default -> false;
		};
	}

	public boolean handleStart(Long chatId, Update update) {
		String username = update.getMessage().getFrom().getUserName();
		if (!userService.isRegistered(chatId)) {
			userService.createUser(chatId, username);
			messageService.sendHtml(chatId, "Hello, <b>%s!</b> You are successfully registered.".formatted(username));
		} else {
			messageService.sendHtml(chatId, "Welcome back, <b>%s!</b> You already logged in.".formatted(username));
		}
		showHelp(chatId);
		return true;
	}

	public boolean handleHelp(Long chatId, Update update) {
		showHelp(chatId);
		return true;
	}

	public boolean handleAbout(Long chatId, Update update) {
		String about = messageFormatter.about();
		messageService.sendHtml(chatId, about);
		return true;
	}

	public boolean handleMyInfo(Long chatId, Update update) {
		if (!userService.isRegistered(chatId)) {
			messageService.sendText(chatId, "You are not registered. Use /start command to register.");
			return true;
		}

		adminVerifiedStateHandler.showInfoByUsername(chatId, update.getMessage().getFrom().getUserName());
		return true;
	}

	public void showHelp(Long chatId) {
		String help = messageFormatter.help();
		messageService.sendHtml(chatId, help);
	}

}
