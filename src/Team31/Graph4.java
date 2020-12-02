package Team31;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


public class Graph4 extends JPanel {
    /*

    private static ArrayList<Deaths> deaths2Array;
    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;

    public Graph4(ArrayList<Deaths> deaths2Array) {
        this.deaths2Array = deaths2Array;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((int) getWidth() - (2 * padding) - labelPadding) / (deaths2Array.size() - 1);
        double yScale = ((int) getHeight() - 2 * padding - labelPadding) / (getMaxDeath() - getMinDeath());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < deaths2Array.size(); i++)
        {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxDeath() - deaths2Array.get(i).cumulative) * yScale + padding);
            //System.out.println(y1);
            graphPoints.add(new Point(x1, y1));
        }

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++)
        {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (deaths2Array.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinDeath() + (getMaxDeath() - getMinDeath()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                yLabel = String.valueOf(Math.round(Double.parseDouble(yLabel))); //Used that to remove the long characters and I converted it to integers.
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < deaths2Array.size(); i++) {
            if (deaths2Array.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (deaths2Array.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((deaths2Array.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // create x and y axes
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }

    private double getMinDeath() {
        int minDeath = Integer.MAX_VALUE;
        //long minCase = Long.MAX_VALUE;
        for (Deaths death2 : deaths2Array) {
            //System.out.println(case2.g());
            minDeath = Math.min(minDeath, (int)death2.newToday);
            //minCase = Integer.valueOf(Math.round(double.parseDouble(minCase)));
        }
        return minDeath;
    }

    private int getMaxDeath() {
        int maxDeath = Integer.MIN_VALUE;
        for (Deaths death2 : deaths2Array) {
            maxDeath = Math.max(maxDeath, (int) death2.newToday);

        }
        return maxDeath;
    }

    private int getMinNew() {
        int minDeaths = Integer.MAX_VALUE;
        for (Deaths death2 : deaths2Array) {
            minDeaths = Math.min(minDeaths, (int)death2.newToday);
        }
        return minDeaths;
    }

    private int getMaxNew() {
        int maxDeaths = Integer.MIN_VALUE;
        for (Deaths death2 : deaths2Array) {
            maxDeaths = Math.max(maxDeaths, (int)death2.newToday);
        }
        return maxDeaths;
    }


    public void createAndShowGui(){
        {
            ArrayList<Deaths> deaths2Array = new ArrayList<>();
            Data Data = new Data();
            Data.readFile("deaths");
            ArrayList<Deaths> deaths2 = Data.getDeathsArray();

            for (int i = 0; i < deaths2.size(); i++)
            {
                int temp = (deaths2.size() - 1) - i;
                String date = deaths2.get(temp).date;
                long newToday = deaths2.get(temp).newToday;
                // System.out.println(cumulative);
                if (temp % 7 == 0)
                {
                    deaths2Array.add( new Deaths(date, newToday));
                }
            }

            Team31.Graph2 mainGraph = new Team31.Graph2(deaths2Array);
            mainGraph.setPreferredSize(new Dimension(800, 600));
            JFrame frame = new JFrame("Deaths Graph");
            frame.setPreferredSize(new Dimension(1000, 800));
            JPanel labelPanel = new JPanel();


            JLabel peakDeaths = new JLabel("---PEAK VALUE OF DEATHS: " + getMaxDeath() + "---");
            peakDeaths.setBounds(100, 100, 10, 10);
            JLabel minDeaths = new JLabel("---MINIMUM VALUE OF DEATHS: " + getMinDeath() + "---");
            minDeaths.setBounds(100, 200, 10, 10);



            JLabel maxNew = new JLabel("---MAXIMUM DEATHS IN ONE DAY: " + getMaxNew() + "---");
            peakDeaths.setBounds(100, 300, 10, 10);
            JLabel minNew = new JLabel("---MINIMUM DEATHS IN ONE DAY: " + getMinNew() + "---");
            minDeaths.setBounds(100, 400, 10, 10);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            labelPanel.add(peakDeaths);
            labelPanel.add(minDeaths);
            labelPanel.add(maxNew);
            labelPanel.add(minNew);
            frame.add(mainGraph, BorderLayout.NORTH);
            frame.add(labelPanel, BorderLayout.WEST);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }
    }

     */
}
