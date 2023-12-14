package de.glowman554.achievements.achievements;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import de.glowman554.achievements.AchievementsMain;

public class KillAchievement extends AbstractAchievement implements Listener
{
	public KillAchievement()
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, AchievementsMain.getInstance());
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		Entity entity = event.getEntity();
		if (entity.getType().toString().equals(target.toUpperCase()))
		{
			if (entity.getLastDamageCause() instanceof EntityDamageByEntityEvent)
			{
				EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
				if (damageEvent.getDamager() instanceof Player)
				{
					process((Player) damageEvent.getDamager());
				}
			}
		}
	}
}
