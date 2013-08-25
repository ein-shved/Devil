package Devil.Event;
import Devil.*;

public abstract class DevilResponceHandler extends DevilEventHandler {
    private DevilRequestID id;
    private Devil devil;
    private String type;
    public DevilResponceHandler (DevilRequestID id, Flag ... flags) {
        //TODO exclude ONCE
        super (flags);
        this.id = id;
    }
    public void handle (DevilEvent event) {
        DevilResponceEvent responce = (DevilResponceEvent) event;
        if (responce.checkID(this.id)) {
            devil.unsubscribeForEvent(this.type, this);
            handleResponce (responce);
        }
    }
    public boolean subscribe (Devil devil, String responce) {
        this.devil = devil;
        this.type = "Responce_" + responce;
        return devil.subscribeForEvent(this.type, this);
    }
    protected abstract void handleResponce (DevilResponceEvent responce);
}
