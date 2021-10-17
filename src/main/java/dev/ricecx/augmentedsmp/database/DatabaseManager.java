package dev.ricecx.augmentedsmp.database;

import com.zaxxer.hikari.HikariDataSource;
import dev.ricecx.augmentedsmp.AugmentedSMP;
import dev.ricecx.augmentedsmp.database.utils.HikariAuthentication;
import dev.ricecx.augmentedsmp.database.utils.HikariUtils;
import dev.ricecx.augmentedsmp.database.utils.SQLTypes;
import dev.ricecx.augmentedsmp.utils.LoggingUtils;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager implements Closeable {
    private static final SQLUtils sqlUtils = new SQLUtils();
    private final HikariDataSource dataSource;


    public DatabaseManager(SQLTypes types) {
        String host = AugmentedSMP.getInstance().getConfig().getString("database.host");
        String user = AugmentedSMP.getInstance().getConfig().getString("database.auth.user");
        String db = AugmentedSMP.getInstance().getConfig().getString("database.database");
        String password = AugmentedSMP.getInstance().getConfig().getString("database.auth.password");

        if (types == SQLTypes.SQLITE)
            dataSource = new HikariDataSource(HikariUtils.generateConfig(types, new HikariAuthentication(user, password, host, AugmentedSMP.getInstance().getDatabasePath() + "/" + db + ".db")));
        else
            dataSource = new HikariDataSource(HikariUtils.generateConfig(types, new HikariAuthentication(user, password, host, db)));
        LoggingUtils.info("Connected to " + types.name() + "!");
    }

    public Connection getConnection() {
        if (isClosed())
            throw new IllegalStateException("Connection is not open.");

        try {

            return dataSource.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        if (isClosed())
            throw new IllegalStateException("Connection is not open.");

        this.dataSource.close();
    }

    public boolean isClosed() {
        return dataSource == null || dataSource.isClosed();
    }

    public static SQLUtils getSQLUtils() {
        return sqlUtils;
    }
}
