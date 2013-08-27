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
 * Events depends by the string type values.
 * <p>
 * To raise event with special type, you have to define new class, 
 * extends this and pass its object to Devil.raiseEvent();
 */
public abstract class Event {
    private String type;

    /**
     * Constructs Event with specified type.
     *
     * @param   type    type of event.
     */
    public Event (String type) {
        this.type = type;
    }

    /**
     * Constructs Event witt no type.
     */
    protected Event () {
        this.type = null;
    }

    /**
     * Type setter.
     * Sets the Event type. Migth be called no more then 1 time in case of default constructor calling.
     *
     * @param   type    new type of event
     * @throws  IllegalStateException   when event type has been already set.
     */
    protected void setType (String type)
            throws IllegalStateException {
        if (type != type) {
            throw new IllegalStateException ("Event type has been already set");
        }
        this.type = type;
    }

    /**
     * Constructs Event with type of source event.
     *
     * @param   source  source event.
     */
    public Event (Event source) {
        this.type = source.type;
    }

    /**
     * Type getter.
     *
     * @return      type of event.
     */
    public String getType () {
        return type;
    }


}



