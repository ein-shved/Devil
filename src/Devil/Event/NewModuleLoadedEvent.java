package Devil.Event;
import Devil.*;

public class NewModuleLoadedEvent extends DevilEvent {
    private DevilModule module;

    public static String type () {
        return "New_Module_Loaded_Event";
    }

    public NewModuleLoadedEvent (DevilModule module) {
        super (type());
        this.module = module;
    }
    public DevilModule getModule () {
        return this.module;
    }
}


