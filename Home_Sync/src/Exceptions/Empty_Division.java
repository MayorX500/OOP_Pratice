package Exceptions;

public class Empty_Division extends Exception {

    public Empty_Division(String s){
        super(s);
        System.out.println("This division is empty");
    }
    
}
