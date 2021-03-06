package Team31;

import java.nio.file.Path;

// constants
public final class Config {
    static final int CASES_FILE = 0;
    static final int DEATHS_FILE = 1;
    static final int OTHER_FILE = 3;
    static final String WEEKLY = "weekly";
    static final String DAILY = "daily";
    static final String OPEN_COMMAND = "open";
    static final String PREVIEW_COMMAND = "preview";

    static final String CASES_FILEPATH = "cases_data_2021-Mar-09.csv"; // path to cases csv
    static final String DEATHS_FILEPATH = "deaths_data_2021-Mar-09.csv"; // path to deaths csv
    static final String FILE_DELIMITER = ","; // delimiter in the csv files
    static Path OTHER_FILE_PATH = null;

    static final String INVALID_FILE = "Files with the provided file extension are unsupported.";
}