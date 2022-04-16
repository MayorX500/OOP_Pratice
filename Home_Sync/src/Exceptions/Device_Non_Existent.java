package Exceptions;

public class Device_Non_Existent extends Exception {

    public Device_Non_Existent(String s){
        super(s);
    }
    
    public Device_Non_Existent(int s){
        super(""+ s + "");
    }
}
