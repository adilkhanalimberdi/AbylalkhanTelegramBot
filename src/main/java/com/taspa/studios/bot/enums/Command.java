package com.taspa.studios.bot.enums;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum Command {

	START("/start"),
	HELP("/help"),
	ABOUT("/about"),
	MY_INFO("/my_info"),
	ADMIN_PANEL("/admin_panel"),
	ALL_USERS("/all_users"),
	ADMIN_HELP("/admin_help"),
	UPLOAD("/upload"),
	BACK("/back"),
	INFO("/info"),
	EXIT("/exit"),
	UNKNOWN(null);

	private final String value;

	Command(String value) {
		this.value = value;
	}

	public boolean matches(String input) {
		if (this.value == null || input == null) return false;
		return input.equals(this.value) || input.startsWith(this.value + " ");
	}


	public static Optional<Command> parse(String value) {
		for (Command command : Command.values()) {
			if (command.matches(value)) {
				return Optional.of(command);
			}
		}
		return Optional.empty();
	}

}
