package Devil.tests;

import Devil.*;
import Devil.Event.*;

import java.lang.System;

public class ClassLoading {
    public static void main (String [] args) {
        Devil devil = new Devil();
        try {
            devil.loadClass ("Devil.tests.TestClass");
        } catch (Exception exc) {
            System.out.println (exc.toString() + " exception caughted.");
        }
        devil.finish();
    }
}
