package com.taspa.studios.bot.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

	private final TelegramClient client;

	public void sendText(Long chatId, String text) {
		send(chatId, text, null);
	}

	public void sendHtml(Long chatId, String text) {
		send(chatId, text, "HTML");
	}

	public void sendMarkdown(Long chatId, String text) {
		send(chatId, text, "MarkdownV2");
	}

	public void delete(Long chatId, Integer messageId) {
		try {
			DeleteMessage deleteMessage = DeleteMessage.builder()
					.chatId(chatId)
					.messageId(messageId)
					.build();

			client.execute(deleteMessage);
		} catch (TelegramApiException e) {
			log.error("Failed to delete message {}", messageId, e);
		}
	}

	public void send(Long chatId, String text, String parseMode) {
		try {
			SendMessage sendMessage = SendMessage.builder()
					.chatId(chatId)
					.text(text)
					.parseMode(parseMode)
					.build();

			client.execute(sendMessage);
		} catch (TelegramApiException e) {
			log.error("Failed to send message to chatId={}", chatId, e);
		}
	}

}
