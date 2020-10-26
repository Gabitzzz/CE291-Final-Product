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

    @Override
    public String getDate() { // overrides method in parent class, returns the date
        return date;
    }

    @Override
    public long getNewToday() { // overrides method in parent class, returns the number of new deaths on the given day
        return newToday;
    }

    @Override
    public long getCumulative() { // overrides method in parent class, returns the cumulative total deaths
        return cumulative;
    }
}
