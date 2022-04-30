package MVC_House_Sync;
import java.util.Scanner;
import java.util.Set;

import Auxiliar.Pair;
import Exceptions.Empty_House;
import House.Address;
import House.Divisions;
import House.House;
import Simulator.Invoice;
import Simulator.Simulator;
import SmartDevice.SmartDevice;
import Suppliers.Suppliers;

import java.time.*;
import java.time.format.*;
import java.time.temporal.ChronoUnit;

public class View{

    private final static int ENTRIES = 5;
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
            4 - Present billing statistics
            
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

    public int addTimeMenu(){
        System.out.println("""
            Select an option:

            1 - Advance 1 day
            2 - Advance to new date
            
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
            4 - View existing houses
            
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

    public int menu_C(){
        System.out.println("""
            Select an option:
            
            1 - Create a client
            2 - Create an address
            3 - Create a supplier
            4 - Create a division
            
            0 - Save changes
            """);
        return (input.nextInt());
    }

    public int menu_Edit(){
        System.out.println("""
            Select an option:
            
            1 - Edit client
            2 - Edit address
            3 - Edit supplier
            4 - Edit division
            5 - View divisions
            
            0 - Save changes
            """);
        return (input.nextInt());
    }

    public int menu_EditDivision(){
        System.out.println("""
            Select an option:
            
            1 - Edit division's name
            2 - Edit division's devices
            
            0 - Save changes
            """);
        return (input.nextInt());
    }

    public int menu_EditDevice(){
        System.out.println("""
            Select an option:
            
            1 - Edit device's name
            2 - Change device's mode
            3 - Edit device's brand
            4 - Edit device's power usage
            5 - Edit device's base cost
            
            0 - Save changes
            """);
        return (input.nextInt());
    }

    public int menu_EditValues(){
        System.out.println("""
            Select an option:
            
            1 - Edit supplier's name
            2 - Edit base price
            3 - Edit tax value
            4 - Edit tax out of range value
            
            0 - Save changes
            """);
        return (input.nextInt());
    }

    public int menu_EditAddress(){
        System.out.println("""
            Select an option:
            
            1 - Edit the city's name
            2 - Edit the street's name
            3 - Edit  number of the street
            4 - Edit the first post-code
            
            0 - Save changes
            """);
        return (input.nextInt());
    }

    public int menu_EditClient(){
        System.out.println("""
            Select an option:
            
            1 - Edit the client's name
            2 - Edit the client's nif
            
            0 - Save changes
            """);
        return (input.nextInt());
    }

    public void printAddress(Address a){
        printAddress(a.getStreet(), a.getStreet_number(), a.getCity(), a.getPost_code());
    }

    public void printSupplier(Suppliers a){
        printSupplier(a.getSupplier_name());
    }

    public void printDivision(Divisions a){
        printDivision(a.getDivision_name());
    }

    public void printDevice(SmartDevice a){
        printDivision(a.getDevice_name());
    }

    public void printAddress(String street, int street_number, String city, Pair<Integer,Integer> post_code){
        System.out.println(street + " " + street_number + "\n" + city + " " + post_code.getL() + "-" + post_code.getR() + "\n");
    }

    public void printSupplier(String supplier_name){
        System.out.println(supplier_name + "\n");
    }

    public void printDivision(String division_name){
        System.out.println(division_name + "\n");
    }

    public void printDevice(String device_name){
        System.out.println(device_name + "\n");
    }

    public void printDate (LocalDate initial_date, LocalDate final_date){
        System.out.println("Issued from" + initial_date.toString() + " to " + final_date.toString() + "\n");
    }

    public void printConsumption(House house){
        System.out.println("Consumption - " + house.getDaily_consumption());
    }

    
    public void printPricePerWatt(House house){
        System.out.println("Price per Watt - " + house.getSupplier().getBase_price());
    }


    public void printFinalPrice(House house,LocalDate date, LocalDate date2){
        double price = 0;
        try {
            price = house.getHouse_daily_Price() * date.until(date2,ChronoUnit.DAYS);
        }
        catch(Empty_House e) {
            System.out.println("The house is empty therefore doesn't have expenses." );   
        }
        System.out.println("Price to pay - " + price);
    }
    public void viewHouses(Simulator simulator){
        int j = 0,i = 0;
        House houses[] = new House[simulator.getHouses().size()];
        simulator.getHouses().toArray(houses);
		for(;i>=0 && i<houses.length;){
			for(j = i;j<(i+ENTRIES);j++){
                System.out.println("\n" + j + " - ");
                printAddress(houses[j].getAddress());
            }
            String d = ask_input_s("Previous(p), Next(n), Quit(q):");
			switch (d) {
				case "p":
					i-=ENTRIES;
					break;
			
				case "n":
					i+=ENTRIES;
					break;
			
				case "q":
					i+=Integer.MAX_VALUE;
					break;
				default:
					break;
			}
		}
    }
    public int pageHouses(Simulator simulator){
        viewHouses(simulator);
        return ask_input_i("Please choose a Address");
    }

    public void viewSuppliers(Simulator simulator){
        int j = 0,i = 0;
        Suppliers suppliers[] = new Suppliers[simulator.getSuppliers().size()];
        simulator.getSuppliers().toArray(suppliers);
		for(;i>=0 && i<suppliers.length;){
			for(j = i;j<(i+ENTRIES);j++){
                System.out.println("\n" + j + " - ");
                printSupplier(suppliers[j]);
            }
            String d = ask_input_s("Previous(p), Next(n), Quit(q):");
			switch (d) {
				case "p":
					i-=ENTRIES;
					break;
			
				case "n":
					i+=ENTRIES;
					break;
			
				case "q":
					i+=Integer.MAX_VALUE;
					break;
				default:
					break;
			}
		}
    }
    public int pageSuppliers(Simulator simulator){
        viewSuppliers(simulator);
        return ask_input_i("Please choose a Supplier");
    }

    public void viewDivisions(House house){
        int j = 0,i = 0;
        Divisions division[] = new Divisions[house.getDivisions().size()];
        house.getDivisions().toArray(division);
		for(;i>=0 && i<division.length;){
			for(j = i;j<(i+ENTRIES);j++){
                System.out.println("\n" + j + " - ");
                printDivision(division[j].getDivision_name());
            }
            String d = ask_input_s("Previous(p), Next(n), Quit(q):");
			switch (d) {
				case "p":
					i-=ENTRIES;
					break;
			
				case "n":
					i+=ENTRIES;
					break;
			
				case "q":
					i+=Integer.MAX_VALUE;
					break;
				default:
					break;
			}
		}
    }

    public int pageDivision(House house){
        viewDivisions(house);
        return ask_input_i("Please choose a Division");
    }

    public void viewDevices(Divisions division){
        int j = 0,i = 0;
        SmartDevice device[] = new SmartDevice[division.getDevices().size()];
        division.getDevices().toArray(device);
		for(;i>=0 && i<device.length;){
			for(j = i;j<(i+ENTRIES);j++){
                System.out.println("\n" + j + " - ");
                printDevice(device[j].getDevice_name());
            }
            String d = ask_input_s("Previous(p), Next(n), Quit(q):");
			switch (d) {
				case "p":
					i-=ENTRIES;
					break;
			
				case "n":
					i+=ENTRIES;
					break;
			
				case "q":
					i+=Integer.MAX_VALUE;
					break;
				default:
					break;
			}
		}
    }
    public int pageDevices(Divisions division){
        viewDevices(division);
        return ask_input_i("Please choose a SmartDevice");
    }

    public void invoice(Invoice invoice,House house){
        System.out.println("###################################\nInvoice " + invoice.getId() + "\n");
        printAddress(invoice.getAddress().getStreet(),invoice.getAddress().getStreet_number(), invoice.getAddress().getCity(),
                     invoice.getAddress().getPost_code());
        printDate(invoice.getInitial_date(), invoice.getFinal_date());
        printConsumption(house);
        printPricePerWatt(house);
        printFinalPrice(house, invoice.getInitial_date(), invoice.getFinal_date());
        System.out.println("\n###################################\n\n");
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






    //STATIC DEFINITIONS
    public static void unrecognizedCommandError() {
        System.out.println("Option not recognized.");
    
    }

    public static void showException (Exception e) {System.out.println(e.toString());}

}