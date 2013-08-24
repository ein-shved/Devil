package Devil;

public abstract class DevilModule {
    private String name;
    private DevilModuleManager manager;
    public abstract void runModule (Devil devil);

    //Must be called by children
    public void stopModule () {
        manager.removeModule (this);
    }

    void setModuleName (String name) {
        this.name = name;
    }
    void setModuleManager (DevilModuleManager manager) {
        this.manager = manager;
    }

    public String getModuleName () {
        return name;
    }
}
