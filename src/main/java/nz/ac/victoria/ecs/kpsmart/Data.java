package nz.ac.victoria.ecs.kpsmart;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import nz.ac.victoria.ecs.kpsmart.entities.logging.CarrierUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.CustomerPriceUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.LocationUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.logging.RouteUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.entities.state.Direction;
import nz.ac.victoria.ecs.kpsmart.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.entities.state.TransportMeans;
import nz.ac.victoria.ecs.kpsmart.integration.EntityManager;

import com.google.inject.Inject;

final class Data {
	@Inject 
	private EntityManager sm;

	@InjectOnCall
	public void createData() {
		/*
		 * 
		 * Carriers
		 * 
		 */
		sm.performEvent(new CarrierUpdateEvent(new Carrier("a")));
		
		/*
		 * 
		 * Locations
		 * 
		 */
		sm.performEvent(new LocationUpdateEvent(new Location("Rome", 42.988576, 12.414551, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Wellington", -41.013066, 174.605713, false)));
		sm.performEvent(new LocationUpdateEvent(new Location("Auckland", -36.694851, 175.155029, false)));
		sm.performEvent(new LocationUpdateEvent(new Location("Sofia", 42.654162, 23.365173, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Amsterdam", 52.749594, 5.998535, true)));
		sm.performEvent(new LocationUpdateEvent(new Location("Christchurch", -43.53, 172.620278, false)));
		/*
		 * 
		 * Routes
		 * 
		 */
		Route r1 = new Route(
				TransportMeans.Air,
				sm.getData().getLocationForName("Wellington"),
				sm.getData().getLocationForName("Rome"),
				sm.getData().getCarrier(1));
		try {
			r1.setCarrierVolumeUnitCost((float) 10.9);
			r1.setCarrierWeightUnitCost((float)10.9);
			r1.setDisabled(false);
			r1.setDuration(10);
			r1.setFrequency(10);
			r1.setStartingTime(new SimpleDateFormat("y-m-d h:m:s").parse("2012-03-27 11:18:18"));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		sm.performEvent(new RouteUpdateEvent(r1));
		
		Route r2 = new Route(
				TransportMeans.Air, 
				sm.getData().getLocationForName("Auckland"), 
				sm.getData().getLocationForName("Wellington"), 
				sm.getData().getCarrier(1));
		try {
			r2.setCarrierVolumeUnitCost((float) 8.0);
			r2.setCarrierWeightUnitCost((float)8.9);
			r2.setDisabled(false);
			r2.setDuration(1);
			r2.setFrequency(6);
			r2.setStartingTime(new SimpleDateFormat("y-m-d h:m:s").parse("2012-03-27 11:45:18"));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		sm.performEvent(new RouteUpdateEvent(r2));
		
		Route r3 = new Route(
				TransportMeans.Air,
				sm.getData().getLocationForName("Wellington"),
				sm.getData().getLocationForName("Christchurch"),
				sm.getData().getCarrier(1));
		try {
			r3.setCarrierVolumeUnitCost((float) 8.0);
			r3.setCarrierWeightUnitCost((float)8.9);
			r3.setDisabled(false);
			r3.setDuration(1);
			r3.setFrequency(6);
			r3.setStartingTime(new SimpleDateFormat("y-m-d h:m:s").parse("2012-03-27 11:45:18"));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		sm.performEvent(new RouteUpdateEvent(r3));
		
		Route r4 = new Route(
				TransportMeans.Air,
				sm.getData().getLocationForName("Auckland"),
				sm.getData().getLocationForName("Rome"),
				sm.getData().getCarrier(1));
		try {
			r4.setCarrierVolumeUnitCost((float) 8.0);
			r4.setCarrierWeightUnitCost((float)8.9);
			r4.setDisabled(false);
			r4.setDuration(1);
			r4.setFrequency(6);
			r4.setStartingTime(new SimpleDateFormat("y-m-d h:m:s").parse("2012-03-27 11:45:18"));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		sm.performEvent(new RouteUpdateEvent(r4));
		
		CustomerPrice price = new CustomerPrice(
				sm.getData().getLocationForName("Rome"), 
				Direction.To, 
				Priority.International_Air);
		price.setPricePerUnitVolume(1);
		price.setPricePerUnitWeight(1);
		sm.performEvent(new CustomerPriceUpdateEvent(price));
	}
}