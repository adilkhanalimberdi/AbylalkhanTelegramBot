package com.taspa.studios.bot.dispatchers;

import com.taspa.studios.bot.bot.BotProperties;
import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.handlers.secret.SecretCommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SecretCommandDispatcher {

	private final Map<Command, SecretCommandHandler> handlers;
	private final BotProperties botProperties;

	public SecretCommandDispatcher(List<SecretCommandHandler> handlers, BotProperties botProperties) {
		this.handlers = handlers.stream()
				.collect(Collectors.toMap(SecretCommandHandler::command, h -> h));
		this.botProperties = botProperties;
	}

	public boolean dispatch(Long userId, Command command, Update update) {
		if (!botProperties.getSecretUsers().contains(userId)) return false;

		SecretCommandHandler handler = handlers.get(command);
		if (handler == null) return false;
		return handler.handle(userId, command, update);
	}

}
