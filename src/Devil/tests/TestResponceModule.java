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
