package MVC_House_Sync;
import java.util.Scanner;

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

    //menu create a simulation or use one 
    public int menu_CS(){
        System.out.println("""
            Select an option:
            
            1 - Create a simulation
            2 - Import a simulation file
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
}