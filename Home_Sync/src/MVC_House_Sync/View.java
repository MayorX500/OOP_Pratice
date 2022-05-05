package MVC_House_Sync;
import java.util.Scanner;

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

    private final static int ENTRIES = 10;
    private final Scanner input = new Scanner(System.in);


    public View() {
    }


    public void loadingMenu(){
        System.out.println("""
                SimCity
                    _by_HouseSync_
                """);
    }

    //base menu
    public String baseMenu(){
        System.out.println("""
            Select an option:

            1 - Create simulation
            2 - Edit/add houses
            3 - Advance time
            4 - Present billing statistics
            5 - Print Suppliers
            
            0 - Quit
            """);
        return (input.nextLine());
    }

    //menu create a simulation or use one 
    public String menu_CS(){
        System.out.println("""
            Select an option:
            
            1 - Create simulation
            2 - Import a simulation file
            
            0 - Back
            """);
        return (input.nextLine());
    }

    //menu set date -- first thing after selecting 'Create a simulation'
    public LocalDateTime menu_SD(){

        return ask_input_l("Provide new date in format 'YYYY-MM-DD'.");
    }

    public String addTimeMenu(){
        System.out.println("""
            Select an option:

            1 - Advance 1 day
            2 - Advance to new date
            
            0 - Back
            """);
        return (input.nextLine());
    }


    //menu when 'Edit/add houses' is selected
    public String houseMenu(){
        System.out.println("""
            Select an option:

            1 - Add new house
            2 - Eliminate existing house
            3 - Edit existing house
            4 - View existing houses
            
            0 - Back
            """);
        return (input.nextLine());
    }

    //menu edit a house
    public String menu_EH(){
        System.out.println("""
            Select an option:

            1 - Turn ON/OFF all devices in a room
            2 - Turn ON/OFF specific device
            3 - Swap energy provider
            
            0 - Back
            """);
        return (input.nextLine());
    }

    public String menu_C(){
        System.out.println("""
            Select an option:
            
            1 - Create a client
            2 - Create an address
            3 - Create a supplier
            4 - Add a division
            9 - Cancel
            
            0 - Create

            Disclaimer:
            If not all fields are filled, default values may be applied.
            """);
        return (input.nextLine());
    }

    public String menu_Edit(){
        System.out.println("""
            Select an option:
            
            1 - Edit client
            2 - Edit address
            3 - Edit supplier
            4 - Edit division
            5 - View divisions
            
            0 - Save changes
            """);
        return (input.nextLine());
    }

    public String menu_EditDivision(){
        System.out.println("""
            Select an option:
            
            1 - Edit division's name
            2 - Edit division's devices
            
            0 - Save changes
            """);
        return (input.nextLine());
    }

    public String menu_EditDevice(){
        System.out.println("""
            Select an option:
            
            1 - Edit device's name
            2 - Change device's mode
            3 - Edit device's brand
            4 - Edit device's power usage
            5 - Edit device's base cost
            
            0 - Save changes
            """);
        return (input.nextLine());
    }

    public String menu_EditValues(){
        System.out.println("""
            Select an option:
            
            1 - Edit supplier's name
            2 - Edit base price
            3 - Edit tax value
            4 - Edit tax out of range value
            
            0 - Save changes
            """);
        return (input.nextLine());
    }

    public String menu_EditAddress(){
        System.out.println("""
            Select an option:
            
            1 - Edit the city's name
            2 - Edit the street's name
            3 - Edit  number of the street
            4 - Edit the first post-code
            
            0 - Save changes
            """);
        return (input.nextLine());
    }

    public String menu_EditClient(){
        System.out.println("""
            Select an option:
            
            1 - Edit the client's name
            2 - Edit the client's nif
            
            0 - Save changes
            """);
        return (input.nextLine());
    }

    public String menu_chooseSupplier(){
        System.out.println("""
            Select an option:
            
            1 - Create Supplier
            2 - Choose existing Supplier
            2 - View Suppliers
            
            0 - Save changes
            """);
        return (input.nextLine());
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

    public void printDate (LocalDateTime initial_date, LocalDateTime final_date){
        System.out.println("Issued from" + initial_date.toString() + " to " + final_date.toString() + "\n");
    }

    public void printConsumption(House house){
        System.out.println("Consumption - " + house.getDaily_consumption());
    }

    
    public void printPricePerWatt(House house){
        System.out.println("Price per Watt - " + house.getSupplier().getBase_price());
    }


    public void printFinalPriceAproximation(House house,LocalDateTime date, LocalDateTime date2){
        double price = 0;
        try {
            price = house.getHouse_daily_Price() * date.until(date2,ChronoUnit.DAYS);
        }
        catch(Empty_House e) {
            System.out.println("The house is empty therefore doesn't have expenses." );   
        }
        System.out.println("Price to pay - " + price);
    }

    public void printFinalPrice(Double final_usage,Suppliers supplier){
        double final_price = final_usage*supplier.getSupplier_rate();
        System.out.println("Price to pay - " + final_price);
    }

    public void viewHouses(Simulator simulator){
        int j = 0,i = 0;
        House houses[] = new House[simulator.getHouses().size()];
        simulator.getHouses().toArray(houses);
		for(;i>=0 && i<houses.length;){
            View.clear();
			for(j = i;j<(i+ENTRIES) && j<houses.length;j++){
                printAddress(j + " - " + houses[j].getAddress().getStreet(),
                                        houses[j].getAddress().getStreet_number(),
                                        houses[j].getAddress().getCity(),
                                        houses[j].getAddress().getPost_code()
                                        );
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
            View.clear();
			for(j = i;j<(i+ENTRIES) && j<suppliers.length;j++){
                printSupplier(j + " - " + suppliers[j].getSupplier_name());
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
            View.clear();
			for(j = i;j<(i+ENTRIES) && j<division.length;j++){
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
			for(j = i;j<(i+ENTRIES) && j<device.length;j++){
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

    public void invoice(Invoice invoice,House house,double final_usage){
        System.out.println("###################################\nInvoice " + invoice.getId() + "\n");
        printAddress(invoice.getAddress().getStreet(),invoice.getAddress().getStreet_number(), invoice.getAddress().getCity(),
                     invoice.getAddress().getPost_code());
        printDate(invoice.getInitial_date(), invoice.getFinal_date());
        printConsumption(house);
        printPricePerWatt(house);
        printFinalPrice(final_usage,house.getSupplier());
        System.out.println("Aproximated price:");
        printFinalPriceAproximation(house, invoice.getInitial_date(), invoice.getFinal_date());
        System.out.println("\n###################################\n\n");
    }

    public void print_s(String s){
        System.out.println(s);
    }

    // ask user for a string:
    public String ask_input_s(String s){
        System.out.println(s);
        return input.nextLine();
    }

    // ask user for a int:
    public int ask_input_i(String s){
        System.out.println(s);
        return Integer.parseInt(input.nextLine());
    }

    // ask user for a float:
    public float ask_input_f(String s){
        System.out.println(s);
        return Float.parseFloat(input.nextLine());
    }

    // ask user for a double
    public double ask_input_d(String s){
        System.out.println(s);
        return Double.parseDouble(input.nextLine());
    }

    // ask user for a LocalDateTime
    public LocalDateTime ask_input_l(String s){
        System.out.println(s);
        String newS = input.nextLine();

        DateTimeFormatter f = DateTimeFormatter.ofPattern("YYYY-MM-DD");
        LocalDateTime date = LocalDateTime.parse(newS, f);

        return date;
    }

    //STATIC DEFINITIONS
    public static void unrecognizedCommandError() {
        System.out.println("Option not recognized.");
    
    }

    public static void clear(){ System.out.print("\033\143"); }

    public static void showException (Exception e) {System.out.println(e.toString());}

}