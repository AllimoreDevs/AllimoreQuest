package taurasi.marc.allimorequest.Database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import taurasi.marc.allimorequest.Config.ConfigWrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;
    private String host, database, username, password;
    public String table;
    private int port;

    public DatabaseManager(FileConfiguration config){
        OpenConnection(config);
    }

    public void OpenConnection(FileConfiguration config){
        SQLCredentials credentials = ConfigWrapper.ReadSQLConfig(config);

        host = credentials.host;
        port = credentials.port;
        database = credentials.database;
        username = credentials.username;
        password = credentials.password;
        table = "player_data";

        try{
            synchronized (this){
                if(getConnection() != null && !getConnection().isClosed()){
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password));

                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"MYSQL Connected!");
            }
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
