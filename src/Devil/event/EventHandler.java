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

import java.util.*;

/**
 * Handler of devil's events.
 * <p>
 * You have to extends this class to subscribe for event.
 * handle() method of this class will be called when event, your method subscribed for
 * occures.
 *
 * @see Event
 * @see Devil.subscribeForEvent
 */

public abstract class EventHandler {
    /**
     * Pass this flags to EventHandler constructor.
     */
    public enum Flag {
        /**
         * This flag means, that handler will be called once.
         * <p>
         * After first call of this handler, its subscribtions will be removed.
         */
        ONCE,
        /**
         * This flag means, that new thread for EventHandler.handle won't be created.
         * <p>
         * Passing this flag, you have to ensure, that your handler will finish in short time.
         */
        FAST;
    }
    EnumSet<Flag> flags; 

    /**
     * Handler constructor.
     * <p>
     * Constructs EventHandler with specified flags.
     *
     * @param   flags   the set of flags.
     */
    public EventHandler (Flag ... flags) {
        if ( flags.length == 0) {
            this.flags = EnumSet.noneOf (Flag.class);
        } else {
            this.flags = EnumSet.of (flags[0], Arrays.copyOfRange(flags, 1, flags.length));
        }
    }

    /**
     * Sets new flag.
     *
     * @param   flag    flag to set.
     * @see     EventHandler.Flag
     */
    public void setFlag (Flag flag) {
        this.flags.add(flag);
    }

    /**
     * Unsets new flag.
     *
     * @param   flag    flag to unset.
     * @see     EventHandler.Flag
     */
    public void unsetFlag (Flag flag) {
        this.flags.remove(flag);
    }

    /**
     * Checks if flag is setted.
     *
     * @param   flag    flag to check.
     * @return          true if handler contains flag, false otherwize.
     * @see     EventHandler.Flag
     */
    public boolean hasFlag (Flag flag) {
        return this.flags.contains(flag);
    }

    /**
     * Handler in person.
     * <p>
     * This method will be called when the event it subscribed for raises.
     *
     * @param   event   raised event.
     * @see     Event
     */
    public abstract void handle (Event event);
}
