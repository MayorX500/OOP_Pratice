package MVC_House_Sync;

import java.io.File;
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
import Simulator.*;
import SmartDevice.*;
import Suppliers.*;

public class Controler {
    File tempFile = new File("../../Resources/logs.txt");
    boolean exists = tempFile.exists();
    private Model model;
    private View view = new View();
    private boolean unsavedChanges; 

    public Controler(){
        Model m;
        
        try {
            if(exists){
                m = Parser.parse("../../Resources/logs.txt");
            }
            else m = Parser.parse("Resources/logs.txt");
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

    public void first_menu(){
        boolean flag = true;
        while(flag){
            View.clear();
            String opt = view.first_menu();
            switch (opt) {
                case "1":
                    simulation_menu();
                    break;
                case "2":
                    manage_simulation();
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
                    flag = false;
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }

        }
    }





    private void simulation_menu() {
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.simulation_menu();
            switch (choice) {
                case "1":
                    create_simulation();
                    break;
                case "2":
                    increase_time();
                    break;
                case "3":
                    import_sim();
                    break;
                case "4":
                    export_sim();
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

    private void manage_simulation() {
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.manage_simulation();
            switch (choice) {
                case "1":
                    create_house_menu();
                    break;
                case "2":
                    select_house_menu();
                    break;
                case "3":
                    select_supplier_menu();
                    break;
                case "4":
                    mass_on_off_menu();
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

    private void billing_menu(){
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.billing_menu();
            switch (choice) {
                case "1":
                    specific_billing_menu();
                    break;
                case "2":
                    general_billing_menu();
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

    private void view_menu(){
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.view_menu();
            switch (choice) {
                case "1":
                    view_houses_menu();
                    break;
                case "2":
                    view_suppliers_menu();
                    break;
                case "3":
                    view_invoices_menu();
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

    private void loadingMenu_controler() {
        boolean flag = true;
        while(flag) {
            View.clear();
            String saves = view.ask_input_s("There are some unsaved changes, Do you wish to save them (Yes/No):");
            switch (saves){
                case "Yes","Y","yes","y" :
                    export_sim();
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
    }

    private void specific_billing_menu() {
        House h = null;
        View.clear();
        Invoice bill = new Invoice();
        boolean sucs = false;
        if(this.model.getSimulator().getHouses().size()>0){
            try{
            h = view.pageHouses(this.model.getSimulator().getHouses());
            } catch (Empty_Simulation e1) {
                View.showException(e1);
                View.clear();
            
                view.print_s("Empty Simulation. Please create a house.");
                Wait.wait(5000);
            }
        
            try{
                bill = Invoice.generateInvoice(h, this.model.getSimulator().getStarting_simulation_date(), this.model.getSimulator().getSimulation_date());
                this.model.getSimulator().addInvoice(bill);
                sucs = true;
            }catch(Empty_House e){
                sucs = false;
            }
        }
        if(!sucs) view.print_s("This simulation is empty, please add some houses in the previous menu.");
        else view.invoice(bill);
        view.ask_input_s("Press any key to continue.");
    }

    private void create_simulation(){
        boolean flag = true;
        while (flag) {
            View.clear();
            String choice = view.create_simulation();

            switch (choice) {
                case "1":
                    create_house_menu();
                    break;
                case "2":
                    create_supplier_menu();
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

    private void increase_time(){
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.addTimeMenu();
            switch (choice) {
                case "1":
                    this.model.getSimulator().setSimulation_date(this.model.getSimulator().getSimulation_date().plusHours(1));
                    try{
                        this.model.getSimulator().advanceOneHour();
                    }
                    catch(Empty_Simulation empty_sim){
                        View.showException(empty_sim);
                    }
                    catch(Empty_House empty_hous){
                        View.showException(empty_hous);
                    }
                    catch(Empty_Division empty_div){
                        View.showException(empty_div);
                    }
                    Wait.wait(5000);
                    break;
                case "2":
                    this.model.getSimulator().setSimulation_date(this.model.getSimulator().getSimulation_date().plusDays(1));
                    try{
                        for(int i = 0; i<24;i++)
                        this.model.getSimulator().advanceOneHour();
                    }
                    catch(Empty_Simulation empty_sim){
                        View.showException(empty_sim);
                    }
                    catch(Empty_House empty_hous){
                        View.showException(empty_hous);
                    }
                    catch(Empty_Division empty_div){
                        View.showException(empty_div);
                    }
                    Wait.wait(5000);

                    break;
                case "3":
                    LocalDateTime ask = view.menu_SD();
                    long diff = ChronoUnit.HOURS.between(this.model.getSimulator().getSimulation_date(), ask);
                    try{
                        for(int i = 0; i<diff;i++)
                        this.model.getSimulator().advanceOneHour();
                    }
                    catch(Empty_Simulation empty_sim){
                        View.showException(empty_sim);
                    }
                    catch(Empty_House empty_hous){
                        View.showException(empty_hous);
                    }
                    catch(Empty_Division empty_div){
                        View.showException(empty_div);
                    }
                    Wait.wait(5000);
                    this.model.getSimulator().setSimulation_date(ask);
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

    //EXPORT
    private void export_sim() {
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
    private void import_sim() {
        View.clear();
        String file = view.ask_input_s("Name of the file:");
        
        try {
            this.model = FileHandler.importModelFromFile(file);
        }
        catch (IOException | ClassNotFoundException e) {
            View.showException(e);
        }
    }

    private void create_house_menu(){
        
        House h = null;
        Address address = null;
        Client owner = null;
        Suppliers supplier = null;
        Set<Divisions> divisions = new HashSet<>();

        boolean flag = true;
        while(flag){
            if(address == null){
                address = address_menu();
            }
            if(owner == null){
                owner = client_menu();
            }
            if(supplier == null){
                supplier = supplier_menu();
            }
            if(address!=null && owner!=null && supplier!= null){
                flag = false;
            }
        }
        flag = true;
        while (flag) {
            View.clear();
            String choice = view.create_house_menu();
            switch (choice) {
                case "1":
                    int ndivs = view.ask_input_i("How many divisions do you wish to add?");
                    while(ndivs>0){
                        divisions.add(create_divison());
                    }                    
                    break;
                case "9":
                    h = new House(address, owner, divisions, supplier, 0);
                    this.model.addHouse(h);
                    this.unsavedChanges = true;
                    flag = false;
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

    private void select_house_menu(){
        boolean flag = true;
        while (flag) {
            View.clear();
            String choice = view.select_house_menu();

            switch (choice) {
                case "1":
                    edit_house_data();
                    break;
                case "2":
                    edit_house_divisions();
                    break;
                case "3":
                    edit_house_supplier();
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

    private void select_supplier_menu(){
        boolean flag = true;
        Suppliers s = null;
        Suppliers temp = null;
        
        try {
            s = view.pageSuppliers(this.model.getSimulator().getSuppliers());
        } catch (Empty_Simulation e) {
            flag = false;
        }
        if(s != null){
            temp = s.clone();
        }

        while(flag){
            View.clear();
            String choice = view.menu_EditValues();
            switch(choice){
                case "1":
                    String supplier_name = view.ask_input_s("Enter the name of the supplier:");
                    temp.setSupplier_name(supplier_name);
                    break;
                case "2":
                    float base_price = view.ask_input_f("Enter the base price");
                    temp.setBase_price(base_price);
                    break;
                case "3":
                    int tax_int = view.ask_input_i("Enter the tax percentage:");
                    float tax = tax_int / 100;
                    temp.setTax(tax);
                    break;
                case "4":
                    int out_of_range_tax_int = view.ask_input_i("Enter the out of range tax percentage:");
                    float out_of_range_tax = out_of_range_tax_int / 100;
                    temp.setOut_of_range_tax(out_of_range_tax);
                    break;
                case "9":
                    s.setSupplier(temp);
                    flag = false;
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

    private void mass_on_off_menu(){
        House h = null;
        boolean flag = true;
        try {
            h = view.pageHouses(model.getSimulator().getHouses());
        } catch (Empty_Simulation e) {
            View.showException(e);
            view.print_s("There are no houses available in the simulator, please create some.");
        flag = false;
        }
        while(flag) {
            View.clear();
            String choice = view.menu_EH();
            switch (choice) {
                case "1":
                    try {
                        Divisions division = view.pageDivision(h.getDivisions(), h.getHouse_id());
                        boolean state = view.ask_input_on_off("On/Off?");
                        this.model.turnDivision(h, division.getDivision_name(), state);
                    } catch (Empty_House e) {
                        View.showException(e);
                        view.print_s("The house does not have any divisions");
                    } catch (Empty_Division e2) {
                        View.showException(e2);
                    } catch (Division_Non_Existent e3) {
                        View.showException(e3);
                    }
                    break;
                case "2":                
                    try {
                        Divisions division = view.pageDivision(h.getDivisions(), h.getHouse_id());
                        boolean state = view.ask_input_on_off("On/Off?");
                        SmartDevice device = view.pageDevices(division.getDevices());
                        this.model.turnDevice(h, division.getDivision_name(), device, state);
                    } catch (Empty_House e) {
                        View.showException(e);
                        
                    }  catch (State_Not_Changed e1) {
                        View.showException(e1);
                    } catch (Empty_Division e2) {
                        View.showException(e2);
                    } catch (Device_Non_Existent e3) {
                        View.showException(e3);
                    }                                  
                    break;
                case "3":
                    if(h.getDivisions().size()>0){
                        boolean state = view.ask_input_on_off("On/Off?");
                        for(Divisions division : h.getDivisions()){
                            try {
                                this.model.turnDivision(h, division.getDivision_name(), state);
                            } catch (Empty_House e) {
                                View.showException(e);
                                view.print_s("The house does not have any divisions");
                            } catch (Empty_Division e2) {
                                View.showException(e2);
                            } catch (Division_Non_Existent e3) {
                                View.showException(e3);
                            }
                        }
                    }
                    else view.print_s("The house does not have any divisions");
                    flag = false;
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

    private void general_billing_menu(){
        boolean b = view.ask_input_y_n("Do you wish to generate invoices for every house? (yes/no)");
        if(b){
            try {
                this.model.getSimulator().generate_invoices(this.model.getSimulator().getStarting_simulation_date(), this.model.getSimulator().getSimulation_date());
            } catch (Empty_Simulation e) {
                this.model.getSimulator().setInvoices(new HashSet<>());
                
            }
        }

    }

    private void view_houses_menu(){

    }

    private void view_suppliers_menu(){

    }

    private void view_invoices_menu(){

    }

    private void edit_house_data(){

    }

    private void edit_house_divisions(){

    }
    
    private void edit_house_supplier(){

    }

    private Address address_menu(){
        Address address = null;
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.ask_create_or_exitent();
            switch (choice) {
                case "1":
                    address = create_address_menu();
                    break;
                case "2":
                    address = view.pageAddress(this.model.getSimulator().getHouses());
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
        return address;
    }

    private Client client_menu(){
        Client client = null;
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.ask_create_or_exitent();
            switch (choice) {
                case "1":
                    client = create_client_menu();
                    break;
                case "2":
                    client = view.pageClients(this.model.getSimulator().getHouses());
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
        return client;
    }

    private Suppliers supplier_menu(){
        Suppliers supplier = null;
        boolean flag = true;
        while(flag) {
            View.clear();
            String choice = view.ask_create_or_exitent();
            switch (choice) {
                case "1":
                    supplier = create_supplier_menu();
                    break;
                case "2":
                    supplier = view.pageSuppliers(this.model.getSimulator().getSuppliers());
                    break;
                default:
                    View.unrecognizedCommandError();
                    break;
            }
        }
        return supplier;
    }

    private Divisions create_divison(){
        View.clear();
        String division_name = view.ask_input_s("Enter the name of the division:");
        Divisions division = new Divisions(division_name, new HashSet<>());
        boolean flag = false;
        View.clear();
        String ask = view.ask_input_s("Do you wish to add a device to "+ name);
        switch (ask) {
            case "Yes","Y","yes","y":
                flag = true;
                break;
            default:
                break;
        while(flag){}
            SmartDevice dev = create_device();
            if(dev==null)
                flag = false;
            else division.addDevice(dev);
        }
        this.unsavedChanges = false;
        return division;
    }

    private Suppliers create_supplier_menu(){
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

    private Address create_address_menu(){
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

    private Client create_client_menu(){
        View.clear();
        String name = view.ask_input_s("Enter the name of the new Client:");
        int nif = view.ask_input_i("Enter the NIF of the new Client:");
        this.unsavedChanges = false;

        return new Client(name, nif);
    }

    private SmartDevice create_device(){
        View.clear();
        SmartDevice dev = null;
        String select = view.ask_input_s(
                "Choose the type of smart device:\n\t1 - SmartBulb\n\t2 - SmartCamera\n\t3 - SmartSpeaker\n\t4 - Cancel");
        String device_name = view.ask_input_s("Enter the name of the new device:");
        String brand = view.ask_input_s("Enter the name of the device's brand:");

        switch (select) {
            case "1":
                float dimension = view.ask_input_f("Enter the dimension of the bulb:");
                dev = new SmartBulb(device_name + " - SmartBulb", brand, dimension);
                break;
            case "2":
                String resolution = view.ask_input_s("Enter the resolution of the camera in the following format (1234x1234):");
                String resol = resolution.replaceAll("\\(", "").replaceAll("\\)","");
                String[] real = resol.split("x");
                double file_size = view.ask_input_d("Enter the size of the files on the camera:");
                dev = new SmartCamera(device_name + " - SmartCamera", brand, new Pair<Integer,Integer>(Integer.parseInt(real[0]),Integer.parseInt(real[1])), file_size);
                break;
            case "3":
                int volume = view.ask_input_i("Enter the volume of the speaker:");
                String radio_info = view.ask_input_s("Enter the online radio on:");
                dev = new SmartSpeaker(device_name + " - SmartSpeaker", brand, volume, 15f, radio_info);
                break;
            default:
            View.unrecognizedCommandError();
                break;
        }
        return dev;
    }


}
