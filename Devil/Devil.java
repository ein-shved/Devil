package Devil;

import Devil.Event.*;

import java.util.concurrent.*;
import java.util.*;
import java.lang.*;
import java.lang.String;

public class Devil {
    DevilEventProcessor processor;
    DevilEventQueue queue;
    DevilModuleManager moduleManager;

    public Devil () {
        processor = new DevilEventProcessor ();
        queue = new DevilEventQueue ();
        processor.setQueue(queue);
        queue.setProcessor(processor);

        moduleManager = new DevilModuleManager (this);
    }
    
    public boolean raiseEvent (DevilEvent event) throws NullPointerException {
        if ( processor.containsKey(event.getType())){
            return queue.add (event);
        } else {
            return false;
        }
    }

    public boolean subscribeForEvent (String event_type, DevilEventHandler handler) {
        return processor.addSubscription (event_type, handler);
    }
    public boolean unsubscribeForEvent (String event_type, DevilEventHandler handler) {
        return processor.removeSubscription (event_type, handler);
    }
    public void finish () {
        processor.finish();
        moduleManager.removeAll();
    }
    public DevilModule loadModule (String classname) 
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return moduleManager.put (classname);
    }

}

