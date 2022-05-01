import java.util.Locale;

import com.github.javafaker.Faker;

import SmartDevice.SmartBulb;

public class Main {

	public static void main(String[] args) {	
		Faker faker = new Faker(new Locale("pt"));
		System.out.println(faker.address().fullAddress());

		SmartBulb device1 = new SmartBulb();
		System.out.println(device1.toString());

		}

}
