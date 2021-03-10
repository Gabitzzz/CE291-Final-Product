package Team31;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;

//---------------------------------------------------------------------------//
//    Reads and Puts the original Deaths and Cases data into an ArrayList    //
//---------------------------------------------------------------------------//
// t
public class Data {
    Path path;
    private ArrayList<DataStore> casesArray = new ArrayList<>(); //initialise the cases array ready for data insertion
    private ArrayList<DataStore> deathsArray = new ArrayList<>(); //initialise the deaths array ready for data insertion
    private ArrayList<DataStore> otherArray = new ArrayList<>();

    public Data(){}
    public Data(Path path)
    {
        this.path = path;
    }

    public void readFile(int file) { // reads the specified file (cases or deaths)
       try {
           String filePath;
           BufferedReader br;

           if (file == Config.DEATHS_FILE) {
               filePath = Config.DEATHS_FILEPATH;
               URL url = getClass().getResource(filePath);
               InputStream is = url.openStream();
               br = new BufferedReader(new InputStreamReader(is));

               deathsArray = new ArrayList<>(); // ensures the array is empty before manipulation

           } else if (file == Config.CASES_FILE) {
               filePath = Config.CASES_FILEPATH;
               URL url = getClass().getResource(filePath);
               InputStream is = url.openStream();
               br = new BufferedReader(new InputStreamReader(is));

               casesArray = new ArrayList<>(); // ensures the array is empty before manipulation
           } else if (file == Config.OTHER_FILE) {
               filePath = path.toString();

               if (!filePath.endsWith(".csv")) {
                   throw new InvalidFileExtensionException(filePath);
               }

               br = new BufferedReader(new FileReader(filePath));

               otherArray = new ArrayList<>(); // ensures the array is empty before manipulation
           } else {
               return;
           }

           try {
               String line;
               boolean firstLine = true;

               while ((line = br.readLine()) != null) { // loops through file line by line whilst there is still a non null line
                   if (!firstLine) { // ensures that we dont attempt to pass to the array the first line of the csv
                       String[] values = line.split(Config.FILE_DELIMITER); // splits the lines by the provided delimiter

                       if (file == Config.DEATHS_FILE) {
                           if (values.length < 6) {
                               values = new String[]{values[0], values[1], values[2], values[3], values[4], "0"};
                           }

                           deathsArray.add(new DataStore(values[3], Long.parseLong(values[4]), Long.parseLong(values[5])));// adds to the deathsArray a reference to the new Deaths Object
                       } else if (file == Config.CASES_FILE) {
                           if (values.length < 6) { // checks to see if the values array is less that 6 (some data was missing in the "cumCasesByPublishDate" column in the csv)
                               values = new String[]{values[0], values[1], values[2], values[3], values[4], "0"}; // if data is missing, assigns a 0
                           }

                           casesArray.add(new DataStore(values[3], Long.parseLong(values[4]), Long.parseLong(values[5]))); // adds to the casesArray a reference to the new Cases Object
                       } else {
                           if (values.length < 6) { // checks to see if the values array is less that 6 (some data was missing in the "cumCasesByPublishDate" column in the csv)
                               values = new String[]{values[0], values[1], values[2], values[3], values[4], "0"}; // if data is missing, assigns a 0
                           }

                           otherArray.add(new DataStore(values[3], Long.parseLong(values[4]), Long.parseLong(values[5]))); // adds to the casesArray a reference to the new Cases Object
                       }
                   } else {
                       firstLine = false; // sets variable to false after the first loop, therefore skipping first line
                   }
               }
           } catch (IOException | IndexOutOfBoundsException e) { // catches exception so program don't crash
               JOptionPane.showMessageDialog(null, "Selected file may be deleted or removed from the original path.", "File Not Found", JOptionPane.ERROR_MESSAGE);
           }
       } catch (IOException e) {
           e.printStackTrace();
       } catch (InvalidFileExtensionException e) {
           e.printStackTrace();
           System.exit(0);
       }
    }

    public void saveAsImage(JFrame frame)
    {
        Path file;
        JOptionPane.showMessageDialog(null,"Please select a path and type the name of the file without any file extensions.","INFORMATION", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int r = j.showOpenDialog(null);

        if (r == JFileChooser.APPROVE_OPTION)
        {
            file = j.getSelectedFile().toPath();
            System.out.println(file);

            int a=JOptionPane.showConfirmDialog(null,"Graph will be saved as image to path: " + file.toString() + ".png");
            if(a==JOptionPane.YES_OPTION)
            {
                Dimension size = frame.getSize();
                BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = image.createGraphics();
                frame.paint(g2);
                try
                {
                    ImageIO.write(image, "png", new File(file + ".png"));
                    System.out.println("Frame Saved");
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,"Program could not save the graph as image.","An Error Occurred", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<DataStore> getDeathsArray() { return deathsArray; }

    public ArrayList<DataStore> getCasesArray() { return casesArray; }

    public ArrayList<DataStore> getOtherArray() { return otherArray; }
}