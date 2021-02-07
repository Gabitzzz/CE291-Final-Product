package Team31;

import java.io.*;
import java.util.ArrayList;

//---------------------------------------------------------------------------//
//    Reads and Puts the original Deaths and Cases data into an ArrayList    //
//---------------------------------------------------------------------------//

public class Data {
    private ArrayList<DataStore> casesArray = new ArrayList<>(); //initialise the cases array ready for data insertion
    private ArrayList<DataStore> deathsArray = new ArrayList<>(); //initialise the deaths array ready for data insertion int y1 = (int) ((getMaxCase() - casesArray.get(i).cumulative) * yScale + padding);

    public void readFile(int file) { // reads the specified file (cases or deaths)
        String filePath;

        if (file == Config.DEATHS_FILE) {
            filePath = Config.DEATHS_FILEPATH;

            deathsArray = new ArrayList<>(); // ensures the array is empty before manipulation
        } else if (file == Config.CASES_FILE) {
            filePath = Config.CASES_FILEPATH;

            casesArray = new ArrayList<>(); // ensures the array is empty before manipulation
        } else {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) { // loops through file line by line whilst there is still a non null line
                if (!firstLine) { // ensures that we dont attempt to pass to the array the first line of the csv
                    String[] values = line.split(Config.FILE_DELIMITER); // splits the lines by the provided delimiter

                    if (file == Config.DEATHS_FILE) {
                        deathsArray.add(new DataStore(values[3], Long.parseLong(values[4]), Long.parseLong(values[5])));// adds to the deathsArray a reference to the new Deaths Object
                    } else {
                        if (values.length < 6) { // checks to see if the values array is less that 6 (some data was missing in the "cumCasesByPublishDate" column in the csv)
                            values = new String[] {values[0], values[1], values[2], values[3], values[4], "0"}; // if data is missing, assigns a 0
                        }

                        casesArray.add(new DataStore(values[3], Long.parseLong(values[4]), Long.parseLong(values[5]))); // adds to the casesArray a reference to the new Cases Object
                    }
                } else {
                    firstLine = false; // sets variable to false after the first loop, therefore skipping first line
                }
            }
        } catch (IOException e) { // catches exception so program don't crash
            e.printStackTrace();
        }
    }

    public ArrayList<DataStore> getDeathsArray() { // returns the deathsArray
        return deathsArray;
    }

    public ArrayList<DataStore> getCasesArray() { // returns the casesArray
        return casesArray;
    }
}
