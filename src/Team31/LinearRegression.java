package Team31;

import java.util.ArrayList;

//-------------------------------------------------------------------//
//  Predicts a new Cumulative Value According to the Value of weeks  //
//-------------------------------------------------------------------//

// Linear Regression Model.
public class LinearRegression
{
    // ArrayList for holding the training data
    private ArrayList<Integer> xTime ;
    private ArrayList<Long> yCumulative;

    // Constructor to add the training data
    public LinearRegression(ArrayList<Integer> xTime, ArrayList<Long> yDeaths)
    {
        this.xTime = xTime;
        this.yCumulative = yDeaths;
    }

    // Calculating the mean of the x-axis
    public long getXmean(){
        // Variables to hold the results of the prediction
        long result1 = 0;
        for (Integer integer : xTime) {
            result1 = result1 + integer;
        }
        return result1 / xTime.size();

    }

    // Calculating the mean of the y-axis
    public  long getYmean(){
        long result2 = 0;
        for (Long aLong : yCumulative) {
            result2 = result2 + aLong;
        }
        return  result2 / yCumulative.size();
    }

    // Calculating the gradient of the training data
    public long getGradient(long Xmean, long Ymean, long x1, long y1){
        long num1= x1 - Xmean;
        long num2 = y1 - Ymean;
        long denom = (x1 - Xmean) * (x1 -Xmean);
        return (num1 * num2) /denom;
    }

    // Getting the y-intercept of the training data
    public long getYIntercept (long getXmean , long getYmean, long lineslope){
        return getYmean - (lineslope *getXmean);
    }

    // Calculating the prediction value
    public long predictCumulatives(long input){
        long x1 = ((xTime.get(0)));
        long y1 = ((yCumulative.get(0)));
        int Xmean = (int) getXmean();
        long Ymean= getYmean();
        long lineslope = getGradient(Xmean, Ymean, x1,y1);
        long YIntercept = getYIntercept(Xmean,Ymean,lineslope);
        return (lineslope * input) + YIntercept;
    }
}
