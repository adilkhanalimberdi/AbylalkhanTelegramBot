package com.taspa.studios.bot.consumer;

import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.Command;
import com.taspa.studios.bot.services.UserStateService;
import com.taspa.studios.bot.state.GlobalCommandHandler;
import com.taspa.studios.bot.state.StateHandlerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

	private final UserStateService userStateService;
	private final StateHandlerRegistry stateHandlerRegistry;
	private final GlobalCommandHandler globalCommandHandler;

	@Override
	public void consume(Update update) {
		if (!update.hasMessage()) {
			return;
		}

		Long chatId = update.getMessage().getChatId();

		Command command = null;
		if (update.getMessage().hasText()) { // if message has text
			command = Command.parse(update.getMessage().getText()).orElse(Command.UNKNOWN);
		} else {
			command = Command.UNKNOWN;
		}

		if (update.getMessage().hasText() && globalCommandHandler.handle(chatId, command, update)) { // if message has text, and if its global command
			return;
		}

		BotState state = userStateService.getState(chatId);
		stateHandlerRegistry.getHandler(state).handle(chatId, command, update);

		log.info(
				"Received message {} from {}, current state - {}",
				update.getMessage().getText(),
				chatId,
				userStateService.getState(chatId)
		);
	}

}
