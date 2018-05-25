package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Map<Integer, Country> countriesIdMap;
	private Map<Integer, Border> bordersIdMap;
	private BordersDAO bdao;
	private Graph<Country, DefaultEdge> graph;
	private List<Country> countries;
	private List<Border> borders;
	
	private List<Country> reachables;
	private final int MAX_VISITS = 10000;

	public Model() {
		this.countriesIdMap = new HashMap<>();
		this.bordersIdMap = new HashMap<>();
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		this.bdao = new BordersDAO();
		this.reachables = new ArrayList<>();
		
	}

	public void createBorderGraphUpToYear(int anno) {
		
		this.borders = this.bdao.getCountryPairs(anno, this.bordersIdMap, countriesIdMap);
		this.countries = new ArrayList<>(this.countriesIdMap.values());
		Graphs.addAllVertices(this.graph, this.countries);
		
		for(Border b : this.borders) {
			this.graph.addEdge(b.getC1(), b.getC2());
		}
		
	}

	public Graph<Country, DefaultEdge> getGraph() {
		return graph;
	}

	public String printSituation() {
		if(this.graph == null || this.graph.vertexSet().isEmpty())
			return "Nessun grafo! Creare un grafo";
		
		StringBuilder sb = new StringBuilder();
		for(Country c : this.countries) {
			sb.append(c.getcName().toUpperCase()+" confina con "+this.graph.degreeOf(c)+" stati\n");
		}
		return sb.toString();
	}

	public int getNumberOfConnectedComponents() {
		
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<>(this.graph);
		return ci.connectedSets().size();
	}
	public String stampaComponentiConnesse() {
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<>(this.graph);
		StringBuilder sb = new StringBuilder();
		for(Set<Country> sc : ci.connectedSets()) {
			sb.append("Componente connessa:\n"+sc.toString()+"\nDimensione: "+sc.size()+"\n");
		}
		return sb.toString();
	}
	
	public List<Country> findReachablesFrom(Country start){
		this.reachables.clear();
		int visitCount = 0;
		List<Country> vicini = new ArrayList<>(Graphs.neighborListOf(this.graph, start));
		
		this.recursive(visitCount, vicini);
		
		return this.reachables;
	}
	
	private void recursive(int level, List<Country> parziale) {
		
		if(level>=this.MAX_VISITS) {
			return;
		}
		if(parziale.isEmpty()) {
			return;
		}
		
		for(Country c: parziale) {
			if(!this.reachables.contains(c)) {
				this.reachables.add(c);			
				List <Country> vicini= 	new ArrayList<>(Graphs.neighborListOf(this.graph, c));
				this.recursive(level+1, vicini);
			}
		}
	}

	public List<Country> getCountries() {
		return this.countries;
	}

	public Map<Country, Integer> getCountryCounts(){
		Map<Country, Integer> stats = new HashMap<>();
		for(Country c: this.countries) {
			stats.put(c, Graphs.neighborListOf(this.graph, c).size());
		}
		return stats;
	}
	
}
