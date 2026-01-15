package com.taspa.studios.bot.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
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

	public void sendHtmlWithMarkup(Long chatId, String text, ReplyKeyboard replyKeyboard) {
		sendWithMarkup(chatId, text, "HTML", replyKeyboard);
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

	public void sendWithMarkup(Long chatId, String text, String parseMode, ReplyKeyboard keyboard) {
		try {
			SendMessage sendMessage = SendMessage.builder()
					.chatId(chatId)
					.text(text)
					.parseMode(parseMode)
					.build();
			sendMessage.setReplyMarkup(keyboard);

			client.execute(sendMessage);
		} catch (TelegramApiException e) {
			log.error("Failed to send message to chatId={}", chatId, e);
		}
	}

	public void sendPhoto(Long chatId, String text, InputFile photo) {
		try {
			SendPhoto sendPhoto = SendPhoto.builder()
					.chatId(chatId)
//					.caption(text)
					.photo(photo)
					.build();

			client.execute(sendPhoto);
		} catch (TelegramApiException e) {
			log.error("Failed to send photo to chatId={}", chatId, e);
		}
	}

	public void sendAnimation(Long chatId, String text, InputFile animation) {
		try {
			SendAnimation sendAnimation = SendAnimation.builder()
					.chatId(chatId)
					.caption(text)
					.animation(animation)
					.build();

			client.execute(sendAnimation);
		} catch (TelegramApiException e) {
			log.error("Failed to send animation to chatId={}", chatId, e);
		}
	}

	public Message sendAnimationAndGetMessage(Long chatId, String text, InputFile animation) {
		try {
			SendAnimation sendAnimation = SendAnimation.builder()
					.chatId(chatId)
					.caption(text)
					.animation(animation)
					.build();

			return client.execute(sendAnimation);
		} catch (TelegramApiException e) {
			log.error("Failed to send animation to chatId={}", chatId, e);
			return null;
		}
	}

	public void sendSticker(Long chatId, InputFile sticker) {
		try {
			SendSticker sendAnimation = SendSticker.builder()
					.chatId(chatId)
					.sticker(sticker)
					.build();

			client.execute(sendAnimation);
		} catch (TelegramApiException e) {
			log.error("Failed to send sticker to chatId={}", chatId, e);
		}
	}

}
