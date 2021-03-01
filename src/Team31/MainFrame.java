package Team31;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//---------------------------------------------------------------------------//
//    Generates the Main Menu Frame and Includes the Button Functionality    //
//---------------------------------------------------------------------------//
//hello
// Template for our GUI.
public class MainFrame
{
    public MainFrame()
    {
        // Main frame and panel for the GUI.
        JFrame frame = new JFrame("Main Menu");
        JPanel panel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JScrollPane scrollBar = new JScrollPane (panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Headings of the graphs
        JLabel wDeaths = new JLabel("Weekly Deaths Graph Preview");
        JLabel wCases = new JLabel("Weekly Cases Graph Preview");
        JLabel dDeaths = new JLabel("Daily Deaths Graph Preview");
        JLabel dCases = new JLabel("Daily Cases Graph Preview");

        // All functional buttons
        JButton deathB = new JButton("Deaths Full Report");
        JButton casesB = new JButton("Cases Full Report");
        JButton PredictCases = new JButton("Predict Cases");
        JButton PredictDeaths = new JButton("Predict Deaths");



        // Calls the method which creates a new frame with predicted death values
        PredictDeaths.addActionListener(e -> makePredictedDeathsGraph(Config.DEATHS_FILE));

        // Calls the method which creates a new frame with predicted cases values
        PredictCases.addActionListener(e -> makePredictedDeathsGraph(Config.CASES_FILE));

        // Generates the detailed version of deaths graph
        deathB.addActionListener(e -> {
            GenerateGraph graph = new GenerateGraph(Config.DEATHS_FILE, Config.WEEKLY);
            graph.createAndShowGui();

        });
        // Generates the detailed version of the cases graph
        casesB.addActionListener(e -> {
            GenerateGraph graph = new GenerateGraph(Config.CASES_FILE, Config.WEEKLY);
            graph.createAndShowGui();
        });



        panel.setPreferredSize(new Dimension(1180, 800));
        buttonPanel.setPreferredSize(new Dimension(1200, 100));
        panel.setLayout(null);
        buttonPanel.setLayout(null);

        // Setting the position and size of the GUI elements
        wDeaths.setBounds(150, 50, 200, 50);
        wCases.setBounds(700, 50, 200, 50);
        dDeaths.setBounds(150, 400, 200, 50);
        dCases.setBounds(700, 400, 200, 50);
        deathB.setBounds(150, 580, 200, 50);
        casesB.setBounds(700, 580, 200, 50);
        PredictCases.setBounds(700, 640, 200, 50);
        PredictDeaths.setBounds(150,640,200,50);

        // Adding the GUI elements to the panel
        panel.add(wDeaths);
        panel.add(wCases);
        panel.add(dDeaths);
        panel.add(dCases);
        buttonPanel.add(deathB);
        buttonPanel.add(casesB);
        buttonPanel.add(PredictCases);
        buttonPanel.add(PredictDeaths);

        // Generating the smaller previews of the both graphs
        GenerateGraph graph = new GenerateGraph(Config.CASES_FILE, Config.WEEKLY);
        GenerateGraph graph2 = new GenerateGraph(Config.DEATHS_FILE, Config.WEEKLY);
        GenerateGraph graph3 = new GenerateGraph(Config.CASES_FILE, Config.DAILY);
        GenerateGraph graph4 = new GenerateGraph(Config.DEATHS_FILE, Config.DAILY);

        // Setting the position and size of the graphs
        graph.setBounds(600, 100, 500, 300);
        panel.add(graph);
        graph2.setBounds(50, 100, 500, 300);
        panel.add(graph2);
        graph3.setBounds(600, 460, 500, 300);
        panel.add(graph3);
        graph4.setBounds(50, 460, 500, 300);
        panel.add(graph4);


        frame.setSize(new Dimension(1200, 740));
        scrollBar.setSize(new Dimension(1180, 560));
        scrollBar.getVerticalScrollBar().setUnitIncrement(14);
        frame.add(scrollBar);
        frame.add(buttonPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Generates and shows the graph of predicted deaths
    private void makePredictedDeathsGraph(int dataChoice)
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
        for (int i = 0; i < 3; i++)
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
        predictedDeathGraph.setPreferredSize(new Dimension(1000, 700));
        JFrame frame = new JFrame("Deaths Prediction");
        frame.setPreferredSize(new Dimension(1200, 900));

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(predictedDeathGraph, BorderLayout.NORTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}