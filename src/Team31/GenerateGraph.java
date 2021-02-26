package Team31;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

//------------------------------------------------------------//
//    Configures and Generates a Line Graph for Cases Data    //
//------------------------------------------------------------//

// te
public class GenerateGraph extends JPanel {

    private static ArrayList<DataStore> casesArray;
    private static ArrayList<DataStore> deathsArray;
    private int padding = 25;
    private int labelPadding = 30;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private int DataChoice;
    private String PresentationFormat;

    public GenerateGraph(int DataChoice, String PresentationFormat)
    {
        this.DataChoice = DataChoice;
        this.PresentationFormat = PresentationFormat;

        if (DataChoice == 0){this.casesArray = getCasesData();}
        else if (DataChoice == 1){this.deathsArray = getDeathsData();}
    }

    public GenerateGraph(int DataChoice, ArrayList<DataStore> data)
    {
        this.DataChoice = DataChoice;
        this.PresentationFormat = "weekly";

        if (DataChoice == 0){casesArray = data;}
        else if (DataChoice == 1){deathsArray = data;}
    }


    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g3 = (Graphics2D) g;
        g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ArrayList<DataStore> graph = new ArrayList<>();
        if (DataChoice == 0){graph = this.casesArray;}
        else if (DataChoice == 1){graph = this.deathsArray;}

        double xScale = (getWidth() - (2 * padding) - labelPadding) / (graph.size() - 1);
        double yScale = (getHeight() - 2 * padding - labelPadding) / (getMaxCase() - getMinCase());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < graph.size(); i++)
        {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = 0;
            if (PresentationFormat == "weekly") { y1 = (int) ((getMaxCase() - graph.get(i).cumulative) * yScale + padding); }
            else if ( PresentationFormat == "daily"){y1 = (int) ((getMaxCase() - graph.get(i).newToday) * yScale + padding); }
            graphPoints.add(new Point(x1, y1));
        }

        //Sketching the background of the graph
        g3.setColor(Color.WHITE);
        g3.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);


        // Creating Y-Axis
        for (int i = 0; i < numberYDivisions + 1; i++)
        {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (graph.size() > 0) {
                g3.setColor(gridColor);
                g3.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g3.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinCase() + (getMaxCase() - getMinCase()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                yLabel = String.valueOf(Math.round(Double.parseDouble(yLabel))); //Round the numbers to integers as they are presented to the csv
                //Implement Y-axis numbers
                FontMetrics metrics = g3.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g3.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g3.drawLine(x0, y0, x1, y1);
        }

        // Creating X-Axis
        for (int i = 0; i < graph.size(); i++) {
            if (graph.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (graph.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((graph.size() / 20.0)) + 1)) == 0) {
                    g3.setColor(gridColor);
                    g3.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g3.setColor(Color.BLACK);
                    String xLabel = i + "";
                    //Implement X-axis numbers
                    FontMetrics metrics = g3.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g3.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g.drawLine(x0, y0, x1, y1);
            }
        }

        // Creating Both AXES
        g3.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g3.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        Stroke oldStroke = g3.getStroke();
        g3.setColor(lineColor);
        g3.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g3.drawLine(x1, y1, x2, y2);
        }

        g3.setStroke(oldStroke);
        g3.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g3.fillOval(x, y, ovalW, ovalH);
            g3.setColor(Color.black);
        }

        g3.drawString("Weeks After First Case", 50, 580);
    }
    //Using functions to be displayed below the graphs to show a better statistic report
    //For example it will be displayed the minimum of cases that were on that range of days.
    private double getMinCase() {
        int minCase = Integer.MAX_VALUE;
        ArrayList<DataStore> graph = new ArrayList<>();
        if (DataChoice == 0){graph = this.casesArray;}
        else if (DataChoice == 1){graph = this.deathsArray;}

        for (DataStore case1 : graph) {

            minCase = Math.min(minCase, (int)case1.cumulative);

        }
        return minCase;
    }

    private int getMaxCase() {
        int maxCase = Integer.MIN_VALUE;
        ArrayList<DataStore> graph = new ArrayList<>();
        if (DataChoice == 0){graph = this.casesArray;}
        else if (DataChoice == 1){graph = this.deathsArray;}

        for (DataStore case1 : graph) {
            maxCase = Math.max(maxCase, (int) case1.cumulative);

        }
        return maxCase;
    }

    private int getMinNew() {
        int minCases = Integer.MAX_VALUE;
        ArrayList<DataStore> graph = new ArrayList<>();
        if (DataChoice == 0){graph = this.casesArray;}
        else if (DataChoice == 1){graph = this.deathsArray;}

        for (DataStore case1 : graph) {
            minCases = Math.min(minCases, (int)case1.newToday);
        }
        return minCases;
    }

    private int getMaxNew() {
        int maxDeaths = Integer.MIN_VALUE;
        ArrayList<DataStore> graph = new ArrayList<>();
        if (DataChoice == 0){graph = this.casesArray;}
        else if (DataChoice == 1){graph = this.deathsArray;}

        for (DataStore case1 : graph) {
            maxDeaths = Math.max(maxDeaths, (int)case1.newToday);
        }
        return maxDeaths;
    }

    public void createAndShowGui(){
        {
            ArrayList<DataStore> data = new ArrayList<>();
            GenerateGraph mainGraph = new GenerateGraph(-1, "default");
            if (DataChoice == 0)
            {
                data = getCasesData();
                mainGraph = new GenerateGraph(0, "weekly");
            }
            else if (DataChoice == 1)
            {
                data = getDeathsData();
                mainGraph = new GenerateGraph(1, "weekly");
            }


            mainGraph.setPreferredSize(new Dimension(800, 600));
            JFrame frame = new JFrame("Cases Graph");
            frame.setPreferredSize(new Dimension(1000, 800));
            JPanel labelPanel = new JPanel();

            JLabel peakCases = new JLabel("---PEAK VALUE OF CASES: " + getMaxCase() + "---");
            JLabel minCases = new JLabel("---MINIMUM VALUE OF CASES: " + getMinCase() + "---");

            JLabel maxNew = new JLabel("---MAXIMUM CASES IN ONE DAY: " + getMaxNew() + "---");
            peakCases.setBounds(100, 300, 10, 10);
            JLabel minNew = new JLabel("---MINIMUM CASES IN ONE DAY: " + getMinNew() + "---");
            minCases.setBounds(100, 400, 10, 10);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            labelPanel.add(peakCases);
            labelPanel.add(minCases);
            labelPanel.add(maxNew);
            labelPanel.add(minNew);
            frame.add(mainGraph, BorderLayout.NORTH);
            frame.add(labelPanel, FlowLayout.CENTER);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }
    }

    private ArrayList<DataStore> getCasesData()   // Gets the original cases data
    {
        ArrayList<DataStore> casesArray = new ArrayList<>();
        Data Data = new Data();
        Data.readFile(Config.CASES_FILE);
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
        Data2.readFile(Config.DEATHS_FILE);
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
}
