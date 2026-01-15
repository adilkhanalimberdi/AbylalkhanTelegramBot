package com.taspa.studios.bot.handlers.admin;

import com.taspa.studios.bot.entities.Post;
import com.taspa.studios.bot.enums.command.Command;
import com.taspa.studios.bot.message.MessageService;
import com.taspa.studios.bot.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminAllPostsHandler implements AdminCommandHandler {

	private final PostService postService;
	private final MessageService messageService;

	@Override
	public Command command() {
		return Command.ALL_POSTS;
	}

	@Override
	public boolean handle(Long userId, Update update) {
		List<Post> posts = postService.getAllPosts();

		List<InlineKeyboardRow> keyboardRows = new ArrayList<>();
		for (int i = 0; i < posts.size(); i++) {
			Post post = posts.get(i);
			InlineKeyboardButton button = InlineKeyboardButton.builder()
					.text((i + 1) + ". " + post.getTitle() + " (" + post.getState().toString() + ")")
					.callbackData(post.getTitle() + post.getId())
					.build();
			keyboardRows.add(new InlineKeyboardRow(button));
		}
		messageService.sendHtmlWithMarkup(userId, "Here are all your posts, you can see more information about them by selecting one:", new InlineKeyboardMarkup(keyboardRows));

		return true;
	}

}
