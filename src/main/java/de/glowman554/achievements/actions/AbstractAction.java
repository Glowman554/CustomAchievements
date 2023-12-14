package de.glowman554.achievements.actions;

import org.bukkit.entity.Player;

public abstract class AbstractAction
{
	protected String target;
	public abstract void perform(Player player);
	
	public void setTarget(String target)
	{
		this.target = target;
	}
}
