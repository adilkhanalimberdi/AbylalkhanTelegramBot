package com.taspa.studios.bot.bot.callback;

import com.taspa.studios.bot.bot.BotProperties;
import com.taspa.studios.bot.entities.User;
import com.taspa.studios.bot.message.MessageFormatter;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CallbackQueryHandler {

	private final UserService userService;
	private final MessageService messageService;
	private final MessageFormatter messageFormatter;
	private final BotProperties botProperties;

	public void handle(Update update) {
		CallbackQuery callbackQuery = update.getCallbackQuery();

		List<User> users = userService.getAllUsers();
		for (User user : users) {
			String chatId = user.getChatId().toString();
			if (("more_about_" + chatId).equals(callbackQuery.getData())) {
				handleUserInfo(callbackQuery, user);
				return;
			}

			if (("delete_" + chatId).equals(callbackQuery.getData())) {
				handleDeleteUser(callbackQuery, user);
				return;
			}
		}
		log.info("Callback Query data: {}", callbackQuery.getData());
	}

	public void handleUserInfo(CallbackQuery query, User user) {
		log.info("handling user info: {}", user.getUsername());

		List<InlineKeyboardRow> keyboard = new ArrayList<>();
		InlineKeyboardButton deleteButton = InlineKeyboardButton.builder()
				.text("Delete")
				.callbackData("delete_" + user.getChatId().toString())
				.build();
		keyboard.add(new InlineKeyboardRow(deleteButton));

		String info = messageFormatter.userInfo(user);
		messageService.sendHtmlWithMarkup(query.getFrom().getId(), info, new InlineKeyboardMarkup(keyboard));
	}

	public void handleDeleteUser(CallbackQuery query, User user) {
		log.info("handling delete user: {}", user.getUsername());

		if (botProperties.getAdminUsers().contains(user.getChatId())) {
			messageService.sendText(query.getFrom().getId(), "Sorry, you cannot delete admin users.");
			return;
		}

		userService.deleteUser(user);
		messageService.sendText(query.getFrom().getId(), "User successfully deleted.");
	}

}
