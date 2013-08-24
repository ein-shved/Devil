package Devil.Event;

public abstract class DevilEvent {
    private String type;

    public DevilEvent (String _type) {
        type = _type;
    }
    public String getType () {
        return type;
    }
    public DevilEvent (DevilEvent source) {
        type = source.type;
    }
}



