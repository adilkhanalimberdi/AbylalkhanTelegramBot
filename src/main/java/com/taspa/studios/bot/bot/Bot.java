package com.taspa.studios.bot.bot;

import com.taspa.studios.bot.consumer.UpdateConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@Component
@RequiredArgsConstructor
public class Bot implements SpringLongPollingBot {

	private final BotProperties botProperties;
	private final UpdateConsumer updateConsumer;

	@Override
	public String getBotToken() {
		return botProperties.getToken();
	}

	@Override
	public LongPollingUpdateConsumer getUpdatesConsumer() {
		return updateConsumer;
	}
}
