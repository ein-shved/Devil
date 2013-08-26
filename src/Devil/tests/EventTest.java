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
import Devil.event.*;

import java.lang.System;

class TestEvent1 extends Event {
    public TestEvent1 () {
        super ("Test_Event_One");
    }
}

class TestEvent2 extends Event {
    public TestEvent2 () {
        super ("Test_Event_Two");
    }
}

class TestEventHandler1 extends EventHandler {
    public void handle (Event event){
        System.out.printf("TestEventHandler1 got event of type %s\n", event.getType());
    }
}

class TestEventHandler2 extends EventHandler {
    public TestEventHandler2 () {
        super (EventHandler.Flag.ONCE,
            EventHandler.Flag.FAST);
    }
    public void handle (Event event){
        System.out.printf("TestEventHandler2 got event of type %s\n", event.getType());
    }
}

public class EventTest {
    public static void main (String [] args) {
        Devil devil = new Devil();
        devil.subscribeForEvent ("Test_Event_One", new TestEventHandler1());
        devil.subscribeForEvent ("Test_Event_Two", new TestEventHandler2());
        devil.raiseEvent (new TestEvent1());
        devil.raiseEvent (new TestEvent2());
        devil.raiseEvent (new TestEvent1());
        devil.finish();
    }
}
