package Auxiliar;
import House.*;
import SmartDevice.*;
import Suppliers.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuSpecific {

    public void newDate(){}//avançar tempo

    public void turnDivision(House home,String divis,boolean state){

        home.divisionOnOff(divis,state);

    }//turn on/off entire division

    public void turnDevice(House home, String divis, SmartDevice device,boolean state){
        home.turnOneOnOFF(divis,device,state);
    }//turn on/off specific device

    public void swapProvider(House home, Suppliers f){
        home.setSupplier(f);
    }//swap energy provider

    public void alterProviderbase_price(Suppliers f,float value){
        f.setBase_price(value);
    }//alter values of energy provider

    public void alterProvidertax(Suppliers f,float value){
        f.setTax(value);
    }//alter values of energy provider

    public void alterProviderout_of_range_tax(Suppliers f,float value){
        f.setOut_of_range_tax(value);
    }//alter values of energy provider

    public void statistics(){

    }//calculate and show statistics

    public void saveFile(){

    }//ponto 5 do enunciado; salvar o estado da aplicação a qualquer momento

}
