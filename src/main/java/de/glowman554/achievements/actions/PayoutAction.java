package de.glowman554.achievements.actions;

import org.bukkit.entity.Player;

import de.glowman554.achievements.AchievementsMain;

public class PayoutAction extends AbstractAction
{
	@Override
	public void perform(Player player)
	{
		AchievementsMain.getInstance().getEconomy().depositPlayer(player, Double.parseDouble(target));
	}

}
