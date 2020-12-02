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
    public Template()
    {
        JFrame frame = new JFrame("Main Menu");
        JPanel panel = new JPanel();

        JScrollPane jScrollPane = new JScrollPane(panel);
// only a configuration to the jScrollPane...
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

// Then, add the jScrollPane to your frame
        frame.getContentPane().add(jScrollPane);


        JLabel deaths = new JLabel("Deaths Graph Preview");
        JLabel cases = new JLabel("Cases Graph Preview");
        JLabel deaths2 = new JLabel("WeeklyDeaths Graph Preview");
        JLabel cases2 = new JLabel("Weekly Cases Graph Preview");

        JButton deathB = new JButton("Deaths Full Report");
        JButton casesB = new JButton("Cases Full Report");
        JButton deathA = new JButton("Deaths weekly Report");
        JButton casesA = new JButton("Cases Weekly Report");
        JButton PredictCases = new JButton("Predict Cases");
        JButton PredictDeaths = new JButton("Predict Deaths");

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
                Data Data2 = new Data();
                Data2.readFile("deaths");
                ArrayList<Deaths> deaths = Data2.getDeathsArray();
                Graph2 graphs2 = new Graph2(deaths);
                graphs2.createAndShowGui();

            }
        });
        casesB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //NewFrame(2);
                Data Data = new Data();
                Data.readFile("cases");
                ArrayList<Cases> cases = Data.getCasesArray();

                Graph graph = new Graph(cases);
                graph.createAndShowGui();
            }
        });

        casesA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //NewFrame(2);
                Data Data3 = new Data();
                Data3.readFile("cases");
                ArrayList<Cases> cases2 = Data3.getCases2Array();

                Graph3 graph3 = new Graph3(cases2);
                graph3.createAndShowGui();
            }
        });

        deathA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //NewFrame(1);
                Data Data4 = new Data();
                Data4.readFile("deaths");
                ArrayList<Deaths> deaths2 = Data4.getDeaths2Array();
                Graph4 graphs4 = new Graph4(deaths2);
                graphs4.createAndShowGui();

            }
        });


        panel.setLayout(null);

        deaths.setBounds(150, 50, 200, 50);
        cases.setBounds(700, 50, 200, 50);
        deathB.setBounds(150, 400, 200, 50);
        casesB.setBounds(700, 400, 200, 50);
        deaths2.setBounds(150, 530, 200, 50);
        cases2.setBounds(700, 530, 200, 50);
        deathA.setBounds(150, 700, 200, 50);
        casesA.setBounds(700, 700, 200, 50);
        PredictCases.setBounds(700, 480, 200, 50);
        PredictDeaths.setBounds(150,480,200,50);

        panel.add(deaths);
        panel.add(cases);
        panel.add(deathB);
        panel.add(casesB);
        panel.add(deaths2);
        panel.add(cases2);
        panel.add(deathA);
        panel.add(casesA);
        panel.add(PredictCases);
        panel.add(PredictDeaths);

        Graph graph = new Graph(getCasesData());
        Graph2 graph2 = new Graph2(getDeathsData());
        Graph3 graph3 = new Graph3(getCasesData());
        Graph4 graph4 = new Graph4(getDeathsData());

        graph.setBounds(600, 100, 500, 300);
        panel.add(graph);
        graph2.setBounds(50, 100, 500, 300);
        panel.add(graph2);
        graph3.setBounds(600, 500, 500, 300);
        panel.add(graph3);
        graph4.setBounds(50, 500, 500, 300);
        panel.add(graph4);



        frame.setSize(new Dimension(1600, 1600));
        frame.add(panel, BorderLayout.CENTER);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private ArrayList<Cases> getCasesData()
    {
        ArrayList<Cases> casesArray = new ArrayList<>();
        Data Data = new Data();
        Data.readFile("cases");
        ArrayList<Cases> casesTemp = Data.getCasesArray();

        for (int i = 0; i < casesTemp.size(); i++)
        {
            int temp = (casesTemp.size() - 1) - i;
            String date = casesTemp.get(temp).date;
            long newToday = casesTemp.get(temp).newToday;
            long cumulative = casesTemp.get(temp).cumulative;
            if (temp % 7 == 0)
            {
                casesArray.add( new Cases(date, newToday, cumulative));
            }
        }
        return casesArray;
    }

    private ArrayList<Deaths> getDeathsData()
    {
        ArrayList<Deaths> deathsArray = new ArrayList<>();
        Data Data2 = new Data();
        Data2.readFile("deaths");
        ArrayList<Deaths> deathsTemp = Data2.getDeathsArray();

        for (int i = 0; i < deathsTemp.size(); i++)
        {
            int temp = (deathsTemp.size() - 1) - i;
            String date = deathsTemp.get(temp).date;
            long newToday = deathsTemp.get(temp).newToday;
            long cumulative = deathsTemp.get(temp).cumulative;
            if (temp % 7 == 0)
            {
                deathsArray.add( new Deaths(date, newToday, cumulative));
            }
        }
        return deathsArray;
    }
}
