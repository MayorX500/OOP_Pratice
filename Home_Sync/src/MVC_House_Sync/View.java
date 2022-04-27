package MVC_House_Sync;
import java.util.Scanner;

import Auxiliar.Pair;
import Simulator.Invoice;

import java.time.*;
import java.time.format.*;

public class View{
    private final Scanner input = new Scanner(System.in);

    public int welcomeMenu(){
        System.out.println("""
            Welcome to the Smart House Simulator, please select an option: 
            
            1 - Start 
            2 - Quit
            """);
        return (input.nextInt());
    }

    //base menu
    public int baseMenu(){
        System.out.println("""
            Select an option:

            1 - Advance time
            2 - Edit/add houses
            3 - Create simulation
            4 - Edit simulation values
            
            0 - Quit
            """);
        return (input.nextInt());
    }

    //menu create a simulation or use one 
    public int menu_CS(){
        System.out.println("""
            Select an option:
            
            1 - Create a simulation
            2 - Import a simulation file
            
            0 - Back
            """);
        return (input.nextInt());
    }

    //menu set date -- first thing after selecting 'Create a simulation'
    public LocalDate menu_SD(){

        return ask_input_l("Provide new date in format 'YYYY-MM-DD'.");
    }

    public int advTimeMenu(){
        System.out.println("""
            Select an option:

            1 - Advance 1 day
            2 - Advance to new date
            
            0 - Quit
            """);
        return (input.nextInt());
    }

   //menu edit a simulation's values -- 'Edit simulation values'
    public int menu_SV(){
        System.out.println("""
            Select an option:
            
            1 - Alter energy provider parameters
            2 - Present billing statistics

            0 - Back
            """);
        return (input.nextInt());
    }

    //menu when 'Edit/add houses' is selected
    public int houseMenu(){
        System.out.println("""
            Select an option:

            1 - Add new house
            2 - Eliminate existing house
            3 - Edit existing house
            
            0 - Back
            """);
        return (input.nextInt());
    }

    //menu edit a house
    public int menu_EH(){
        System.out.println("""
            Select an option:

            1 - Turn ON/OFF all devices in a room
            2 - Turn ON/OFF specific device
            3 - Swap energy provider
            
            0 - Back
            """);
        return (input.nextInt());
    }

    public int menu_C1(){
        System.out.println("""
            Select an option:
            
            1 - Create a client
            2 - Create an address
            3 - Create a supplier
            
            0 - Save changes
            """);
        return (input.nextInt());
    }

    public void printAddress(String street, int street_number, String city, Pair<Integer,Integer> post_code){
        System.out.println(street + " " + street_number + "\n" + city + " " + post_code.getL() + "-" + post_code.getR() + "\n");
    }

    public void printDate (LocalDate initial_date, LocalDate final_date){
        System.out.println("Issued from" + initial_date.toString() + " to " + final_date.toString() + "\n");
    }

    public void invoice(Invoice invoice){
        System.out.println("###################################\nInvoice\n");
        printAddress(invoice.getAddress().getStreet(),invoice.getAddress().getStreet_number(), invoice.getAddress().getCity(),
                     invoice.getAddress().getPost_code());
        printDate(invoice.getInitial_date(), invoice.getFinal_date());
        
        //falta completar
    }

    // ask user for a string:
    public String ask_input_s(String s){
        System.out.println(s);
        return input.nextLine();
    }

    // ask user for a int:
    public int ask_input_i(String s){
        System.out.println(s);
        return input.nextInt();
    }

    // ask user for a float:
    public float ask_input_f(String s){
        System.out.println(s);
        return input.nextFloat();
    }

    // ask user for a double
    public double ask_input_d(String s){
        System.out.println(s);
        return input.nextDouble();
    }

    // ask user for a LocalDate
    public LocalDate ask_input_l(String s){
        System.out.println(s);
        String newS = input.nextLine();

        DateTimeFormatter f = DateTimeFormatter.ofPattern("YYYY-MM-DD");
        LocalDate date = LocalDate.parse(newS, f);

        return date;
    }
}