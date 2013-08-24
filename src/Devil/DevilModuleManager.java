package Devil;

import Devil.Event.*;
import Devil.util.*;

import java.util.concurrent.*;
import java.util.*;
import java.lang.*;
import java.lang.String;

class NewModuleLoadedEvent extends DevilEvent {
    private DevilModule module;
    
    public NewModuleLoadedEvent (DevilModule module) {
        super ("New_Module_Loaded_Event");
        this.module = module;
    }
    public DevilModule getModule () {
        return this.module;
    }
}

class DevilModuleManager extends ConcurrentSkipListMap <String, Pair<DevilModule, Thread> > {
    private Devil devil;
    private HashSet <Thread> unloadedThreads ();

    public DevilModuleManager (Devil devil) {
        super (new StringComparator());
        this.devil = devil;
    }

    private class ModuleThread extends Thread {
        private DevilModule module;
        public ModuleThread (DevilModule module) {
            this.module = module;
        };
        public void run () {
            module.runModule(devil);
        }
    }
    public DevilModule put (String name) 
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Pair<DevilModule, Thread> modulePair = get (name);
        if (modulePair == null) {
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            Class loaded_class = loader.loadClass(name);
            if (loaded_class == null) {
                return null;
            }
            modulePair = new Pair <DevilModule, Thread>();
            modulePair.first = (DevilModule) loaded_class.newInstance();
            devil.raiseEvent(new NewModuleLoadedEvent(modulePair.first));
            modulePair.second = new ModuleThread(modulePair.first);
            put (name, modulePair );
            modulePair.second.start();
        }
        return modulePair.first;

    }
    public DevilModule removeModule (String name) {
        Pair <DevilModule, Thread> modulePair = super.remove (name);

        if (modulePair == null) {
            return null;
        }
        modulePair.first.stopModule();
        if (modulePair.second.getState() != Thread.State.TERMINATED) {
            unloadedThreads.add (modulePair.second);
        }
        return modulePair.first;
    }
    public void removeAll () {
        while (!isEmpty()) {
            removeModule(firstKey());
        }
    }
}
