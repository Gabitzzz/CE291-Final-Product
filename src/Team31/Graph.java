package Team31;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

//-------------------------------------------------------//
//    MADE createAndShowGui NOT STATIC TO CALL IT FROM   //
//    TEMPLATE CLASS AND COMMENTED THE CODE IN MAIN      //
//-------------------------------------------------------//

public class Graph extends JPanel {

    private static ArrayList<Cases> casesArray;
    private int width = 800;
    private int height = 400;
    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;

    public Graph(ArrayList<Cases> casesArray) {
        this.casesArray = casesArray;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((int) getWidth() - (2 * padding) - labelPadding) / (casesArray.size() - 1);
        double yScale = ((int) getHeight() - 2 * padding - labelPadding) / (getMaxCase() - getMinCase());

        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < casesArray.size(); i++)
        {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxCase() - casesArray.get(i).cumulative) * yScale + padding);
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
            if (casesArray.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinCase() + (getMaxCase() - getMinCase()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                yLabel = String.valueOf(Math.round(Double.parseDouble(yLabel))); //Used that to remove the long characters and I converted it to integers.
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < casesArray.size(); i++) {
            if (casesArray.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (casesArray.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((casesArray.size() / 20.0)) + 1)) == 0) {
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

    private double getMinCase() {
        double minCase = Double.MAX_VALUE;
        //long minCase = Long.MAX_VALUE;
        for (Cases case1 : casesArray) {
            //System.out.println(case1.g());
            //System.out.println(case1.cumulative);
            minCase = Math.min(minCase, (int)case1.cumulative);
            //minCase = Integer.valueOf(Math.round(double.parseDouble(minCase)));
        }
        return minCase;
    }

    private int getMaxCase() {
        int maxCase = Integer.MIN_VALUE;
        for (Cases case1 : casesArray) {
            maxCase = Math.max(maxCase, (int) case1.cumulative);

        }
        return maxCase;
    }

    private int getMinNew() {
        int minCase = Integer.MAX_VALUE;
        for (Cases case1 : casesArray) {
            minCase = Math.min(minCase, (int)case1.newToday);
        }
        return minCase;
    }

    private int getMaxNew() {
        int maxCase = Integer.MIN_VALUE;
        for (Cases case1 : casesArray) {
            maxCase = Math.max(maxCase, (int)case1.newToday);
        }
        return maxCase;
    }

    public void setCases(ArrayList<Cases> setCases) {
        this.casesArray = setCases;
        invalidate();
        this.repaint();
    }

    public ArrayList<Cases> getCases() {
        return casesArray;
    }

    public void createAndShowGui(){
    {
        ArrayList<Cases> casesArray = new ArrayList<>();
        Data Data = new Data();
        Data.readFile("cases");
        ArrayList<Cases> cases = Data.getCasesArray();

        for (int i = 0; i < cases.size(); i++)
        {
            int temp = (cases.size() - 1) - i;
            String date = cases.get(temp).date;
            long newToday = cases.get(temp).newToday;
            long cumulative = cases.get(temp).cumulative;
            // System.out.println(cumulative);
            casesArray.add( new Cases(date, newToday, cumulative));
        }

        Graph mainGraph = new Graph(casesArray);
        mainGraph.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("Cases Graph");
        frame.setPreferredSize(new Dimension(1000, 800));
        JPanel labelPanel = new JPanel();


        JLabel peakCases = new JLabel("---PEAK VALUE OF CASES: " + getMaxCase() + "---");
        peakCases.setBounds(100, 100, 10, 10);
        JLabel minCases = new JLabel("----MINIMUM VALUE OF CASES: " + getMinCase() + "---");
        minCases.setBounds(100, 200, 10, 10);



        JLabel maxNew = new JLabel("-MAXIMUM CASES IN ONE DAY: " + getMaxNew() + "---");
        peakCases.setBounds(100, 300, 10, 10);
        JLabel minNew = new JLabel("-MINIMUM CASES IN ONE DAY: " + getMinNew() + "---");
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
}

/*ArrayList<Cases> casesArray = new ArrayList<>();
    Data Data = new Data();
        Data.readFile("cases");
    ArrayList<Cases> cases = Data.getCasesArray();
        for (int i = 0; i < cases.size(); i++)
    {
        casesArray.add( new Cases("1", 0, cases.get(i).cumulative));
        System.out.println(cases.get(i).cumulative);
    }*/


    /*Random random = new Random();
    int maxDataPoints = 40;
    int maxScore = 10;
        for (int i = 0; i < maxDataPoints; i++) {
        casesArray.add( new Cases("1", 0, random.nextInt() * maxScore));

        }*/
