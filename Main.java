package assignment;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Data Data = new Data();

        Data.readFile("deaths");
        Data.readFile("cases");

        ArrayList<Deaths> deaths = Data.getDeathsArray();

        System.out.println(deaths.get(45).date);

        ArrayList<Cases> cases = Data.getCasesArray();

        System.out.println(cases.get(270).date);
    }
}
