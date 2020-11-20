package Team31;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Template for our app.
public class Template
{
    public Template()
    {
        JFrame frame = new JFrame("Main Menu");
        JPanel panel = new JPanel(){
            public void paintComponent( Graphics graph ) {
                draw( graph );
            }

        };

        JLabel deaths = new JLabel("Deaths Graph Preview");
        JLabel cases = new JLabel("Cases Graph Preview");

        JButton deathB = new JButton("Deaths Full Report");
        JButton casesB = new JButton("Cases Full Report");

        deathB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewFrame(1);
            }
        });
        casesB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewFrame(2);
            }
        });


        panel.setSize(new Dimension(1000, 600));
        panel.setLayout(null);

        deaths.setBounds(200, 50, 200, 50);
        cases.setBounds(600, 50, 200, 50);
        deathB.setBounds(200, 400, 200, 50);
        casesB.setBounds(600, 400, 200, 50);

        panel.add(deaths);
        panel.add(cases);
        panel.add(deathB);
        panel.add(casesB);

        frame.setSize(new Dimension(1000, 600));
        frame.add(panel,BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void draw( Graphics g ) {
        g.setColor(Color.BLUE);
        g.fillRect(200, 180, 200, 200);

        g.setColor(Color.RED);
        g.fillRect(600, 180, 200, 200);
    }

    public static void draw2( Graphics g, int n ) {
        if (n == 1)
        {
            g.setColor(Color.BLUE);
        }else if (n == 2)
        {
            g.setColor(Color.RED);
        }
        g.fillRect(400, 200, 400, 400);
    }

    public static void NewFrame(int n)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                String s = " ";
                String m = " ";

                if (n == 1)
                {
                    s = "Cases Graph";
                    m = "Cases Manu";
                }else if (n == 2)
                {
                    s = "Deaths Graph";
                    m = "Deaths Menu";
                }

                JFrame frame = new JFrame(m);
                JPanel panel = new JPanel(){
                    public void paintComponent( Graphics graph ) {
                        draw2( graph, n);
                    }

                };

                JLabel label = new JLabel(s);
                JButton button = new JButton("Full PDF Report");

                panel.setSize(new Dimension(1000, 1000));
                panel.setLayout(null);

                label.setBounds(400, 100, 400, 50);
                button.setBounds(400, 700, 400, 50);

                panel.add(label);
                panel.add(button);

                frame.setSize(new Dimension(1000, 1000));
                frame.add(panel, BorderLayout.CENTER);

                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
