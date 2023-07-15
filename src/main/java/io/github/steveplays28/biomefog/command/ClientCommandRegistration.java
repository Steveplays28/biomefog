package io.github.steveplays28.biomefog.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class ClientCommandRegistration {
	public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {
		BlackListWorldCommand.register(dispatcher);
	}
}
