package com.taspa.studios.bot.handlers.admin;

import com.taspa.studios.bot.entities.User;
import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminAllUsersHandler implements AdminCommandHandler {

	private final UserService userService;
	private final MessageService messageService;

	@Override
	public Command command() {
		return Command.ALL_USERS;
	}

	@Override
	public boolean handle(Long userId, Update update) {
		List<User> users = userService.getAllUsers();

		List<InlineKeyboardRow> keyboardRows = new ArrayList<>();
		for (int i = 0; i < users.size(); i++) {
			InlineKeyboardButton button = InlineKeyboardButton.builder()
					.text((i + 1) + ". @" + users.get(i).getUsername())
					.callbackData("more_about_" + users.get(i).getChatId().toString())
					.build();
			keyboardRows.add(new InlineKeyboardRow(button));
		}
		messageService.sendHtmlWithMarkup(userId, "Select user to see more information:", new InlineKeyboardMarkup(keyboardRows));

		return true;
	}

}
