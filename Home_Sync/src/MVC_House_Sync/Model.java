package MVC_House_Sync;
import java.io.Serializable;

import Exceptions.*;
import House.*;
import Simulator.*;
import SmartDevice.*;
import Suppliers.*;

public class Model implements Serializable{
    private Simulator simulator;

    public Model(){
        this.simulator = new Simulator();
    }

    public Model(Model m){
        this.simulator = m.getSimulator();
    }

    public Simulator getSimulator(){
        return this.simulator;
    }
    public void setSimulator(Simulator s){
        this.simulator = s;
    }

    public void addHouse(House h) {
        this.simulator.addHouse(h);
    }


    public void turnDivision(House home, String division, boolean state) throws Division_Non_Existent, Empty_Division, Empty_House{
        try{
            home.change_division_state(division, state);
        }
        catch(Empty_House s1){
            throw s1;
        }
        catch(Division_Non_Existent s2){
            throw s2;
        }
        catch(Empty_Division s3){
            throw s3;
        }

    }//turn on/off entire division

    public void turnDevice(House home, String divis, SmartDevice device, boolean state) throws State_Not_Changed, Device_Non_Existent, Empty_Division, Empty_House{
        try{
            home.change_device_state(device,state);
        } 
        catch(State_Not_Changed s1){
            throw s1;
        }
        catch(Device_Non_Existent s2){
            throw s2;
        }
        catch(Empty_Division s3){
            throw s3;
        }
        catch(Empty_House s4){
            throw s4;
        }
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

    }//ponto 5 do enunciado; salvar o estado da aplica????o a qualquer momento

}