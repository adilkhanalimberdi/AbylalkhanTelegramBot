package com.taspa.studios.bot.dispatchers;

import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.handlers.global.GlobalCommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GlobalCommandDispatcher {

	private final Map<Command, GlobalCommandHandler> handlers;

	public GlobalCommandDispatcher(List<GlobalCommandHandler> handlers) {
		this.handlers = handlers.stream()
				.collect(Collectors.toMap(GlobalCommandHandler::command, h -> h));
	}

	public boolean dispatch(Long userId, Command command, Update update) {
		GlobalCommandHandler handler = handlers.get(command);
		if (handler == null) return false;
		return handler.handle(userId, command, update);
	}

}
