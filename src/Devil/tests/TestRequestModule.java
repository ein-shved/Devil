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
import Devil.event.*;
import Devil.*;

public class TestRequestModule extends Module {
    private Devil devil;

    private class ResponceModuleEventHandler extends EventHandler {
        public ResponceModuleEventHandler () {
            super();
        }
        public void handle (Event event) {
            NewModuleLoadedEvent module_event = (NewModuleLoadedEvent) event;
            Module responceModule = module_event.getModule();
            System.out.println ("RequestModule have got module '" + responceModule.getModuleName()
                    + "' loaded event.");
            if (responceModule.getModuleName() == RequestTest.responceModuleName) {
                ResponceEvent responce = RequestSender.sendWaitRequest (devil, "Test");
                System.out.printf ("I have got responce with data:\n\t%s\n",
                        ((TestResponceEvent) responce).getResponceData());
                devil.finish();
            }
        }
    }

    public void runModule(Devil devil) {
        this.devil = devil;
        devil.subscribeForEvent(NewModuleLoadedEvent.type(), new ResponceModuleEventHandler());
        devil.loadModuleRequest (RequestTest.responceModuleName);
    }
    public void stopModule () {
        super.stopModule();
    }
}
