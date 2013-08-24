package Devil.Event;

public class DevilRequestID {
    private long value;
    
    public DevilRequestID (long value) {
        this.value = value;
    }
    public DevilRequestID ( DevilRequestID source ) {
        this.value = source.value;
    }
    public boolean equal ( DevilRequestID id) {
        return this.value == id.value;
    }
}


