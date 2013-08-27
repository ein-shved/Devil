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

package Devil;
import Devil.event.*;

/**
 * Evenet raised by ModuleManager when it failed do load module.
 * <p>
 * Event contains classname module, was be tryed to load and failure description.
 * 
 * @see Event
 * @see Module
 * @see NewModuleLoadedEvent
 */

public class ModuleLoadingFailedEvent extends Event {
    private String name;
    private String description;

    /**
     * Usefull method which statically return type of this Event.
     * <p>
     * "New_Module_Loaded_Event"
     *
     * @return  type of this event.
     */
    public static String type () {
        return "Module_Loading_Failed_Event";
    }

    ModuleLoadingFailedEvent (String request, String description) {
        super (type());
        this.name = request;
        this.description = description;
    }
    
    /**
     * Getter of classname, module was tried to load with.
     *
     * @return classname, module was tried to load with.
     */
    public String getRequest () {
        return this.name;
    }

    /**
     * Getter of failure reason.
     *
     * @return the description, why module can not be loaded.
     */
    public String getDescription () {
        return this.description;
    }
}


