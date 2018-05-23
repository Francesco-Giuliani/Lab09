package it.polito.tdp.borders.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		
		System.out.println("Creo il grafo relativo al 1916");
		model.createBorderGraphUpToYear(1916);
		System.out.println(model.getGraph());
		System.out.println("numero stati: "+model.getGraph().vertexSet().size());
		System.out.println("numero confini: "+model.getGraph().edgeSet().size());
		System.out.println("numero componenti connesse: "+model.getNumberOfConnectedComponents());
		System.out.println(model.stampaComponentiConnesse());
		
//		List<Country> countries = model.getCountries();
//		System.out.format("Trovate %d nazioni\n", countries.size());

//		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
	}

}
