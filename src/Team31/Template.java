package Team31;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//----------------------------------------------------//
//    !!! DON'T DELETE ANY COMMENTED CODE YET !!!     //
//----------------------------------------------------//

// Template for our GUI.
public class Template
{
    private static final String DEATHS = "deaths";
    private static final String CASES = "cases";

    public Template()
    {
        JFrame frame = new JFrame("Main Menu");
        JPanel panel = new JPanel();

        JLabel deaths = new JLabel("Deaths Graph Preview");
        JLabel cases = new JLabel("Cases Graph Preview");

        JButton deathB = new JButton("Deaths Full Report");
        JButton casesB = new JButton("Cases Full Report");
        //JButton Predictcases = new JButton("Predict cases");
        //JButton Predictdeaths = new JButton("Predict deaths");

        /*Predictdeaths.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewFrame(1);

            }
        });*/

       /* Predictcases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Data Data = new Data();
                Data.readFile("cases");
                ArrayList<Cases> cases = Data.getCasesArray();
               // Graph graph = new Graph(cases);
                //graph.createAndShowGui();
            }
        });*/

        deathB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //NewFrame(1);
                Data Data = new Data();
                Data.readFile(DEATHS);
                ArrayList<DataStore> deaths = Data.getDataArray();

                Graph graph = new Graph(deaths);
                graph.createAndShowGui(DEATHS);
            }
        });
        casesB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //NewFrame(2);
                Data Data = new Data();
                Data.readFile(CASES);
                ArrayList<DataStore> cases = Data.getDataArray();

                Graph graph = new Graph(cases);
                graph.createAndShowGui(CASES);
            }
        });


        panel.setSize(new Dimension(1000, 600));
        panel.setLayout(null);

        deaths.setBounds(200, 50, 200, 50);
        cases.setBounds(800, 50, 200, 50);
        deathB.setBounds(200, 400, 200, 50);
        casesB.setBounds(800, 400, 200, 50);
        // Predictcases.setBounds(800, 50, 200, 50);
        //Predictdeaths.setBounds(800,50,200,50);

        panel.add(deaths);
        panel.add(cases);
        panel.add(deathB);
        panel.add(casesB);
        //panel.add(Predictcases);
        //panel.add(Predictdeaths);

        Graph casesGraph = new Graph(getData(CASES));
        casesGraph.setBounds(700, 100, 500, 300);
        panel.add(casesGraph);
        Graph deathsGraph = new Graph(getData(DEATHS));
        deathsGraph.setBounds(100, 100, 500, 300);
        panel.add(deathsGraph);

        frame.setSize(new Dimension(1200, 600));
        frame.add(panel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private ArrayList<DataStore> getData(String selection)
    {
        ArrayList<DataStore> array = new ArrayList<>();
        ArrayList<DataStore> data = new ArrayList<>();
        Data data1 = new Data();

        switch (selection)
        {
            case "cases":

                data1.readFile("cases");
                data = data1.getDataArray();
                break;

            case "deaths":
                data1.readFile("deaths");
                data = data1.getDataArray();
                break;
        }

        for (int i = 0; i < data.size(); i++)
        {
            int temp = (data.size() - 1) - i;
            String date = data.get(temp).date;
            long newToday = data.get(temp).newToday;
            long cumulative = data.get(temp).cumulative;
            array.add( new DataStore(date, newToday, cumulative));
        }
        return array;
    }
}
