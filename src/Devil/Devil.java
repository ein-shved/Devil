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
    private volatile boolean finished;

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
        if (finished) {
            return false;
        }
        return processor.addSubscription (event_type, handler);
    }
    public boolean unsubscribeForEvent (String event_type, DevilEventHandler handler) {
        if (finished) {
            return false;
        }
        return processor.removeSubscription (event_type, handler);
    }
    public void main () {
        finished = false;
        moduleManager.main();
    }
    public void finish () {
        finished = true;
        processor.finish();
        moduleManager.finish();
    }
    //succeed to put request or not
    public boolean loadModuleRequest (String classname) {
        if (finished) {
            return false;
        }
        try {
            moduleManager.put (classname);
        } catch (InterruptedException exc) {
            return false;
        }
        return true;
    }

}

