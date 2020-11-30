package Team31;

import java.io.*;
import java.util.ArrayList;

public class Data {
    /* Configuration Variables */
    private static String cfg_casesFilePath = "./src/Team31/cases_data_2020-Oct-30.csv"; // path to cases csv
    private static String cfg_deathsFilePath = "./src/Team31/deaths_data_2020-Oct-30.csv"; // path to deaths csv
    private static String cfg_delimeter = ",";
    /* End of Configuration Variables */

    private ArrayList<DataStore> dataArray = new ArrayList<>(); //initialise the array ready for data insertion

    public void readFile(String file) { // reads the specified file (cases or deaths)
        String filePath = null;

        if (file.equals("deaths")) {
            filePath = cfg_deathsFilePath;
        } else if (file.equals("cases")) {
            filePath = cfg_casesFilePath;
        } else {
            return;
        }

        dataArray = new ArrayList<>(); // ensures the array is empty before manipulation

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) { // loops through file line by line whilst there is still a non null line
                if (!firstLine) { // ensures that we dont attempt to pass to the array the first line of the csv
                    String[] values = line.split(cfg_delimeter); // splits the lines by the provided delimiter

                    if (file.equals("deaths")) {
                        dataArray.add(new DataStore(values[3], Long.parseLong(values[4]), Long.parseLong(values[5]))); // adds to the deathsArray a reference to the new Deaths Object
                    } else if (file.equals("cases")) {
                        if (values.length < 6) { // checks to see if the values array is less that 6 (some data was missing in the "cumCasesByPublishDate" column in the csv)
                            values = new String[] {values[0], values[1], values[2], values[3], values[4], "0"}; // if data is missing, assigns a 0
                        }

                        dataArray.add(new DataStore(values[3], Long.parseLong(values[4]), Long.parseLong(values[5]))); // adds to the casesArray a reference to the new Cases Object
                    }
                } else {
                    firstLine = false; // sets variable to false after the first loop, therefore skipping first line
                }
            }
        } catch (IOException e) { // catches exception so program dont crash
            e.printStackTrace();
        }
    }

    public ArrayList<DataStore> getDataArray() { // returns the deathsArray
        return dataArray;
    }
}
