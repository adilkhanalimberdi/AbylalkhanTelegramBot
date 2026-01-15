package com.taspa.studios.bot.consumer;

import com.taspa.studios.bot.bot.BotProperties;
import com.taspa.studios.bot.bot.callback.CallbackQueryHandler;
import com.taspa.studios.bot.dispatchers.CommandDispatcher;
import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.handlers.state.StateHandler;
import com.taspa.studios.bot.services.UserStateService;
import com.taspa.studios.bot.handlers.StateHandlerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

	private final UserStateService userStateService;
	private final StateHandlerRegistry stateHandlerRegistry;
	private final CallbackQueryHandler callbackQueryHandler;
	private final BotProperties botProperties;
	private final CommandDispatcher commandDispatcher;

	@Override
	public void consume(Update update) {
		if (update.hasMessage()
			&& botProperties.getForwardGroupId().equals(update.getMessage().getChatId())) { // if the message received from the group, we ignore it
			log.info("Messages sent to forward group will be ignored.");
			return;
		}

		if (update.hasCallbackQuery()) { // if its callback
			callbackQueryHandler.handle(update);
			return;
		}

		if (!update.hasMessage()) { // edited messages and other types of updates will be ignored
			return;
		}

		Message message = update.getMessage();
		Long userId = update.getMessage().getFrom().getId();

		Command command = message.hasText() // if message has text
				? Command.parse(message.getText()).orElse(Command.UNKNOWN)
				: Command.UNKNOWN; // for other types of message (sticker, photo, audio), therefore it will be just ignored

		if (message.hasText() // if one of the dispatchers can handle current command
			&& commandDispatcher.dispatch(userId, command, update)) {
			return;
		}

		// otherwise we just pass it to its handler
		BotState state = userStateService.getState(userId);
		stateHandlerRegistry.getHandler(state).handle(userId, command, update);

		// some logging
		log.info(
				"Received message {} from {}, current state - {}",
				update.getMessage().getText(),
				userId,
				userStateService.getState(userId)
		);
	}

}
