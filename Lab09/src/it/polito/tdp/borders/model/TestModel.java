package it.polito.tdp.borders.model;

import java.util.List;
import java.util.Map;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		
		System.out.println("Creo il grafo relativo al 1816");
		model.createBorderGraphUpToYear(1816);
		System.out.println(model.getGraph());
		System.out.println("numero stati: "+model.getGraph().vertexSet().size());
		System.out.println("numero confini: "+model.getGraph().edgeSet().size());
		System.out.println("numero componenti connesse: "+model.getNumberOfConnectedComponents());
		System.out.println(model.stampaComponentiConnesse());
		
		List<Country> countries = model.getCountries();
		System.out.format("Trovate %d nazioni\n", countries.size());

		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
		Map<Country, Integer> stats = model.getCountryCounts();
		for (Country country : stats.keySet())
			System.out.format("%s %d\n", country, stats.get(country));		
		
		Country start = countries.get(0);
		List<Country>reachables = model.findReachablesFrom(start);
		
		System.out.println("Raggiungibili da: "+start+"\n"+reachables);
	}

}
