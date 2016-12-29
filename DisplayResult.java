import java.util.TimerTask;

class DisplayResult extends TimerTask {
    private final Message _m;

    public DisplayResult(Message m) {
        _m = m;
    }

    public void run() {
        _m.SetMessageCount();
        _m.DisplayMessage();
        _m.ResetValues();
    }
}
