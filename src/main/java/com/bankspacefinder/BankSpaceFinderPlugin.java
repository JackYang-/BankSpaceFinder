package com.bankspacefinder;

import com.bankspacefinder.rules.Freeable;
import com.bankspacefinder.rules.FreeableManager;
import com.google.inject.Provides;
import javax.inject.Inject;
import javax.swing.*;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@PluginDescriptor(
	name = "BankSpaceFinder"
)
public class BankSpaceFinderPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private BankSpaceFinderConfig config;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private ItemManager itemManager;

	private NavigationButton navButton;
	private BankSpaceFinderPanel panel;
	private boolean prepared = false;

	private static final BufferedImage ICON = ImageUtil.loadImageResource(BankSpaceFinderPlugin.class, "/816.png");

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");

		panel = new BankSpaceFinderPanel(itemManager);
		navButton = NavigationButton.builder()
				.tooltip("Bank Space Finder")
				.icon(ICON)
				.priority(8)
				.panel(panel)
				.build();

		clientToolbar.addNavigation(navButton);

		if (!prepared) {
			clientThread.invoke( () -> {
				switch(client.getGameState()) {
					case LOGIN_SCREEN:
					case LOGIN_SCREEN_AUTHENTICATOR:
					case LOGGING_IN:
					case LOADING:
					case LOGGED_IN:
					case CONNECTION_LOST:
					case HOPPING:
						Freeable.setItemManager(itemManager);
						FreeableManager.prepareRuleMap();
						prepared = true;
						return true;
					default:
						return false;
				}
			});
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged ev) {
		if (ev.getContainerId() == InventoryID.BANK.getId()) {
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "container changed", null);
			checkItems(ev.getItemContainer());
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	public void checkItems(ItemContainer c) {
		if (c == null) {
			return;
		}

		List<Freeable> matches = new ArrayList<>();
		for (Item item : c.getItems()) {
			int itemID = item.getId();

			if (itemID == -1) {
				continue;
			}

			if (FreeableManager.contains(itemID)) {
				//client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", itemID + " freeables found" , null);
				for (Freeable f : FreeableManager.getFreeables(itemID)) {
					if (f.isFreeable()) {
						matches.add(f);
					}
				}
			}
		}
		SwingUtilities.invokeLater( () -> panel.setFreeables(matches) );
	}

	@Provides
	BankSpaceFinderConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BankSpaceFinderConfig.class);
	}
}
