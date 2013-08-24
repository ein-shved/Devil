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
    private HashSet <Thread> unloadedThreads;

    public DevilModuleManager (Devil devil) {
        super (new StringComparator());
        this.devil = devil;
        unloadedThreads = new HashSet<Thread> ();
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
    DevilModule put (String name) 
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
            modulePair.first.setModuleManager(this);
            modulePair.first.setModuleName(name);
            devil.raiseEvent(new NewModuleLoadedEvent(modulePair.first));
            modulePair.second = new ModuleThread(modulePair.first);
            put (name, modulePair );
            modulePair.second.start();
        }
        return modulePair.first;

    }

    //This one is called by Devil and user. 
    //It is meant that module pass through stopModule method.
    DevilModule removeModule (String name) {
        Pair <DevilModule, Thread> modulePair = super.remove (name);

        if (modulePair == null) {
            return null;
        }
        modulePair.first.stopModule();
        if ( modulePair.second != null &&
                modulePair.second.getState() != Thread.State.TERMINATED) {
            unloadedThreads.add (modulePair.second);
        }
        return modulePair.first;
    }

    //This one is called by module itself (from stopModule method)
    boolean removeModule (DevilModule module) {
        Pair <DevilModule, Thread> modulePair = super.remove (module.getModuleName());

        if ( (modulePair == null) || (modulePair.first != module) ) {
            return false;
        }
        if (modulePair.second.getState() != Thread.State.TERMINATED) {
            unloadedThreads.add (modulePair.second);
        }
        return true;
    }

    void removeAll () {
        while (!isEmpty()) {
            removeModule(firstKey());
        }
    }
}
