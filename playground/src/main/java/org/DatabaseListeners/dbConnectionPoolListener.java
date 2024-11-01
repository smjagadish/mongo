package org.DatabaseListeners;

import com.mongodb.event.*;

public class dbConnectionPoolListener implements ConnectionPoolListener {
    @Override
    public void connectionCheckedOut(ConnectionCheckedOutEvent event) {
        System.out.println("connection check-out event triggered");
    }

    @Override
    public void connectionCheckedIn(ConnectionCheckedInEvent event) {
        System.out.println("connection check-in event triggered");
    }

    @Override
    public void connectionCreated(ConnectionCreatedEvent event) {
        System.out.println("connection created");
    }

    @Override
    public void connectionClosed(ConnectionClosedEvent event) {
        System.out.println("connection closed");
    }
}
