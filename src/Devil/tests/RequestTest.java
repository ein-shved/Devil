package Devil.tests;

import Devil.*;
import Devil.Event.*;
import Devil.util.*;


public class RequestTest {
    static String requestModuleName = "Devil.tests.TestRequestModule";
    static String responceModuleName = "Devil.tests.TestResponceModule";

    private static class ModuleFailedHandler extends DevilEventHandler {
        public ModuleFailedHandler () {
            super (Flag.FAST);
        }
        public void handle (DevilEvent event) {
            ModuleLoadingFailedEvent failure = (ModuleLoadingFailedEvent) event;
            System.out.println ("Can not load module " + failure.getRequest() + 
                    "\nBecause "+ failure.getDescription());
        }
    }

    private static class ModuleLoadedHandler extends DevilEventHandler {
        public ModuleLoadedHandler () {
            super (Flag.FAST);
        }
        public void handle (DevilEvent event) {
            NewModuleLoadedEvent load_event = (NewModuleLoadedEvent) event;
            DevilModule module = load_event.getModule();
            System.out.println ("Module loaded " + module.getModuleName()); 
        }
    }


    public static void main (String args[]) {
        Devil devil = new Devil();
        devil.loadModuleRequest (requestModuleName);
        devil.subscribeForEvent (ModuleLoadingFailedEvent.type(), 
            new ModuleFailedHandler() );
        devil.subscribeForEvent (NewModuleLoadedEvent.type(), 
            new ModuleLoadedHandler() );
        devil.main();
    }
}


