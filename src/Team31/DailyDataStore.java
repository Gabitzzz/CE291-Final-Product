package Team31;

public class DailyDataStore {
    protected String date;
    protected long newToday;

    DailyDataStore(String date, long newToday) {  // constructor, sets protected variables equal to the provided variables
        this.date = date;
        this.newToday = newToday;

    }
}
