package de.glowman554.achievements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.glowman554.achievements.achievements.AbstractAchievement;
import de.glowman554.achievements.achievements.Achievements;
import de.glowman554.achievements.actions.AbstractAction;
import de.glowman554.achievements.actions.Actions;
import net.milkbowl.vault.economy.Economy;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;

public class AchievementsMain extends JavaPlugin
{
	private static AchievementsMain instance;
	
	public AchievementsMain()
	{
		instance = this;
	}
	
	private File databaseFile;
	private File configFile;
	
	private DatabaseConnection databaseConnection;
	private Economy economy;
	
	private void extractDefaultFile(File target, String source)
	{
		if (!target.exists())
		{
			try (InputStream inputStream = AchievementsMain.class.getResourceAsStream(source); OutputStream outputStream = new FileOutputStream(target))
			{

				byte[] buffer = new byte[1024];
				int length;
				while ((length = inputStream.read(buffer)) > 0)
				{
					outputStream.write(buffer, 0, length);
				}
			}
			catch (IOException e)
			{
				throw new IllegalStateException(e);
			}

			getLogger().log(Level.WARNING, "Extracted default file to " + target.getPath());
		}
	}
	
	private void loadAchievments() throws Exception
	{
		Json json = Json.json();
		JsonNode root = json.parse(configFile);
		
		for (int i = 0; i < root.length(); i++)
		{
			JsonNode achievementObj = root.get(i);
						
			JsonNode actionObj = achievementObj.get("action");
			AbstractAction action = Actions.valueOf(actionObj.get("type").asString()).getAction().getDeclaredConstructor().newInstance();
			action.setTarget(actionObj.get("target").asString());
			
			AbstractAchievement achievement = Achievements.valueOf(achievementObj.get("type").asString()).getAchievment().getDeclaredConstructor().newInstance();
			achievement.setTarget(achievementObj.get("target").asString());
			achievement.setMultiplier(achievementObj.get("multiplier").asInt());
			achievement.setRepeating(achievementObj.get("repeating").asBoolean());
			achievement.setAction(action);
			
			getLogger().log(Level.INFO, achievement.toString());
		}
	}

	@Override
	public void onLoad()
	{
		getDataFolder().mkdir();
		databaseFile = new File(getDataFolder(), "achievements.db");
		extractDefaultFile(databaseFile, "/achievements.db");
		configFile = new File(getDataFolder(), "config.json");
		extractDefaultFile(configFile, "/config.json");

		try
		{
			databaseConnection = new DatabaseConnection(databaseFile);
		}
		catch (SQLException e)
		{
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void onEnable()
	{
		try
		{
			loadAchievments();
		}
		catch (Exception e)
		{
			throw new IllegalStateException(e);
		}
		
		if (getServer().getPluginManager().getPlugin("Vault") == null)
		{
			throw new Error("Vault not found!");
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null)
		{
			throw new Error("Vault economy not found!");
		}
		economy = rsp.getProvider();
		getLogger().log(Level.INFO, String.format("Connected to economy plugin %s using Vault", economy.getName()));
	}

	@Override
	public void onDisable()
	{
		try
		{
			databaseConnection.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static AchievementsMain getInstance()
	{
		return instance;
	}
	
	public DatabaseConnection getDatabaseConnection()
	{
		return databaseConnection;
	}
	
	public Economy getEconomy()
	{
		return economy;
	}
}
