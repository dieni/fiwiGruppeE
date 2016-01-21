package fiwiGruppeE;

public class DailyValue {
    private String date;
    private float value;
    
    public DailyValue(String date, String value) {
	setDate(date);
	setValue(Float.parseFloat(value));
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
