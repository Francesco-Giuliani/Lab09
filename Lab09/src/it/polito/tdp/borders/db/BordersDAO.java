package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {
	
	private int nextBorderId =1;
	private final int MAX_COD_COUNTRY = 1000;

	public List<Country> loadAllCountries(Map<Integer, Country> cIdMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int countryCode = rs.getInt("ccode");
				Country old = cIdMap.get(countryCode);
				if(old == null) {
					String countryId = rs.getString("StateAbb");
					String countryName = rs.getString("StateNme");
					old = new Country(countryCode, countryId, countryName);
					cIdMap.put(countryCode, old);
				}
				result.add(old);
				//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno, Map<Integer, Border> bidmap, Map<Integer, Country> cidmap) {
		
		String sql = "SELECT c.state1no s1num, c1.StateAbb s1abb, c1.StateNme s1name, "+ 
						"c.state2no s2num, c2.StateAbb s2abb, c2.StateNme s2name, c.year "+
						"from contiguity as c, country as c1, country as c2 "+ 
						"where year <= ? and c.state1no = c1.CCode and c.state2no = c2.CCode and c.conttype=1";
						
		List<Border> borders = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int codS1 = rs.getInt("s1num");
				int codS2 = rs.getInt("s2num");
				Country c1 = cidmap.get(codS1);
				Country c2 = cidmap.get(codS2);
				if(c1 ==null){
					c1 = new Country(codS1, rs.getString("s1abb"), rs.getString("s1name"));
					cidmap.put(codS1, c1);
				}
				if(c2 ==null){
					c2 = new Country(codS2, rs.getString("s2abb"), rs.getString("s2name"));
					cidmap.put(codS2, c2);
				}
				int bId1 = codS1*this.MAX_COD_COUNTRY + codS2;
				int bId2 = codS2*this.MAX_COD_COUNTRY + codS1;
				
				Border b; 
				if((b= bidmap.get(bId1)) != null){
					borders.add(b);
				}
				else if((b = bidmap.get(bId2))!=null){
					borders.add(b);
				}
				else{
					int bId = codS1*this.MAX_COD_COUNTRY + codS2;
					b = new Border(bId, c1, c2, anno);
					bidmap.put(bId, b);
					c1.getBorders().add(b);
					c2.getBorders().add(b);
					borders.add(b);
				}				
				
			}				
			conn.close();
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}catch(Exception e){
			e.printStackTrace();
		}			
		return borders;
	}

	
}
