package com.taspa.studios.bot.handlers.state;

import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class NotStartedStateHandler implements StateHandler {

	private final MessageService messageService;

	@Override
	public BotState getState() {
		return BotState.NOT_STARTED;
	}

	@Override
	public void handle(Long chatId, Command command, Update update) {
		messageService.sendText(chatId, "Hello my friend!\nUse /start command to see what can you do.");
	}

}
