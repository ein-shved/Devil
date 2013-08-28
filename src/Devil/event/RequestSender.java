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

package Devil.event;
import Devil.*;

public class RequestSender {
    private static class Request extends RequestEvent {
        public Request (RequestID id, String type) {
            super(id, type);
        }
    }

    private static class Handler extends ResponceHandler {
        volatile boolean handled;
        private ResponceEvent responce;

        public Handler(RequestID id) {
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

        protected void handleResponce (ResponceEvent responce) {
            isHandled(true);
            this.responce = responce;
        }

        public ResponceEvent getResponce () {
            return this.responce;
        }
    }

    private static volatile long id_counter = 0;
    public static ResponceEvent sendWaitRequest (Devil devil, String request_type) {
        RequestID id = new RequestID(id_counter++);
        Handler handler = new Handler(id);
        RequestEvent request = new Request(id, request_type);

        handler.subscribe (devil, request_type);
        devil.raiseEvent (request);

        while (!handler.isHandled(false));
        return handler.getResponce();
    }
    public static boolean subscribeResponcer (Devil devil, String request_type, EventHandler responcer) {
        return devil.subscribeForEvent(ResponceEvent.prefix() + request_type, responcer);
    }
    public static boolean unsubscribeResponcer (Devil devil, String request_type, EventHandler responcer) {
        return devil.unsubscribeForEvent(ResponceEvent.prefix() + request_type, responcer);
    }

}
