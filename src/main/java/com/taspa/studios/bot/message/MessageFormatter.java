package com.taspa.studios.bot.message;

import com.taspa.studios.bot.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageFormatter {

	public String help() {
		return """
				Here is a list of commands:
				❓<b>/help</b> - display manual page.
				🤔<b>/about</b> - show information about us.
				🧑‍💻<b>/admin_panel</b> - login to admin panel.
				ℹ️<b>/my_info</b> - show your information.
				""";
	}

	public String adminHelp() {
		return """
				Here is a list of commands:
				❓<b>/help</b> - display manual page.
				🤔<b>/about</b> - show information about us.
				🧑‍💻<b>/admin_panel</b> - login to admin panel.
				ℹ️<b>/my_info</b> - show your information.
				
				You also have an access to these commands:
				❓<b>/admin_help</b> - display admin manual page.
				👥<b>/all_users</b> - display all users in the system.
				👤<b>/info {username}</b> - get information about concrete user.
				⬆️<b>/upload</b> - upload file to the system.
				🚪<b>/exit</b> - exit admin panel.
				""";
	}

	public String about() {
		return """
				📸 <b>TASPA Studios</b>
				<i>Creative Video Studio | Shymkent</i>
				
				🎬 <b>What we do:</b>
				We create professional video content for businesses and projects.
				From concept development to shooting and editing, we craft videos that <b>deliver results</b>,
				help brands <b>stand out</b>, and <b>engage their audience</b>.
				
				✨ <b>Our philosophy:</b>
				Every frame tells a story, and every video is a tool to <b>grow your business</b>.
				
				📞 <b>Contact us:</b>
				+7 777 123 45 67
				<a href="https://www.instagram.com/taspa.studios/">instagram.com/taspa.studios</a>
				""";
	}

	public String userInfo(User user) {
		String instagram = user.getInstagramUsername() != null
				? "<a href=\"https://www.instagram.com/%s\">@%s</a>"
				.formatted(user.getInstagramUsername(), user.getInstagramUsername())
				: "Not set";
		String[] createdDateParts = user.getCreatedDate().toLocalDate().toString().split("-");

		return """
				<b>Information about User</b>
				
				<b>User ID:</b> <code>%d</code>
				<b>Username:</b> @%s
				<b>Instagram:</b> %s
				<b>Created at:</b> %s
				<b>Is Admin:</b> %s
				""".formatted(
					user.getChatId(),
					user.getUsername(),
					instagram,
					createdDateParts[2] + "." + createdDateParts[1] + "." + createdDateParts[0],
					user.isAdmin() ? "Yes" : "No");
	}

	public String allUsers(List<User> users) {
		StringBuilder sb = new StringBuilder("Here is a list of users in database:\n");
		for (int i = 0; i < users.size(); i++) {
			sb
					.append(i + 1)
					.append(". @")
					.append(users.get(i).getUsername())
					.append(" [<code>")
					.append(users.get(i).getChatId())
					.append("</code>]");

			User user = users.get(i);
			if (user.isAdmin()) {
				sb
						.append(" ")
						.append("(admin)");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
