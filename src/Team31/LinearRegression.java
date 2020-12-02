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

    // Variables to hold the results of the prediction
    private long result1;
    private long result2;

    // Constructor to add the training data
    public LinearRegression(ArrayList xTime, ArrayList yDeaths)
    {
        this.xTime = xTime;
        this.yCumulative = yDeaths;
    }

    // Calculating the mean of the x-axis
    public long getXmean(ArrayList<Integer> cases1){
        result1 = 0;
        for (int i = 0; i < xTime.size(); i++){
            result1= result1 + xTime.get(i);
        }
        return result1 / xTime.size();

    }

    // Calculating the mean of the y-axis
    public  long getYmean(ArrayList<Long> cases2){
        result2=0;
        for(int i=0; i < yCumulative.size(); i++){
            result2=result2+yCumulative.get(i);
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
        int Xmean = (int) getXmean(xTime);
        long Ymean= getYmean(yCumulative);
        long lineslope = getGradient(Xmean, Ymean, x1,y1);
        long YIntercept = getYIntercept(Xmean,Ymean,lineslope);
        long prediction = (lineslope * input) + YIntercept;
        return prediction;
    }
}