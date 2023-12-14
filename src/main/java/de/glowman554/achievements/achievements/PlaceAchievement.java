package de.glowman554.achievements.achievements;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.glowman554.achievements.AchievementsMain;

public class PlaceAchievement extends AbstractAchievement implements Listener
{
	public PlaceAchievement()
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, AchievementsMain.getInstance());
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if (event.getBlock().getType().toString().equals(target.toUpperCase()))
		{
			process(event.getPlayer());
		}
	}
}
