package com.taspa.studios.bot.bot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "bot")
@Getter
@Setter
public class BotProperties {

	private String token;
	private String username;
	private List<Long> adminUsers;
	private String adminPassword;
	private List<Long> secretUsers;
	private Long forwardGroupId;
	private String catServiceUrl;

}
