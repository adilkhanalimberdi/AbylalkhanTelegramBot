package com.taspa.studios.bot.dispatchers;

import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.handlers.admin.AdminCommandHandler;
import com.taspa.studios.bot.services.UserStateService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AdminCommandDispatcher {

	private final Map<Command, AdminCommandHandler> handlers;
	private final UserStateService userStateService;

	public AdminCommandDispatcher(List<AdminCommandHandler> handlers, UserStateService userStateService) {
		this.handlers = handlers.stream()
				.collect(Collectors.toMap(AdminCommandHandler::command, h -> h));
		this.userStateService = userStateService;
	}

	public boolean dispatch(Long userId, Command command, Update update) {
		if (userStateService.getState(userId) != BotState.ADMIN_VERIFIED) return false;

		AdminCommandHandler handler = handlers.get(command);
		if (handler == null) return false;
		return handler.handle(userId, update);
	}

}
