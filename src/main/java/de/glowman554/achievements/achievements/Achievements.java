package de.glowman554.achievements.achievements;

public enum Achievements
{

	COLLECT(CollectAchievement.class),
	BREAK(BreakAchievement.class),
	PLACE(PlaceAchievement.class),
	KILL(KillAchievement.class)
	
	;

	private Class<? extends AbstractAchievement> achievment;

	private Achievements(Class<? extends AbstractAchievement> achievment)
	{
		this.achievment = achievment;
	}

	public Class<? extends AbstractAchievement> getAchievment()
	{
		return achievment;
	}
	
	public static Achievements get(Class<? extends AbstractAchievement> achievment)
	{
		for (Achievements v : Achievements.values())
		{
			if (v.getAchievment() == achievment)
			{
				return v;
			}
		}
		throw new IllegalArgumentException();
	}
}
