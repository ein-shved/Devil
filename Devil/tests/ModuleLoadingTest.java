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
