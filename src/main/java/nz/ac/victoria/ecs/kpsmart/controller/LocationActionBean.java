package nz.ac.victoria.ecs.kpsmart.controller;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.ajax.JavaScriptResolution;
import nz.ac.victoria.ecs.kpsmart.entities.logging.LocationUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.resolutions.FormValidationResolution;

@UrlBinding("/event/location?{$event}") // TODO: not in /event?
public class LocationActionBean extends AbstractActionBean {
	private String name;
	private double latitude;
	private double longitude;
	private boolean isInternational;
	
	@DefaultHandler
	@HandlesEvent("list")
	public Resolution jsonLocationList() {
		return new ForwardResolution("/views/event/locationListJSON.jsp"); // TODO: not in /event?
	}
	
	@HandlesEvent("map")
	public Resolution locationMap() {
		return new ForwardResolution("/views/event/locationMap.jsp"); // TODO: not in /event?
	}
	
	@HandlesEvent("new")
	public Resolution addNewLocation() {
		if(getState().getLocationForName(name) == null) {
			Location location = new Location(name, latitude, longitude, isInternational);
			LocationUpdateEvent event = new LocationUpdateEvent(location);
			getEntityManager().performEvent(event);
			return new FormValidationResolution(true, null, null);
		}
		else {
			return new FormValidationResolution(false, null, null);
		}
	}
	
	@HandlesEvent("connected")
	public Resolution getConnectedLocations() {
		if(name == null || name.length() == 0) {
			return new JavaScriptResolution(new Object[0]);
		}
		else {
			Location from = getState().getLocationForName(name);
			if(from == null) {
				return new JavaScriptResolution(new Object[0]);
			}
			else {
				List<String> answer = new ArrayList<String>();
				
				List<Route> routes = getState().getRoutesConnectedTo(from);
				for(Route r : routes) {
					if(r.getStartPoint() == from) {
						answer.add(r.getEndPoint().getName());
					}
					else {
						answer.add(r.getStartPoint().getName());
					}
				}
				
				return new JavaScriptResolution(answer);
			}
		}
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the isInternational
	 */
	public boolean isInternational() {
		return isInternational;
	}

	/**
	 * @param isInternational the isInternational to set
	 */
	public void setIsInternational(boolean isInternational) {
		this.isInternational = isInternational;
	}
}
