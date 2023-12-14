package de.glowman554.achievements.actions;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class MessageAction extends AbstractAction
{
	@Override
	public void perform(Player player)
	{
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', target));
	}
}
