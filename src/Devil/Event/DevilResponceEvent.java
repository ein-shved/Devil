package Devil.Event;

public abstract class DevilResponceEvent extends DevilEvent {
    private DevilRequestID id;

    public DevilResponceEvent (DevilRequestID id, String responce_type) {
        super ( "Responce_" + responce_type );
        this.id = id;
    }
    public DevilResponceEvent (DevilResponceEvent source) {
        super (source);
        id = source.id;
    }
    
    public DevilRequestID getID () {
        return this.id;
    }
    public boolean checkID (DevilRequestID id) {
        return this.id.equal(id);
    }
}
