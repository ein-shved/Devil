package Devil;

public abstract class DevilModule {
    private String moduleName;
    public abstract void runModule (Devil devil);
    public abstract void stopModule ();

    void setModuleName (String name) {
        moduleName = name
    }
    String getModuleName () {
        return moduleName;
    }
}
