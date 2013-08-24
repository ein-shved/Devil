package Devil.Event;

public abstract class DevilRequestEvent extends DevilEvent {
    private RequestID id;

    public DevilRequestEvent (RequestID _id, String request_type) {
        super ( "Request_" + request_type );
        id = new RequestID(_id);
    }
    public DevilRequestEvent (DevilRequestEvent source) {
        super (source);
        id = new RequestID(source.id);
    }

    public RequestID getID () {
        return id;
    }
    public boolean checkID (RequestID _id) {
        return id.equal(_id);
    }
}
