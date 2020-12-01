package Team31;

public class Cases extends Data {
    protected String date;
    protected long newToday;
    protected long cumulative;

    Cases(String date, long newToday, long cumulative) {  // constructor, sets protected variables equal to the provided variables
        this.date = date;
        this.newToday = newToday;
        this.cumulative = cumulative;
    }
}

