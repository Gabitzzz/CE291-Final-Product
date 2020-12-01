package Team31;

import javax.swing.*;
import java.awt.*;

public class TestGraph
{
    /*public static void NewFrame(int n)
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
                    m = "Cases Menu";
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
    }*/
}
