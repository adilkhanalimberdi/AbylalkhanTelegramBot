package com.taspa.studios.bot.dispatchers;

import com.taspa.studios.bot.enums.command.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CommandDispatcher {

	private final GlobalCommandDispatcher globalCommandDispatcher;
	private final AdminCommandDispatcher adminCommandDispatcher;
	private final SecretCommandDispatcher secretCommandDispatcher;

	public boolean dispatch(Long userId, Command command, Update update) {
		if (command == null || command.getType() == null) return false;

		return switch (command.getType()) {
			case GLOBAL -> globalCommandDispatcher.dispatch(userId, command, update);
			case ADMIN -> adminCommandDispatcher.dispatch(userId, command, update);
			case SECRET -> secretCommandDispatcher.dispatch(userId, command, update);
		};
	}

}
