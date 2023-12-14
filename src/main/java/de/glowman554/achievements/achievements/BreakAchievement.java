package de.glowman554.achievements.achievements;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.glowman554.achievements.AchievementsMain;

public class BreakAchievement extends AbstractAchievement implements Listener
{
	public BreakAchievement()
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, AchievementsMain.getInstance());
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		if (event.getBlock().getType().toString().equals(target.toUpperCase()))
		{
			process(event.getPlayer());
		}
	}
}
