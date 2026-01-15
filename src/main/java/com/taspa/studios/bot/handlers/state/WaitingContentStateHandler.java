package com.taspa.studios.bot.handlers.state;

import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.PostService;
import com.taspa.studios.bot.services.UserStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
@RequiredArgsConstructor
public class WaitingContentStateHandler implements StateHandler {

	private final MessageService messageService;
	private final PostService postService;
	private final WaitingTitleStateHandler waitingTitleStateHandler;
	private final UserStateService userStateService;

	@Override
	public BotState getState() {
		return BotState.WAITING_FOR_CONTENT;
	}

	@Override
	public void handle(Long chatId, Command command, Update update) {
		if (!update.hasMessage() || !update.getMessage().hasText()) {
			return;
		}

		Message message = update.getMessage();
		String content = message.getText().trim();

		if (content.isBlank()) {
			messageService.sendText(chatId, "Incorrect content, please try again.");
			return;
		}

		userStateService.setState(chatId, BotState.ADMIN_VERIFIED);
		postService.createPost(chatId, waitingTitleStateHandler.getTitle(), content);
		messageService.sendText(chatId, "Post successfully saved! Use /all_posts to see your posts");
	}

}
