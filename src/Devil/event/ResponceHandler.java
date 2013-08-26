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

public abstract class ResponceHandler extends EventHandler {
    private RequestID id;
    private Devil devil;
    private String type;
    public ResponceHandler (RequestID id, Flag ... flags) {
        //TODO exclude ONCE
        super (flags);
        this.id = id;
    }
    public void handle (Event event) {
        ResponceEvent responce = (ResponceEvent) event;
        if (responce.checkID(this.id)) {
            devil.unsubscribeForEvent(this.type, this);
            handleResponce (responce);
        }
    }
    public boolean subscribe (Devil devil, String responce) {
        this.devil = devil;
        this.type = "Responce_" + responce;
        return devil.subscribeForEvent(this.type, this);
    }
    protected abstract void handleResponce (ResponceEvent responce);
}
