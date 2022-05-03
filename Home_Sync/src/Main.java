import Exceptions.Empty_Simulation;
import MVC_House_Sync.Controler;
import MVC_House_Sync.View;

public class Main {

	public static void main(String[] args) {	
		Controler c = new Controler();
		try{c.firstMenu();}
		catch(Empty_Simulation e){
			View.showException(e);
		}
	}

}
