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
