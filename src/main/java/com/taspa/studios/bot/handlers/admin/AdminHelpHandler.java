package com.taspa.studios.bot.handlers.admin;

import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.message.MessageFormatter;
import com.taspa.studios.bot.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class AdminHelpHandler implements AdminCommandHandler {

	private final MessageFormatter messageFormatter;
	private final MessageService messageService;

	@Override
	public Command command() {
		return Command.ADMIN_HELP;
	}

	@Override
	public boolean handle(Long userId, Update update) {
		String adminHelp = messageFormatter.adminHelp();
		messageService.sendHtml(userId, adminHelp);
		return true;
	}

}
