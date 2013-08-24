package Devil.tests;

import Devil.*;
import Devil.Event.*;

import java.lang.System;

class TestEvent1 extends DevilEvent {
    public TestEvent1 () {
        super ("Test_Event_One");
    }
}

class TestEvent2 extends DevilEvent {
    public TestEvent2 () {
        super ("Test_Event_Two");
    }
}

class TestEventHandler1 extends DevilEventHandler {
    public void handle (DevilEvent event){
        System.out.printf("TestEventHandler1 got event of type %s\n", event.getType());
    }
}

class TestEventHandler2 extends DevilEventHandler {
    public TestEventHandler2 () {
        super (DevilEventHandler.Flag.ONCE,
            DevilEventHandler.Flag.FAST);
    }
    public void handle (DevilEvent event){
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
