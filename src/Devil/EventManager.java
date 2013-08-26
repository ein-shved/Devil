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
import Devil.util.*;

import java.util.concurrent.*;
import java.util.*;
import java.lang.*;
import java.lang.String;



class EventQueue extends ConcurrentLinkedQueue<Event> {
    private EventProcessor processor; 

    public void setProcessor (EventProcessor processor) {
        this.processor = processor;
    }
    
    public boolean add (Event event) throws NullPointerException {
        boolean result = super.add (event);
        if (result != false) {
            processor.awake ();
        }
        return result;
    }
}

class EventProcessor extends 
        ConcurrentSkipListMap <String, HashSet<EventHandler>> {
    private EventQueue queue;

    private class ProcessorThread extends Thread {
        private volatile boolean terminated;
        public synchronized void makeSleep (boolean awake)
                throws InterruptedException {
            if (awake) {
                this.notifyAll();
            } else {
                this.wait(10);
            }
        }
        public void start () {
            terminated = false;
            super.start();
        }
        public void run () {
            try {
                while (this.isAlive() && !terminated) {
                    this.makeSleep(false);
                    if (queue == null) {
                        continue;
                    }
                    while (!queue.isEmpty()) {
                        Event event = queue.poll();
                        HashSet<EventHandler> set = get(event.getType());
                        if (set == null) {
                            continue;
                        }
                        Iterator<EventHandler> it = set.iterator();
                        while (it.hasNext()) {
                            EventHandler handler = it.next();
                            if (handler.hasFlag(EventHandler.Flag.ONCE)) {
                                set.remove (handler);
                                if (set.isEmpty() ) {
                                    remove(event.getType());
                                }
                            }
                            invokeHandler(handler, event);
                        }
                    }
                }
            } catch (InterruptedException exc) {}
        }
        public void terminate () {
            terminated = true;
            try {
                this.makeSleep(true);
            } catch (InterruptedException exc) {};
        }
    }

    private class ConcurrentHandlerInvoker extends Thread {
        private EventHandler handler;
        private Event event;
        ConcurrentHandlerInvoker (EventHandler handler, Event event) {
            this.handler = handler;
            this.event = event;
        }
        public void run () {
            handler.handle(event);
        }
    }

    private ProcessorThread thread;

    public EventProcessor () {
        super (new StringComparator());
        queue = null;
        thread = new ProcessorThread();
        thread.setName ("Devil.event Processor");
        thread.start();
    }

    public void setQueue (EventQueue _queue) {
        queue = _queue;
    }

    public void awake () {
        try {
            thread.makeSleep(true);
        } catch (InterruptedException exc) {};
    }
    
    public boolean addSubscription (String type, EventHandler handler) {
        HashSet<EventHandler> set;
        if ( (set = super.get(type)) == null ) {
            set = new HashSet<EventHandler>();
            set.add (handler);
            super.put(type, set);
            return true;
        }
        return set.add (handler);
    }

    public boolean removeSubscription (String type, EventHandler handler) {
        HashSet<EventHandler> set;
        if ( (set = super.get(type)) == null ) {
            return false;
        }
        boolean result = set.remove(handler);
        if (result && set.isEmpty() ) {
            super.remove(type);
        }
        return result;
    }

    protected void finish () {
        thread.terminate();
    }

    private synchronized void invokeHandler (EventHandler handler, Event event) {
        if (handler.hasFlag(EventHandler.Flag.FAST)) {
            handler.handle(event);
        } else {
            (new ConcurrentHandlerInvoker(handler,event)).start();
        }
    }
}
