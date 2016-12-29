import java.util.Timer;

class Pitcher {
    private final Message m;
    private final int port;
    private final int mps;
    private final int size;
    private final String hostname;
    private int counter;

    public Pitcher(int port, int mps, int size, String hostname) {

        this.port = port;
        this.mps = mps;
        this.size = size;
        this.hostname = hostname;
        this.counter = 0;
        m = new Message();
    }

    public void startPitcher() {
        Timer timer = new Timer();
        timer.schedule(new SendMessage(port, size, hostname, counter, m), 0, 1000 / mps);
        timer.schedule(new DisplayResult(m), 0, 1000);
        counter++;
    }

}
