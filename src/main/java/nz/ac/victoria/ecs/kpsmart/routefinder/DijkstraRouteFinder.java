package nz.ac.victoria.ecs.kpsmart.routefinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import nz.ac.victoria.ecs.kpsmart.InjectOnContruct;
import nz.ac.victoria.ecs.kpsmart.entities.state.Location;
import nz.ac.victoria.ecs.kpsmart.entities.state.Priority;
import nz.ac.victoria.ecs.kpsmart.entities.state.Route;
import nz.ac.victoria.ecs.kpsmart.state.State;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

@InjectOnContruct
public final class DijkstraRouteFinder implements RouteFinder {
	@Inject
	private State state;
	
	@Override
	public List<Route> calculateRoute(Priority priority, Location startPoint, Location endPoint, float weight, float volume) {
		GraphNode start = this.buildGraph(priority, startPoint);
		GraphNode goal = null;
		PriorityQueue<SearchNode> fringe = new PriorityQueue<DijkstraRouteFinder.SearchNode>();
		fringe.add(new SearchNode(null, start, new Weight(0, 0)));
			
		for (SearchNode current = fringe.poll(); current != null; current = fringe.poll()) {
			if (current.to.currentLocation.equals(endPoint)) {
				goal = current.to;
				current.to.pathTo = current.from;
				current.to.pathLength = current.costSoFar;
				break;
			}
			
			if (current.to.visited)
				continue;
			
			current.to.visited = true;
			current.to.pathTo = current.from;
			current.to.pathLength = current.costSoFar;
			
			for (GraphEdge edge : current.to.edges) {
				GraphNode neighber = edge.to;
				
				if (!neighber.visited)
					fringe.offer(new SearchNode(current.to, neighber, current.costSoFar.add(edge.weight)));
			}
		}
		
		List<GraphNode> path = new LinkedList<DijkstraRouteFinder.GraphNode>();
		
		for (GraphNode current = goal; current != null; current = current.pathTo)
			path.add(current);
		
		if (path.isEmpty() || path.get(path.size()-1) != start)
			return null;
			// We didnt find a path
		
		Collections.reverse(path);
		
		List<Route> routes = new ArrayList<Route>(path.size()-1);
		
		for (int i=0; i<path.size()-1; i++) {
			routes.add(Collections.min(
					this.state.getRoutesBetween(path.get(i).currentLocation, path.get(i+1).currentLocation, priority),
					new Comparator<Route>() {
						@Override
						public int compare(Route o1, Route o2) {
							return Double.compare(
									Math.min(o1.getCarrierVolumeUnitCost(), o1.getCarrierWeightUnitCost()), 
									Math.min(o2.getCarrierVolumeUnitCost(), o2.getCarrierWeightUnitCost())
							);
						}
					}
			));
		}
		
		return routes;
	}
	
	/**
	 * Build a graph that represents the routes starting from the given location. We only use air routes for air priorities 
	 * and all methods for standard.
	 * 
	 * @param p	The priority to use
	 * @param startPoint	The point to start at
	 * @return
	 */
	private GraphNode buildGraph(Priority p, Location startPoint) {
		// Create the nodes of the graph
		Map<Location, GraphNode> nodes = new HashMap<Location, DijkstraRouteFinder.GraphNode>();
		for (Location l : this.state.getAllLocations())
			// Create the node for this location
			nodes.put(l, new GraphNode(l));
		
		// Create the edges
		Map<Route, GraphEdge> edges = new HashMap<Route, DijkstraRouteFinder.GraphEdge>();
		for (Route r : this.state.getAllRoutesForPriority(p)) {
			// Create the edge for this route
			GraphEdge edge = new GraphEdge(
					new Weight(r.getCarrierVolumeUnitCost(), r.getCarrierWeightUnitCost()), 
					nodes.get(r.getStartPoint()), 
					nodes.get(r.getEndPoint()),
					r
			);
			edges.put(r, edge);
			nodes.get(r.getStartPoint()).edges.add(edge);
		}
		
		// Return the starting node
		return nodes.get(startPoint);
	}
	
	/**
	 * A node on the dijkstra graph
	 * 
	 * @author hodderdani
	 *
	 */
	private static final class SearchNode implements Comparable<SearchNode> {
		private final GraphNode from;
		
		private final GraphNode to;
		
		private final Weight costSoFar;
		
		public SearchNode(final GraphNode from, final GraphNode to, final Weight costSoFar) {
			this.from = from;
			this.to = to;
			this.costSoFar = costSoFar;
		}

		@Override
		public int compareTo(SearchNode o) {
			return this.costSoFar.compareTo(o.costSoFar);
		}
	}
	
	private static final class GraphNode {
		private boolean visited;

		public final Location currentLocation;
		
		public final Collection<GraphEdge> edges = new HashSet<DijkstraRouteFinder.GraphEdge>();
		
		public GraphNode pathTo;
		
		public Weight pathLength;
		
		public GraphNode(final Location current) {
			this.currentLocation = current;
		}
	}
	
	private static final class GraphEdge {
		public final Weight weight;
		
		public final GraphNode to;
		
		public final GraphNode from;
		
		public final Route routeRepresented;
		
		public GraphEdge(final Weight weight, final GraphNode from, final GraphNode to, final Route routeRepresented) {
			this.weight = weight;
			this.from = from;
			this.to = to;
			this.routeRepresented = routeRepresented;
		}
	}
	
	private static final class Weight implements Comparable<Weight> {
		public final double volumeCost;
		
		public final double weightCost;
		
		public Weight(double volumeCost, double weightCost) {
			this.volumeCost = volumeCost;
			this.weightCost = weightCost;
		}

		public static Weight add(Weight w1, Weight w2) {
			return new Weight(w1.volumeCost+w2.volumeCost, w1.weightCost+w2.weightCost);
		}
		
		public Weight add(Weight w) {
			return add(this, w);
		}

		@Override
		public int compareTo(Weight o) {
			return Double.compare(Math.min(this.volumeCost, this.weightCost), Math.min(o.volumeCost, o.weightCost));
		}
	}
	
	public static final class Module extends AbstractModule {
		@Override
		protected void configure() {
			bind(RouteFinder.class).to(DijkstraRouteFinder.class);
		}
	}
}
