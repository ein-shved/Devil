package Devil.Event;
import Devil.*;

public class DevilRequestSender {
    private static class Request extends DevilRequestEvent {
        public Request (DevilRequestID id, String type) {
            super(id, type);
        }
    }

    private static class Handler extends DevilResponceHandler {
        volatile boolean handled;
        private DevilResponceEvent responce;

        public Handler(DevilRequestID id) {
            super(id, Flag.FAST);
        }
        public synchronized boolean isHandled (boolean set) {
            if (!set) {
                if (!handled) {
                    try {
                        this.wait();
                    } catch (InterruptedException exc) {}
                }
            } else {
                handled = true;
                this.notify();
            }
            return handled;
        }

        protected void handleResponce (DevilResponceEvent responce) {
            isHandled(true);
            this.responce = responce;
        }

        public DevilResponceEvent getResponce () {
            return this.responce;
        }
    }

    private static volatile long id_counter = 0;
    public static DevilResponceEvent sendWaitRequest (Devil devil, String request_type) {
        DevilRequestID id = new DevilRequestID(id_counter++);
        Handler handler = new Handler(id);
        DevilRequestEvent request = new Request(id, request_type);

        handler.subscribe (devil, request_type);
        devil.raiseEvent (request);

        while (!handler.isHandled(false));
        return handler.getResponce();
    }
    public static boolean subscribeResponcer (Devil devil, String request_type, DevilEventHandler responcer) {
        return devil.subscribeForEvent("Responce_" + request_type, responcer);
    }
    public static boolean unsubscribeResponcer (Devil devil, String request_type, DevilEventHandler responcer) {
        return devil.unsubscribeForEvent("Responce_" + request_type, responcer);
    }

}
