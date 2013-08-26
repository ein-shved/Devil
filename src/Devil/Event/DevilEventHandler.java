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

import java.util.*;

public abstract class DevilEventHandler {
    public enum Flag {
        ONCE,
        FAST;
    }
    EnumSet<Flag> flags; 
    public DevilEventHandler (Flag ... flags) {
        if ( flags.length == 0) {
            this.flags = EnumSet.noneOf (Flag.class);
        } else {
            this.flags = EnumSet.of (flags[0], Arrays.copyOfRange(flags, 1, flags.length));
        }
    }
    public void setFlag (Flag flag) {
        this.flags.add(flag);
    }
    public void unsetFlag (Flag flag) {
        this.flags.remove(flag);
    }
    public boolean hasFlag (Flag flag) {
        return this.flags.contains(flag);
    }



    public abstract void handle (DevilEvent event);
}
