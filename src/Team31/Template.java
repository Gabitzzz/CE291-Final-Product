package Team31;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//----------------------------------------------------//
//    !!! DON'T DELETE ANY COMMENTED CODE YET !!!     //
//----------------------------------------------------//

// Template for our GUI.
public class Template
{
    public Template()
    {
        // Main frame and panel for the GUI.
        JFrame frame = new JFrame("Main Menu");
        JPanel panel = new JPanel();

        JLabel deaths = new JLabel("Deaths Graph Preview");
        JLabel cases = new JLabel("Cases Graph Preview");

        JButton deathB = new JButton("Deaths Full Report");
        JButton casesB = new JButton("Cases Full Report");
        JButton PredictCases = new JButton("Predict Cases");
        JButton PredictDeaths = new JButton("Predict Deaths");

        PredictDeaths.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                makePredictedDeathsGraph();
            }
        });

       PredictCases.addActionListener(new ActionListener()
       {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               makePredictedCasesGraph();
            }
        });

        deathB.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //NewFrame(1);
                Data Data2 = new Data();
                Data2.readFile("deaths");
                ArrayList<Deaths> deaths = Data2.getDeathsArray();
                Graph2 graphs2 = new Graph2(deaths);
                graphs2.createAndShowGui();

            }
        });
        casesB.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //NewFrame(2);
                Data Data = new Data();
                Data.readFile("cases");
                ArrayList<Cases> cases = Data.getCasesArray();

                Graph graph = new Graph(cases);
                graph.createAndShowGui();
            }
        });


        panel.setSize(new Dimension(1200, 600));
        panel.setLayout(null);

        deaths.setBounds(150, 50, 200, 50);
        cases.setBounds(700, 50, 200, 50);
        deathB.setBounds(150, 400, 200, 50);
        casesB.setBounds(700, 400, 200, 50);
        PredictCases.setBounds(700, 480, 200, 50);
        PredictDeaths.setBounds(150,480,200,50);

        panel.add(deaths);
        panel.add(cases);
        panel.add(deathB);
        panel.add(casesB);
        panel.add(PredictCases);
        panel.add(PredictDeaths);

        Graph graph = new Graph(getCasesData());
        Graph2 graph2 = new Graph2(getDeathsData());

        graph.setBounds(600, 100, 500, 300);
        panel.add(graph);
        graph2.setBounds(50, 100, 500, 300);
        panel.add(graph2);


        frame.setSize(new Dimension(1200, 600));
        frame.add(panel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private ArrayList<Cases> getCasesData()
    {
        ArrayList<Cases> casesArray = new ArrayList<>();
        Data Data = new Data();
        Data.readFile("cases");
        ArrayList<Cases> casesTemp = Data.getCasesArray();

        for (int i = 0; i < casesTemp.size(); i++)
        {
            int temp = (casesTemp.size() - 1) - i;
            String date = casesTemp.get(temp).date;
            long newToday = casesTemp.get(temp).newToday;
            long cumulative = casesTemp.get(temp).cumulative;
            if (temp % 7 == 0)
            {
                casesArray.add( new Cases(date, newToday, cumulative));
            }
        }
        return casesArray;
    }

    private ArrayList<Deaths> getDeathsData()
    {
        ArrayList<Deaths> deathsArray = new ArrayList<>();
        Data Data2 = new Data();
        Data2.readFile("deaths");
        ArrayList<Deaths> deathsTemp = Data2.getDeathsArray();

        for (int i = 0; i < deathsTemp.size(); i++)
        {
            int temp = (deathsTemp.size() - 1) - i;
            String date = deathsTemp.get(temp).date;
            long newToday = deathsTemp.get(temp).newToday;
            long cumulative = deathsTemp.get(temp).cumulative;
            if (temp % 7 == 0)
            {
                deathsArray.add( new Deaths(date, newToday, cumulative));
            }
        }
        return deathsArray;
    }

    // Generates and shows the graph of predicted deaths
    private void makePredictedDeathsGraph()
    {
        // Getting the original deaths data
        Data Data = new Data();
        Data.readFile("deaths");
        ArrayList<Deaths> deaths = Data.getDeathsArray();
        ArrayList<Deaths> deathsForGraph = new ArrayList<>();

        // ArrayLists for training data
        ArrayList<Integer> xTime = new ArrayList<>();
        ArrayList<Long> yDeaths = new ArrayList<>();




        // Reverting the original data and prepare the data for graph
        for (int i = 0; i < deaths.size(); i++)
        {
            int temp = (deaths.size() - 1) - i;
            String date = deaths.get(temp).date;
            long newToday = deaths.get(temp).newToday;
            long cumulative = deaths.get(temp).cumulative;
            if (temp % 7 == 0)
            {
                deathsForGraph.add( new Deaths(date, newToday, cumulative));
            }
        }

        // Adding the training data.
        for (int i = 0; i < deathsForGraph.size(); i++)
        {
            long cumulative = deathsForGraph.get(i).cumulative;

            if (i >= 31)
            {
                xTime.add(i);
                yDeaths.add(cumulative);
            }
        }

        // Adding prediction values to the graph data
        LinearRegression deathsPrediction = new LinearRegression(xTime, yDeaths);
        long step = 34;
        for (int i = 0; i < 10; i++)
        {
            long result = deathsPrediction.predictCumulatives(step);
            step++;
            String date = i + " weeks after last death";

            deathsForGraph.add(new Deaths(date, 0, result));
        }

        // Making the graph and generating the frame
        Graph2 predictedDeathGraph = new Graph2(deathsForGraph);
        predictedDeathGraph.setPreferredSize(new Dimension(1000, 700));
        JFrame frame = new JFrame("Deaths Prediction");
        frame.setPreferredSize(new Dimension(1200, 900));

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(predictedDeathGraph, BorderLayout.NORTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Generates and shows the graph of predicted cases
    private void makePredictedCasesGraph()
    {
        Data Data = new Data();
        Data.readFile("cases");
        ArrayList<Cases> cases = Data.getCasesArray();
        ArrayList<Cases> casesForGraph = new ArrayList<>();

        ArrayList<Integer> xTime = new ArrayList<>();
        ArrayList<Long> yDeaths = new ArrayList<>();

        for (int i = 0; i < cases.size(); i++)
        {
            int temp = (cases.size() - 1) - i;
            String date = cases.get(temp).date;
            long newToday = cases.get(temp).newToday;
            long cumulative = cases.get(temp).cumulative;
            if (temp % 7 == 0)
            {
                casesForGraph.add( new Cases(date, newToday, cumulative));
            }
        }

        // Adding the training data.
        for (int i = 0; i < casesForGraph.size(); i++)
        {
            long cumulative = casesForGraph.get(i).cumulative;

            if (i >= 34)
            {
                xTime.add(i);
                yDeaths.add(cumulative);
            }
        }

        LinearRegression deathsPrediction = new LinearRegression(xTime, yDeaths);
        long step = 39;
        for (int i = 0; i < 10; i++)
        {
            long result = deathsPrediction.predictCumulatives(step);
            step++;
            String date = i + " weeks after last death";

            casesForGraph.add(new Cases(date, 0, result));
        }

        Graph predictedDeathGraph = new Graph(casesForGraph);
        predictedDeathGraph.setPreferredSize(new Dimension(1000, 700));
        JFrame frame = new JFrame("Cases Prediction");
        frame.setPreferredSize(new Dimension(1200, 900));

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(predictedDeathGraph, BorderLayout.NORTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
