package com.taspa.studios.bot.handlers.state;

import com.taspa.studios.bot.bot.BotProperties;
import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.FileStorageService;
import com.taspa.studios.bot.services.UserStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class WaitingUploadStateHandler implements StateHandler {

	private final MessageService messageService;
	private final UserStateService userStateService;
	private final TelegramClient client;
	private final BotProperties botProperties;
	private final RestTemplate restTemplate;
	private final FileStorageService fileStorageService;


	@Override
	public BotState getState() {
		return BotState.WAITING_FOR_UPLOAD;
	}

	@Override
	public void handle(Long chatId, Command command, Update update) {
		if (command == Command.BACK) {
			userStateService.setState(chatId, BotState.ADMIN_VERIFIED);
			messageService.sendText(chatId, "Uploading cancelled.");
			return;
		}

		if (command != Command.UNKNOWN) {
			return;
		}

		if (!update.hasMessage() || !update.getMessage().hasDocument()) {
			return;
		}

		Document document = update.getMessage().getDocument();
		String fileId = document.getFileId();
		String fileName = document.getFileName();

		File file = getFileFromTelegram(fileId);
		if (file == null) {
			messageService.sendText(chatId, "Failed to download file from Telegram. Please try again.");
			return;
		}

		String fileUrl = "https://api.telegram.org/file/bot" + botProperties.getToken() + "/" + file.getFilePath();
		byte[] bytes = restTemplate.getForObject(fileUrl, byte[].class); // download file via http

		if (bytes == null || bytes.length == 0) {
			messageService.sendText(chatId, "File download failed or empty file.");
			return;
		}

		String safeFileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
		String s3Key = "users/%d/%s".formatted(chatId, UUID.randomUUID() + "-" + safeFileName);

		fileStorageService.upload(s3Key, bytes);

		messageService.sendText(chatId, "File uploaded successfully!");
		userStateService.setState(chatId, BotState.ADMIN_VERIFIED);
	}

	public File getFileFromTelegram(String fileId) {
		GetFile getFile = new GetFile(fileId);

		try {
			return client.execute(getFile);
		} catch (TelegramApiException e) {
			log.error("Failed to get file from telegram", e);
		}
		return null;
	}

}
