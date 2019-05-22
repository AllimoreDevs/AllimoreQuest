package taurasi.marc.allimorequest.Database;

public class SQLCredentials {
    public String host, database, username, password;
    public int port;

    public SQLCredentials(String host, String database, String username, String password, int port) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;
    }
}
