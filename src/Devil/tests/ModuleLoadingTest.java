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

package Devil.tests;

import Devil.*;
import Devil.Event.*;

import java.lang.System;

public class ModuleLoadingTest {
    public static void main (String [] args) {
        Devil devil = new Devil();
        try {
            devil.loadModuleRequest ("Devil.tests.TestModule");
        } catch (Exception exc) {
            System.out.println (exc.toString() + " exception was caughted.");
            StackTraceElement [] stackTrace = exc.getStackTrace();
            for (int i=0; i<stackTrace.length; ++i) {
                System.out.printf ( "%s: %d\t Method: %s\n", stackTrace[i].getFileName(),
                    stackTrace[i].getLineNumber(), stackTrace[i].getMethodName());
            }
        }
        devil.finish();
    }
}
