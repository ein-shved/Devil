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
 * Request identifier.
 * <p>
 * RequestID is simple long value.
 *
 * @see RequestEvent
 * @see ResponceEvent
 */

public class RequestID {
    private long value;

    /**
     * Constructs RequestID with its walue.
     *
     * @param   value   
     */
    public RequestID (long value) {
        this.value = value;
    }

    /**
     * Constructs RequestID as copy of source.
     *
     * @param   source      RequestID value of which will be copied
     */
    public RequestID ( RequestID source ) {
        this.value = source.value;
    }

    /**
     * Compare with enother id
     *
     * @param   id          RequestID to compare with.
     * @return              true if id is equal to current.  
     */
    public boolean equal ( RequestID id) {
        return this.value == id.value;
    }
}


