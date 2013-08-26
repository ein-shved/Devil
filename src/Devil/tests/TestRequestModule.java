package Devil.tests;
import Devil.*;
import Devil.Event.*;

public class TestRequestModule extends DevilModule {
    private Devil devil;

    private class ResponceModuleEventHandler extends DevilEventHandler {
        public ResponceModuleEventHandler () {
            super();
        }
        public void handle (DevilEvent event) {
            NewModuleLoadedEvent module_event = (NewModuleLoadedEvent) event;
            DevilModule responceModule = module_event.getModule();
            System.out.println ("RequestModule have got module '" + responceModule.getModuleName()
                    + "' loaded event.");
            if (responceModule.getModuleName() == RequestTest.responceModuleName) {
                DevilResponceEvent responce = DevilRequestSender.sendWaitRequest (devil, "Test");
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
