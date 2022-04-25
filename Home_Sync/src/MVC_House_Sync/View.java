package MVC_House_Sync;
import java.util.Scanner;
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
            3 - Edit simulation values
            
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
            
            0 - Quit
            """);
        return (input.nextInt());
    }

    //menu set date -- first thing after selecting 'Create a simulation'
    public LocalDate menu_SD(){
        return ask_input_l("Provide starting date in format 'YYYY-MM-DD'.");
    }

    //menu create new simulation
    public int menu_CNS(){
        System.out.println("""
            Select an option:
            
            1 - Add energy provider
            2 - Add house
            
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

            0 - Quit
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
            
            0 - Quit
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
            
            0 - Quit
            """);
        return (input.nextInt());
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

    public LocalDate ask_input_l(String s){
        System.out.println(s);
        String newS = input.nextLine();

        DateTimeFormatter f = DateTimeFormatter.ofPattern("YYYY-MM-DD");
        LocalDate date = LocalDate.parse(newS, f);

        return date;
    }
}