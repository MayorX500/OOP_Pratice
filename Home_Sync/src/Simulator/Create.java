package Simulator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;

import House.*;
import SmartDevice.SmartBulb;
import SmartDevice.SmartCamera;
import SmartDevice.SmartDevice;
import SmartDevice.SmartSpeaker;
import Exceptions.*;
import Client.*;
import Auxiliar.*;
import Suppliers.*;


public class Create {
    Scanner input = new Scanner(System.in);

    //create Client
    public Client createClient(){
        System.out.println("Enter the name of the new Client:");
        String name = input.nextLine();
        System.out.println("Enter the NIF of the new Client:");
        int nif = input.nextInt();

        return new Client(name, nif);
    }

    //create Address
    public Address createAddress(){
        System.out.println("Enter the name of the city:");
        String city = input.nextLine();
        System.out.println("Enter the name of the street:");
        String street = input.nextLine();
        System.out.println("Enter the number of the street:");
        int street_number = input.nextInt();
        System.out.println("Enter the first four numbers of the post-code:");
        int l = input.nextInt();
        System.out.println("Enter the last three numbers of the post-code:");
        int r = input.nextInt();
        Pair<Integer,Integer> post_code = new Pair<Integer, Integer> (l,r);

        return new Address(street, street_number, city, post_code);
    }

    //create Suppliers
    public Suppliers createSuppliers() {
        System.out.println("Enter the name of the supplier:");
        String supplier_name = input.nextLine();
        System.out.println("Enter the base price:");
        float base_price = input.nextFloat();
        System.out.println("Enter the tax percentage:");
        int tax_int = input.nextInt();
        float tax = tax_int/100;
        System.out.println("Enter the percentage of the out of range tax:");
        int out_of_range_tax_int = input.nextInt();
        float out_of_range_tax = out_of_range_tax_int/100;

        return new Suppliers(supplier_name, base_price, tax, out_of_range_tax);
    }

    //create House
    public House createHouse(Client owner, Address address, Suppliers supplier){
        return new House(address, owner, new HashSet<>(), supplier, 0);
    }

    //create Divisions
    public House createDivision(House house){
        System.out.println("Enter the name of the new division:");
        String division_name = input.nextLine();

        Divisions division = new Divisions(division_name,new HashSet<>());
        house.addDivision(division);

        return house;
    }

    //create SmartDevices 
    public Divisions createDevice(Divisions division) {
        System.out.println("Choose the type of smart device:\n\t1 - SmartBulb\n\t2 - SmartCamera\n\t3 - SmartSpeaker");
        int select = input.nextInt();
        System.out.println("Enter the name of the new device:");
        String device_name = input.nextLine();
        System.out.println("Enter the name of the device's brand:");
        String brand = input.nextLine();

        switch(select){
            case 1:
                System.out.println("Enter the dimension of the bulb:");
                float dimension = input.nextFloat();
                SmartDevice smartdevice1 = new SmartBulb(device_name, brand, dimension);
                division.addDevice(smartdevice1);
                break;

            case 2:
                System.out.println("Enter the resolution of the camera:");
                double resolution = input.nextDouble();
                System.out.println("Enter the size of the files on the camera:");
                double file_size = input.nextDouble();         
                SmartDevice smartdevice2 = new SmartCamera(device_name, brand, resolution, file_size);
                division.addDevice(smartdevice2);
                break;

            case 3:
                System.out.println("Enter the volume of the speaker:");
                int volume = input.nextInt();
                System.out.println("Enter the online radio on:");
                String radio_info = input.nextLine();         
                SmartDevice smartdevice3 = new SmartSpeaker(device_name, brand, volume, radio_info);
                division.addDevice(smartdevice3);
                break;
            
            default:
                System.out.println("Error");
                break;
        }
        return division;
    }

}
