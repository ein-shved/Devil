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

class TestResponceEvent extends DevilResponceEvent {
    public TestResponceEvent (DevilRequestID id) {
        super (id, "Test");
    }
    public String getResponceData () {
        return "Test Responce";
    }
}


public class TestResponceModule extends DevilModule {
    private Devil devil;

    private class TestResponcer extends DevilEventHandler {
        public TestResponcer () {
            super (Flag.FAST);
        }
        public void handle (DevilEvent event) {
            System.out.println ("ResponceModule have got event");
            if (event.getType().equals("Request_Test")) {
                System.out.println ("It was event of correct type");
                DevilRequestEvent request = (DevilRequestEvent) event;
                devil.raiseEvent (new TestResponceEvent(request.getID()));
            }
        }
    }

    public void runModule (Devil devil) {
        this.devil = devil;
        devil.subscribeForEvent ("Request_Test", new TestResponcer());
    }
}
