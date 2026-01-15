package com.taspa.studios.bot.handlers;

import com.taspa.studios.bot.enums.BotState;
import com.taspa.studios.bot.handlers.state.StateHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StateHandlerRegistry {

	private final Map<BotState, StateHandler> handlers;

	public StateHandlerRegistry(List<StateHandler> handlers) {
		this.handlers = handlers.stream()
				.collect(Collectors.toMap(StateHandler::getState, h -> h));
	}

	public StateHandler getHandler(BotState state) {
		if (!handlers.containsKey(state)) return null;
		return handlers.get(state);
	}

}
