package de.glowman554.achievements.achievements;

import java.sql.SQLException;

import org.bukkit.entity.Player;

import de.glowman554.achievements.AchievementsMain;
import de.glowman554.achievements.DatabaseConnection;
import de.glowman554.achievements.actions.AbstractAction;
import de.glowman554.achievements.actions.Actions;

public class AbstractAchievement
{
	protected AbstractAction action;
	protected String target;
	protected int multiplier;
	protected boolean repeating;

	public void process(Player player)
	{
		DatabaseConnection db = AchievementsMain.getInstance().getDatabaseConnection();
		String type = getDbType();

		try
		{
			int count = db.getPlayerAchievementCount(player, type) + 1;
			db.setPlayerAchievementCount(player, type, count);

			if (repeating)
			{
				if (count % multiplier == 0)
				{
					action.perform(player);
				}
			}
			else
			{
				if (count == multiplier)
				{
					action.perform(player);
				}
			}
		}
		catch (SQLException e)
		{
			throw new IllegalStateException(e);
		}
	}

	private String getDbType()
	{
		return String.format("%s_%s_%d_%s", Achievements.get(this.getClass()), target, multiplier, Actions.get(action.getClass()));
	}

	public void setAction(AbstractAction action)
	{
		this.action = action;
	}

	public void setTarget(String target)
	{
		this.target = target;
	}

	public void setMultiplier(int multiplier)
	{
		this.multiplier = multiplier;
	}

	public void setRepeating(boolean repeating)
	{
		this.repeating = repeating;
	}
}
