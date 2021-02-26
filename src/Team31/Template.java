package Team31;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//---------------------------------------------------------------------------//
//    Generates the Main Menu Frame and Includes the Button Functionality    //
//---------------------------------------------------------------------------//
//hello
// Template for our GUI.
public class Template
{
    public Template()
    {
        // Main frame and panel for the GUI.
        JFrame frame = new JFrame("Main Menu");
        JPanel panel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JScrollPane scrollBar = new JScrollPane (panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Headings of the graphs
        JLabel deaths = new JLabel("Deaths Graph Preview");
        JLabel cases = new JLabel("Cases Graph Preview");

        // All functional buttons
        JButton deathB = new JButton("Deaths Full Report");
        JButton casesB = new JButton("Cases Full Report");
        JButton PredictCases = new JButton("Predict Cases");
        JButton PredictDeaths = new JButton("Predict Deaths");

        // JButton dailyA = new JButton("Daily case Report");
        // JButton dailyB = new JButton("Daily death Report");

        // Calls the method which creates a new frame with predicted death values
        PredictDeaths.addActionListener(e -> makePredictedDeathsGraph());

        // Calls the method which creates a new frame with predicted cases values
        PredictCases.addActionListener(e -> makePredictedCasesGraph());

        // Generates the detailed version of deaths graph
        deathB.addActionListener(e -> {
            Data data = new Data();
            data.readFile(Config.DEATHS_FILE);
            GenerateGraph graph = new GenerateGraph(1, "weekly");
            graph.createAndShowGui();

        });
        // Generates the detailed version of the cases graph
        casesB.addActionListener(e -> {
            Data data = new Data();
            data.readFile(Config.CASES_FILE);
            GenerateGraph graph = new GenerateGraph(0, "weekly");
            graph.createAndShowGui();
        });

        /*TODO, dailyA is having an error
        */
       /* dailyA.addActionListener(e -> {
            Data Data = new Data();
            Data.readFile(Config.CASES_FILE);
            ArrayList<DailyDataStore> cases1 = Data.getDailyCaseArray();

            Graph3 graph3 = new Graph(cases1);
            graph3.createAndShowGui();
        });*/

        panel.setPreferredSize(new Dimension(1180, 800));
        buttonPanel.setPreferredSize(new Dimension(1200, 200));
        panel.setLayout(null);
        buttonPanel.setLayout(null);

        // Setting the position and size of the GUI elements
        deaths.setBounds(150, 50, 200, 50);
        cases.setBounds(700, 50, 200, 50);
        deathB.setBounds(150, 440, 200, 50);
        casesB.setBounds(700, 440, 200, 50);
        PredictCases.setBounds(700, 500, 200, 50);
        PredictDeaths.setBounds(150,500,200,50);

        // Adding the GUI elements to the panel
        panel.add(deaths);
        panel.add(cases);
        buttonPanel.add(deathB);
        buttonPanel.add(casesB);
        buttonPanel.add(PredictCases);
        buttonPanel.add(PredictDeaths);

        // Generating the smaller previews of the both graphs
        GenerateGraph graph = new GenerateGraph(0, "weekly");
        GenerateGraph graph2 = new GenerateGraph(1, "weekly");
        GenerateGraph graph3 = new GenerateGraph(0, "daily");
        GenerateGraph graph4 = new GenerateGraph(1, "daily");

        // Setting the position and size of the graphs
        graph.setBounds(600, 100, 500, 300);
        panel.add(graph);
        graph2.setBounds(50, 100, 500, 300);
        panel.add(graph2);
        graph3.setBounds(600, 420, 500, 300);
        panel.add(graph3);
        graph4.setBounds(50, 420, 500, 300);
        panel.add(graph4);


        frame.setSize(new Dimension(1200, 600));
        scrollBar.setSize(new Dimension(1180, 420));
        scrollBar.getVerticalScrollBar().setUnitIncrement(14);
        frame.add(scrollBar);
        frame.add(buttonPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Generates and shows the graph of predicted deaths
    private void makePredictedDeathsGraph()
    {
        // Getting the original deaths data
        Data Data = new Data();
        Data.readFile(Config.DEATHS_FILE);
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
        GenerateGraph predictedDeathGraph = new GenerateGraph(1, deathsForGraph);
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
        Data.readFile(Config.CASES_FILE);

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

        GenerateGraph predictedDeathGraph = new GenerateGraph(0, casesForGraph);
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
