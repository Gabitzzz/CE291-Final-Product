package Team31;

import javax.swing.*;
import java.awt.*;
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

    public LinearRegression(){}

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

    // Generates and shows the graph of predicted deaths
    public void makePredictedGraph(int dataChoice)
    {
        Data data = new Data();
        ArrayList<DataStore> array = new ArrayList<>();
        ArrayList<DataStore> arrayForGraph = new ArrayList<>();

        // Getting the original deaths data
        if (dataChoice == Config.CASES_FILE)
        {
            data.readFile(Config.CASES_FILE);
            array = data.getCasesArray();
        }
        else if (dataChoice == Config.DEATHS_FILE)
        {
            data.readFile(Config.DEATHS_FILE);
            array = data.getDeathsArray();
        }

        // ArrayLists for training data
        ArrayList<Integer> xTime = new ArrayList<>();
        ArrayList<Long> yDeaths = new ArrayList<>();

        // Reverting the original data and prepare the data for graph
        for (int i = 0; i < array.size(); i++)
        {
            int temp = (array.size() - 1) - i;
            String date = array.get(temp).date;
            long newToday = array.get(temp).newToday;
            long cumulative = array.get(temp).cumulative;
            if (temp % 7 == 0)
            {
                arrayForGraph.add( new DataStore(date, newToday, cumulative));
            }
        }

        // Adding the training data.
        int n = arrayForGraph.size() - 1;
        for (int i = 0; i < 5; i++)
        {
            long cumulative = arrayForGraph.get(n - i).cumulative;

            xTime.add(n - i);
            yDeaths.add(cumulative);
        }

        // Calculating the predictions
        LinearRegression deathsPrediction = new LinearRegression(xTime, yDeaths);
        long step = arrayForGraph.size();   // Last week of the original data

        // Adding prediction values to the graph data
        for (int i = 0; i < 10; i++)
        {
            long result = deathsPrediction.predictCumulatives(step);
            step++;
            String date = i + " weeks after last death";

            arrayForGraph.add(new DataStore(date, 0, result));
        }

        // Making the graph and generating the frame
        GenerateGraph predictedDeathGraph = new GenerateGraph(dataChoice, arrayForGraph);
        predictedDeathGraph.setPreferredSize(new Dimension(1200, 700));
        JFrame frame = new JFrame("Deaths Prediction");
        frame.setPreferredSize(new Dimension(1400, 900));

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(predictedDeathGraph, BorderLayout.NORTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}