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
