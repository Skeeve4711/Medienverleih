import java.util.HashMap;

public class Typ {
	
	HashMap<String,String> typeigenschaften = new HashMap<>();
	
	public Typ(String[] name,String[] wert) {
		for(int i=0;i<name.length;i++) {
			this.typeigenschaften.put(name[i], wert[i]);
		}
	}
	
	public String getEigenschaft(String name) {
		return typeigenschaften.get(name);
	}
	
	public void addKey(String name,String wert) {
		if(!typeigenschaften.containsKey(name)) {
			typeigenschaften.put(name, wert);
		}
	}
	
	public void removeKey(String name) {
		typeigenschaften.remove(name);
	}
}
