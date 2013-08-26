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
import Devil.*;

public class ModuleLoadingFailedEvent extends Event {
    private String name;
    private String description;

    public static String type () {
        return "Module_Loading_Failed_Event";
    }

    public ModuleLoadingFailedEvent (String request, String description) {
        super (type());
        this.name = request;
        this.description = description;
    }
    public String getRequest () {
        return this.name;
    }
    public String getDescription () {
        return this.description;
    }
}


