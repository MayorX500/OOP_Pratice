package MVC_House_Sync;

import java.io.IOException;
import java.time. LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;

import Auxiliar.*;
import Client.*;
import Exceptions.*;
import House.*;
import Parser.*;
import Simulator.Simulator;
import SmartDevice.*;
import Suppliers.*;

public class Controler {
    private Model model;
    private View view;
    private boolean unsavedChanges; 

    public Controler(){
        Model m;
        try {
            m = Parser.parse("../../../Resources/logs.txt");
        }
        catch (Wrong_Line e) {
            View.showException(e);
            m = new Model();
        }
        this.model = m;
        this.view = new View();
        this.unsavedChanges = false;
        view.loadingMenu();
    }

    //Create client
    public Client createClient() {
        String name = view.ask_input_s("Enter the name of the new Client:");
        int nif = view.ask_input_i("Enter the NIF of the new Client:");
        return new Client(name, nif);
    }

    // create Address
    public Address createAddress() {
        String city = view.ask_input_s("Enter the name of the city:");
        String street = view.ask_input_s("Enter the name of the street:");
        int street_number = view.ask_input_i("Enter the number of the street:");
        int l = view.ask_input_i("Enter the first four numbers of the post-code:");
        int r = view.ask_input_i("Enter the last three numbers of the post-code:");
        Pair<Integer, Integer> post_code = new Pair<Integer, Integer>(l, r);

        return new Address(street, street_number, city, post_code);
    }

    // create Suppliers
    public Suppliers createSuppliers() {
        String supplier_name = view.ask_input_s("Enter the name of the supplier:");
        float base_price = view.ask_input_f("Enter the base price");
        int tax_int = view.ask_input_i("Enter the tax percentage:");
        float tax = tax_int / 100;
        int out_of_range_tax_int = view.ask_input_i("Enter the out of range tax percentage:");
        float out_of_range_tax = out_of_range_tax_int / 100;

        return new Suppliers(supplier_name, base_price, tax, out_of_range_tax);
    }

    // Edit Suppliers
    public void editSuppliers(Simulator simulator) throws Empty_Simulation {
        int choice = view.pageSuppliers(simulator);
        Suppliers suppliers[] = new Suppliers[simulator.getSuppliers().size()];
        simulator.getSuppliers().toArray(suppliers);

        Suppliers supplier = suppliers[choice].clone();
        editSuppliersMenu(supplier);

        if (simulator.getHouses().size() > 0){
            for (House house: simulator.getHouses()){
                if (house.getSupplier().equals(suppliers[choice])) house.setSupplier(supplier);
            }
        }
        else throw new Empty_Simulation("Empty simulator");

    }
    
    // create House
    public House createHouse(Client owner, Address address, Suppliers supplier) {
        return new House(address, owner, new HashSet<>(), supplier, 0);
    }

    // edit House
    public House editHouse(House house) {
        
        return house;
    }

    // create Divisions
    public House createDivision(House house) {
        String division_name = view.ask_input_s("Enter the name of the division:");
        Divisions division = new Divisions(division_name, new HashSet<>());
        createDevice(division);
        house.addDivision(division);
        return house;
    }

    // edit Divisions
    public House editDivision(House house) throws Empty_House {
        int choice = view.pageDivision(house);
        Divisions divisions[] = new Divisions[house.getDivisions().size()];
        this.model.getSimulator().getHouseFromAddress(house.getAddress()).getDivisions().toArray(divisions);
              
        int choice2 = view.menu_EditDivision();
        switch(choice2) {
            case 1:
                String division_name = view.ask_input_s("Enter the new name for the division:");
                divisions[choice].setDivision_name(division_name);
                view.menu_EditDivision();
                break;
            case 2:
                int choice3 = view.pageDevices(divisions[choice]);
                SmartDevice devices[] = new SmartDevice[this.model.getSimulator().getHouseFromAddress(house.getAddress()).getDivisions().size()];
                this.model.getSimulator().getHouseFromAddress(house.getAddress()).getDivisions().toArray(divisions);
                
                editDevice(devices[choice3]);
                view.menu_EditDevice();         
            }
        return house;
    }

    private void editDevice(SmartDevice device) {
        int choice = view.menu_EditDevice();
        switch(choice){
            case 1:
                String device_name = view.ask_input_s("Enter the new name of the device:");
                device.setDevice_name(device_name);
                break;
            case 2:
                if(device.getIs_on()==true) {
                    System.out.println("The device is now off");
                    device.setIs_on(false);
                } else {
                    System.out.println("The device is now on");
                    device.setIs_on(true);
                }
                break;
            case 3:
                String brand = view.ask_input_s("Enter the new brand of the device:");
                device.setBrand(brand);
                break;
            case 4:
                double power_usage = view.ask_input_d("Enter the new power usage of the device:");
                device.setPower_usage(power_usage);
                break;
            case 5:
                double base_cost = view.ask_input_d("Enter the new base cost of the device:");
                device.setBase_cost(base_cost);
                break;
            case 0:
            
                break;
        }
    }

    // create SmartDevices
    public Divisions createDevice(Divisions division) {
        int select = view.ask_input_i(
                "Choose the type of smart device:\n\t1 - SmartBulb\n\t2 - SmartCamera\n\t3 - SmartSpeaker");
        String device_name = view.ask_input_s("Enter the name of the new device:");
        String brand = view.ask_input_s("Enter the name of the device's brand:");

        switch (select) {
            case 1:
                float dimension = view.ask_input_f("Enter the dimension of the bulb:");
                SmartDevice smartdevice1 = new SmartBulb(device_name, brand, dimension);
                division.addDevice(smartdevice1.clone());
                break;

            case 2:
                String resolution = view.ask_input_s("Enter the resolution of the camera in the following format (1234x1234):");
                String resol = resolution.replaceAll("\\(", "").replaceAll("\\)","");
                String[] real = resol.split("x");
                double file_size = view.ask_input_d("Enter the size of the files on the camera:");
                SmartDevice smartdevice2 = new SmartCamera(device_name, brand, new Pair<Integer,Integer>(Integer.parseInt(real[0]),Integer.parseInt(real[1])), file_size);
                division.addDevice(smartdevice2.clone());
                break;

            case 3:
                int volume = view.ask_input_i("Enter the volume of the speaker:");
                String radio_info = view.ask_input_s("Enter the online radio on:");
                SmartDevice smartdevice3 = new SmartSpeaker(device_name, brand, volume, 15f, radio_info);
                division.addDevice(smartdevice3.clone());
                break;

            default:
                System.out.println("Error");
                break;
        }
        return division;
    }

    public void createSimulation() {
        boolean exit = false;
        while (!exit) {
            int choice = view.menu_C();
            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 2:
                    createAddress();
                    break;
                case 3:
                    createSuppliers();
                    break;
                case 0:

                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }

    }

    public void loadingMenu_controler(){
        boolean flag = true;
        while(flag) {
            if(unsavedChanges){
                String saves = view.ask_input_s("There are some unsaved changes, Do you wish to save them (Yes/No):");
                switch (saves){
                    case "Yes","Y","yes","y" -> {
                        //exportSimulationMenu();
                        unsavedChanges = false;
                        view.print_s("Closing SimCity.");
                        flag = false;
                    }
                    case "No","no","N","n" ->{
                        view.print_s("Closing SimCity.");
                        flag = false;
                    }
                    default -> {  
                        flag = true;
                    }
                }
            }
        }
    }

    public void billing_menu()throws Empty_Simulation{
        if(this.model.getSimulator().getSuppliers().size()>0){
            int choice2 = view.pageHouses(this.model.getSimulator());
            House houses[] = new House[this.model.getSimulator().getHouses().size()];
            this.model.getSimulator().getHouses().toArray(houses);
            Address address = houses[choice2].getAddress();
            view.invoice(this.model.getSimulator().getInvoiceFromAddress(address), houses[choice2]);        
        }else throw new Empty_Simulation(this.model.getSimulator().getHouses().toString());
    }

    public void firstMenu() throws Empty_Simulation{
        boolean flag = true;
        while(flag) {
            int choice = view.baseMenu();
            switch (choice) {
                case 1:
                    addTimeMenu_2();
                    break;
                case 2:
                    addHouseMenu_2();
                    break;
                case 3:
                    crSimMenu_2();
                    break;
                case 4:
                    billing_menu();
                    break;
                case 0:
                    loadingMenu_controler();
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
    }

    public void addTimeMenu_2() {
        boolean flag = true;
        while(flag) {
            int choice = view.addTimeMenu();
            switch (choice) {
                case 1:
                    this.model.getSimulator().setSimulation_date(this.model.getSimulator().getSimulation_date().plusDays(1));
                    try{
                        for(int i = 0; i<24;i++)
                        this.model.getSimulator().advanceOneHour();
                    }
                    catch(Empty_Simulation empty_sim){View.showException(empty_sim);}
                    catch(Empty_House empty_hous){View.showException(empty_hous);}
                    catch(Empty_Division empty_div){View.showException(empty_div);}
                    break;
                case 2:
                    LocalDateTime ask = view.menu_SD();
                    LocalDateTime date =  LocalDateTime.of(ask.getYear(), ask.getMonth(), ask.getDayOfMonth(), ask.getHour(), ask.getMinute(), ask.getSecond());
                    long diff = ChronoUnit.HOURS.between(this.model.getSimulator().getSimulation_date(), date);
                    try{
                        for(int i = 0; i<diff;i++)
                        this.model.getSimulator().advanceOneHour();
                    }
                    catch(Empty_Simulation empty_sim){View.showException(empty_sim);}
                    catch(Empty_House empty_hous){View.showException(empty_hous);}
                    catch(Empty_Division empty_div){View.showException(empty_div);}                    this.model.getSimulator().setSimulation_date(date);
                    break;
                case 0:
                    try{firstMenu();}
                    catch(Empty_Simulation e){
                        View.showException(e);
                    }
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
    }

    public void addHouseMenu_2() throws Empty_Simulation {
        boolean flag = true;
        while(flag) {
            int choice = view.houseMenu();
            switch (choice) {
                case 1:
                    createHouse(view.menu_C());
                    break;
                case 2:
                    Address address = createAddress();
                    this.model.getSimulator().eliminateHouses(this.model.getSimulator().getHouses(), address);
                    view.houseMenu();
                    break;
                case 3:
                    int choice2 = view.pageHouses(this.model.getSimulator());
                    House houses[] = new House[this.model.getSimulator().getHouses().size()];
                    this.model.getSimulator().getHouses().toArray(houses);
                    editHouseMenu(houses[choice2]);
                    break;
                case 4:
                    view.pageHouses(this.model.getSimulator());
                    break;
                case 0:
                    try{firstMenu();}
                    catch(Empty_Simulation e){
                        View.showException(e);
                    }
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
    }

    public void createHouse(int choice){
        boolean flag = true;
        while(flag) {
            switch (choice) {
                case 1:
                    //Create client
                    createClient();
                    break;
                case 2:
                    //Create address
                    createAddress();
                    break;
                case 3:
                    //Create supplier
                    createSuppliers();
                    break;
                case 0:
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
    }

    public void editHouseMenu(House house) throws Empty_Simulation{
        boolean flag = true;
        while(flag) {
            int choice = view.menu_Edit();
            switch (choice) {
                case 1:
                    //Edit client
                    editClientMenu(house.getOwner());
                    break;
                case 2:
                    //Edit address
                    editAddressMenu(house.getAddress());
                    break;
                case 3:
                    //Edit supplier
                    editSuppliers(this.model.getSimulator());
                    break;
                case 4:
                    break;
                case 5:
                    view.pageDivision(house);
                    break;
                case 0:
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
    }

    public void editSuppliersMenu(Suppliers supplier){
        boolean flag = true;
        while(flag) {
            int choice = view.menu_EditValues();
            switch(choice){
                case 1:
                    String supplier_name = view.ask_input_s("Enter the name of the supplier:");
                    supplier.setSupplier_name(supplier_name);
                    break;
                case 2:
                    float base_price = view.ask_input_f("Enter the base price");
                    supplier.setBase_price(base_price);
                    break;
                case 3:
                    int tax_int = view.ask_input_i("Enter the tax percentage:");
                    float tax = tax_int / 100;
                    supplier.setTax(tax);
                    break;
                case 4:
                    int out_of_range_tax_int = view.ask_input_i("Enter the out of range tax percentage:");
                    float out_of_range_tax = out_of_range_tax_int / 100;
                    supplier.setOut_of_range_tax(out_of_range_tax);
                    break;
                case 0:
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
    }

    public void editAddressMenu(Address address){
        boolean flag = true;
        while(flag) {
            int choice = view.menu_EditAddress();
            switch(choice){
                case 1:
                    String city = view.ask_input_s("Enter the name of the city:");
                    address.setCity(city);
                    break;
                case 2:
                    String street = view.ask_input_s("Enter the name of the street:");
                    address.setStreet(street);
                    break;
                case 3:
                    int street_number = view.ask_input_i("Enter the number of the street:");
                    address.setStreet_number(street_number);
                    break;
                case 4:
                    int l = view.ask_input_i("Enter the first four numbers of the post-code:");
                    int r = view.ask_input_i("Enter the last three numbers of the post-code:");
                    Pair<Integer,Integer> post_code = new Pair<>(l,r);
                    address.setPost_code(post_code);
                    break;
                case 0:
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;                  
            }
        }
    }

    public void editClientMenu(Client client){
        boolean flag = true;
        while(flag) {
            int choice = view.menu_EditClient();
            switch(choice){
                case 1:
                    String name = view.ask_input_s("Enter the name of the new Client:");
                    client.setClient_name(name);
                    break;
                case 2:
                    int nif = view.ask_input_i("Enter the NIF of the new Client:");
                    client.setClient_NIF(nif);
                    break;
                case 0:
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;                  
            }
        }
    }

    public void crSimMenu_2() {
        boolean flag = true;
        while(flag) {
            int choice = view.menu_CS();
            switch (choice) {
                case 1:
                    view.menu_C();
                    break;
                case 2:
                    importSimulatMenu();
                    break;
                case 0:
                    try{firstMenu();}
                    catch(Empty_Simulation e){
                        View.showException(e);
                    }
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;  
            }
        }
    }

    //EXPORT
    public void exportSimulationMenu() {
        try {
            FileHandler.exportModelToFile(this.model, view.ask_input_s("Name of the file:"));
            this.unsavedChanges = false;
        }
        catch (IOException e) {
            View.showException(e);
        }
    }

    //IMPORT
    public void importSimulatMenu() {
        String file = view.ask_input_s("Name of the file:");
        try {
            this.model = FileHandler.importModelFromFile(file);
        }
        catch (IOException | ClassNotFoundException e) {
            View.showException(e);
        }
    }
}
