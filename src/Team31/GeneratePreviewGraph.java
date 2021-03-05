package Team31;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class GeneratePreviewGraph extends JPanel {

    private static ArrayList<DataStore> casesArray;
    private static ArrayList<DataStore> deathsArray;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int DataChoice;
    private String PresentationFormat;

    public GeneratePreviewGraph(int DataChoice, String PresentationFormat)
    {
        this.DataChoice = DataChoice;
        this.PresentationFormat = PresentationFormat;

        if (DataChoice == 0){ casesArray = getData(DataChoice);}
        else if (DataChoice == 1){ deathsArray = getData(DataChoice);}
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g3 = (Graphics2D) g;
        g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ArrayList<DataStore> graph = new ArrayList<>();
        if (DataChoice == Config.CASES_FILE){graph = casesArray;}
        else if (DataChoice == Config.DEATHS_FILE){graph = deathsArray;}

        int padding = 25;
        int labelPadding = 30;
        double xScale = (float)(getWidth() - (2 * padding) - labelPadding) / (graph.size() - 1);
        double yScale = (getHeight() - 2 * padding - labelPadding) / (getMaxCase() - getMinCase());


        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < graph.size(); i++)
        {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1;
            if (PresentationFormat.equals(Config.WEEKLY))
            {
                if (i % 7 == 0) { y1 = (int) ((getMaxCase() - graph.get(i).cumulative) * yScale + padding); graphPoints.add(new Point(x1, y1)); }
            }
            else if (PresentationFormat.equals(Config.DAILY))
            {
                y1 = (int) ((getMaxCase() - graph.get(i).newToday) * yScale + padding); graphPoints.add(new Point(x1, y1));
            }
            else
            {y1 = (int) ((getMaxCase() - graph.get(i).cumulative) * yScale + padding); graphPoints.add(new Point(x1, y1));
            }
        }

        //Sketching the background of the graph
        g3.setColor(Color.WHITE);
        g3.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);


        // Creating Y-Axis
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

        // Creating X-Axis
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
                    if (PresentationFormat.equals(Config.WEEKLY)) { xLabel = i / 7 + " "; }
                    else { xLabel = i + " "; }
                    //Implement X-axis numbers
                    FontMetrics metrics = g3.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g3.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                if (i % 2 == 0)g.drawLine(x0, y0, x0, y1);
                else {g.drawLine(x0, y0, x0, y1);}
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
        for (Point graphPoint : graphPoints) {
            int x = graphPoint.x - pointWidth / 2;
            int y = graphPoint.y - pointWidth / 2;
            if (PresentationFormat.equals(Config.DAILY)){g3.fillOval(x, y, pointWidth / 2, pointWidth / 2);}
            else {g3.fillOval(x, y, pointWidth, pointWidth);}
            g3.setColor(Color.black);
        }

    }
    //Using functions to be displayed below the graphs to show a better statistic report
    //For example it will be displayed the minimum of cases that were on that range of days.
    private double getMinCase() {
        int minCase = Integer.MAX_VALUE;
        ArrayList<DataStore> graph = new ArrayList<>();
        if (DataChoice == Config.CASES_FILE){graph = casesArray;}
        else if (DataChoice == Config.DEATHS_FILE){graph = deathsArray;}

        for (DataStore case1 : graph)
        {
            if (PresentationFormat.equals(Config.WEEKLY)){minCase = Math.min(minCase, (int)case1.cumulative);}
            else if (PresentationFormat.equals(Config.DAILY)) {minCase = Math.min(minCase, (int)case1.newToday);}

        }
        return minCase;
    }

    private int getMaxCase() {
        int maxCase = Integer.MIN_VALUE;
        ArrayList<DataStore> graph = new ArrayList<>();
        if (DataChoice == Config.CASES_FILE){graph = casesArray;}
        else if (DataChoice == Config.DEATHS_FILE){graph = deathsArray;}

        for (DataStore case1 : graph) {
            if (PresentationFormat.equals(Config.WEEKLY)){maxCase = Math.max(maxCase, (int)case1.cumulative);}
            else if (PresentationFormat.equals(Config.DAILY)) {maxCase = Math.max(maxCase, (int)case1.newToday);}

        }
        return maxCase;
    }

    private ArrayList<DataStore> getData (int choice)   // Getting the original data
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
        // Reverting the original data
        for (int i = 0; i < tempData.size(); i++)
        {
            int temp = (tempData.size() - 1) - i;
            String date = tempData.get(temp).date;
            long newToday = tempData.get(temp).newToday;
            long cumulative = tempData.get(temp).cumulative;
            //if (temp % 7 == 0)
            //{
            currentData.add( new DataStore(date, newToday, cumulative));
            //}
        }
        return currentData;
    }
}
