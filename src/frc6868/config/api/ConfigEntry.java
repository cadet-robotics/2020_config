package frc6868.config.api;

/**
 * A container for an entry
 * This class is not intended for use outside of the Config class
 * 
 * @author Alex Pickering
 */
public class ConfigEntry {
	private static final String COLON_ERROR_MSG = "Names cannot contain colons (:)";
	
	private String name,
				   description,
				   value;
	
	/**
	 * Creates a full Entry
	 * 
	 * @param name
	 * @param description
	 * @param value
	 */
	protected ConfigEntry(String name, String description, String value) {
		this.name = name;
		this.description = description;
		this.value = value;
	}
	
	/*
	 * Getters
	 */
	protected String getDescription() { return description; }
	protected String getValue() { return value; }
	protected String getFullName() { return name; }
	
	protected String getName() {
		if(name.contains(":")) {
			return name.substring(name.indexOf(':') + 1);
		}
		
		return name;
	}
	
	protected String getCategory() {
		if(name.contains(":")) {
			return name.substring(0, name.indexOf(':'));
		}
		
		return "";
	}
	
	/*
	 * Setters
	 */
	protected void setDescription(String d) { description = d; }
	protected void setValue(String v) { value = v; }
	protected void setFullName(String n) { name = n; }
	
	protected void setName(String n) {
		if(n.contains(":")) throw new IllegalArgumentException(COLON_ERROR_MSG);
		
		if(name.contains(":")) {
			String category = name.substring(0, name.indexOf(":"));
			name = category + ":" + n;
		} else name = n;
	}
	
	protected void setCategory(String c) {
		if(name.contains(":")) {
			name = c + ":" + name.substring(name.indexOf(":" + 1));
		} else name = c + ":" + name;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s) = %s", name, description, value);
	}
}




