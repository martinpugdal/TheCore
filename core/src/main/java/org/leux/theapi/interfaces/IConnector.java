package org.leux.theapi.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnector {

    boolean isInitialized();

    void closeConnection();

    void connect(ConnectionCallback callback);

    interface ConnectionCallback {
        void accept(Connection connection) throws SQLException;
    }
}
