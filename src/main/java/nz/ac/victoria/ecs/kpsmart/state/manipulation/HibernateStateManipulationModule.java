package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.io.IOException;
import java.util.Properties;

import nz.ac.victoria.ecs.kpsmart.state.entities.log.CustomerPriceUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityUpdateEvent.CarrierUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityUpdateEvent.LocationUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EntityUpdateEvent.RouteUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.Event;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.MailDeliveryEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.TransportCostUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.TransportDiscontinuedEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.DomesticCustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.RouteID;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.StorageEntity;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.google.inject.AbstractModule;

public class HibernateStateManipulationModule extends AbstractModule {
	@Override
	public void configure() {
		// General Bindings
		bind(StateManipulator.class).to(HibernateImpl.class);
		bind(ReportManager.class).to(HibernateImpl.class);
		bind(LogManipulator.class).to(HibernateImpl.class);
		
		Session session = this.getSession("hibernate.properties");
		session.beginTransaction();
		bind(Session.class).toInstance(session);
		
	}
	
	protected Session getSession(String configurationFileName) {
		// Configure hibernate
		final Class<?>[] annotatedClasses = {
				CustomerPriceUpdateEvent.class,
				MailDeliveryEvent.class,
				TransportCostUpdateEvent.class,
				TransportDiscontinuedEvent.class,
				CustomerPriceUpdateEvent.class,
				CarrierUpdateEvent.class,
				RouteUpdateEvent.class,
				LocationUpdateEvent.class,
				CustomerPriceUpdateEvent.class,
				Event.class,
						
				StorageEntity.class,
				Carrier.class,
				Location.class,
				MailDelivery.class,
				Route.class,
				RouteID.class,
				CustomerPrice.class,
				DomesticCustomerPrice.class
		};
		
		try {
			Properties p = new Properties();
			p.load(this.getClass().getClassLoader().getResourceAsStream(configurationFileName));
			final Configuration configuration = new Configuration();
			configuration.addProperties(p);
			
			for (final Class<?> c : annotatedClasses)
				configuration.addAnnotatedClass(c);
			
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			return configuration.buildSessionFactory(serviceRegistry).getCurrentSession();
		} catch (IOException e) {
			throw new RuntimeException("Could not load the hibernate properties file", e);
		}
	}
}
