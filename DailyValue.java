package fiwiGruppeE;

public class DailyValue {
    private String date;
    private double value;
    
    public DailyValue(String date, String value) {
	setDate(date);
	setValue(Double.parseDouble(value));
    }
    
    public DailyValue(String date, double value) {
	setDate(date);
	setValue(value);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
