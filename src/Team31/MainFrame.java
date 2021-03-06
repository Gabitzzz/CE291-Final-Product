package Team31;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

//---------------------------------------------------------------------------//
//    Generates the Main Menu Frame and Includes the Button Functionality    //
//---------------------------------------------------------------------------//

public class MainFrame
{
    private JFrame frame = new JFrame("Analytic System For COVID Data");
    public MainFrame()
    {
        // Main frame and panel for the GUI.
        JPanel panel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JScrollPane scrollBar = new JScrollPane (panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Headings of the graphs
        JLabel wDeaths = new JLabel("Weekly Deaths Graph Preview");
        JLabel wCases = new JLabel("Weekly Cases Graph Preview");
        JLabel dDeaths = new JLabel("Daily Deaths Graph Preview");
        JLabel dCases = new JLabel("Daily Cases Graph Preview");
        JLabel fileSelected = new JLabel("No File Selected");
        JLabel region = new JLabel("UK COVID-19 March Data Analyse, Please Select Different Files For Different Regions Or Different Time Frames.");



        // All functional buttons
        JButton deathB = new JButton("Deaths Full Report");
        JButton casesB = new JButton("Cases Full Report");
        JButton PredictCases = new JButton("Predict Cases");
        JButton PredictDeaths = new JButton("Predict Deaths");
        JButton selectFileButton = new JButton("Select A File");
        JButton previewSelectedFile = new JButton("Preview Data");


        // Calls the method which creates a new frame with predicted death values
        PredictDeaths.addActionListener(e -> { LinearRegression prediction = new LinearRegression(); prediction.makePredictedGraph(Config.DEATHS_FILE); });

        // Calls the method which creates a new frame with predicted cases values
        PredictCases.addActionListener(e -> { LinearRegression prediction = new LinearRegression(); prediction.makePredictedGraph(Config.CASES_FILE); });

        // Generates the detailed version of deaths graph
        deathB.addActionListener(e -> { GenerateGraph graph = new GenerateGraph(Config.DEATHS_FILE, Config.WEEKLY);graph.createAndShowGui("DEATHS"); });

        // Generates the detailed version of the cases graph
        casesB.addActionListener(e -> { GenerateGraph graph = new GenerateGraph(Config.CASES_FILE, Config.WEEKLY);graph.createAndShowGui("CASES"); });

        // Generates the detailed version of deaths graph
        selectFileButton.addActionListener(e -> fileSelection(Config.OPEN_COMMAND, fileSelected));

        // Generates the detailed version of the cases graph
        previewSelectedFile.addActionListener(e -> fileSelection(Config.PREVIEW_COMMAND, fileSelected));

        panel.setPreferredSize(new Dimension(1380, 800));
        buttonPanel.setPreferredSize(new Dimension(1200, 200));
        panel.setLayout(null);
        buttonPanel.setLayout(null);

        // Setting the position and size of the GUI elements
        wDeaths.setBounds(150, 75, 200, 50);
        wCases.setBounds(800, 75, 200, 50);
        dDeaths.setBounds(150, 450, 200, 50);
        dCases.setBounds(800, 450, 200, 50);
        PredictCases.setBounds(800, 660, 200, 40);
        casesB.setBounds(800, 610, 200, 40);
        PredictDeaths.setBounds(200,660,200,40);
        deathB.setBounds(200, 610, 200, 40);
        selectFileButton.setBounds(400, 720, 150, 25);
        previewSelectedFile.setBounds(550,720,150,25);
        fileSelected.setBounds(720,720,800,25);
        region.setBounds(260, 0, 1000, 100);

        //Decorating
        region.setForeground(Color.DARK_GRAY);
        region.setFont(new Font("Serif", Font.BOLD, 18));
        wDeaths.setFont(new Font("Serif", Font.ITALIC, 16));
        wCases.setFont(new Font("Serif", Font.ITALIC, 16));
        dDeaths.setFont(new Font("Serif", Font.ITALIC, 16));
        dCases.setFont(new Font("Serif", Font.ITALIC, 16));



        // Adding the GUI elements to the panel
        panel.add(wDeaths);
        panel.add(wCases);
        panel.add(dDeaths);
        panel.add(dCases);
        panel.add(region);
        buttonPanel.add(deathB);
        buttonPanel.add(casesB);
        buttonPanel.add(PredictCases);
        buttonPanel.add(PredictDeaths);
        buttonPanel.add(selectFileButton);
        buttonPanel.add(previewSelectedFile);
        buttonPanel.add(fileSelected);

        // Generating the smaller previews of the both graphs
        GenerateGraph graph = new GenerateGraph(Config.CASES_FILE, Config.WEEKLY);
        GenerateGraph graph2 = new GenerateGraph(Config.DEATHS_FILE, Config.WEEKLY);
        GenerateGraph graph3 = new GenerateGraph(Config.CASES_FILE, Config.DAILY);
        GenerateGraph graph4 = new GenerateGraph(Config.DEATHS_FILE, Config.DAILY);

        // Setting the position and size of the graphs
        graph.setBounds(700, 100, 600, 300);
        panel.add(graph);
        graph2.setBounds(50, 100, 600, 300);
        panel.add(graph2);
        graph3.setBounds(700, 460, 600, 300);
        panel.add(graph3);
        graph4.setBounds(50, 460, 600, 300);
        panel.add(graph4);

        // setting up the frame
        frame.setSize(new Dimension(1400, 800));
        scrollBar.setSize(new Dimension(1380, 600));
        scrollBar.getVerticalScrollBar().setUnitIncrement(14);
        frame.add(scrollBar);
        frame.add(buttonPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void fileSelection(String command, JLabel label)   // method for file selection option
    {
        if (command.equals(Config.OPEN_COMMAND))   // opens the dialog
        {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            j.setFileFilter(filter);

            int r = j.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION)   // selects the file
            {
                // checking if the selected file is valid
                try {
                    if (!j.getSelectedFile().getAbsolutePath().endsWith(".csv")) { throw new InvalidFileExtensionException(j.getSelectedFile().toPath().toString()); }

                    label.setText(j.getSelectedFile().getAbsolutePath());
                    Path file = j.getSelectedFile().toPath();
                    System.out.println(file);
                } catch (InvalidFileExtensionException e)
                {
                    // error message to prevent program to crush
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(frame,"Program could not read the selected file. Can only read .csv files.","Unsupported File", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
                label.setText("Selection Cancelled");
        }
        else if (command.equals(Config.PREVIEW_COMMAND))   // generates graph of selected file
        {
            String filePath = label.getText();
            if (filePath.equals("Selection Cancelled") || filePath.equals("No File Selected"))   // error message if the user did not select a file
            {
                JOptionPane.showMessageDialog(frame,"You haven't selected a file yet.","Select A File", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                // accessing the selected file and configuring the array list
                Path file = Paths.get(filePath);
                Config.OTHER_FILE_PATH = file;
                Data data = new Data(file);
                data.readFile(Config.OTHER_FILE);
                ArrayList<DataStore> array = data.getOtherArray();
                ArrayList<DataStore> arrayForGraph = new ArrayList<>();
                if(array.isEmpty())
                {JOptionPane.showMessageDialog(frame,"Program could not read the selected file","File Not Readable", JOptionPane.ERROR_MESSAGE); return;}

                for (int i = 0; i < array.size(); i++)   // reversing the data
                {
                    int temp = (array.size() - 1) - i;
                    String date = array.get(temp).date;
                    long newToday = array.get(temp).newToday;
                    long cumulative = array.get(temp).cumulative;
                    arrayForGraph.add(new DataStore(date, newToday, cumulative));
                }
                // generates the frame
                GenerateGraph graph = new GenerateGraph(arrayForGraph);
                graph.createAndShowGui("INPUTTED DATA");
            }
        }
    }
}
