package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.io.IOException;
import java.util.Properties;

import nz.ac.victoria.ecs.kpsmart.state.entities.log.CarrierDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.CarrierUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.CustomerPriceDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.CustomerPriceUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.DomesticCustomerPriceDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.DomesticCustomerPriceUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.EventID;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.LocationDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.LocationUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.MailDeliveryDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.MailDeliveryUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.RouteDeleteEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.log.RouteUpdateEvent;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Carrier;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.CustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.CustomerPriceID;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.DomesticCustomerPrice;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.EntityID;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.MailDelivery;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Route;

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
				CustomerPriceDeleteEvent.class,
				
				MailDeliveryUpdateEvent.class,
				MailDeliveryDeleteEvent.class,
				
				CarrierUpdateEvent.class,
				CarrierDeleteEvent.class,
				
				DomesticCustomerPriceUpdateEvent.class,
				DomesticCustomerPriceDeleteEvent.class,
				
				RouteUpdateEvent.class,
				RouteDeleteEvent.class,
				
				LocationUpdateEvent.class,
				LocationDeleteEvent.class,
				
				
				
////				Event.class,
				EventID.class,
//						
//				StorageEntity.class,
				Carrier.class,
				Location.class,
				MailDelivery.class,
				Route.class,
				EntityID.class,
				CustomerPrice.class,
				CustomerPriceID.class,
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
