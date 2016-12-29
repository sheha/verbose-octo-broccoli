import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Message {
    private Integer _messageCountTotal = 0;
    private Integer _messagePerSecond = 0;
    private float _averageTimePerScond = 0;
    private float _averageTimeFromCtoP = 0;
    private float _averageTimeFromPtoC = 0;
    private float _maxTotalTime = 0;
    private List<Integer> _sentMessages = new ArrayList<Integer>();
    private List<Integer> _receivedMessages = new ArrayList<Integer>();

    private String GetFormattedDate() {
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        return sdfTime.format(new Date());
    }

    public void SetMessageCount() {
        _messageCountTotal = _messageCountTotal + _messagePerSecond;
    }

    private int GetMessageCount() {
        return _messageCountTotal;
    }

    private void SetPerSecond() {
        _messagePerSecond++;
    }

    private int GetPerSecond() {
        return _messagePerSecond;
    }

    private void ResetPerSecondCount() {
        _messagePerSecond = 0;
    }

    private float GetAverageTimePerSecond() {
        return _averageTimePerScond;
    }

    private void SetAverageTimePerSecond(double totalTime) {
        _averageTimePerScond = (float) (_averageTimePerScond + totalTime) / _messagePerSecond;
    }

    private void ResetAverageTimePerSecond() {
        _averageTimePerScond = 0;
    }

    private float GetAverageTimeFromCtoP() {
        return _averageTimeFromCtoP;
    }

    private void SetAverageTimeFromCtoP(double totalTime) {
        _averageTimeFromCtoP = (float) (_averageTimeFromCtoP + totalTime) / _messagePerSecond;
    }

    private void ResetAverageTimeFromCtoP() {
        _averageTimeFromCtoP = 0;
    }

    private float GetAverageTimeFromPtoC() {
        return _averageTimeFromPtoC;
    }

    private void SetAverageTimeFromPtoC(double totalTime) {
        _averageTimeFromPtoC = (float) (_averageTimeFromPtoC + totalTime) / _messagePerSecond;
    }

    private void ResetAverageTimeFromPtoC() {
        _averageTimeFromPtoC = 0;
    }

    private void SetMaxTotalTime(float newTotalTime) {
        if (_maxTotalTime == 0) {
            _maxTotalTime = newTotalTime;
        } else if (newTotalTime > _maxTotalTime) {
            _maxTotalTime = newTotalTime;
        }
    }

    private float GetMaxTotalTime() {
        return _maxTotalTime;
    }

    public void CalculateNewValues(float totalTime, float receiveTime, float sendTime) {
        SetPerSecond();
        SetAverageTimePerSecond(totalTime);
        SetAverageTimeFromCtoP(receiveTime);
        SetAverageTimeFromPtoC(sendTime);
        SetMaxTotalTime(totalTime);
    }

    public void ResetValues() {
        ResetPerSecondCount();
        ResetAverageTimePerSecond();
        ResetAverageTimeFromCtoP();
        ResetAverageTimeFromPtoC();
        ResetMessages();
    }

    public void AddNewReceivedMsg(Integer id) {
        _receivedMessages.add(id);
    }

    public void AddNewSentMsg(Integer id) {
        _sentMessages.add(id);
    }

    private void ResetMessages() {
        _receivedMessages = new ArrayList<Integer>();
        _sentMessages = new ArrayList<Integer>();
    }

    private ArrayList<Integer> RetrieveLostMsg() {
        ArrayList<Integer> lostMessages = new ArrayList<Integer>();
        for (int id : _sentMessages) {
            if (!_receivedMessages.contains(id)) {
                lostMessages.add(id);
            }
        }
        return lostMessages;
    }

    public void DisplayMessage() {
        String newLine = System.lineSeparator();
        System.out.println(GetFormattedDate() + " Total msg: " + GetMessageCount() + " Total msg per sec: " + GetPerSecond() + " Max total time: " + GetMaxTotalTime() + newLine +
                "Avg time per sec: " + GetAverageTimePerSecond() + " Avg send time: " + GetAverageTimeFromPtoC() + " Avg recieve time: " + GetAverageTimeFromCtoP() + newLine
                + CreateMessageForLostSockets() + newLine);
    }

    private String CreateMessageForLostSockets() {
        String lostMsg = "";
        ArrayList<Integer> lostMessages = RetrieveLostMsg();
        if (lostMessages.size() > 0) {
            lostMsg = "Lost Messages: ";
            for (int id : lostMessages) {
                lostMsg += id + ", ";
            }
        }
        return lostMsg;
    }


}
