package de.glowman554.achievements.actions;

public enum Actions
{
	MESSAGE(MessageAction.class),
	PAYOUT(PayoutAction.class)

	;

	private Class<? extends AbstractAction> action;

	private Actions(Class<? extends AbstractAction> action)
	{
		this.action = action;
	}

	public Class<? extends AbstractAction> getAction()
	{
		return action;
	}
	
	public static Actions get(Class<? extends AbstractAction> achievment)
	{
		for (Actions v : Actions.values())
		{
			if (v.getAction() == achievment)
			{
				return v;
			}
		}
		throw new IllegalArgumentException();
	}
}
