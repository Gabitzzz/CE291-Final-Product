package Team31;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

//------------------------------------------------------------//
//   Configures and Generates a Line Graph for Several Inputs //
//------------------------------------------------------------//


public class GenerateGraph extends JComponent {

    // arrays to store several data
    private static ArrayList<DataStore> casesArray;
    private static ArrayList<DataStore> deathsArray;
    private static ArrayList<DataStore> otherArray;
    private static ArrayList<DataStore> currentArray;
    private static String label;

    // configurations for graph
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private Color forecastLine = new Color(255, 0, 0);
    private Color modellingLine = new Color(0, 128, 0);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);

    // configurations for different selections
    private int originalSize;
    private int DataChoice;
    private String PresentationFormat;
    private boolean isPredictionGraph = false;

    public GenerateGraph(int DataChoice, String PresentationFormat)   // constructor for pre-selected data
    {
        this.DataChoice = DataChoice;
        this.PresentationFormat = PresentationFormat;

        if (DataChoice == 0){ casesArray = currentArray = getData(DataChoice); label = "Cases";}
        else if (DataChoice == 1){ deathsArray = currentArray = getData(DataChoice); label = "Deaths";}
    }

    public GenerateGraph(int DataChoice, ArrayList<DataStore> data)   // constructor for prediction graphs
    {
        this.DataChoice = DataChoice;
        this.PresentationFormat = Config.WEEKLY;
        originalSize = getOriginalSize(DataChoice);
        isPredictionGraph = true;
        otherArray = data;

        if (DataChoice == 0){label = "Cases";}
        else if (DataChoice == 1){label = "Deaths";}
    }

    public GenerateGraph(ArrayList<DataStore> data)   // constructor for selected data
    {
        otherArray = currentArray = data;
        DataChoice = Config.OTHER_FILE;
        PresentationFormat = Config.WEEKLY;
        isPredictionGraph = false;
        label = "Data";
    }


    @Override
    protected void paintComponent(Graphics g)   // draws the graph
    {
        super.paintComponent(g);
        Graphics2D g3 = (Graphics2D) g;
        g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // gets the data
        ArrayList<DataStore> graph = new ArrayList<>();
        if (DataChoice == Config.CASES_FILE){if (isPredictionGraph){graph = otherArray;}else {graph = casesArray;}}
        else if (DataChoice == Config.DEATHS_FILE){if (isPredictionGraph){graph = otherArray;}else {graph = deathsArray;}}
        else if (DataChoice == Config.OTHER_FILE){graph = otherArray;}

        int padding = 25;
        int labelPadding = 30;
        double xScale = (float)(getWidth() - (2 * padding) - labelPadding) / (graph.size() - 1);
        double yScale = (getHeight() - 2 * padding - labelPadding) / (getMaxCase() - getMinCase());

        // adds data to graph coordinates
        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < graph.size(); i++)
        {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1;
            if (PresentationFormat.equals(Config.WEEKLY) && !isPredictionGraph)
            {
                if (i % 7 == 0) { y1 = (int) ((getMaxCase() - graph.get(i).cumulative) * yScale + padding); graphPoints.add(new Point(x1, y1)); }
            }
            else if (PresentationFormat.equals(Config.DAILY))
            {
                y1 = (int) ((getMaxCase() - graph.get(i).newToday) * yScale + padding); graphPoints.add(new Point(x1, y1));
            }
            else
                {y1 = (int) ((getMaxCase() - graph.get(i).cumulative) * yScale + padding); graphPoints.add(new Point(x1, y1)); }
        }

        // drawing the background
        g3.setColor(Color.WHITE);
        g3.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);

        // creating Y-Axis
        int pointWidth = 4;
        int numberYDivisions = 10;
        for (int i = 0; i < numberYDivisions + 1; i++)
        {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            if (graph.size() > 0) {
                g3.setColor(gridColor);
                g3.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y0);
                g3.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinCase() + (getMaxCase() - getMinCase()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                yLabel = String.valueOf(Math.round(Double.parseDouble(yLabel))); //Round the numbers to integers as they are presented to the csv
                //Implement Y-axis numbers
                FontMetrics metrics = g3.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g3.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g3.drawLine(x0, y0, x1, y0);
        }

        // creating X-Axis
        for (int i = 0; i < graph.size(); i++) {
            if (graph.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (graph.size() - 1) + padding + labelPadding;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((graph.size() / 20.0)) + 1)) == 0) {
                    g3.setColor(gridColor);
                    g3.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x0, padding);
                    g3.setColor(Color.BLACK);
                    String xLabel;
                    if (PresentationFormat.equals(Config.WEEKLY) && !isPredictionGraph) { xLabel = i / 7 + " "; }
                    else { xLabel = i + " "; }
                    //Implement X-axis numbers
                    FontMetrics metrics = g3.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g3.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                if (!isPredictionGraph) {if (i % 2 == 0)g.drawLine(x0, y0, x0, y1);}
                else {g.drawLine(x0, y0, x0, y1);}
            }
        }

        // creating both axes
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
            if(isPredictionGraph){if (i > originalSize-1) {g3.setColor(forecastLine);}}
            g3.drawLine(x1, y1, x2, y2);

        }

        // drawing modelling line for prediction graphs
        if (isPredictionGraph)
        {
            g3.setColor(modellingLine);
            int n = graphPoints.size() - 1;
            for (int i = 1; i < graphPoints.size()/5; i++)
            {
                int i2 = i * 5;
                int x1 = graphPoints.get((i2-5)+1).x;
                int y1 = graphPoints.get((i2-5)+1).y;
                int x2;
                int y2;
                if (i == (graphPoints.size() / 5) - 1) {
                    x2 = graphPoints.get(n).x;
                    y2 = graphPoints.get(n).y;
                } else {
                    x2 = graphPoints.get(i2 + 1).x;
                    y2 = graphPoints.get(i2 + 1).y; }
                g3.drawLine(x1 - 10, y1 - 10, x2 - 10, y2 - 10);
                g3.fillOval(x1 - 12, y1 - 12, pointWidth, pointWidth);
                g3.fillOval(x2 - 12, y2 - 12, pointWidth, pointWidth);
            }
        }

        // drawing the line
        g3.setStroke(oldStroke);
        g3.setColor(pointColor);
        for (Point graphPoint : graphPoints) {
            int x = graphPoint.x - pointWidth / 2;
            int y = graphPoint.y - pointWidth / 2;
            if (PresentationFormat.equals(Config.DAILY)){g3.fillOval(x, y, pointWidth / 2, pointWidth / 2);}
            else {g3.fillOval(x, y, pointWidth, pointWidth);}
            g3.setColor(Color.black);
        }

        g3.drawString("Time", 80, 680);
        g3.drawString(label, 0, 620);
    }

    // using methods to be displayed below the graphs to show a better statistic report
    // for example it will be displayed the minimum of cases that were on that range of the time frame.
    // gets minimum cases
    private double getMinCase()
    {
        int minCase = Integer.MAX_VALUE;
        ArrayList<DataStore> graph = new ArrayList<>();
        if (DataChoice == Config.CASES_FILE){if (isPredictionGraph){graph = otherArray;}else {graph = casesArray;}}
        else if (DataChoice == Config.DEATHS_FILE){if (isPredictionGraph){graph = otherArray;}else {graph = deathsArray;}}
        else if (DataChoice == Config.OTHER_FILE){graph = otherArray;}

        for (DataStore case1 : graph)
        {
            if (PresentationFormat.equals(Config.WEEKLY)){minCase = Math.min(minCase, (int)case1.cumulative);}
            else if (PresentationFormat.equals(Config.DAILY)) {minCase = Math.min(minCase, (int)case1.newToday);}

        }
        return minCase;
    }

    // gets maximum cases
    private int getMaxCase()
    {
        int maxCase = Integer.MIN_VALUE;
        ArrayList<DataStore> graph = new ArrayList<>();
        if (DataChoice == Config.CASES_FILE){if (isPredictionGraph){graph = otherArray;}else {graph = casesArray;}}
        else if (DataChoice == Config.DEATHS_FILE){if (isPredictionGraph){graph = otherArray;}else {graph = deathsArray;}}
        else if (DataChoice == Config.OTHER_FILE){graph = otherArray;}

        for (DataStore case1 : graph) {
            if (PresentationFormat.equals(Config.WEEKLY)){maxCase = Math.max(maxCase, (int)case1.cumulative);}
            else if (PresentationFormat.equals(Config.DAILY)) {maxCase = Math.max(maxCase, (int)case1.newToday);}

        }
        return maxCase;
    }

    // gets maximum new cases in one day
    private int getMaxNew()
    {
        int maxDeaths = Integer.MIN_VALUE;
        ArrayList<DataStore> graph = new ArrayList<>();
        if (DataChoice == Config.CASES_FILE){if (isPredictionGraph){graph = otherArray;}else {graph = casesArray;}}
        else if (DataChoice == Config.DEATHS_FILE){if (isPredictionGraph){graph = otherArray;}else {graph = deathsArray;}}
        else if (DataChoice == Config.OTHER_FILE){graph = otherArray;}

        for (DataStore case1 : graph) {
            maxDeaths = Math.max(maxDeaths, (int)case1.newToday);
        }
        return maxDeaths;
    }

    // this method is for calculating original size of the predicted data to draw forecast line
    private int getOriginalSize(int choice)
    {
        Data data = new Data();
        int size = 0;
        if (choice == Config.CASES_FILE)
        {
            data.readFile(Config.CASES_FILE);
            size = data.getCasesArray().size();
        }else if (choice == Config.DEATHS_FILE)
        {
            data.readFile(Config.DEATHS_FILE);
            size = data.getDeathsArray().size();
        }else if (choice == Config.OTHER_FILE)
        {
            data = new Data(Config.OTHER_FILE_PATH);
            data.readFile(Config.OTHER_FILE);
            size = data.getOtherArray().size();
        }

        return size/7;
    }

    // finds index of a given item
    public int findItem(long item)
    {
        int max = currentArray.size() -1;
        int descCnt = max;

        for(int cnt = 0; cnt <= max/2; cnt++)
        {
            if(currentArray.get(cnt).cumulative == item) return cnt/7;
            if(currentArray.get(descCnt).cumulative == item) return descCnt/7;
            if(currentArray.get(cnt).newToday == item) return cnt/7;
            if(currentArray.get(descCnt).newToday == item) return descCnt/7;
            --descCnt;
        }
        return -1;
    }

    // generates new frame and displays it
    public void createAndShowGui(String data){
        {
            GenerateGraph mainGraph = new GenerateGraph(-1, "default");
            GenerateGraph secondGraph = new GenerateGraph(-1, "default");

            // generates the graphs
            if (DataChoice == Config.CASES_FILE)
            {
                mainGraph = new GenerateGraph(0, "weekly"); mainGraph.setPreferredSize(new Dimension(1400, 700));
                secondGraph = new GenerateGraph(0, "daily"); secondGraph.setPreferredSize(new Dimension(1400, 700));
            }
            else if (DataChoice == Config.DEATHS_FILE)
            {
                mainGraph = new GenerateGraph(1, "weekly"); mainGraph.setPreferredSize(new Dimension(1400, 700));
                secondGraph = new GenerateGraph(1, "daily"); secondGraph.setPreferredSize(new Dimension(1400, 700));
            }
            else if (DataChoice == Config.OTHER_FILE)
            {
                mainGraph = new GenerateGraph(DataChoice, "weekly"); mainGraph.setPreferredSize(new Dimension(1400, 700));
                secondGraph = new GenerateGraph(DataChoice, "daily"); secondGraph.setPreferredSize(new Dimension(1400, 700));
            }

            // frame and panel configurations
            JFrame frame = new JFrame(data + " GRAPH");
            frame.setPreferredSize(new Dimension(1500, 900));
            JPanel main = new JPanel(); main.add(mainGraph);
            JPanel second = new JPanel(); second.add(secondGraph);
            JPanel labelPanel = new JPanel(new GridLayout(2, 3, 10, 10));
            JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
            JPanel butPanel = new JPanel(new GridLayout(2, 1, 10, 10));

            // label configurations
            EmptyBorder border1 = new EmptyBorder(10, 40, 0, 20);
            labelPanel.setBorder(border1);
            JLabel peakCases = new JLabel("PEAK VALUE OF " + data + ": " + getMaxCase());
            peakCases.setFont(new Font("Helvetica", Font.BOLD, 15));
            JLabel peakNew = new JLabel("MAXIMUM " + data + " IN ONE DAY: " + getMaxNew());
            peakNew.setFont(new Font("Helvetica", Font.BOLD, 15));
            JLabel date1 = new JLabel("OCCURRED IN: WEEK " + findItem(getMaxCase()) + " | DATE: " + currentArray.get(findItem(getMaxCase())).date);
            date1.setFont(new Font("Helvetica", Font.BOLD, 15));
            JLabel date2 = new JLabel("OCCURRED IN: WEEK " + findItem(getMaxNew()) + " | DATE: " + currentArray.get(findItem(getMaxNew())).date);
            date2.setFont(new Font("Helvetica", Font.BOLD, 15));

            // option pane to display other version of the data
            String[] country ={"Weekly Graph", "Daily Graph"};
            JComboBox<String> cb = new JComboBox<>(country);
            JButton show = new JButton("SHOW SELECTION");
            show.setFont(new Font("Helvetica", Font.BOLD, 15));
            panel.add(cb);
            panel.add(show);
            show.addActionListener(e ->
            {
                String selection = (String) cb.getSelectedItem();
                assert selection != null;
                if (selection.equals("Weekly Graph")) { frame.remove(second); frame.add(main); }
                else if (selection.equals("Daily Graph")) { frame.remove(main); frame.add(second); }
                frame.revalidate();
                frame.repaint();
            });

            // other buttons for different functionalities
            JButton save = new JButton("SAVE PAGE AS .PNG");
            save.setFont(new Font("Helvetica", Font.BOLD, 15));
            save.addActionListener(e -> { Data saveFrame = new Data(); saveFrame.saveAsImage(frame); });
            JButton predict = new JButton("PREDICT FUTURE DATA");
            predict.setFont(new Font("Helvetica", Font.BOLD, 15));
            predict.addActionListener(e -> { LinearRegression showPrediction = new LinearRegression(); showPrediction.makePredictedGraph(DataChoice); });
            butPanel.add(save); butPanel.add(predict);

            // setting up the panels
            labelPanel.add(peakCases);
            labelPanel.add(date1);
            labelPanel.add(panel);
            labelPanel.add(peakNew);
            labelPanel.add(date2);
            labelPanel.add(butPanel);

            // setting up the frame
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(main, BorderLayout.NORTH);
            frame.add(labelPanel, FlowLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    private ArrayList<DataStore> getData (int choice)   // getting the original data
    {
        ArrayList<DataStore> tempData = new ArrayList<>();
        Data data;
        if (choice == Config.CASES_FILE)
        {
            data = new Data();
            data.readFile(Config.CASES_FILE);
            tempData = new ArrayList<>(data.getCasesArray());
        }
        else if (choice == Config.DEATHS_FILE)
        {
            data = new Data();
            data.readFile(Config.DEATHS_FILE);
            tempData = new ArrayList<>(data.getDeathsArray());
        }

        ArrayList<DataStore> currentData = new ArrayList<>();
        // Reversing the original data
        for (int i = 0; i < tempData.size(); i++)
        {
            int temp = (tempData.size() - 1) - i;
            String date = tempData.get(temp).date;
            long newToday = tempData.get(temp).newToday;
            long cumulative = tempData.get(temp).cumulative;
            currentData.add( new DataStore(date, newToday, cumulative));
        }
        return currentData;
    }
}
