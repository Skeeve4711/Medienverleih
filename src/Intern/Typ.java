package Intern;
import java.util.ArrayList;

public class Typ {
	
	private String name;
	private ArrayList<String> typeigenschaften = new ArrayList<>();
	
	public Typ(String name,String[] wert) {
		this.name = name;
		for(String eigenschaft : wert) {
			this.typeigenschaften.add(eigenschaft);
		}
	}
	
	public Typ(String name,ArrayList<String> werte) {
		this.name = name;
		this.typeigenschaften = werte;
	}
	
	public ArrayList<String> getEigenschaften() {
		return this.typeigenschaften;
	}
	
	public void addKey(String wert) {
		if(!typeigenschaften.contains(name)) {
			typeigenschaften.add(wert);
		}
	}
	
	public void removeKey(String wert) {
		typeigenschaften.remove(wert);
	}
}
