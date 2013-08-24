package Devil.tests;

import Devil.*;
import Devil.Event.*;

import java.lang.System;

public class ModuleLoadingTest {
    public static void main (String [] args) {
        Devil devil = new Devil();
        try {
            devil.loadModule ("Devil.tests.TestModule");
        } catch (Exception exc) {
            System.out.println (exc.toString() + " exception caughted.");
        }
        devil.finish();
    }
}
