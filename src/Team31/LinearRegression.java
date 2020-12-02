package Team31;

import java.util.ArrayList;

// Linear Regression Model..
public class LinearRegression
{
    private ArrayList<Integer> xTime ;
    private ArrayList<Long> yCumulative;
    private long result1;
    private long result2;

    public LinearRegression(ArrayList xTime, ArrayList yDeaths)
    {
        this.xTime = xTime;
        this.yCumulative = yDeaths;
    }

    public long getXmean(ArrayList<Integer> cases1){
        result1 = 0;
        for (int i = 0; i < xTime.size(); i++){
            result1= result1 + xTime.get(i);
        }
        return result1 / xTime.size();

    }

    public  long getYmean(ArrayList<Long> cases2){
        result2=0;
        for(int i=0; i < yCumulative.size(); i++){
            result2=result2+yCumulative.get(i);
        }
        return  result2 / yCumulative.size();
    }

    public long getLineSlope(long Xmean, long Ymean, long x1, long y1){
        long num1= x1 - Xmean;
        long num2 = y1 - Ymean;
        long denom = (x1 - Xmean) * (x1 -Xmean);
        return (num1 * num2) /denom;
    }

    public long getYIntercept (long getXmean , long getYmean, long lineslope){
        return getYmean - (lineslope *getXmean);
    }


    public long predictCumulatives(long input){
        long x1 = ((xTime.get(0)));
        long y1 = ((yCumulative.get(0)));
        int Xmean = (int) getXmean(xTime);
        long Ymean= getYmean(yCumulative);
        long lineslope = getLineSlope(Xmean, Ymean, x1,y1);
        long YIntercept = getYIntercept(Xmean,Ymean,lineslope);
        long prediction = (lineslope * input) + YIntercept;
        return prediction;
    }
}
