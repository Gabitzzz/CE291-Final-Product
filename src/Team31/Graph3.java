package Team31;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

//Generate graph for daily case growth

public class Graph3 extends JPanel {

    private static ArrayList<DailyDataStore> dailyCaseArray;
    private int padding = 25;
    private int labelPadding = 30;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;

    public Graph3(ArrayList<DailyDataStore> dailyCaseArray) {
        this.dailyCaseArray = dailyCaseArray;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g3 = (Graphics2D) g;
        g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((int) getWidth() - (2 * padding) - labelPadding) / (dailyCaseArray.size() - 1);
        double yScale = ((int) getHeight() - 2 * padding - labelPadding) / (getMaxNew() - getMinNew());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < dailyCaseArray.size(); i++)
        {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((dailyCaseArray.get(i).newToday) * yScale + padding);

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
            if (dailyCaseArray.size() > 0) {
                g3.setColor(gridColor);
                g3.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g3.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinNew() + (getMaxNew() - getMinNew()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                yLabel = String.valueOf(Math.round(Double.parseDouble(yLabel))); //Round the numbers to integers as they are presented to the csv
                //Implement Y-axis numbers
                FontMetrics metrics = g3.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g3.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g3.drawLine(x0, y0, x1, y1);
        }

        // Creating X-Axis
        for (int i = 0; i < dailyCaseArray.size(); i++) {
            if (dailyCaseArray.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (dailyCaseArray.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((dailyCaseArray.size() / 20.0)) + 1)) == 0) {
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
        }

        g3.drawString("Days After First Case", 50, 580);
    }
    //Using functions to be displayed below the graphs to show a better statistic report
    //For example it will be displayed the minimum of cases that were on that range of days.

    private int getMinNew() {
        int minCases = Integer.MAX_VALUE;
        for (DailyDataStore case1 : dailyCaseArray) {
            minCases = Math.min(minCases, (int)case1.newToday);
        }
        return minCases;
    }

    private int getMaxNew() {
        int maxDeaths = Integer.MIN_VALUE;
        for (DailyDataStore case1 : dailyCaseArray) {
            maxDeaths = Math.max(maxDeaths, (int)case1.newToday);
        }
        return maxDeaths;
    }

    public void createAndShowGui(){
        {
            ArrayList<DailyDataStore> dailyCaseArray = new ArrayList<>();
            Data Data = new Data();
            Data.readFile(Config.CASES_FILE);
            ArrayList<DailyDataStore> cases3 = Data.getDailyCaseArray();

            for (int i = 0; i < cases3.size(); i++)
            {
                int temp = (cases3.size() - 1) - i;
                String date = cases3.get(temp).date;
                long newToday = cases3.get(temp).newToday;
                dailyCaseArray.add(new DailyDataStore(date, newToday));
            }

            Graph3 mainGraph = new Graph3(dailyCaseArray);
            mainGraph.setPreferredSize(new Dimension(800, 600));
            JFrame frame = new JFrame("Daily Case Graph");
            frame.setPreferredSize(new Dimension(1000, 800));
            JPanel labelPanel = new JPanel();

            JLabel maxNew = new JLabel("---MAXIMUM CASES IN ONE DAY: " + getMaxNew() + "---");
            JLabel minNew = new JLabel("---MINIMUM CASES IN ONE DAY: " + getMinNew() + "---");

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            labelPanel.add(maxNew);
            labelPanel.add(minNew);
            frame.add(mainGraph, BorderLayout.NORTH);
            frame.add(labelPanel, FlowLayout.CENTER);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }
    }
}
