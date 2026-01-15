package com.taspa.studios.bot.enums.command;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum Command {

	START("/start", CommandType.GLOBAL),
	HELP("/help", CommandType.GLOBAL),
	ABOUT("/about", CommandType.GLOBAL),
	MY_INFO("/my_info", CommandType.GLOBAL),

	ADMIN_PANEL("/admin_panel", CommandType.ADMIN),
	ALL_USERS("/all_users", CommandType.ADMIN),
	ADMIN_HELP("/admin_help", CommandType.ADMIN),
	INFO("/info", CommandType.ADMIN),
	UPLOAD("/upload", CommandType.ADMIN),
	CREATE_POST("/create_post", CommandType.ADMIN),
	ALL_POSTS("/all_posts", CommandType.ADMIN),
	EXIT("/exit", CommandType.ADMIN),

	BACK("/back", CommandType.GLOBAL),

	KITTY("/kitty", CommandType.SECRET),

	UNKNOWN(null, null);

	private final String value;
	private final CommandType type;

	Command(String value, CommandType type) {
		this.value = value;
		this.type = type;
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
