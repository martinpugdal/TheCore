package org.leux.theapi.database.connectors;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnector {

    /**
     * Returns true if this connector has been initialized and is ready to use.
     *
     * @return true if the connector is initialized, false otherwise
     */
    boolean isInitialized();

    /**
     * Returns a connection to the MariaDB database.
     *
     * @return a connection to the database
     * @throws SQLException if a database access error occurs
     */
    Connection getConnection() throws SQLException;

    /**
     * Closes the connection to the MariaDB database.
     *
     * @throws SQLException if a database access error occurs
     */
    void close() throws SQLException;

    /**
     * Returns the name of the database that this connector is connected to.
     *
     * @return the name of the database
     * @throws SQLException if a database access error occurs
     */
    String getDatabaseName() throws SQLException;

    /**
     * Returns the version of the MariaDB database that this connector is connected to.
     *
     * @return the version of the database
     * @throws SQLException if a database access error occurs
     */
    String getDatabaseVersion() throws SQLException;

    interface ConnectionCallback {
        void accept(Connection connection) throws SQLException;
    }

    void connect(ConnectionCallback callback);

    void closeConnection();
}
