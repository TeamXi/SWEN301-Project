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
	public Collection<RevenueExpediture> getAllRevenueExpenditure();
	
	/**
	 * Get the number of events since the initialization of the system.
	 * 
	 * @return	The total number of events
	 */
	public int getNumberOfEvents();
	
	/**
	 * Get the total expenditure.
	 * 
	 * @return	The total amount of money spent by KPS
	 */
	public double getTotalExpenditure();
	
	/**
	 * Get the total revenue of KPS
	 * 
	 * @return	The total revenue
	 */
	public double getTotalRevenue();
	
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
		private final Location startPoint;
		
		/**
		 * The end point
		 */
		private final Location endPoint;
		
		/**
		 * The total number of items transfered over the route.
		 */
		private final int items;
		
		/**
		 * The total weight of items transfered over the route
		 */
		private final double totalWeight;
		
		/**
		 * The total volume of items transfered over the route
		 */
		public final double totalVolume;
		
		public AmountOfMail(Location start, Location end, int items, double weight, double volume) {
			this.startPoint = start;
			this.endPoint = end;
			this.items = items;
			this.totalWeight = weight;
			this.totalVolume = volume;
		}

		@Override
		public String toString() {
			return "AmountOfMail [StartPoint=" + startPoint + ", EndPoint="
					+ endPoint + ", Items=" + items + ", TotalWeight="
					+ totalWeight + ", TotalVolume=" + totalVolume + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((endPoint == null) ? 0 : endPoint.hashCode());
			result = prime * result + items;
			result = prime * result
					+ ((startPoint == null) ? 0 : startPoint.hashCode());
			long temp;
			temp = Double.doubleToLongBits(totalVolume);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(totalWeight);
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
			if (endPoint == null) {
				if (other.endPoint != null)
					return false;
			} else if (!endPoint.equals(other.endPoint))
				return false;
			if (items != other.items)
				return false;
			if (startPoint == null) {
				if (other.startPoint != null)
					return false;
			} else if (!startPoint.equals(other.startPoint))
				return false;
			if (Double.doubleToLongBits(totalVolume) != Double
					.doubleToLongBits(other.totalVolume))
				return false;
			if (Double.doubleToLongBits(totalWeight) != Double
					.doubleToLongBits(other.totalWeight))
				return false;
			return true;
		}

		public Location getStartPoint() {
			return startPoint;
		}

		public Location getEndPoint() {
			return endPoint;
		}

		public int getItems() {
			return items;
		}

		public double getTotalWeight() {
			return totalWeight;
		}

		public double getTotalVolume() {
			return totalVolume;
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
		private final Location startPoint;
		
		/**
		 * The end point
		 */
		private final Location endPoint;
		
		/**
		 * The priority
		 */
		private final Priority priority;
		
		/**
		 * The revenue generated from this route
		 */
		private final double revenue;
		
		/**
		 * The expenditure generated from this route
		 */
		private final double expenditure;
		
		/**
		 * The average delivery time of mail for this route (in hours)
		 */
		private final double averageDeliveryTime;
		
		public RevenueExpediture(Location start, Location end, Priority priority, double revenue, double expenditure, double averageDeliveryTime) {
			this.startPoint = start;
			this.endPoint = end;
			this.priority = priority;
			this.revenue = revenue;
			this.expenditure = expenditure;
			this.averageDeliveryTime = averageDeliveryTime;
		}

		@Override
		public String toString() {
			return "RevenueExpediture [StartPoint=" + startPoint
					+ ", EndPoint=" + endPoint + ", Priority=" + priority
					+ ", Revenue=" + revenue + ", Expenditure=" + expenditure
					+ "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((endPoint == null) ? 0 : endPoint.hashCode());
			long temp;
			temp = Double.doubleToLongBits(expenditure);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result
					+ ((priority == null) ? 0 : priority.hashCode());
			temp = Double.doubleToLongBits(revenue);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result
					+ ((startPoint == null) ? 0 : startPoint.hashCode());
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
			if (endPoint == null) {
				if (other.endPoint != null)
					return false;
			} else if (!endPoint.equals(other.endPoint))
				return false;
			if (Double.doubleToLongBits(expenditure) != Double
					.doubleToLongBits(other.expenditure))
				return false;
			if (priority != other.priority)
				return false;
			if (Double.doubleToLongBits(revenue) != Double
					.doubleToLongBits(other.revenue))
				return false;
			if (startPoint == null) {
				if (other.startPoint != null)
					return false;
			} else if (!startPoint.equals(other.startPoint))
				return false;
			return true;
		}

		public Location getStartPoint() {
			return startPoint;
		}

		public Location getEndPoint() {
			return endPoint;
		}

		public Priority getPriority() {
			return priority;
		}

		public double getRevenue() {
			return revenue;
		}

		public double getExpenditure() {
			return expenditure;
		}

		public double getAverageDeliveryTime() {
			return averageDeliveryTime;
		}
	}
}
