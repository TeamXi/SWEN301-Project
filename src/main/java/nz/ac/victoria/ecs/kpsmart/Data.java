package nz.ac.victoria.ecs.kpsmart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import nz.ac.victoria.ecs.kpsmart.entities.logging.CarrierUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.CustomerPriceUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.DomesticCustomerPriceUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.LocationUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.MailDeliveryUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.RouteUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.entities.state.Direction;
import nz.ac.victoria.ecs.kpsmart.entities.state.DomesticCustomerPrice;
import nz.ac.victoria.ecs.kpsmart.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.entities.state.TransportMeans;
import nz.ac.victoria.ecs.kpsmart.integration.EntityManager;
import nz.ac.victoria.ecs.kpsmart.routefinder.RouteFinder;

import com.google.inject.Inject;

public final class Data {
	@Inject 
	private EntityManager sm;
	
	@Inject
	private RouteFinder finder;

	@InjectOnCall
	public void createData() {
		/*
		 * 
		 * Domestic customer prices
		 * 
		 */
		sm.performEvent(new DomesticCustomerPriceUpdateEvent(new DomesticCustomerPrice(0.01f, 0.02f, Priority.Domestic_Standard)));
		sm.performEvent(new DomesticCustomerPriceUpdateEvent(new DomesticCustomerPrice(0.02f, 0.04f, Priority.Domestic_Air)));
		
		/*
		 * 
		 * Carriers
		 * 
		 */
		sm.performEvent(new CarrierUpdateEvent(new Carrier("Air New Zealand"))); // 1
		sm.performEvent(new CarrierUpdateEvent(new Carrier("New Zealand Post"))); // 2
		sm.performEvent(new CarrierUpdateEvent(new Carrier("Singapore Air"))); // 3
		sm.performEvent(new CarrierUpdateEvent(new Carrier("Trans World Airlines"))); // 4
		sm.performEvent(new CarrierUpdateEvent(new Carrier("IcelandAir"))); // 5
		sm.performEvent(new CarrierUpdateEvent(new Carrier("Emirates"))); // 6
		sm.performEvent(new CarrierUpdateEvent(new Carrier("FedEx"))); // 7
		sm.performEvent(new CarrierUpdateEvent(new Carrier("Aeroflot"))); // 8
		
		/*
		 * 
		 * Locations
		 * 
		 */
		sm.performEvent(new LocationUpdateEvent(new Location("Rome", 41.9, 12.5, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Wellington", -41.288889, 174.777222, false)));
		sm.performEvent(new LocationUpdateEvent(new Location("Auckland", -36.840417, 174.739869, false)));
		sm.performEvent(new LocationUpdateEvent(new Location("Sofia", 42.7, 23.333333, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Amsterdam", 52.373056, 4.892222, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Christchurch", -43.53, 172.620278, false)));
		sm.performEvent(new LocationUpdateEvent(new Location("Sydney", -33.859972, 151.211111, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Singapore", 1.3, 103.8, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Los Angeles", 34.05, -118.25, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("New York", 40.664167, -73.938611, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("London", 51.507222, -0.1275, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Reykjavik", 64.133333, -21.933333, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Stockholm", 59.329444, 18.068611, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Moscow", 55.75, 37.616667, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Shanghai", 31.2, 121.5, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("New Delhi", 28.613889, 77.208889, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Cape Town", -33.925278, 18.423889, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Tel Aviv", 32.066667, 34.783333, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Cairo", 30.058056, 31.228889, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Athens", 37.966667, 23.716667, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Beirut", 33.886944, 35.513056, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Hamilton", -37.783333, 175.283333, false)));
		sm.performEvent(new LocationUpdateEvent(new Location("Rotorua", -38.137778, 176.251389, false)));
		sm.performEvent(new LocationUpdateEvent(new Location("Palmerston North", -40.355, 175.611667, false)));
		sm.performEvent(new LocationUpdateEvent(new Location("Dunedin", -45.866667, 170.5, false)));
		/*
		 * 
		 * Routes
		 * 
		 */
		this.createRoute(TransportMeans.Land, "Auckland", "Wellington", 2, (float)20, (float)20, 8, 6);
		this.createRoute(TransportMeans.Land, "Wellington", "Auckland", 2, (float)20, (float)20, 8, 6);
		this.createRoute(TransportMeans.Sea, "Wellington", "Christchurch", 2, (float)5, (float)5, 12, 12);
		this.createRoute(TransportMeans.Sea, "Christchurch", "Wellington", 2, (float)5, (float)5, 12, 12);
		this.createRoute(TransportMeans.Air, "Auckland", "Christchurch", 1, (float)40, (float)40, 2, 1);
		this.createRoute(TransportMeans.Air, "Christchurch", "Auckland", 1, (float)40, (float)40, 2, 1);
		this.createRoute(TransportMeans.Land, "Christchurch", "Dunedin", 2, (float)15, (float)15, 2, 1);
		this.createRoute(TransportMeans.Land, "Dunedin", "Christchurch", 2, (float)15, (float)15, 2, 1);
		this.createRoute(TransportMeans.Land, "Palmerston North", "Wellington", 2, (float)15, (float)15, 2, 1);
		this.createRoute(TransportMeans.Land, "Wellington", "Palmerston North", 2, (float)15, (float)15, 2, 1);
		this.createRoute(TransportMeans.Land, "Rotorua", "Palmerston North", 2, (float)10, (float)10, 2, 1);
		this.createRoute(TransportMeans.Land, "Palmerston North", "Rotorua", 2, (float)10, (float)10, 2, 1);
		this.createRoute(TransportMeans.Land, "Hamilton", "Rotorua", 2, (float)10, (float)10, 2, 1);
		this.createRoute(TransportMeans.Land, "Rotorua", "Hamilton", 2, (float)10, (float)10, 2, 1);
		this.createRoute(TransportMeans.Land, "Auckland", "Hamilton", 2, (float)10, (float)10, 2, 1);
		this.createRoute(TransportMeans.Land, "Hamilton", "Auckland", 2, (float)10, (float)10, 2, 1);
		this.createRoute(TransportMeans.Air, "Wellington", "Hamilton", 2, (float)20, (float)20, 8, 6);
		this.createRoute(TransportMeans.Air, "Hamilton", "Wellington", 2, (float)20, (float)20, 8, 6);
		this.createRoute(TransportMeans.Air, "Wellington", "Dunedin", 2, (float)40, (float)40, 8, 6);
		this.createRoute(TransportMeans.Air, "Dunedin", "Wellington", 2, (float)40, (float)40, 8, 6);
		this.createRoute(TransportMeans.Land, "Hamilton", "Palmerston North", 2, (float)10, (float)10, 8, 6);
		this.createRoute(TransportMeans.Land, "Palmerston North", "Hamilton", 2, (float)10, (float)10, 8, 6);
		
		this.createRoute(TransportMeans.Air, "Wellington", "Sydney", 1, (float)30, (float)30, 3, 12);
		this.createRoute(TransportMeans.Sea, "Athens", "Beirut", 7, (float)0.75, (float)0.75, 30, 170);
		this.createRoute(TransportMeans.Land, "Beirut", "Tel Aviv", 7, (float)2.5, (float)2.5, 10, 24);
		this.createRoute(TransportMeans.Land, "Tel Aviv", "Cairo", 7, (float)2.5, (float)2.5, 10, 24);
		this.createRoute(TransportMeans.Sea, "Athens", "Tel Aviv", 7, (float)0.5, (float)0.5, 20, 48);
		this.createRoute(TransportMeans.Land, "Athens", "Sofia", 7, (float)1.5, (float)1.5, 6, 24);
		this.createRoute(TransportMeans.Air, "Rome", "Athens", 6, (float)40, (float)40, 4, 16);
		this.createRoute(TransportMeans.Air, "London", "Rome", 6, (float)70, (float)70, 7, 12);
		this.createRoute(TransportMeans.Air, "London", "Reykjavik", 5, (float)70, (float)70, 7, 72);
		this.createRoute(TransportMeans.Air, "Sydney", "Singapore", 3, (float)70, (float)70, 7, 12);
		this.createRoute(TransportMeans.Air, "Sydney", "Los Angeles", 4, (float)160, (float)160, 16, 24);
		this.createRoute(TransportMeans.Air, "Auckland", "Singapore", 1, (float)90, (float)90, 9, 24);
		this.createRoute(TransportMeans.Air, "Singapore", "Shanghai", 3, (float)50, (float)50, 5, 24);
		this.createRoute(TransportMeans.Air, "Singapore", "New Delhi", 3, (float)60, (float)60, 6, 24);
		this.createRoute(TransportMeans.Air, "Singapore", "Moscow", 8, (float)160, (float)160, 16, 72);
		this.createRoute(TransportMeans.Air, "Los Angeles", "New York", 4, (float)80, (float)80, 8, 24);
		this.createRoute(TransportMeans.Air, "New York", "Los Angeles", 4, (float)80, (float)80, 8, 24);
		this.createRoute(TransportMeans.Air, "New York", "London", 4, (float)60, (float)60, 6, 24);
		this.createRoute(TransportMeans.Air, "New Delhi", "Cairo", 6, (float)80, (float)80, 8, 48);
		this.createRoute(TransportMeans.Sea, "Cairo", "Cape Town", 7, (float)4.3, (float)4.3, 172, 200);
		this.createRoute(TransportMeans.Air, "Singapore", "London", 3, (float)160, (float)160, 16, 48);
		this.createRoute(TransportMeans.Air, "London", "Stockholm", 5, (float)60, (float)60, 6, 48);
		
		this.createCustomerPrice("Rome", Priority.International_Air, (float)1.0, (float)1.0);
		this.createCustomerPrice("Rome", Priority.International_Standard, (float)0.75, (float)0.75);
		
		this.createCustomerPrice("Sofia", Priority.International_Air, (float)1.0, (float)1.0);
		this.createCustomerPrice("Sofia", Priority.International_Standard, (float)0.8, (float)0.8);
		
		this.createCustomerPrice("Amsterdam", Priority.International_Air, (float)0.9, (float)0.9);
		this.createCustomerPrice("Amsterdam", Priority.International_Standard, (float)0.7, (float)0.7);
		
		this.createCustomerPrice("Sydney", Priority.International_Air, (float)0.4, (float)0.4);
		this.createCustomerPrice("Sydney", Priority.International_Standard, (float)0.2, (float)0.2);
		
		this.createCustomerPrice("Singapore", Priority.International_Air, (float)0.5, (float)0.5);
		this.createCustomerPrice("Singapore", Priority.International_Standard, (float)0.45, (float)0.45);
		
		this.createCustomerPrice("Los Angeles", Priority.International_Air, (float)0.25, (float)0.25);
		this.createCustomerPrice("Los Angeles", Priority.International_Standard, (float)0.225, (float)0.225);
		
		this.createCustomerPrice("New York", Priority.International_Air, (float)0.55, (float)0.55);
		this.createCustomerPrice("New York", Priority.International_Standard, (float)0.5, (float)0.5);
		
		this.createCustomerPrice("London", Priority.International_Air, (float)0.9, (float)0.9);
		this.createCustomerPrice("London", Priority.International_Standard, (float)0.7, (float)0.7);
		
		this.createCustomerPrice("Reykjavik", Priority.International_Air, (float)0.1, (float)0.1);
		this.createCustomerPrice("Reykjavik", Priority.International_Standard, (float)0.08, (float)0.08);
		
		this.createCustomerPrice("Stockholm", Priority.International_Air, (float)0.95, (float)0.95);
		this.createCustomerPrice("Stockholm", Priority.International_Standard, (float)0.75, (float)0.75);
		
		this.createCustomerPrice("Moscow", Priority.International_Air, (float)0.7, (float)0.7);
		this.createCustomerPrice("Moscow", Priority.International_Standard, (float)0.5, (float)0.5);
		
		this.createCustomerPrice("Shanghai", Priority.International_Air, (float)0.9, (float)0.9);
		this.createCustomerPrice("Shanghai", Priority.International_Standard, (float)0.7, (float)0.7);
		
		this.createCustomerPrice("New Delhi", Priority.International_Air, (float)0.7, (float)0.7);
		this.createCustomerPrice("New Delhi", Priority.International_Standard, (float)0.5, (float)0.5);
		
		this.createCustomerPrice("Cape Town", Priority.International_Air, (float)0.9, (float)0.9);
		this.createCustomerPrice("Cape Town", Priority.International_Standard, (float)0.7, (float)0.7);
		
		this.createCustomerPrice("Tel Aviv", Priority.International_Air, (float)1.0, (float)1.0);
		this.createCustomerPrice("Tel Aviv", Priority.International_Standard, (float)0.8, (float)0.8);
		
		this.createCustomerPrice("Cairo", Priority.International_Air, (float)0.9, (float)0.9);
		this.createCustomerPrice("Cairo", Priority.International_Standard, (float)0.7, (float)0.7);
		
		this.createCustomerPrice("Athens", Priority.International_Air, (float)0.9, (float)0.9);
		this.createCustomerPrice("Athens", Priority.International_Standard, (float)0.7, (float)0.7);
		
		this.createCustomerPrice("Beirut", Priority.International_Air, (float)1.0, (float)1.0);
		this.createCustomerPrice("Beirut", Priority.International_Standard, (float)0.9, (float)0.9);
		
		this.setDomesticCustomerPrice(Priority.Domestic_Air,(float) 0.05, (float) 0.05);
		this.setDomesticCustomerPrice(Priority.Domestic_Standard, (float) 0.025, (float) 0.025); 
		
		Random value = new Random(1469654);
		
		for (int i=0; i<5; i++) {
			for (int j = 0; j < 20; j++)
				if (value.nextBoolean())
					this.createMailDelivery("Wellington", "Auckland", Priority.Domestic_Standard, value.nextFloat() * 100, value.nextFloat() * 1000);
			for (int j = 0; j < 20; j++)
				if (value.nextBoolean())
					this.createMailDelivery("Wellington", "Christchurch", Priority.Domestic_Standard, value.nextFloat() * 100, value.nextFloat() * 1000);
			for (int j = 0; j < 20; j++)
				if (value.nextBoolean())
					this.createMailDelivery("Christchurch", "Auckland", Priority.Domestic_Air, value.nextFloat() * 100, value.nextFloat() * 1000);
			if (value.nextBoolean())
				this.createMailDelivery("Wellington", "Sydney", Priority.International_Air, value.nextFloat() * 100, value.nextFloat() * 1000);
			if (value.nextBoolean())
				this.createMailDelivery("Wellington", "London", Priority.International_Air, value.nextFloat() * 100, value.nextFloat() * 1000);
			if (value.nextBoolean())
				this.createMailDelivery("Auckland", "London", Priority.International_Air, value.nextFloat() * 100, value.nextFloat() * 1000);
			if (value.nextBoolean())
				this.createMailDelivery("Auckland", "Los Angeles", Priority.International_Air, value.nextFloat() * 100, value.nextFloat() * 1000);
			if (value.nextBoolean())
				this.createMailDelivery("Wellington", "New York", Priority.International_Air, value.nextFloat() * 100, value.nextFloat() * 1000);
			if (value.nextBoolean())
				this.createMailDelivery("Christchurch", "Rome", Priority.International_Air, value.nextFloat() * 100, value.nextFloat() * 1000);
			if (value.nextBoolean())
				this.createMailDelivery("Wellington", "Reykjavik", Priority.International_Air, value.nextFloat() * 100, value.nextFloat() * 1000);
		}
	}
	
	private void createMailDelivery(
			String source, 
			String destination,
			Priority priority, 
			float volume, 
			float weight) {
		int hour = new Random().nextInt(23);
		
		try {
			this.sm.performEvent(new MailDeliveryUpdateEvent(new MailDelivery(
					this.finder.calculateRoute(
							priority, 
							this.sm.getData().getLocationForName(source), 
							this.sm.getData().getLocationForName(destination), 
							weight, 
							volume), 
					priority, 
					weight, 
					volume, 
					new SimpleDateFormat("y-m-d h:m:s").parse("2010-05-16 "+hour+":18:18"))));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	private void createRoute(
			TransportMeans trans, 
			String source, 
			String destination, 
			int carrierId, 
			float volcost, 
			float weightcost, 
			int duration, 
			int frequency
	) {
		Route r1 = new Route(
				trans,
				sm.getData().getLocationForName(source),
				sm.getData().getLocationForName(destination),
				sm.getData().getCarrier(carrierId));
		try {
			r1.setCarrierVolumeUnitCost(volcost / (float) 100);
			r1.setCarrierWeightUnitCost(weightcost / (float) 1000);
			r1.setDisabled(false);
			r1.setDuration(duration);
			r1.setFrequency(frequency);
			
			int day = new Random().nextInt(30);
			int hour = new Random().nextInt(23);
			r1.setStartingTime(new SimpleDateFormat("y-m-d h:m:s").parse("2010-03-"+day+" "+hour+":18:18"));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		sm.performEvent(new RouteUpdateEvent(r1));
	}
	
	private void createCustomerPrice(String location, Priority priority, float volumeCost, float weightCost) {
		CustomerPrice price = new CustomerPrice(
				sm.getData().getLocationForName(location), 
				Direction.To, 
				priority);
		price.setPricePerUnitVolume(volumeCost);
		price.setPricePerUnitWeight(weightCost);
		sm.performEvent(new CustomerPriceUpdateEvent(price));
	}
	
	private void setDomesticCustomerPrice(Priority p, float weight, float volume) {
		DomesticCustomerPrice price = this.sm.getData().getDomesticCustomerPrice(p);
		price.setPricePerUnitVolume(volume);
		price.setPricePerUnitWeight(weight);
		this.sm.performEvent(new DomesticCustomerPriceUpdateEvent(price));
	}
}