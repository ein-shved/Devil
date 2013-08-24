package Devil.Event;

public abstract class DevilResponceEvent extends DevilEvent {
    private RequestID id;

    public DevilResponceEvent (RequestID _id, String responce_type) {
        super ( "Responce_" + responce_type );
        id = new RequestID(_id);
    }
    public DevilResponceEvent (DevilResponceEvent source) {
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
