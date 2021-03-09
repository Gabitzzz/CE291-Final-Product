package Team31;

import java.util.ArrayList;
import java.util.Scanner;

//---------------------------------------------------------------------------//
//   Initiates the Main Graphical User Interface and Shows the Main Frame    //
//---------------------------------------------------------------------------//

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // The GUI
        new MainFrame();
        // Simple report interface through the console
        System.out.println("\n");
        System.out.println("---------- TYPE THE NUMBER TO SHOW ---------");
        System.out.println("1- Deaths Data Report\t2- Cases Data Report");
        System.out.println("--------------------------------------------\n");
        try
        {
            int choice = Integer.parseInt(input.nextLine());
            new Main(choice);
        } catch (NumberFormatException e)
        {
            System.out.println("Invalid Input");
        }
    }

    // Simple report menu.
    //Show's report about deaths and cases based on the csv file.
    Main(int n)
    {
        if (n == 1)
        {
            Data Data = new Data();
            Data.readFile(Config.DEATHS_FILE);
            ArrayList<DataStore> deaths = Data.getDeathsArray();

            System.out.println("-------------------------------------");
            System.out.println("|      Deaths Data Report           |");
            System.out.println("-------------------------------------");
            System.out.println("| Date     | New Today | Cumulative |");
            System.out.println("-------------------------------------");

            for (DataStore death : deaths) {
                System.out.print("|" + death.date + " |");
                System.out.print(" " + death.newToday + "         |");
                System.out.print(" " + death.cumulative + "   |\n");
            }
            System.out.println("---------------------------------");
        }else if (n == 2)
        {
            Data Data = new Data();
            Data.readFile(Config.CASES_FILE);
            ArrayList<DataStore> cases = Data.getCasesArray();

            System.out.println("---------------------------------------");
            System.out.println("|      Cases Data Report              |");
            System.out.println("---------------------------------------");
            System.out.println("| Date     | New Today |  Cumulative  |");
            System.out.println("---------------------------------------");

            for (DataStore aCase : cases) {
                System.out.print("|" + aCase.date + " |");
                System.out.print(" " + aCase.newToday + "         |");
                System.out.print(" " + aCase.cumulative + "   |\n");
            }
            System.out.println("-----------------------------------");
        }
    }
}