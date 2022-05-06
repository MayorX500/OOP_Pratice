package MVC_House_Sync;

import java.io.IOException;
import java.time. LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

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
    private View view = new View();
    private boolean unsavedChanges; 

    public Controler(){
        Model m;
        
        try {
            m = Parser.parse("../../Resources/logs.txt");
        }
        catch (Wrong_Line e) {
            View.showException(e);
            m = new Model();
        }
        
        this.model = m;
        this.unsavedChanges = false;
        view.loadingMenu();
    }




//MENUS

    public void createSimulation() {
        boolean exit = false;
        while (!exit) {
            View.clear();
            String choice = view.menu_C();

            switch (choice) {
                case "1":
                    createClient();
                    break;
                case "2":
                    createAddress();
                    break;
                case "3":
                    createSuppliers();
                    break;
                case "0":
                exit = true;
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
            View.clear();
            String saves = view.ask_input_s("There are some unsaved changes, Do you wish to save them (Yes/No):");
            switch (saves){
                case "Yes","Y","yes","y" :
                    exportSimulationMenu();
                    unsavedChanges = false;
                    view.print_s("Closing SimCity.");
                    flag = false;
                    break;
                case "No","no","N","n" :
                    view.print_s("Closing SimCity.");
                    flag = false;
                    break;
                default :
                    break;
            }
        }
        if(!flag){
            System.exit(0);
        }
    }

    public void billing_menu(){
        View.clear();
        if(this.model.getSimulator().getSuppliers().size()>0){
            int choice2=-1;
            try {
                choice2 = view.pageHouses(this.model.getSimulator());
            } catch (Empty_Simulation e1) {
                View.showException(e1);
                createHouse();
            }
            House houses[] = new House[this.model.getSimulator().getHouses().size()];
            this.model.getSimulator().getHouses().toArray(houses);
            Address address = houses[choice2].getAddress();
            double fu=0;
            try{model.getSimulator().create_invoice_final_usage(houses[choice2]);}
            catch(Empty_House e){
                View.showException(e);
            }
            view.invoice(this.model.getSimulator().getInvoiceFromAddress(address), houses[choice2],fu);        
        }else view.print_s("This simulation is empty, please add some houses in the previous menu");;
    }

    public void firstMenu(){
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.baseMenu();
            switch (choice) {
                case "1":
                    crSimMenu_2();
                    break;
                case "2":
                    manage_houses();
                    break;
                case "3":
                    billing_menu();
                    break;
                case "4":
                    view_menu();
                    break;
                case "0":
                    if (this.unsavedChanges) {
                        loadingMenu_controler();
                    }
                    else System.exit(0);
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
    }

    private void manage_houses() {
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.manage_houses();
            switch (choice) {
                case "1":
                    addHouseMenu_2();
                    break;
                case "2":
                    manage_divisions();
                    break;
                case "3":
                    chooseSupplier();
                    break;
                case "0":
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }

    }

    private void manage_divisions() {
        House h = new House();
        House houses[] = new House[this.model.getSimulator().getHouses().size()];
        boolean flag = true;
        if(this.model.getSimulator().getHouses().size()>0){
            this.model.getSimulator().getHouses().toArray(houses);
            try {
                h = houses[view.pageHouses(this.model.getSimulator())];
            } catch (Empty_Simulation e) {
                
                View.showException(e);
                view.print_s("There are no houses available in the simulator, please create some.");
            Wait.wait(5000);
            flag = false;
            }
        }
        else{
            View.clear();
            view.print_s("There are no houses available in the simulator, please create some.");
            Wait.wait(5000);
            flag = false;
        }
        while(flag) {
            View.clear();
            String choice = view.manage_divisions();
            switch (choice) {
                case "1":
                    try {
                        editDivision(h);
                    } catch (Empty_House e) {
                        View.showException(e);
                        view.print_s("The house does not have any divisions");;
                    }
                    break;
                case "2":
                    view.viewDivisions(h);                                    
                    break;
                case "0":
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
    }




    private void view_menu() {
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.view_menu();
            switch (choice) {
                case "1":
                    view.viewHouses(this.model.getSimulator());
                    break;
                case "2":
                    view.viewSuppliers(this.model.getSimulator());
                    break;
                case "0":
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
    }

    public void addTimeMenu_2(){
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.addTimeMenu();
            switch (choice) {
                case "1":
                    this.model.getSimulator().setSimulation_date(this.model.getSimulator().getSimulation_date().plusDays(1));
                    try{
                        for(int i = 0; i<24;i++)
                        this.model.getSimulator().advanceOneHour();
                    }
                    catch(Empty_Simulation empty_sim){View.showException(empty_sim);}
                    catch(Empty_House empty_hous){View.showException(empty_hous);}
                    catch(Empty_Division empty_div){View.showException(empty_div);}
                    break;
                case "2":
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
                case "0":
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
    }

    public void addHouseMenu_2(){
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.houseMenu();
            switch (choice) {
                case "1":
                    createHouse();
                    break;
                case "2":
                    int choice2 = 0;
                    try {
                        choice2 = view.pageHouses(this.model.getSimulator());
                    } catch (Empty_Simulation e1) {
                        View.showException(e1);
                        view.print_s("The simulation is empty, nothing to delete.");
                        Wait.wait(5000);
                        firstMenu();
                    }
                    House houses[] = new House[this.model.getSimulator().getHouses().size()];
                    this.model.getSimulator().getHouses().toArray(houses);
                    this.model.getSimulator().eliminateHouses(this.model.getSimulator().getHouses(), houses[choice2].getAddress());
                    view.houseMenu();
                    break;
                case "3":
                    int choice3 = 0;
                    try {
                        choice3 = view.pageHouses(this.model.getSimulator());
                    } catch (Empty_Simulation e1) {
                        View.showException(e1);
                        view.print_s("The simulation is empty, please create a house.");
                        Wait.wait(5000);
                        createHouse();
                    }
                    if(this.model.getSimulator().getHouses().size()>0){
                        House houses2[] = new House[this.model.getSimulator().getHouses().size()];
                        this.model.getSimulator().getHouses().toArray(houses2);
                        editHouseMenu(houses2[choice3]);
                    }else{
                        view.print_s("The simulation is empty");
                    Wait.wait(5000);
                    }
                    break;
                case "0":
                flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
    }

    public void createHouse(){
        boolean flag = true;
        Client c = new Client();
        Address a = new Address();
        Suppliers s = new Suppliers();
        Set<Divisions> divs = new HashSet<>();
        while(flag) {
            View.clear();
            String choice = view.menu_C();
            switch (choice) {
                case "1":
                    //Create client
                    c = createClient();
                    break;
                case "2":
                    //Create address
                    a = createAddress();
                    break;
                case "3":
                    //Create supplier
                    s = chooseSupplier();
                    break;
                case "4":
                    //Create supplier
                    divs.add(createDivision());
                    break;
                case "9":
                    flag = false;
                    break;
                case "0":
                    model.addHouse(new House(a, c, divs , s, 0));
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
    }

    public Suppliers chooseSupplier(){
        boolean flag = true;
        Suppliers s = new Suppliers();
        while(flag) {
            View.clear();
            String choice = view.menu_chooseSupplier();

            switch (choice) {
                case "1":
                    //Create Suplier
                    s = createSuppliers();
                    break;
                case "2":
                    // Choose existing suplier
                    try { 
                        int choice2 = view.pageSuppliers(this.model.getSimulator());
                        Suppliers suppliers[] = new Suppliers[this.model.getSimulator().getSuppliers().size()];
                        this.model.getSimulator().getSuppliers().toArray(suppliers);
                        s = suppliers[choice2].clone();

                    } catch(Empty_Simulation e){
                        View.showException(e);
                        view.print_s("Please create a new Supplier");
                        s = createSuppliers();
                    }
                    break;
                case "0":
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
        return s;
    }

    public void editHouseMenu(House house){
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.menu_Edit();
            switch (choice) {
                case "1":
                    //Edit client
                    editClientMenu(house.getOwner());
                    break;
                case "2":
                    //Edit address
                    editAddressMenu(house.getAddress());
                    break;
                case "3":
                    //Edit supplier
                    try {
                        editSuppliers(this.model.getSimulator());
                    } catch (Empty_Simulation e) {

                        e.printStackTrace();
                    }
                    break;
                case "4":
                    try {
                        editDivision(house);
                    } catch (Empty_House e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case "5":
                    try {
                        view.pageDivision(house);
                    } catch (Empty_House e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case "0":
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
            View.clear();
            String choice = view.menu_EditValues();
            switch(choice){
                case "1":
                    String supplier_name = view.ask_input_s("Enter the name of the supplier:");
                    supplier.setSupplier_name(supplier_name);
                    break;
                case "2":
                    float base_price = view.ask_input_f("Enter the base price");
                    supplier.setBase_price(base_price);
                    break;
                case "3":
                    int tax_int = view.ask_input_i("Enter the tax percentage:");
                    float tax = tax_int / 100;
                    supplier.setTax(tax);
                    break;
                case "4":
                    int out_of_range_tax_int = view.ask_input_i("Enter the out of range tax percentage:");
                    float out_of_range_tax = out_of_range_tax_int / 100;
                    supplier.setOut_of_range_tax(out_of_range_tax);
                    break;
                case "0":
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
            View.clear();
            String choice = view.menu_EditAddress();
            switch(choice){
                case "1":
                    String city = view.ask_input_s("Enter the name of the city:");
                    address.setCity(city);
                    break;
                case "2":
                    String street = view.ask_input_s("Enter the name of the street:");
                    address.setStreet(street);
                    break;
                case "3":
                    int street_number = view.ask_input_i("Enter the number of the street:");
                    address.setStreet_number(street_number);
                    break;
                case "4":
                    int l = view.ask_input_i("Enter the first four numbers of the post-code:");
                    int r = view.ask_input_i("Enter the last three numbers of the post-code:");
                    Pair<Integer,Integer> post_code = new Pair<>(l,r);
                    address.setPost_code(post_code);
                    break;
                case "0":
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
            View.clear();
            String choice = view.menu_EditClient();
            switch(choice){
                case "1":
                    String name = view.ask_input_s("Enter the name of the new Client:");
                    client.setClient_name(name);
                    break;
                case "2":
                    int nif = view.ask_input_i("Enter the NIF of the new Client:");
                    client.setClient_NIF(nif);
                    break;
                case "0":
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;                  
            }
        }
    }

    public void crSimMenu_2(){
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.menu_CS();
            switch (choice) {
                case "1":
                    view.menu_C();
                    break;
                case "2":
                    addTimeMenu_2();
                    break;
                case "3":
                    importSimulatMenu();
                    break;
                case "4":
                    exportSimulationMenu();
                    this.unsavedChanges = false;
                    break;
                case "0":
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;  
            }
        }
    }



//CREATE



    //Create client
    public Client createClient() {
        View.clear();
        String name = view.ask_input_s("Enter the name of the new Client:");
        int nif = view.ask_input_i("Enter the NIF of the new Client:");
        this.unsavedChanges = false;

        return new Client(name, nif);
    }

    // create Address
    public Address createAddress() {
        View.clear();
        String city = view.ask_input_s("Enter the name of the city:");
        String street = view.ask_input_s("Enter the name of the street:");
        int street_number = view.ask_input_i("Enter the number of the street:");
        int l = view.ask_input_i("Enter the first four numbers of the post-code:");
        int r = view.ask_input_i("Enter the last three numbers of the post-code:");
        Pair<Integer, Integer> post_code = new Pair<Integer, Integer>(l, r);
        this.unsavedChanges = false;

        return new Address(street, street_number, city, post_code);
    }

    // create Suppliers
    public Suppliers createSuppliers() {
        View.clear();
        String supplier_name = view.ask_input_s("Enter the name of the supplier:");
        float base_price = view.ask_input_f("Enter the base price");
        int tax_int = view.ask_input_i("Enter the tax percentage:");
        float tax = tax_int / 100;
        int out_of_range_tax_int = view.ask_input_i("Enter the out of range tax percentage:");
        float out_of_range_tax = out_of_range_tax_int / 100;
        this.unsavedChanges = false;

        return new Suppliers(supplier_name, base_price, tax, out_of_range_tax);
    }
    // create House
    public House createHouse(Client owner, Address address, Suppliers supplier) {
        this.unsavedChanges = false;
        return new House(address, owner, new HashSet<>(), supplier, 0);
    }

    // create Divisions
    public Divisions createDivision() {
        View.clear();
        String division_name = view.ask_input_s("Enter the name of the division:");
        Divisions division = new Divisions(division_name, new HashSet<>());
        this.unsavedChanges = false;
        return division;
    }
    // create SmartDevices
    public Divisions createDevice(Divisions division) {
        View.clear();
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
            View.unrecognizedCommandError();
                break;
        }
        this.unsavedChanges = false;
        return division;
    }



//EDIT



   // Edit Suppliers
   public void editSuppliers(Simulator simulator) throws Empty_Simulation {
    View.clear();
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
    this.unsavedChanges = false;

    }

    // edit Divisions
    public House editDivision(House house) throws Empty_House {
        View.clear();
        int choice = view.pageDivision(house);
        Divisions divisions[] = new Divisions[house.getDivisions().size()];
        this.model.getSimulator().getHouseFromAddress(house.getAddress()).getDivisions().toArray(divisions);
        View.clear();
        String choice2 = view.menu_EditDivision();
        switch(choice2) {
            case "1":
                String division_name = view.ask_input_s("Enter the new name for the division:");
                divisions[choice].setDivision_name(division_name);
                view.menu_EditDivision();
                break;
            case "2":
                int choice3 = view.pageDevices(divisions[choice]);
                SmartDevice devices[] = new SmartDevice[this.model.getSimulator().getHouseFromAddress(house.getAddress()).getDivisions().size()];
                this.model.getSimulator().getHouseFromAddress(house.getAddress()).getDivisions().toArray(divisions);
                
                editDevice(devices[choice3]);
                view.menu_EditDevice();   
                break;
            default:
                break;      
            }
        this.unsavedChanges = false;
        return house;
    }

    // Edit Device
    private void editDevice(SmartDevice device) {
        View.clear();
        String choice = view.menu_EditDevice();
        switch(choice){
            case "1":
                String device_name = view.ask_input_s("Enter the new name of the device:");
                device.setDevice_name(device_name);
                break;
            case "2":
                if(device.getIs_on()==true) {
                    System.out.println("The device is now off");
                    device.setIs_on(false);
                } else {
                    System.out.println("The device is now on");
                    device.setIs_on(true);
                }
                break;
            case "3":
                String brand = view.ask_input_s("Enter the new brand of the device:");
                device.setBrand(brand);
                break;
            case "4":
                double power_usage = view.ask_input_d("Enter the new power usage of the device:");
                device.setPower_usage(power_usage);
                break;
            case "5":
                double base_cost = view.ask_input_d("Enter the new base cost of the device:");
                device.setBase_cost(base_cost);
                break;
            case "0":
                break;
            default:
                View.unrecognizedCommandError();
                break;
        }
        this.unsavedChanges = false;

    }

    //EXPORT
    public void exportSimulationMenu() {
        View.clear();
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
        View.clear();
        String file = view.ask_input_s("Name of the file:");
        
        try {
            this.model = FileHandler.importModelFromFile(file);
        }
        catch (IOException | ClassNotFoundException e) {
            View.showException(e);
        }
    }
}
