package nz.ac.victoria.ecs.kpsmart;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import nz.ac.victoria.ecs.kpsmart.integration.EntityManager;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.CarrierUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.CustomerPriceUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.LocationUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.RouteUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.TransportMeans;

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
		Carrier c1 = new Carrier();
		{
			c1.setName("a");
		}
		sm.performEvent(new CarrierUpdateEvent(c1));
		
		/*
		 * 
		 * Locations
		 * 
		 */
		Location l1 = createLocation(true, 42.988576, 12.414551, "Rome");
		sm.performEvent(new LocationUpdateEvent(l1));
		Location l2 = createLocation(false, -41.013066, 174.605713, "Wellington");
		sm.performEvent(new LocationUpdateEvent(l2));
		Location l3 = createLocation(false, -36.694851, 175.155029, "Auckland");
		sm.performEvent(new LocationUpdateEvent(l3));
		Location l4 = createLocation(true, 42.654162, 23.365173, "Sofia");
		sm.performEvent(new LocationUpdateEvent(l4));
		Location l5 = createLocation(true, 52.749594, 5.998535, "Amsterdam");
		sm.performEvent(new LocationUpdateEvent(l5));
		Location l6 = createLocation(false, -1, -1, "Christchurch");
		sm.performEvent(new LocationUpdateEvent(l6));
		/*
		 * 
		 * Routes
		 * 
		 */
		Route r1 = Route.newInstance();
		try {
			r1.setCarrier(sm.getData().getCarrier(1));
			r1.setCarrierVolumeUnitCost((float) 10.9);
			r1.setCarrierWeightUnitCost((float)10.9);
			r1.setDisabled(false);
			r1.setDuration(10);
			r1.setEndPoint(sm.getData().getLocationForName("Rome"));
			r1.setFrequency(10);
			r1.setStartPoint(sm.getData().getLocationForName("Wellington"));
			r1.setStartingTime(new SimpleDateFormat("y-m-d h:m:s").parse("2012-03-27 11:18:18"));
			r1.setTransportMeans(TransportMeans.Air);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		sm.performEvent(new RouteUpdateEvent(r1));
		
		Route r2 = Route.newInstance();
		try {
			r2.setCarrier(sm.getData().getCarrier(1));
			r2.setCarrierVolumeUnitCost((float) 8.0);
			r2.setCarrierWeightUnitCost((float)8.9);
			r2.setDisabled(false);
			r2.setDuration(1);
			r2.setEndPoint(sm.getData().getLocationForName("Auckland"));
			r2.setFrequency(6);
			r2.setStartPoint(sm.getData().getLocationForName("Wellington"));
			r2.setStartingTime(new SimpleDateFormat("y-m-d h:m:s").parse("2012-03-27 11:45:18"));
			r2.setTransportMeans(TransportMeans.Air);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		sm.performEvent(new RouteUpdateEvent(r2));
		
		Route r3 = Route.newInstance();
		try {
			r3.setCarrier(sm.getData().getCarrier(1));
			r3.setCarrierVolumeUnitCost((float) 8.0);
			r3.setCarrierWeightUnitCost((float)8.9);
			r3.setDisabled(false);
			r3.setDuration(1);
			r3.setEndPoint(sm.getData().getLocationForName("Christchurch"));
			r3.setFrequency(6);
			r3.setStartPoint(sm.getData().getLocationForName("Wellington"));
			r3.setStartingTime(new SimpleDateFormat("y-m-d h:m:s").parse("2012-03-27 11:45:18"));
			r3.setTransportMeans(TransportMeans.Air);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		sm.performEvent(new RouteUpdateEvent(r3));
		
		Route r4 = Route.newInstance();
		try {
			r4.setCarrier(sm.getData().getCarrier(1));
			r4.setCarrierVolumeUnitCost((float) 8.0);
			r4.setCarrierWeightUnitCost((float)8.9);
			r4.setDisabled(false);
			r4.setDuration(1);
			r4.setEndPoint(sm.getData().getLocationForName("Rome"));
			r4.setFrequency(6);
			r4.setStartPoint(sm.getData().getLocationForName("Auckland"));
			r4.setStartingTime(new SimpleDateFormat("y-m-d h:m:s").parse("2012-03-27 11:45:18"));
			r4.setTransportMeans(TransportMeans.Air);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		sm.performEvent(new RouteUpdateEvent(r4));
		
		CustomerPrice price = CustomerPrice.newInstance();
		price.setStartLocation(null);
		price.setEndLocation(sm.getData().getLocationForName("Rome"));
		price.setPricePerUnitVolume(1);
		price.setPricePerUnitWeight(1);
		price.setPriority(Priority.International_Air);
		sm.performEvent(new CustomerPriceUpdateEvent(price));
	}

	public Location createLocation(boolean international, double lat, double lon, String name) {
		Location l = new Location(name, lat, lon, international);
		return l;
	}
}