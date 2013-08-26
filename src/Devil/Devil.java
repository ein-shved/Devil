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

