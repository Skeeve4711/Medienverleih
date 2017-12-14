
public class Kategorie {
	
	private String name;
	private String wert;
	private Typ typ;
	
	public Kategorie(String name, String wert, Typ typ) {
		super();
		this.name = name;
		this.wert = wert;
		this.typ = typ;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWert() {
		return wert;
	}
	public void setWert(String wert) {
		this.wert = wert;
	}
	
	public Typ getTyp() {
		return this.typ;
	}
}
