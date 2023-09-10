package io.github.steveplays28.biomefog.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.steveplays28.biomefog.config.gui.ConfigCustomScreen;

import static io.github.steveplays28.biomefog.client.BiomeFogClient.MOD_NAMESPACE;

public class ModMenuEntrypoint implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> new ConfigCustomScreen(parent, MOD_NAMESPACE);
	}
}
