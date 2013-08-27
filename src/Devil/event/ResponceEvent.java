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
 * Class of responce Event.
 * <p>
 * Request-Responce mechanism needed in case when one module whants some data
 * from enother. The data provider hav to subscribe for Request_For_Such_Data_Event
 * and raise ResponceEvent in responce. Each Request has its own ID, to identifficate
 * the responce for the special request.
 *
 * @see Event
 * @see RequestEvent
 * @see RequestID
 */

public abstract class ResponceEvent extends Event {
    private RequestID id;
    
    /**
     * Constructor of responce.
     * <p> 
     * Recives ID of request and type of responce. The rusult type of event will be "Responce_" + responce_type.
     * ID mast be got from recieved request.
     *
     * @param   id              identifficator of request.
     * @param   request_type    type of request.
     */
    public ResponceEvent (RequestID id, String responce_type) {
        super ( "Responce_" + responce_type );
        this.id = id;
    }

    /**
     * Constructs responce as copy.
     * <p>
     * TODO copy for Event class.
     *
     * @param   source  source request
     */
    public ResponceEvent (ResponceEvent source) {
        super (source);
        id = source.id;
    }
   
    /**
     * Request id getter.
     *
     * @return              id of responce.
     * @see     RequestID
     */
    public RequestID getID () {
        return this.id;
    }

    /**
     * Compare request's id with passed id.
     * TODO copmare with Event class.
     *
     * @param   id      id to compare with
     * @return          true if request's id equals the passed id, false otherwize.
     */
    public boolean checkID (RequestID id) {
        return this.id.equal(id);
    }
}
