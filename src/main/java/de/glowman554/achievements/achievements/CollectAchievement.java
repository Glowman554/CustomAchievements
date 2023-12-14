package de.glowman554.achievements.achievements;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import de.glowman554.achievements.AchievementsMain;

public class CollectAchievement extends AbstractAchievement implements Listener
{
	public CollectAchievement()
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, AchievementsMain.getInstance());
	}
	
	@EventHandler
	public void onPlayerPickup(EntityPickupItemEvent event)
	{
		if (event.getEntity() instanceof Player && event.getItem().getItemStack().getType().toString().equals(target.toUpperCase()))
		{
			process((Player) event.getEntity());
		}
	}
}
