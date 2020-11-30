package Team31;

public class DataStore {
    protected String date;
    protected long newToday;
    protected long cumulative;

    DataStore(String date, long newToday, long cumulative) {  // constructor, sets protected variables equal to the provided variables
        this.date = date;
        this.newToday = newToday;
        this.cumulative = cumulative;
    }
}
