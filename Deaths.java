package assignment;

public class Deaths extends Data {
    protected String date;
    protected long newToday;
    protected long cumulative;

    Deaths(String date, long newToday, long cumulative) { // constructor, sets protected variables equal to the provided variables
        this.date = date;
        this.newToday = newToday;
        this.cumulative = cumulative;
    }
}
