package Devil.Event;

public abstract class DevilRequestEvent extends DevilEvent {
    private DevilRequestID id;

    public DevilRequestEvent (DevilRequestID id, String request_type) {
        super ( "Request_" + request_type );
        this.id = id;
    }
    public DevilRequestEvent (DevilRequestEvent source) {
        super (source);
        this.id = source.id;
    }

    public DevilRequestID getID () {
        return this.id;
    }
    public boolean checkID (DevilRequestID id) {
        return this.id.equal(id);
    }
}
