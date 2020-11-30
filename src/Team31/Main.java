package Team31;

import java.util.ArrayList;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {
        // the GUI
        new Template();

        Data Data = new Data();
        Scanner input = new Scanner(System.in);

        Data.readFile("deaths");
        Data.readFile("cases");

        ArrayList<DataStore> deaths = Data.getDataArray();

        System.out.println(deaths.get(45).date);

        ArrayList<DataStore> cases = Data.getDataArray();

        System.out.println(cases.get(270).date);

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

    Main(int n)
    {
        if (n == 1)
        {
            Data Data = new Data();
            Data.readFile("deaths");
            ArrayList<DataStore> deaths = Data.getDataArray();

            System.out.println("-------------------------------------");
            System.out.println("|      Deaths Data Report           |");
            System.out.println("-------------------------------------");
            System.out.println("| Date     | New Today | Cumulative |");
            System.out.println("-------------------------------------");

            for (int i = 0; i < deaths.size(); i++)
            {
                System.out.print("|" + deaths.get(i).date + " |");
                System.out.print(" " + deaths.get(i).newToday + "         |");
                System.out.print( " " + deaths.get(i).cumulative + "   |\n");
            }
            System.out.println("---------------------------------");
        }else if (n == 2)
        {
            Data Data = new Data();
            Data.readFile("cases");
            ArrayList<DataStore> cases = Data.getDataArray();

            System.out.println("---------------------------------------");
            System.out.println("|      Cases Data Report              |");
            System.out.println("---------------------------------------");
            System.out.println("| Date     | New Today |  Cumulative  |");
            System.out.println("---------------------------------------");

            for (int i = 0; i < cases.size(); i++)
            {
                System.out.print("|" + cases.get(i).date + " |");
                System.out.print(" " + cases.get(i).newToday + "         |");
                System.out.print( " " + cases.get(i).cumulative + "   |\n");
            }
            System.out.println("-----------------------------------");
        }
    }
}
