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

public abstract class DevilRequestEvent extends DevilEvent {
    private DevilRequestID id;

    public DevilRequestEvent (DevilRequestID id, String request_type) {
        super ( "Request_" + request_type );
        this.id = id;
    }
    public DevilRequestEvent (DevilRequestEvent source) {
        super (source);
        this.id = source.id;
    }

    public DevilRequestID getID () {
        return this.id;
    }
    public boolean checkID (DevilRequestID id) {
        return this.id.equal(id);
    }
}
