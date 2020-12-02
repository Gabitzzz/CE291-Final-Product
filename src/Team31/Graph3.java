package Team31;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


public class Graph3 extends JPanel {
    /*

    private static ArrayList<Cases> cases2Array;
    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;

    public Graph3(ArrayList<Cases> cases2Array) {
        this.cases2Array = cases2Array;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g4 = (Graphics2D) g;
        g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((int) getWidth() - (2 * padding) - labelPadding) / (cases2Array.size() - 1);
        double yScale = ((int) getHeight() - 2 * padding - labelPadding) / (getMaxCase() - getMinCase());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < cases2Array.size(); i++)
        {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxCase() - cases2Array.get(i).cumulative) * yScale + padding);
            //System.out.println(y1);
            graphPoints.add(new Point(x1, y1));
        }

        // draw white background
        g4.setColor(Color.WHITE);
        g4.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g4.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++)
        {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (cases2Array.size() > 0) {
                g4.setColor(gridColor);
                g4.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g4.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinCase() + (getMaxCase() - getMinCase()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                yLabel = String.valueOf(Math.round(Double.parseDouble(yLabel))); //Used that to remove the long characters and I converted it to integers.
                FontMetrics metrics = g4.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g4.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g4.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < cases2Array.size(); i++) {
            if (cases2Array.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (cases2Array.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((cases2Array.size() / 20.0)) + 1)) == 0) {
                    g4.setColor(gridColor);
                    g4.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g4.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g4.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g4.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g4.drawLine(x0, y0, x1, y1);
            }
        }

        // create x and y axes
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
        }
    }

    private double getMinCase() {
        int minCase = Integer.MAX_VALUE;
        //long minCase = Long.MAX_VALUE;
        for (Cases case1 : cases2Array) {
            //System.out.println(case1.g());
            //System.out.println(case1.cumulative);
            minCase = Math.min(minCase, (int)case1.newToday);
            //minCase = Integer.valueOf(Math.round(double.parseDouble(minCase)));
        }
        return minCase;
    }

    private int getMaxCase() {
        int maxCase = Integer.MIN_VALUE;
        for (Cases case1 : cases2Array) {
            maxCase = Math.max(maxCase, (int) case1.newToday);

        }
        return maxCase;
    }

    private int getMinNew() {
        int minCases = Integer.MAX_VALUE;
        for (Cases case1 : cases2Array) {
            minCases = Math.min(minCases, (int)case1.newToday);
        }
        return minCases;
    }

    private int getMaxNew() {
        int maxDeaths = Integer.MIN_VALUE;
        for (Cases case1 : cases2Array) {
            maxDeaths = Math.max(maxDeaths, (int)case1.newToday);
        }
        return maxDeaths;
    }



    public void createAndShowGui(){
        {
            ArrayList<Cases> cases2Array = new ArrayList<>();
            Data Data = new Data();
            Data.readFile("cases");
            ArrayList<Cases> cases2 = Data.getCases2Array();

            for (int i = 0; i < cases2.size(); i++)
            {
                int temp = (cases2.size() - 1) - i;
                String date = cases2.get(temp).date;
                long newToday = cases2.get(temp).newToday;
                if (temp % 7 == 0)
                {
                    cases2Array.add( new Cases(date, newToday));
                }
            }

            Graph mainGraph = new Graph(cases2Array);
            mainGraph.setPreferredSize(new Dimension(800, 600));
            JFrame frame = new JFrame("Cases Graph");
            frame.setPreferredSize(new Dimension(1000, 800));
            JPanel labelPanel = new JPanel();


            JLabel peakCases = new JLabel("---PEAK VALUE OF CASES: " + getMaxCase() + "---");
            peakCases.setBounds(100, 100, 10, 10);
            JLabel minCases = new JLabel("---MINIMUM VALUE OF CASES: " + getMinCase() + "---");
            minCases.setBounds(100, 200, 10, 10);



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
            frame.add(labelPanel, BorderLayout.WEST);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }
    }
    */
}




