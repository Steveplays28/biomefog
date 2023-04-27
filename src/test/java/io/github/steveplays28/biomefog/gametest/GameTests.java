package io.github.steveplays28.biomefog.gametest;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

public class GameTests {
	@GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE, batchId = "test_game_start")
	public void testGameStart(TestContext context) {
		context.complete();
	}
}
