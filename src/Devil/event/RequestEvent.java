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

/**
 * Class of request Event.
 * <p>
 * Request-Responce mechanism needed in case when one module whants some data
 * from enother. The data provider hav to subscribe for Request_For_Such_Data_Event
 * and raise ResponceEvent in responce. Each Request has its own ID, to identifficate
 * the responce for the special request.
 *
 * @see Event
 * @see RequestID
 * @see ResponceEvent
 */

public abstract class RequestEvent extends Event {
    private RequestID id;

    /**
     * Return prefix of all RequestEvents' type.
     *
     * @return  common request prefix
     */
    public static String prefix () {
        return "Request_";
    }

    /**
     * Constructor of request.
     * <p> 
     * Recives ID of request and type of request. The rusult type of event will be "Request_" + request_type.
     * Unique ID can be got from RequestSender.
     *
     * @param   id              identifficator of request.
     * @param   request_type    type of request.
     * @see     RequestSender
     */
    public RequestEvent (RequestID id, String request_type) {
        super ( prefix() + request_type );
        this.id = id;
    }

    /**
     * Constructs request as copy.
     * <p>
     * If source is RequestEvent or ResponceEvent id will be copied, else ClassCastException
     * will be thrown.
     *
     * @param   source              source request
     * @throws  ClassCastException  if source is not RequestEvent or ResponceEvent.
     */
    public RequestEvent (Event source)
            throws ClassCastException {
        super ();
        try {
            RequestEvent request = (RequestEvent) source;
            this.id = request.id;
            super.setType (source.getType());
            return;
        } catch (ClassCastException exc) {}
        ResponceEvent responce = (ResponceEvent) source;
        String type = source.getType();
        String resp_prefix = ResponceEvent.prefix();
        if (!type.startsWith(resp_prefix)) {
            throw new ClassCastException ("Wrong ResponceEvent type suffix");
        }
        super.setType (type.replaceFirst(resp_prefix, prefix()));
        this.id = responce.getID();
    }

    /**
     * Request id getter.
     *
     * @return          id of request
     * @see     RequestID
     */
    public RequestID getID () {
        return this.id;
    }

    /**
     * Compare request's id with passed id.
     *
     * @param   id      id to compare with
     * @return          true if request's id equals the passed id, false otherwize.
     */
    public boolean checkID (RequestID id) {
        return this.id.equal(id);
    }

    /**
     * Compare request's id with id of passed event.
     *
     * @param   id      id to compare with
     * @return          true if event is Request or Responce and this.id equals the passed event id, false otherwize.
     */
    public boolean checkID (Event event) {
         try {
            ResponceEvent responce = (ResponceEvent) event;
            return responce.checkID(this.id);
        } catch (ClassCastException exc) {}
        try {
            RequestEvent request = (RequestEvent) event;
            return this.id == request.id;
        } catch (ClassCastException exc) {}
        return false;
    }
}
