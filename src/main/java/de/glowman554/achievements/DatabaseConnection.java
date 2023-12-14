package de.glowman554.achievements;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.entity.Player;

public class DatabaseConnection
{
	private Connection connection = null;

	public DatabaseConnection(File database) throws SQLException
	{
		AchievementsMain.getInstance().getLogger().log(Level.INFO, "Opening database");
		connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", database.getPath()));
	}

	public void close() throws SQLException
	{
		connection.close();
	}
	
	private void insertPlayerAchievementCount(Player player, String type) throws SQLException
	{
		PreparedStatement st = connection.prepareStatement("INSERT INTO achievements (uuid, type, counter) values(?, ?, 0)");
		st.setString(1, player.getUniqueId().toString());
		st.setString(2, type);
		st.execute();
		st.close();
	}

	public int getPlayerAchievementCount(Player player, String type) throws SQLException
	{
		PreparedStatement st = connection.prepareStatement("SELECT counter from achievements where uuid = ? and type = ?");
		st.setString(1, player.getUniqueId().toString());
		st.setString(2, type);

		ResultSet rs = st.executeQuery();
		int result = 0;
		if (rs.next())
		{
			result = rs.getInt("counter");
		}
		else
		{
			insertPlayerAchievementCount(player, type);
		}

		rs.close();
		st.close();

		return result;
	}
	
	public void setPlayerAchievementCount(Player player, String type, int count) throws SQLException
	{
		PreparedStatement st = connection.prepareStatement("UPDATE achievements set counter = ? where uuid = ? and type = ?");
		st.setInt(1, count);
		st.setString(2, player.getUniqueId().toString());
		st.setString(3, type);
		st.execute();
		st.close();
	}
}
