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

class ModuleManager extends LinkedBlockingQueue <String> {
    private Devil devil;

    //Here contains the modul thread, which didn't stop after module removing.
    private HashSet <Thread> unloadedThreads;

    //Here contains modules' names, modules themself and their threads.
    private ConcurrentSkipListMap <String, Pair<Module, Thread> > modules;
    private volatile boolean finished;

    public ModuleManager (Devil devil) {
        this.devil = devil;
        unloadedThreads = new HashSet<Thread> ();
        modules = new ConcurrentSkipListMap<String, Pair<Module, Thread> > (new StringComparator());
    }

    //Thread in which modul will be runned
    private class ModuleThread extends Thread {
        private Module module;
        public ModuleThread (Module module) {
            this.module = module;
        };
        public void run () {
            module.runModule(devil);
        }
    }

    //load module from queue.
    private Module queue_put (String name) 
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Pair<Module, Thread> modulePair = modules.get (name);
        //Don't load modules with same names.
        if (modulePair == null) {
            //Loading the class to the JVM.
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            Class loaded_class = loader.loadClass(name);
            if (loaded_class == null) {
                return null;
            }
            //Prepare pair to store.
            modulePair = new Pair <Module, Thread>();
            modulePair.first = (Module) loaded_class.newInstance();
            modulePair.first.setModuleManager(this);
            modulePair.first.setModuleName(name);
            modulePair.second = new ModuleThread(modulePair.first);
            //Rise Success event.
            devil.raiseEvent(new NewModuleLoadedEvent(modulePair.first));
            //Store module record.
            modules.put (name, modulePair );
            //Run module.
            modulePair.second.start();
        }
        return modulePair.first;
    }

    //This one is called by Devil and user. 
    //It means that module didn't pass through stopModule method.
    Module removeModule (String name) {
        Pair <Module, Thread> modulePair = modules.remove (name);

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
    boolean removeModule (Module module) {
        Pair <Module, Thread> modulePair = modules.remove (module.getModuleName());

        if ( (modulePair == null) || (modulePair.first != module) ) {
            return false;
        }
        if (modulePair.second.getState() != Thread.State.TERMINATED) {
            unloadedThreads.add (modulePair.second);
        }
        return true;
    }

    void stopAllModules () {
        while (!modules.isEmpty()) {
            removeModule(modules.firstKey());
        }
    }
    void main () {
        finished = false;
        while (/*(!super.isEmpty() || !modules.isEmpty()) &&*/ !finished ) {
            String name = "";
            try {
                name =  super.poll(500, TimeUnit.MILLISECONDS );
                if (name != null) {
                    queue_put (name);
                }
            } catch (InterruptedException exc) {} //the only exception of the poll
             catch (Exception exc) {
                if (!name.equals("")) {
                    devil.raiseEvent (new ModuleLoadingFailedEvent(name, "Can not load module")); 
                }
            }
        }
    }
    void finish () {
        finished = true;
        //Remove all many times because of multithreading.
        while (!super.isEmpty() || !modules.isEmpty()) {
            while (!super.isEmpty()) {
                try {
                    super.poll(0,TimeUnit.NANOSECONDS);
                } catch (Exception exc) {};
            }
            this.stopAllModules();
        }
    }
}
