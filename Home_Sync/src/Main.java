import java.util.HashSet;

import Auxiliar.*;
import Client.*;
import House.*;
import SmartDevice.*;
import Suppliers.*;

public class Main {

	public static void main(String[] args) {
		Suppliers supplier1 = new Suppliers("EDP", 2.4f, 0.13f, 0.0f);
		Address address1 = new Address("Rua Random", 2, "Cidade Random", new Pair<Integer,Integer>(420, 69));
		Client client1 = new Client("Miguel Gomes", 999999999);
		HashSet<Divisions> divisions1 = new HashSet<>();
		SmartDevice device1 = new SmartBulb();
		HashSet<SmartDevice> room = new HashSet<>();
		room.add(device1);
		Divisions division1 = new Divisions("Quarto", room);
		divisions1.add(division1);

		House house1 = new House(address1, client1, divisions1, supplier1, 10);
		
		System.out.println(house1.toString());
	}
}
