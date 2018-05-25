package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

	public Model() {
		this.countriesIdMap = new HashMap<>();
		this.bordersIdMap = new HashMap<>();
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		this.bdao = new BordersDAO();
		
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
			sb.append(c.getcName()+" confina con "+this.graph.degreeOf(c)+" stati\n");
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
	
	public Set<Country> findAllReachableCountries(Country country){
	
		try {
			if(this.graph == null || (this.graph.edgeSet().isEmpty() && this.graph.vertexSet().isEmpty())) 
				throw new NoGraphException();
		} catch (NoGraphException e) {
			e.printStackTrace();
			return null;
		}
		
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<>(this.graph);
		return ci.connectedSetOf(country);	
	}
	
	
}
