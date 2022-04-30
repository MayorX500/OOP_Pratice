package MVC_House_Sync;

import java.time.LocalDate;
import java.util.HashSet;

import Auxiliar.Pair;
import Client.Client;
import House.Address;
import House.Divisions;
import House.House;
import SmartDevice.SmartBulb;
import SmartDevice.SmartCamera;
import SmartDevice.SmartDevice;
import SmartDevice.SmartSpeaker;
import Suppliers.Suppliers;

public class Controler {
    private Model model;
    private View view;

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

    // create House
    public House createHouse(Client owner, Address address, Suppliers supplier) {
        return new House(address, owner, new HashSet<>(), supplier, 0);
    }

    // create Divisions
    public House createDivision(House house) {
        String division_name = view.ask_input_s("Enter the name of the division:");
        Divisions division = new Divisions(division_name, new HashSet<>());
        createDevice(division);
        house.addDivision(division);
        return house;
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
                division.addDevice(smartdevice1);
                break;

            case 2:
                double resolution = view.ask_input_d("Enter the resolution of the camera:");
                double file_size = view.ask_input_d("Enter the size of the files on the camera:");
                SmartDevice smartdevice2 = new SmartCamera(device_name, brand, resolution, file_size);
                division.addDevice(smartdevice2);
                break;

            case 3:
                int volume = view.ask_input_i("Enter the volume of the speaker:");
                String radio_info = view.ask_input_s("Enter the online radio on:");
                SmartDevice smartdevice3 = new SmartSpeaker(device_name, brand, volume, radio_info);
                division.addDevice(smartdevice3);
                break;

            default:
                System.out.println("Error");
                break;
        }
        return division;
    }

    public void createSimulation() {
        boolean exit = false;
        int choice = view.menu_C();
        while (!exit) {
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
                    exit = true;
                    break;
            }
        }

    }

    public void firstMenu() {
        int choice = view.welcomeMenu();
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
                edSimMenu_2();
                break;
            case 0:
                System.out.println("Thank you for choosing Smart House Simulator. We hope to see you again soon.");
                break;
        }

    }

    public void addTimeMenu_2() {
        int choice = view.addTimeMenu();
        switch (choice) {
            case 1:
                this.model.getSimulator().setSimulation_date(this.model.getSimulator().getSimulation_date().plusDays(1));
                break;
            case 2:
                LocalDate date = LocalDate.of(view.menu_SD().getYear(), view.menu_SD().getMonth(), view.menu_SD().getDayOfMonth());
                this.model.getSimulator().setSimulation_date(date);
                break;
            case 0:
                firstMenu();
                break;
        }
        addTimeMenu_2();
    }

    public void addHouseMenu_2() {
        int choice = view.houseMenu();
        switch (choice) {
            case 1:
                view.menu_C();
                break;
            case 2:
                Address address = createAddress();
                this.model.getSimulator().eliminateHouses(this.model.getSimulator().getHouses(), address);
                view.houseMenu();
                break;
            case 3:
                view.menu_Edit();
                break;
            case 0:
                firstMenu();
                break;
        }
    }

    public void crSimMenu_2() {
        int choice = view.menu_CS();
        switch (choice) {
            case 1:
                view.menu_C();
                break;
            case 2:
                // impSimMenu_3(); import simulation file
                break;
            case 0:
                firstMenu();
                break;
        }
    }

    public void edSimMenu_2() {
        int choice = view.menu_SV();
        switch (choice) {
            case 1:
            //1 - Alter energy provider parameters

                break;
            case 2:
            //2 - Present billing statistics
                int choice2 = view.pageHouses(this.model.getSimulator());
                House houses[] = new House[this.model.getSimulator().getHouses().size()];
                this.model.getSimulator().getHouses().toArray(houses);
                Address address = houses[choice2].getAddress();
                view.invoice(this.model.getSimulator().getInvoiceFromAddress(address), houses[choice2]);
                break;
            case 0:
                firstMenu();
                break;
        }
    }

}
