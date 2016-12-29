import java.io.Serializable;
import java.util.Random;

class DurationInformation implements Serializable {


    private final int _msgByteSizeEaten = 12;  // figure out if calculating this for all props is worth your while,
    private Integer _id;
    private long _pitcherStartTime;
    private long _pitcherEndTime;
    private long _catcherStartTime;
    private byte[] _additionalData;

    //if not, explain what 12 is.

    public void SetId(Integer id) {
        this._id = id;
    }

    public void SetPitcherStartTime() {
        this._pitcherStartTime = System.nanoTime();
    }

    public void SetPitcherEndTime() {
        this._pitcherEndTime = System.nanoTime();
    }

    public void SetCatcherStartTim() {
        this._catcherStartTime = System.nanoTime();
    }

    // calculate time needed to send and receive info
    public float GetSendTime() {
        return (this._catcherStartTime - this._pitcherStartTime) / 1000000.0f;
    }

    public float GetReceiveTime() {
        return (this._pitcherEndTime - this._catcherStartTime) / 1000000.0f;
    }

    public float GetTotalTime() {
        return (this._pitcherEndTime - this._pitcherStartTime) / 1000000.0f;
    }

    public void SetAdditionalData(int size) {

        _additionalData = new byte[size - _msgByteSizeEaten];
        new Random().nextBytes(_additionalData);
    }

}
