package Devil.Event;

public class DevilRequestID {
    private long value;
    
    public RequestID (long _value) {
        value = _value;
    }
    public RequestID ( RequestID source ) {
        value = source.value;
    }
    public boolean equal (RequestID id) {
        return value == id.value;
    }
}


