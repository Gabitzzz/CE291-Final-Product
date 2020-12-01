package Team31;

import com.sun.jdi.FloatType;

import java.util.ArrayList;

public class LinearRegression {

    private ArrayList<Cases> xcases ;
    private ArrayList<Deaths> ydeaths;
    private long result1;
    private long result2;

    public long getXmean(ArrayList<Cases> cases1){
        result1 = 0;
        for (Integer i=0; i<xcases.size(); i++){
            result1= result1 + xcases.get(i).newToday;
        }
        return result1 /xcases.size();

    }
    public  long getYmean(ArrayList<Cases> cases2){
        result2=0;
        for(Integer i=0; i<xcases.size(); i++){
            result2=result2+xcases.get(i).cumulative;
        }
        return  result2/xcases.size();
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


    public long predictcases(long inputvalue){
        long x1 = ((xcases.get(0).newToday));
        long y1 = ((xcases.get(0).cumulative));
        long Xmean = getXmean(xcases);
        long Ymean= getYmean(xcases);
        long lineslope = getLineSlope(Xmean, Ymean, x1,y1);
        long YIntercept = getYIntercept(Xmean,Ymean,lineslope);
        long prediction = (lineslope*inputvalue) +YIntercept;
        return prediction;





    }

}
