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

/**
 * Class of module.
 * <p>
 * Each module is represented by .class file, which contains class,
 * inherits from this one. To load module cpecify the class like in 
 * 'java -cp . class' command.
 */

public abstract class Module {
    private String name;
    private ModuleManager manager;

    /**
     * Called when loaded.
     * <p>
     * This method must initialise and start module. It haven't to
     * finish immideately. Even all module work can be done in this method.
     *
     * @param   devil   devil in person.
     */
    public abstract void runModule (Devil devil);

    /**
     * Called befire removing module.
     * <p>
     * This method must be called by all inheritors and each inheritor mast
     * stop the module work.
     */
    public void stopModule () {
        manager.removeModule (this);
    }

    void setModuleName (String name) {
        this.name = name;
    }
    void setModuleManager (ModuleManager manager) {
        this.manager = manager;
    }

    /** 
     * Module name getter.
     *  <p>
     * Returns the string, the module was loaded by.
     *
     * @return  module name.
     */
    public String getModuleName () {
        return name;
    }
}
