package org.DatabaseListeners;

import com.mongodb.event.CommandListener;
import com.mongodb.event.CommandSucceededEvent;

public class dbCommandListener implements CommandListener {
    @Override
    public void commandSucceeded(CommandSucceededEvent event) {
        if(event.getCommandName().startsWith("update"))
            System.out.println("listener invoked for update operation");
    }
}
