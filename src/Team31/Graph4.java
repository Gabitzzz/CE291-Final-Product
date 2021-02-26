package Team31;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/*
public class Graph4 extends JPanel {

    private static ArrayList<DataStore> dailyDeathArray;
    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;

    public Graph4(ArrayList<DataStore> dailyDeathArray) {
        this.dailyDeathArray = dailyDeathArray;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g4 = (Graphics2D) g;
        g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((int) getWidth() - (2 * padding) - labelPadding) / (dailyDeathArray.size() - 1);
        double yScale = ((int) getHeight() - 2 * padding - labelPadding) / (getMaxNew() - getMinNew());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < dailyDeathArray.size(); i++)
        {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((dailyDeathArray.get(i).newToday) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        //Sketching the background of the graph
        g4.setColor(Color.WHITE);
        g4.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);


        // Creating Y-Axis
        for (int i = 0; i < numberYDivisions + 1; i++)
        {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (dailyDeathArray.size() > 0) {
                g4.setColor(gridColor);
                g4.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g4.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinNew() + (getMaxNew() - getMinNew()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                yLabel = String.valueOf(Math.round(Double.parseDouble(yLabel))); //Round the numbers to integers as they are presented to the csv
                //Implement Y-axis numbers
                FontMetrics metrics = g4.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g4.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g4.drawLine(x0, y0, x1, y1);
        }

        // Creating X-Axis
        for (int i = 0; i < dailyDeathArray.size(); i++) {
            if (dailyDeathArray.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (dailyDeathArray.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((dailyDeathArray.size() / 20.0)) + 1)) == 0) {
                    g4.setColor(gridColor);
                    g4.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g4.setColor(Color.BLACK);
                    String xLabel = i + "";
                    //Implement X-axis numbers
                    FontMetrics metrics = g4.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g4.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g4.drawLine(x0, y0, x1, y1);
            }
        }

        // Creating Both AXES
        g4.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g4.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        Stroke oldStroke = g4.getStroke();
        g4.setColor(lineColor);
        g4.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g4.drawLine(x1, y1, x2, y2);
        }

        g4.setStroke(oldStroke);
        g4.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g4.fillOval(x, y, ovalW, ovalH);
            g4.setColor(Color.black);
        }
        g4.drawString("Days After First Death", 50, 680);
    }

    //Using functions to be displayed below the graphs to show a better statistic report
    //For example it will be displayed the minimum of deaths that were on that range of days.


    private int getMinNew() {
        int minDeaths = Integer.MAX_VALUE;
        for (DataStore death2 : dailyDeathArray) {
            minDeaths = Math.min(minDeaths, (int)death2.newToday);
        }
        return minDeaths;
    }

    private int getMaxNew() {
        int maxDeaths = Integer.MIN_VALUE;
        for (DataStore death1 : dailyDeathArray) {
            maxDeaths = Math.max(maxDeaths, (int)death1.newToday);
        }
        return maxDeaths;
    }

    public void createAndShowGui(){
        {
            ArrayList<DataStore> dailyDeathArray = new ArrayList<>();
            Data Data = new Data();
            Data.readFile(Config.DEATHS_FILE);
            ArrayList<DataStore> deaths = Data.getDeathsArray();

            for (int i = 0; i < deaths.size(); i++)
            {
                int temp = (deaths.size() - 1) - i;
                String date = deaths.get(temp).date;
                long newToday = deaths.get(temp).newToday;
                long cumulative = deaths.get(temp).newToday;

                    dailyDeathArray.add( new DataStore(date, newToday, cumulative));
            }

            Graph4 mainGraph = new Graph4(dailyDeathArray);
            mainGraph.setPreferredSize(new Dimension(1000, 700));
            JFrame frame = new JFrame("Daily Death Graph");
            frame.setPreferredSize(new Dimension(1200, 900));
            JPanel labelPanel = new JPanel();

            JLabel maxNew = new JLabel("---MAXIMUM DEATHS IN ONE DAY: " + getMaxNew() + "---");

            JLabel minNew = new JLabel("---MINIMUM DEATHS IN ONE DAY: " + getMinNew() + "---");


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
*/