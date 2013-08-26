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
import Devil.Module;

/**
 * Evenet raised by ModuleManager when is succeed do load module.
 * <p>
 * Event contains link to loaded module.
 * 
 * @see Event
 * @see Module
 * @see ModuleLoadingFailedEvent
 */
public class NewModuleLoadedEvent extends Event {
    private Module module;

    /**
     * Usefull method which statically return type of this Event.
     * <p>
     * "New_Module_Loaded_Event"
     *
     * @return  type of this event.
     */
    public static String type () {
        return "New_Module_Loaded_Event";
    }

    NewModuleLoadedEvent (Module module) {
        super (type());
        this.module = module;
    }

    /**
     * Module link getter.
     *
     * @return new loaded module.
     */
    public Module getModule () {
        return this.module;
    }
}


