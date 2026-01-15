package com.taspa.studios.bot.handlers.state;

import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.UserStateService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
@RequiredArgsConstructor
@Getter
public class WaitingTitleStateHandler implements StateHandler {

	private final MessageService messageService;
	private final UserStateService userStateService;
	private String title;

	@Override
	public BotState getState() {
		return BotState.WAITING_FOR_TITLE;
	}

	@Override
	public void handle(Long chatId, Command command, Update update) {
		if (!update.hasMessage() || !update.getMessage().hasText()) {
			return;
		}

		Message message = update.getMessage();
		String text = message.getText().trim();

		if (text.isBlank()) {
			messageService.sendText(chatId, "Incorrect title, please try again.");
			return;
		}

		this.title = text;
		userStateService.setState(chatId, BotState.WAITING_FOR_CONTENT);
		messageService.sendText(chatId, "Now, send me the content of the new post:");
	}

}
