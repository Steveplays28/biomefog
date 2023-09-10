package io.github.steveplays28.biomefog.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import io.github.steveplays28.biomefog.client.BiomeFogClient;
import io.github.steveplays28.biomefog.config.BiomeFogConfigLoader;
import io.github.steveplays28.biomefog.util.WorldUtil;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

import static io.github.steveplays28.biomefog.config.BiomeFogConfigLoader.BiomeFogConfigurations.BLACKLISTED_WORLDS;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class BlackListWorldCommand {
	public static final String NAME = "worldblacklist";

	public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
		var command = literal(BiomeFogClient.MOD_NAMESPACE).then(literal(NAME)
				.then(literal("add").executes(BlackListWorldCommand::add))
				.then(literal("remove").executes(BlackListWorldCommand::remove)));

		dispatcher.register(command);
	}

	private static int add(CommandContext<FabricClientCommandSource> context) {
		var name = WorldUtil.getWorldOrServerName();

		if (WorldUtil.isWorldBlacklisted(name)) {
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
		var name = WorldUtil.getWorldOrServerName();

		if (BLACKLISTED_WORLDS.blackListedWorlds.remove(name)) {
			BiomeFogConfigLoader.save();
		}

		context.getSource().sendFeedback(Text.literal("Removed the current world from the blacklisted worlds!"));
		return 1;
	}
}
