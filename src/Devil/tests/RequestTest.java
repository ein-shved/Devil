package Devil.tests;

import Devil.*;
import Devil.Event.*;
import Devil.util.*;


public class RequestTest {
    static String requestModuleName = "Devil.tests.RequestModule";
    static String responceModuleName = "Devil.tests.ResponceModule";
    public static void main (String args[]) {
        Devil devil = new Devil();
        try {
            devil.loadModule (requestModuleName);
        } catch (Exception exc) {
            System.out.println (exc.toString() + " exception was caughted.");
            StackTraceElement [] stackTrace = exc.getStackTrace();
            for (int i=0; i<stackTrace.length; ++i) {
                System.out.printf ( "%s: %d\t Method: %s\n", stackTrace[i].getFileName(),
                    stackTrace[i].getLineNumber(), stackTrace[i].getMethodName());
            }
        }
  
    }
}

class TestResponceEvent extends DevilResponceEvent {
    public TestResponceEvent (DevilRequestID id) {
        super (id, "Test");
    }
    public String getResponceData () {
        return "Test Responce";
    }
}

class RequestModule extends DevilModule {
    private Devil devil;

    private class ResponceModuleEventHandler extends DevilEventHandler {
        public ResponceModuleEventHandler () {
            super();
        }
        public void handle (DevilEvent event) {
            NewModuleLoadedEvent module_event = (NewModuleLoadedEvent) event;
            DevilModule responceModule = module_event.getModule();
            if (responceModule.getModuleName() == RequestTest.responceModuleName) {
                DevilResponceEvent responce = DevilRequestSender.sendWaitRequest (devil, "Test");
                System.out.printf ("I have got responce with data:\n\t%s\n",
                        ((TestResponceEvent) event).getResponceData());
            }
        }
    }

    public void runModule(Devil devil) {
        this.devil = devil;
        devil.subscribeForEvent(NewModuleLoadedEvent.type(), new ResponceModuleEventHandler());
        try {
            devil.loadModule (RequestTest.responceModuleName);
        } catch (Exception exc) {
            System.out.println (exc.toString() + " exception was caughted.");
            StackTraceElement [] stackTrace = exc.getStackTrace();
            for (int i=0; i<stackTrace.length; ++i) {
                System.out.printf ( "%s: %d\t Method: %s\n", stackTrace[i].getFileName(),
                    stackTrace[i].getLineNumber(), stackTrace[i].getMethodName());
            }
        }
    }
    public void stopModule () {
        super.stopModule();
    }
}

class ResponceModule extends DevilModule {
    private Devil devil;

    private class TestResponcer extends DevilEventHandler {
        public TestResponcer () {
            super (Flag.FAST);
        }
        public void handle (DevilEvent event) {
            DevilResponceEvent responce = (DevilResponceEvent) event;
            if (responce.getType() == "Request_Test") {
                devil.raiseEvent (new TestResponceEvent(responce.getID()));
            }
        }
    }

    public void runModule (Devil devil) {
        this.devil = devil;

    }
}
