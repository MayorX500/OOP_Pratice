import Exceptions.Empty_House;
import Exceptions.Empty_Simulation;
import MVC_House_Sync.Controler;
import MVC_House_Sync.View;

public class Main {

	public static void main(String[] args) throws Empty_House {	
		Controler c = new Controler();
		try{c.firstMenu();}
		catch(Empty_Simulation e){
			View.showException(e);
		}
	}

}
