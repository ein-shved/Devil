package Devil;

import Devil.Event.*;
import Devil.ClassManagement.*;

import java.util.concurrent.*;
import java.util.*;
import java.lang.*;
import java.lang.String;

public class Devil {
    DevilEventProcessor processor;
    DevilEventQueue queue;

    public Devil () {
        processor = new DevilEventProcessor ();
        queue = new DevilEventQueue ();
        processor.setQueue(queue);
        queue.setProcessor(processor);
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
    }
    public DevilSharedClass loadClass (String classname) 
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Class loaded_class = loader.loadClass(classname);
        if (loaded_class == null) {
            return null;
        }
        DevilSharedClass shared_class = (DevilSharedClass) loaded_class.newInstance();
        shared_class.loadClass(this);
        return shared_class;
    }
}

class DevilEventQueue extends ConcurrentLinkedQueue<DevilEvent> {
    private DevilEventProcessor processor; 

    public void setProcessor (DevilEventProcessor _processor) {
        processor = _processor;
    }
    
    public boolean add (DevilEvent event) throws NullPointerException {
        boolean result = super.add (event);
        if (result != false) {
            processor.awake ();
        }
        return result;
    }
}

class StringComparator implements Comparator<String> {
    public int compare (String o1, String o2) {
        return o1.compareTo(o2);
    }
    public boolean equals (String o1, String o2) {
        return o1.equals(o2);
    }
}

class DevilEventProcessor extends 
        ConcurrentSkipListMap <String, HashSet<DevilEventHandler>> {
    private DevilEventQueue queue;

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
                        DevilEvent event = queue.poll();
                        HashSet<DevilEventHandler> set = get(event.getType());
                        if (set == null) {
                            continue;
                        }
                        Iterator<DevilEventHandler> it = set.iterator();
                        while (it.hasNext()) {
                            DevilEventHandler handler = it.next();
                            if (handler.hasFlag(DevilEventHandler.Flag.ONCE)) {
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
        private DevilEventHandler handler;
        private DevilEvent event;
        ConcurrentHandlerInvoker (DevilEventHandler handler, DevilEvent event) {
            this.handler = handler;
            this.event = event;
        }
        public void run () {
            handler.handle(event);
        }
    }

    private ProcessorThread thread;

    public DevilEventProcessor () {
        super (new StringComparator());
        queue = null;
        thread = new ProcessorThread();
        thread.setName ("Devil Event Processor");
        thread.start();
    }

    public void setQueue (DevilEventQueue _queue) {
        queue = _queue;
    }

    public void awake () {
        try {
            thread.makeSleep(true);
        } catch (InterruptedException exc) {};
    }
    
    public boolean addSubscription (String type, DevilEventHandler handler) {
        HashSet<DevilEventHandler> set;
        if ( (set = super.get(type)) == null ) {
            set = new HashSet<DevilEventHandler>();
            set.add (handler);
            super.put(type, set);
            return true;
        }
        return set.add (handler);
    }

    public boolean removeSubscription (String type, DevilEventHandler handler) {
        HashSet<DevilEventHandler> set;
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

    private synchronized void invokeHandler (DevilEventHandler handler, DevilEvent event) {
        if (handler.hasFlag(DevilEventHandler.Flag.FAST)) {
            handler.handle(event);
        } else {
            (new ConcurrentHandlerInvoker(handler,event)).start();
        }
    }
}



