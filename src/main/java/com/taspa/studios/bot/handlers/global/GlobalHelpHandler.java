package com.taspa.studios.bot.handlers.global;

import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.message.MessageFormatter;
import com.taspa.studios.bot.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class GlobalHelpHandler implements GlobalCommandHandler {

	private final MessageFormatter messageFormatter;
	private final MessageService messageService;

	@Override
	public Command command() {
		return Command.HELP;
	}

	@Override
	public boolean handle(Long userId, Command command, Update update) {
		String help = messageFormatter.help();
		messageService.sendHtml(userId, help);
		return true;
	}

}
