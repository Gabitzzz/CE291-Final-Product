package Team31;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // The GUI
        new Template();

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

    Main(int n)
    {
        if (n == 1)
        {
            Data Data = new Data();
            Data.readFile("deaths");
            ArrayList<Deaths> deaths = Data.getDeathsArray();

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
            ArrayList<Cases> cases = Data.getCasesArray();

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


