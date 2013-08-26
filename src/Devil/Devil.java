/* Devil is the complex messager with distributed structure.
 * Copyright (C) 2013  Shvedov Yury
 * 
 * This file is part of Devil.
 *
 * Devil is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Devil is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Devil.  If not, see <http://www.gnu.org/licenses/>.
 */

package Devil;

import Devil.event.*;

import java.util.concurrent.*;
import java.util.*;
import java.lang.*;
import java.lang.String;

/**
 * @author Shvedov Yury <shved at lvk.cs.msu.su>
 * @version 0.1.0
 */


public class Devil {
    EventProcessor processor;
    EventQueue queue;
    ModuleManager moduleManager;
    private volatile boolean finished;

    /**
     * Constructor, creates the Devil object.
     */

    public Devil () {
        processor = new EventProcessor ();
        queue = new EventQueue ();
        processor.setQueue(queue);
        queue.setProcessor(processor);

        moduleManager = new ModuleManager (this);

        this.finished = false;
    }
    
    /**
     * Rises new event. 
     * Return true if there is any subscriber for event.
     * <p>
     * This method only puts event to the event queue.
     * It wil rise when EventManager gets it in enother thread.
     *
     * @param   event   an event to raise.
     * @return          true if there are any subscribtions for event, false otherwize.
     * @see     Event
     */
    public boolean raiseEvent (Event event) throws NullPointerException {
        if ( processor.containsKey(event.getType())){
            return queue.add (event);
        } else {
            return false;
        }
    }

    /**
     * Subscribes handler for event of type event_type.
     * <p>
     * The handle() method of handler will be call if event of type event_type is rises in system.
     * Threre two cases of fault in subscribtion: the finished() method was called before, or
     * the handler is already subscribed for the event. 
     *
     * @param   event_type  an event type to subscribe for.
     * @param   handler     handler to call in case of event.
     * @return              true if succeed to subscribe, false otherwize.
     * @see     Event
     * @see     Handler
     */
    public boolean subscribeForEvent (String event_type, EventHandler handler) {
        if (finished) {
            return false;
        }
        return processor.addSubscription (event_type, handler);
    }

    /**
     * Unubscribes handler for event of type event_type.
     * <p>
     * Threre two cases of fault in unsubscribtion: the finished() method was called before, or
     * the handler doesn't subscribed for the event. 
     *
     * @param   event_type  an event type to subscribe for.
     * @param   handler     handler to call in case of event.
     * @return              true if succeed to subscribe, false otherwize.
     * @see     Event
     * @see     Handler
     */
    public boolean unsubscribeForEvent (String event_type, EventHandler handler) {
        if (finished) {
            return false;
        }
        return processor.removeSubscription (event_type, handler);
    }

    /**
     * Make the Devil alive UAHAHAHA.
     * This method must be called to run entire system.
     * <p>
     * Launches the ModuleManager to wait for incoming LoadModule requests to load them from
     * main thread. Returns only after finish() method calling.
     *
     * @see main
     */
    public void main () {
        finished = false;
        moduleManager.main();
    }

    /**
     * Opposite to main().
     * This method must be called to stop entire system.
     * <p>
     * Stops work of EventManager, ModuleManager and all of modules.
     *
     * @see finish
     */
    public void finish () {
        finished = true;
        processor.finish();
        moduleManager.finish();
    }

    /**
     * Requests for module loading.
     * Put the classname to the queue of loadingModule requests.
     * <p>
     * The modules mast be loaded from main thread. So threre is a LoadModuleRequestsQueue from 
     * which ModuleManager gets the classnames and loads them in main thread. When the loading 
     * succeds, runModule method of module is called and NewModuleLoadedEvent is rised. If loading
     * fails, the ModuleLoadingFailedEvent will be rised;
     *
     * @param   classname   module name to load e.g. Devil.tests.TestModule
     * @return              always should be true.
     * @see     Module
     */
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

