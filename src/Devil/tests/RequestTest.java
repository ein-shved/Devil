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


