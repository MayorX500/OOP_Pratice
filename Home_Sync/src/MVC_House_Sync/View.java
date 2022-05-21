package MVC_House_Sync;

import Auxiliar.*;
import Client.Client;
import Exceptions.*;
import House.*;
import Simulator.*;
import SmartDevice.*;
import Suppliers.*;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.Set;

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
        clear();
        loadingMenu();
        System.out.println("""
            Select an option:

            1 - Manage simulation
            2 - Manage houses
            3 - Present billing statistics
            4 - View Menu
            
            0 - Quit
            """);
        return (input.nextLine());
    }

    public String manage_houses(){
        clear();
        loadingMenu();
        System.out.println("""
            Select an option:

            1 - Create House
            2 - Manage House
            3 - Change House Supplier
            4 - Change States
            
            0 - Back
            """);
        return (input.nextLine());
    }

    //menu create a simulation or use one 
    public String menu_CS(){
        clear();
        System.out.println("""
            Select an option:
            
            1 - Create new simulation
            2 - Manage current simulation
            3 - Load Simulation
            4 - Save Simulation
            
            0 - Back
            """);
        return (input.nextLine());
    }

    //menu set date -- first thing after selecting 'Create a simulation'
    public LocalDateTime menu_SD(){
        clear();
        return ask_input_l("Provide new date in format 'YYYY-MM-DD'.");
    }

    public String addTimeMenu(){
        clear();
        System.out.println("""
            Select an option:

            1 - Advance 1 hour
            2 - Advance 1 day
            3 - Advance to new date
            
            0 - Back
            """);
        return (input.nextLine());
    }


    //menu when 'Edit/add houses' is selected
    public String houseMenu(){
        clear();
        System.out.println("""
            Select an option:

            1 - Add new house
            2 - Eliminate existing house
            3 - Edit existing house
            
            0 - Back
            """);
        return (input.nextLine());
    }

    //menu edit a house
    public String menu_EH(){
        clear();
        System.out.println("""
            Select an option:

            1 - Turn ON/OFF all devices in a room
            2 - Turn ON/OFF specific device
            3 - Turn ON/OFF all devices in the house
            
            0 - Back
            """);
        return (input.nextLine());
    }

    public String menu_C(){
        clear();
        System.out.println("""
            Select an option:
            
            1 - Create a client
            2 - Create an address
            3 - Add a supplier
            4 - Add a division
            
            9 - Cancel
            0 - Create

            Disclaimer:
            If not all fields are filled, default values may be applied.
            """);
        return (input.nextLine());
    }

    public String menu_Edit(){
        clear();
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

    public String manage_divisions(){
        clear();
        System.out.println("""
            Select an option:
            
            1 - Edit divisions
            2 - View divisions
            
            0 - Back
            """);
        return (input.nextLine());
    }

    public String menu_EditDivision(){
        clear();
        System.out.println("""
            Select an option:
            
            1 - Edit division's name
            2 - Edit division's devices
            
            0 - Back
            """);
        return (input.nextLine());
    }

    public String menu_EditDevice(){
        clear();
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
        clear();
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
        clear();
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
        clear();
        System.out.println("""
            Select an option:
            
            1 - Edit the client's name
            2 - Edit the client's nif
            
            0 - Save changes
            """);
        return (input.nextLine());
    }

    public String menu_chooseSupplier(){
        clear();
        System.out.println("""
            Select an option:
            
            1 - Create Supplier
            2 - Choose existing Supplier
            
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

    public void printClient(Client a){
        printClient(a.getClient_id(), a.getClient_name(), a.getClient_NIF());
    }


    public void printAddress(String street, int street_number, String city, Pair<Integer,Integer> post_code){
        System.out.println(street + " " + street_number + "\n" + city + " " + post_code.getL() + "-" + post_code.getR() + "\n");
    }

    public void printSupplier(String supplier_name){
        System.out.println(supplier_name + "\n");
    }

    public void printClient(int id,String client_name, int nif){
        System.out.println(id + " - " + client_name + "\n" + "NIF: " + nif + "\n");
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
            Wait.wait(5000);   
        }
        System.out.println("Price to pay - " + price);
    }

    public void printFinalPrice(Double final_usage,Suppliers supplier){
        double final_price = final_usage*supplier.getSupplier_rate();
        System.out.println("Price to pay - " + final_price);
    }

    public void viewHouses(Set<House> h){
        clear();
        int j = 0,i = 0;
        House houses[] = new House[h.size()];
        h.toArray(houses);
		for(;i>=0 && i<houses.length;){
            View.clear();
			for(j = i;j<(i+ENTRIES) && j<houses.length;j++){
                printAddress(houses[j].getHouse_id() + " - " + houses[j].getAddress().getStreet(),
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
    public House pageHouses(Set<House> houses) throws Empty_Simulation{
        House out = null;
        int h_id =-1;
        clear();
        if(houses.size()>0){
            viewHouses(houses);
            h_id = ask_input_i("Please choose a House address using the number.");
            for(House h : houses){
                if(h.getHouse_id()== h_id){
                    out = h;
                }
            }
        }else throw new Empty_Simulation("Empty Simulation.");
        return out;
    }

    public void viewSuppliers(Set<Suppliers> s){
        clear();
        int j = 0,i = 0;
        Suppliers suppliers[] = new Suppliers[s.size()];
        s.toArray(suppliers);
		for(;i>=0 && i<suppliers.length;){
            View.clear();
			for(j = i;j<(i+ENTRIES) && j<suppliers.length;j++){
                printSupplier(suppliers[j].getSupplier_id() + " - " + suppliers[j].getSupplier_name());
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

    public Suppliers pageSuppliers(Set<Suppliers> suppliers) throws Empty_Simulation{
        Suppliers out = null;
        int s_id =-1;
        clear();
        if(suppliers.size()>0){
            viewSuppliers(suppliers);
            s_id = ask_input_i("Please choose a Supplier using the number.");
            for(Suppliers s : suppliers){
                if(s.getSupplier_id()== s_id){
                    out = s;
                }
            }
        }else throw new Empty_Simulation("Empty Simulation.");
        return out;
    }

    public void viewAddress(Set<House> h){
        clear();
        int j = 0,i = 0;
        House houses[] = new House[h.size()];
        h.toArray(houses);
		for(;i>=0 && i<houses.length;){
            View.clear();
			for(j = i;j<(i+ENTRIES) && j<houses.length;j++){
                printAddress(houses[j].getHouse_id() + " - " + houses[j].getAddress().getStreet(),
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
    public House pageAddress(Set<House> h) throws Empty_Simulation{
        House out = null;
        int h_id =-1;
        clear();
        if(h.size()>0){
            viewAddress(h);
            h_id = ask_input_i("Please choose a House address using the number.");
            for(House house : h){
                if(house.getHouse_id()== h_id){
                    out = house;
                }
            }
        }else throw new Empty_Simulation("Empty Simulation.");
        return out;
    }
    public void viewClient(Set<House> h){
        clear();
        int j = 0,i = 0;
        House houses[] = new House[h.size()];
        h.toArray(houses);
		for(;i>=0 && i<houses.length;){
            View.clear();
			for(j = i;j<(i+ENTRIES) && j<houses.length;j++)
                printClient(houses[j].getOwner());
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
    
    public House pageClient(Set<House> h) throws Empty_Simulation{
        House out = null;
        int c_id =-1;
        clear();
        if(h.size()>0){
            viewClient(h);
            c_id = ask_input_i("Please choose a Client using the number.");
            for(House house : h){
                if(house.getOwner().getClient_id()== c_id){
                    out = house;
                }
            }
        }else throw new Empty_Simulation("Empty Simulation.");
        return out;
    }
    public void viewDivisions(Set<Divisions> div){
        clear();
        int j = 0,i = 0;
        Divisions division[] = new Divisions[div.size()];
        div.toArray(division);
		for(;i>=0 && i<division.length;){
            View.clear();
            for(j = i;j<(i+ENTRIES) && j<division.length;j++){
                    printDivision(j + " - " + division[j].getDivision_name());
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

    public Divisions pageDivision(Set<Divisions> divisions,int id) throws Empty_House{
        Divisions out = null;
        String div_name = null;
        clear();
        if(divisions.size()>0){
            viewDivisions(divisions);
            div_name = ask_input_s("Please choose a Division using it's name.");
            for(Divisions div : divisions){
                if(div.getDivision_name().equals(div_name)){
                    out = div;
                }
            }
        }else throw new Empty_House(""+id);
        return out;
    }

    public void viewDevices(Set<SmartDevice> devices){
        clear();
        int j = 0,i = 0;
        SmartDevice device[] = new SmartDevice[devices.size()];
        devices.toArray(device);
		for(;i>=0 && i<device.length;){
			for(j = i;j<(i+ENTRIES) && j<device.length;j++){
                printDevice(device[j].getDevice_id() + " - " + device[j].getDevice_name());
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
    public SmartDevice pageDevices(Set<SmartDevice> devices) throws Empty_Division{
        SmartDevice out = null;
        int dev_id = -1;
        clear();
        if(devices.size()>0){
            viewDevices(devices);
            dev_id = ask_input_i("Please choose a Device using it's ID.");
            for(SmartDevice dev : devices){
                if(dev.getDevice_id() ==(dev_id)){
                    out = dev;
                }
            }
        }else throw new Empty_Division("");
        return out;
    }

    public void invoice(Invoice invoice){
        clear();
        System.out.println("###################################\nInvoice " + invoice.getId() + "\n");
        printAddress(invoice.getAddress().getStreet(),invoice.getAddress().getStreet_number(), invoice.getAddress().getCity(),
                     invoice.getAddress().getPost_code());
        printDate(invoice.getInitial_date(), invoice.getFinal_date());
        System.out.println("Real Consumption : "+invoice.getFinal_price()/invoice.getPrice_per_watt() + " W");

        System.out.println("Real Price : "+invoice.getPrice_to_pay() + " €");

        System.out.println("\n###################################\n\n");
    }

    public void print_s(String s){
        System.out.println(s);
        Wait.wait(5000);
    }

    // ask user for a string:
    public String ask_input_s(String s){
        System.out.println(s);
        return input.nextLine();
    }

    public boolean ask_input_on_off(String string) {
        System.out.println(string);
        String var = input.nextLine();
        boolean out = false;
        switch (var) {
            case "on","ON","On","oN":
                out = true;
                break;
            case "Off","OFf","OFF","OfF","off","oFf","ofF","oFF":
                out = false;
                break;
            default:
                break;
        }
        return out;
    }

    public boolean ask_input_y_n(String string) {
        System.out.println(string);
        String var = input.nextLine();
        boolean out = false;
        switch (var) {
            case "no","nO","No","NO":
                out = false;
                break;
            case "yes","yeS","yEs","yES","Yes","YeS","YEs","YES":
                out = true;
                break;
            default:
                break;
        }
        return out;
    }

    // ask user for a int:
    public int ask_input_i(String s){
        System.out.println(s);
        int out = 0;
        try{
            out = Integer.parseInt(input.nextLine());
        }catch(NumberFormatException e){};
        return out;
    }

    // ask user for a float:
    public float ask_input_f(String s){
        System.out.println(s);
        float out = 0;
        try{
            out = Float.parseFloat(input.nextLine());
        }catch(NumberFormatException e){};
        return out;
    }

    // ask user for a double
    public double ask_input_d(String s){
        System.out.println(s);
        double out = 0;
        try{
            out = Double.parseDouble(input.nextLine());
        }catch(NumberFormatException e){};
        return out;
    }

    // ask user for a LocalDateTime
    public LocalDateTime ask_input_l(String s){
        System.out.println(s);
        String newS = input.nextLine();
        LocalDate date = LocalDate.parse(newS);
        LocalDateTime dateTime = date.atStartOfDay();

        return dateTime;
    }

    public String view_menu() {
        clear();
        loadingMenu();
        System.out.println("""
            Select an option:

            1 - List Houses
            2 - List Suppliers
            
            0 - Back
            """);
        return (input.nextLine());
    }

    //STATIC DEFINITIONS
    public static void unrecognizedCommandError() {
        System.out.println("Option not recognized.");
    
    }

    public static void clear(){ System.out.print("\033\143"); }

    public static void showException (Exception e) {System.out.println(e.toString());}




}