import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.TimerTask;

class SendMessage extends TimerTask {
    private final Message m;
    private final int port;
    private final int size;
    private final String hostname;
    private final int counter;

    public SendMessage(int port, int size, String hostname, int counter, Message m) {
        this.port = port;
        this.size = size;
        this.hostname = hostname;
        this.counter = counter;
        this.m = m;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(hostname, port);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            DurationInformation info = new DurationInformation();
            info.SetId(counter);
            info.SetPitcherStartTime();
            info.SetAdditionalData(size);
            m.AddNewSentMsg(counter);
            os.writeObject(info);

            InputStream in = socket.getInputStream();
            ObjectInputStream is = new ObjectInputStream(in);
            DurationInformation returnMessage = (DurationInformation) is.readObject();
            socket.close();

            returnMessage.SetPitcherEndTime();
            m.AddNewReceivedMsg(counter);
            m.CalculateNewValues(returnMessage.GetTotalTime(), returnMessage.GetReceiveTime(), returnMessage.GetSendTime());
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
