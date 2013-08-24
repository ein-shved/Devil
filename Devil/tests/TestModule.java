package Devil.tests;

import Devil.*;
import java.lang.System;

public class TestModule implements DevilModule {
    public void runModule (Devil devil) {
        System.out.println(this.toString() + " loaded!");
    }
    public void stopModule () {
    }
}
