package Team31;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//---------------------------------------------------------------------------//
//    Generates the Main Menu Frame and Includes the Button Functionality    //
//---------------------------------------------------------------------------//

// Template for our GUI.
public class Template
{
    public Template()
    {
        // Main frame and panel for the GUI.
        JFrame frame = new JFrame("Main Menu");
        JPanel panel = new JPanel();

        // Headings of the graphs
        JLabel deaths = new JLabel("Deaths Graph Preview");
        JLabel cases = new JLabel("Cases Graph Preview");

        // All functional buttons
        JButton deathB = new JButton("Deaths Full Report");
        JButton casesB = new JButton("Cases Full Report");
        JButton PredictCases = new JButton("Predict Cases");
        JButton PredictDeaths = new JButton("Predict Deaths");

        // Calls the method which creates a new frame with predicted death values
        PredictDeaths.addActionListener(e -> makePredictedDeathsGraph());

        // Calls the method which creates a new frame with predicted cases values
        PredictCases.addActionListener(e -> makePredictedCasesGraph());

        // Generates the detailed version of deaths graph
        deathB.addActionListener(e -> {
            Data Data2 = new Data();
            Data2.readFile("deaths");
            ArrayList<DataStore> deaths1 = Data2.getDeathsArray();
            Graph2 graphs2 = new Graph2(deaths1);
            graphs2.createAndShowGui();

        });
        // Generates the detailed version of the cases graph
        casesB.addActionListener(e -> {
            Data Data = new Data();
            Data.readFile("cases");
            ArrayList<DataStore> cases1 = Data.getCasesArray();

            Graph graph = new Graph(cases1);
            graph.createAndShowGui();
        });


        panel.setSize(new Dimension(1200, 600));
        panel.setLayout(null);

        // Setting the position and size of the GUI elements
        deaths.setBounds(150, 50, 200, 50);
        cases.setBounds(700, 50, 200, 50);
        deathB.setBounds(150, 400, 200, 50);
        casesB.setBounds(700, 400, 200, 50);
        PredictCases.setBounds(700, 480, 200, 50);
        PredictDeaths.setBounds(150,480,200,50);

        // Adding the GUI elements to the panel
        panel.add(deaths);
        panel.add(cases);
        panel.add(deathB);
        panel.add(casesB);
        panel.add(PredictCases);
        panel.add(PredictDeaths);

        // Generating the smaller previews of the both graphs
        Graph graph = new Graph(getCasesData());
        Graph2 graph2 = new Graph2(getDeathsData());

        // Setting the position and size of the graphs
        graph.setBounds(600, 100, 500, 300);
        panel.add(graph);
        graph2.setBounds(50, 100, 500, 300);
        panel.add(graph2);

        frame.setSize(new Dimension(1200, 600));
        frame.add(panel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private ArrayList<DataStore> getCasesData()   // Gets the original cases data
    {
        ArrayList<DataStore> casesArray = new ArrayList<>();
        Data Data = new Data();
        Data.readFile("cases");
        ArrayList<DataStore> casesTemp = Data.getCasesArray();

        // Reverting the original data
        for (int i = 0; i < casesTemp.size(); i++)
        {
            int temp = (casesTemp.size() - 1) - i;
            String date = casesTemp.get(temp).date;
            long newToday = casesTemp.get(temp).newToday;
            long cumulative = casesTemp.get(temp).cumulative;
            if (temp % 7 == 0)
            {
                casesArray.add( new DataStore(date, newToday, cumulative));
            }
        }
        return casesArray;
    }

    private ArrayList<DataStore> getDeathsData()   // Getting the original deaths data
    {
        ArrayList<DataStore> deathsArray = new ArrayList<>();
        Data Data2 = new Data();
        Data2.readFile("deaths");
        ArrayList<DataStore> deathsTemp = Data2.getDeathsArray();

        // Reverting the original data
        for (int i = 0; i < deathsTemp.size(); i++)
        {
            int temp = (deathsTemp.size() - 1) - i;
            String date = deathsTemp.get(temp).date;
            long newToday = deathsTemp.get(temp).newToday;
            long cumulative = deathsTemp.get(temp).cumulative;
            if (temp % 7 == 0)
            {
                deathsArray.add( new DataStore(date, newToday, cumulative));
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
        ArrayList<DataStore> deaths = Data.getDeathsArray();
        ArrayList<DataStore> deathsForGraph = new ArrayList<>();

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
                deathsForGraph.add( new DataStore(date, newToday, cumulative));
            }
        }

        // Adding the training data.
        for (int i = 0; i < deathsForGraph.size(); i++)
        {
            long cumulative = deathsForGraph.get(i).cumulative;

            if (i >= 31)   // Training the system after week 31
            {
                xTime.add(i);
                yDeaths.add(cumulative);
            }
        }

        // Calculating the predictions
        LinearRegression deathsPrediction = new LinearRegression(xTime, yDeaths);
        long step = 34;   // Last week of the original data

        // Adding prediction values to the graph data
        for (int i = 0; i < 10; i++)
        {
            long result = deathsPrediction.predictCumulatives(step);
            step++;
            String date = i + " weeks after last death";

            deathsForGraph.add(new DataStore(date, 0, result));
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
        // Getting the original deaths data
        Data Data = new Data();
        Data.readFile("cases");

        // ArrayLists for training data
        ArrayList<DataStore> cases = Data.getCasesArray();
        ArrayList<DataStore> casesForGraph = new ArrayList<>();

        ArrayList<Integer> xTime = new ArrayList<>();
        ArrayList<Long> yDeaths = new ArrayList<>();

        // Reverting the original data and prepare the data for graph
        for (int i = 0; i < cases.size(); i++)
        {
            int temp = (cases.size() - 1) - i;
            String date = cases.get(temp).date;
            long newToday = cases.get(temp).newToday;
            long cumulative = cases.get(temp).cumulative;
            if (temp % 7 == 0)
            {
                casesForGraph.add( new DataStore(date, newToday, cumulative));
            }
        }

        // Adding the training data.
        for (int i = 0; i < casesForGraph.size(); i++)
        {
            long cumulative = casesForGraph.get(i).cumulative;

            if (i >= 34)   // Training the system after week 34
            {
                xTime.add(i);
                yDeaths.add(cumulative);
            }
        }

        // Calculating the predictions
        LinearRegression deathsPrediction = new LinearRegression(xTime, yDeaths);
        long step = 39;   // Last week of the original data

        // Adding prediction values to the graph data
        for (int i = 0; i < 10; i++)
        {
            long result = deathsPrediction.predictCumulatives(step);
            step++;
            String date = i + " weeks after last death";

            casesForGraph.add(new DataStore(date, 0, result));
        }

        Graph predictedDeathGraph = new Graph(casesForGraph);
        predictedDeathGraph.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("Cases Prediction");
        frame.setPreferredSize(new Dimension(1000, 800));

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(predictedDeathGraph, BorderLayout.NORTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
