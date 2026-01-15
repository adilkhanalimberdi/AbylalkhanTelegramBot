package com.taspa.studios.bot.handlers.secret;

import com.taspa.studios.bot.bot.BotProperties;
import com.taspa.studios.bot.entities.response.CatGifResponse;
import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.ForwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
@RequiredArgsConstructor
public class SecretKittyHandler implements SecretCommandHandler {

	private final RestTemplate restTemplate;
	private final BotProperties botProperties;
	private final MessageService messageService;
	private final ForwardService forwardService;

	@Override
	public Command command() {
		return Command.KITTY;
	}

	@Override
	public boolean handle(Long userId, Command command, Update update) {
		forwardService.forward(userId, update.getMessage().getMessageId());

		CatGifResponse response = restTemplate.getForObject(
				botProperties.getCatServiceUrl() + "gif?json=true",
				CatGifResponse.class
		);

		if (response == null || response.getId() == null) {
			messageService.sendText(userId, "Failed to load cat.");
			return true;
		}

		String gifUrl = botProperties.getCatServiceUrl() + response.getId();
		InputFile file = new InputFile(gifUrl);

		Message sentMessage = messageService.sendAnimationAndGetMessage(userId, "kitty for you)", file);
		if (sentMessage != null) {
			forwardService.forward(userId, sentMessage.getMessageId());
		}
		return true;
	}

}
