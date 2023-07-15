package io.github.steveplays28.biomefog.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import io.github.steveplays28.biomefog.client.BiomeFogClient;
import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import static io.github.steveplays28.biomefog.config.BiomeFogConfigLoader.BiomeFogConfigurations.BLACKLISTED_WORLDS;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class BlackListWorldCommand {
	public static final String NAME = "worldblacklist";

	public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
		var command = literal(BiomeFogClient.MOD_COMMAND_ID).then(literal(NAME)
				.then(literal("add").executes(BlackListWorldCommand::add))
				.then(literal("remove").executes(BlackListWorldCommand::remove)));

		dispatcher.register(command);
	}

	private static int add(CommandContext<FabricClientCommandSource> context) {
		var minecraftClient = MinecraftClient.getInstance();
		var currentServerEntry = minecraftClient.getCurrentServerEntry();
		var integratedServer = minecraftClient.getServer();

		String name;
		if (currentServerEntry != null && !currentServerEntry.isLocal()) {
			name = currentServerEntry.name;
		} else if (minecraftClient.isInSingleplayer() && integratedServer != null) {
			name = integratedServer.getName();
		} else {
			context.getSource().sendError(Text.literal("Couldn't add the current world to the blacklisted worlds."));
			return -1;
		}

		if (BLACKLISTED_WORLDS.blackListedWorlds.contains(name)) {
			context.getSource().sendFeedback(
					Text.literal("The current world has already been added to the blacklisted worlds!"));
			return 0;
		} else {
			BLACKLISTED_WORLDS.blackListedWorlds.add(name);
			BiomeFogConfigLoader.save();
		}

		context.getSource().sendFeedback(Text.literal("Added the current world to the blacklisted worlds!"));
		return 1;
	}

	private static int remove(CommandContext<FabricClientCommandSource> context) {
		var minecraftClient = MinecraftClient.getInstance();
		var currentServerEntry = minecraftClient.getCurrentServerEntry();
		var integratedServer = minecraftClient.getServer();

		String name;
		if (currentServerEntry != null && !currentServerEntry.isLocal()) {
			name = currentServerEntry.name;
		} else if (minecraftClient.isInSingleplayer() && integratedServer != null) {
			name = integratedServer.getName();
		} else {
			context.getSource().sendError(
					Text.literal("Couldn't remove the current world from the blacklisted worlds."));
			return -1;
		}

		if (BLACKLISTED_WORLDS.blackListedWorlds.remove(name)) {
			BiomeFogConfigLoader.save();
		}

		context.getSource().sendFeedback(Text.literal("Removed the current world from the blacklisted worlds!"));
		return 1;
	}
}
