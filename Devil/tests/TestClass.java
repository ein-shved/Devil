package Devil.tests;

import Devil.*;
import Devil.ClassManagement.*;
import java.lang.System;

public class TestClass implements DevilSharedClass {
    public void loadClass (Devil devil) {
        System.out.println(this.toString() + " loaded!");
    }
    public void unloadClass () {
    }
}
