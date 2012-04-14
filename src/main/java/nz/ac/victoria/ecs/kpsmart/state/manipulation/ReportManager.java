package nz.ac.victoria.ecs.kpsmart.state.manipulation;

import java.util.Collection;

import nz.ac.victoria.ecs.kpsmart.state.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.state.entities.state.Priority;

/**
 * Defines the reporting functionality of the system
 * 
 * @author hodderdani
 *
 */
public interface ReportManager {
	/**
	 * Get the total number, total weight, and total volume of all mail sent between every pair of
	 * locations. This does not filter out routes for which there is no mail.
	 * 
	 * @return	All the information about aggregate mail deliveries for every ({@link Location}, {@link Location}) pair.
	 */
	public Collection<AmountOfMail> getAmountsOfMailForAllRoutes();
	
	/**
	 * Get all the revenue and expenditure information for every 
	 * (Source : {@link Location}, Destination : {@link Location}, Priority : {@link Priority }) triple. This does not
	 * filter out triples for which there is no mail
	 * 
	 * @return	All the information about revenue and expenditure.
	 */
	public Collection<RevenueExpediture> getAllRevinueExpenditure();
	
	/**
	 * Get the number of events since the initialization of the system.
	 * 
	 * @return	The total number of events
	 */
	public int getNumberOfEvents();
	
	/**
	 * Represents the amount of mail for a given source-destination pair.
	 * 
	 * @author hodderdani
	 *
	 */
	public static final class AmountOfMail {
		/**
		 * The start point
		 */
		public final Location StartPoint;
		
		/**
		 * The end point
		 */
		public final Location EndPoint;
		
		/**
		 * The total number of items transfered over the route.
		 */
		public final int Items;
		
		/**
		 * The total weight of items transfered over the route
		 */
		public final double TotalWeight;
		
		/**
		 * The total volume of items transfered over the route
		 */
		public final double TotalVolume;
		
		public AmountOfMail(Location start, Location end, int items, double weight, double volume) {
			this.StartPoint = start;
			this.EndPoint = end;
			this.Items = items;
			this.TotalWeight = weight;
			this.TotalVolume = volume;
		}

		@Override
		public String toString() {
			return "AmountOfMail [StartPoint=" + StartPoint + ", EndPoint="
					+ EndPoint + ", Items=" + Items + ", TotalWeight="
					+ TotalWeight + ", TotalVolume=" + TotalVolume + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((EndPoint == null) ? 0 : EndPoint.hashCode());
			result = prime * result + Items;
			result = prime * result
					+ ((StartPoint == null) ? 0 : StartPoint.hashCode());
			long temp;
			temp = Double.doubleToLongBits(TotalVolume);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(TotalWeight);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AmountOfMail other = (AmountOfMail) obj;
			if (EndPoint == null) {
				if (other.EndPoint != null)
					return false;
			} else if (!EndPoint.equals(other.EndPoint))
				return false;
			if (Items != other.Items)
				return false;
			if (StartPoint == null) {
				if (other.StartPoint != null)
					return false;
			} else if (!StartPoint.equals(other.StartPoint))
				return false;
			if (Double.doubleToLongBits(TotalVolume) != Double
					.doubleToLongBits(other.TotalVolume))
				return false;
			if (Double.doubleToLongBits(TotalWeight) != Double
					.doubleToLongBits(other.TotalWeight))
				return false;
			return true;
		}
	}
	
	/**
	 * Represents the total revenue and expenditure of a given ({@link Location}, {@link Location}, {@link Priority}) tripple.
	 * 
	 * @author hodderdani
	 *
	 */
	public static final class RevenueExpediture {
		/**
		 * The start point
		 */
		public final Location StartPoint;
		
		/**
		 * The end point
		 */
		public final Location EndPoint;
		
		/**
		 * The priority
		 */
		public final Priority Priority;
		
		/**
		 * The revenue generated from this route
		 */
		public final double Revenue;
		
		/**
		 * The expenditure generated from this route
		 */
		public final double Expenditure;
		
		public RevenueExpediture(Location start, Location end, Priority priority, double revenue, double expenditure) {
			this.StartPoint = start;
			this.EndPoint = end;
			this.Priority = priority;
			this.Revenue = revenue;
			this.Expenditure = expenditure;
		}

		@Override
		public String toString() {
			return "RevenueExpediture [StartPoint=" + StartPoint
					+ ", EndPoint=" + EndPoint + ", Priority=" + Priority
					+ ", Revenue=" + Revenue + ", Expenditure=" + Expenditure
					+ "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((EndPoint == null) ? 0 : EndPoint.hashCode());
			long temp;
			temp = Double.doubleToLongBits(Expenditure);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result
					+ ((Priority == null) ? 0 : Priority.hashCode());
			temp = Double.doubleToLongBits(Revenue);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result
					+ ((StartPoint == null) ? 0 : StartPoint.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			RevenueExpediture other = (RevenueExpediture) obj;
			if (EndPoint == null) {
				if (other.EndPoint != null)
					return false;
			} else if (!EndPoint.equals(other.EndPoint))
				return false;
			if (Double.doubleToLongBits(Expenditure) != Double
					.doubleToLongBits(other.Expenditure))
				return false;
			if (Priority != other.Priority)
				return false;
			if (Double.doubleToLongBits(Revenue) != Double
					.doubleToLongBits(other.Revenue))
				return false;
			if (StartPoint == null) {
				if (other.StartPoint != null)
					return false;
			} else if (!StartPoint.equals(other.StartPoint))
				return false;
			return true;
		}
	}
}
