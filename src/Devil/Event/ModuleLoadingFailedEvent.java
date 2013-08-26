package Devil.Event;
import Devil.*;

public class ModuleLoadingFailedEvent extends DevilEvent {
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


