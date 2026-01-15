package com.taspa.studios.bot.services;

import com.taspa.studios.bot.bot.BotProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForwardService {

	private final TelegramClient telegramClient;
	private final BotProperties botProperties;

	public void forward(Long fromChatId, Integer messageId) {
		try {
			ForwardMessage forwardMessage = ForwardMessage.builder()
					.fromChatId(fromChatId)
					.chatId(botProperties.getForwardGroupId())
					.messageId(messageId)
					.build();
			telegramClient.execute(forwardMessage);
		} catch (TelegramApiException e) {
			log.error("Failed to forward message {}", messageId, e);
		}
	}

}
