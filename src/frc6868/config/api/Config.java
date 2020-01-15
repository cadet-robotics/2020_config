package frc6868.config.api;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * The main class that holds the config and manages it
 * 
 * @author Alex Pickering
 */
public class Config {
    private HashMap<String, ConfigEntry> entries;
    
    private String configFileLocation;
    
    
    
    /**
     * Creates a Config instance with the specified file
     * 
     * @param fileLocation The file to read/write
     */
    public Config(String fileLocation) {
        configFileLocation = fileLocation;
        entries = new HashMap<>();
    }
    
    /**
     * Creates a Config instance without a file (must be specified later)
     */
    public Config() {
        configFileLocation = "";
    }
    
    
    /*
     * File IO and JSON stuff
     */
    
    /**
     * Reads the config file, overwriting duplicate entries
     * 
     * @return Itself, i.e. to facilitate something like Config con = new Config(filename).readFile(); 
     * @throws IOException 
     */
    public Config readFile() throws IOException {
        // Read file and parse to a top-level object
        JsonObject jo = JsonParser.parseReader(new FileReader(configFileLocation)).getAsJsonObject();
        
        // Parse each category
        for(String categoryName : jo.keySet()) {
            JsonArray cat = jo.getAsJsonArray(categoryName);
            
            // Parse each entry
            cat.forEach(e -> {
                JsonObject entry = e.getAsJsonObject();
                setEntry(categoryName,
                         entry.get("name").getAsString(),
                         entry.get("description").getAsString(),
                         entry.get("value").getAsString());
            });
        }
        
        // That's it actually
        return this;
    }
    
    /**
     * Reads a given file
     * 
     * @param fileLocation
     * @return This instance
     * @throws IOException 
     */
    public Config readFile(String fileLocation) throws IOException {
        String oldFileLocation = configFileLocation;
        configFileLocation = fileLocation;
        
        Config c = readFile();
        
        configFileLocation = oldFileLocation;
        return c;
    }
    
    /**
     * Writes the config to the file
     * 
     * @param pretty Whether or not to prettyprint
     * @throws IOException
     */
    public void writeFile(boolean pretty) throws IOException {
        // Parse config entries into json
        JsonObject confObject = new JsonObject();
        
        // Loop over categories
        for(String cat : getCategories()) {
            // Category array
            JsonArray catArray = new JsonArray();
            
            // Loop over entries of the category
            for(String fullName : getEntryNames(cat)) {
                ConfigEntry el = entries.get(fullName);
                JsonObject entryObject = new JsonObject();
                
                // Convert to object
                entryObject.addProperty("name", el.getName());
                entryObject.addProperty("description", el.getDescription());
                entryObject.addProperty("value", el.getValue());
                
                catArray.add(entryObject);
            }
            
            // Add complete category
            confObject.add(cat, catArray);
        }
        
        // Write it to a file, with pretty printing if requested
        Gson gson;
        
        if(pretty) gson = new GsonBuilder().setPrettyPrinting().create();
        else gson = new Gson();
        
        String jsonString = gson.toJson(confObject);
        
        BufferedWriter br = new BufferedWriter(new FileWriter(configFileLocation));
        br.write(jsonString);
        br.close();
    }
    
    /**
     * Writes the config to the given file
     * 
     * @param fileLocation
     * @param pretty Whether or not to prettyprint
     * @throws IOException
     */
    public void writeFile(String fileLocation, boolean pretty) throws IOException {
        String oldFileLocation = configFileLocation;
        configFileLocation = fileLocation;
        
        writeFile(pretty);
        
        configFileLocation = oldFileLocation;
    }
    
    
    
    /*
     * Simple Getters
     */
    public String getFileLocation() { return configFileLocation; }
    
    
    
    /*
     * Simple Setters
     */
    public void setFileLocation(String fl) { configFileLocation = fl; }
    
    
    
    /*
     * Stuff to set/create entries
     */
    
    /**
     * Sets the entry with the given category and name to have the given description and value
     * 
     * @param category
     * @param name
     * @param description
     * @param value
     */
    public void setEntry(String category, String name, String description, String value) {
        if(category.equals("")) setEntry(name, description, value);
        else setEntry(category + ":" + name, description, value);
    }
    
    /**
     * Sets the given entry with the given description and integer value
     * 
     * @param category
     * @param name
     * @param description
     * @param value
     */
    public void setEntry(String category, String name, String description, int value) {
        setEntry(category, name, description, Integer.toString(value));
    }
    
    /**
     * Sets the entry with the given name to have the given description and value
     * 
     * @param name
     * @param description
     * @param value
     */
    public void setEntry(String name, String description, String value) {
        entries.put(name, new ConfigEntry(name, description, value));
    }
    
    /**
     * Sets the given entry with the given description and integer value
     * 
     * @param name
     * @param description
     * @param value
     */
    public void setEntry(String name, String description, int value) {
        setEntry(name, description, Integer.toString(value));
    }
    
    /**
     * Internal method to add an Entry object directly
     * 
     * @param entry
     */
    protected void setEntry(ConfigEntry entry) {
        entries.put(entry.getName(), entry);
    }
    
    /*
     * Stuff to get information from entries
     */
    
    /**
     * Gets the value of the given entry, or null if it isn't found
     * 
     * @param name
     * @return The value of the entry
     */
    public String getValue(String name) {
        ConfigEntry entry = entries.get(name);
        
        if(entry == null) return null;
        else return entry.getValue();
    }
    
    /**
     * Gets the value of the given entry, or null if it isn't found
     * 
     * @param category
     * @param name
     * @return The value of the entry
     */
    public String getValue(String category, String name) {
        return getValue(category + ":" + name);
    }
    
    /**
     * Gets the integer value of the given entry, or 0 if it isn't found
     * 
     * @param name
     * @return The integer value of the entry
     */
    public int getIntValue(String name) {
        String val = getValue(name);
        
        if(val == null) return 0;
        else return Integer.parseInt(val);
    }
    
    /**
     * Gets the integer value of the given entry, or 0 if it isn't found
     * 
     * @param category
     * @param name
     * @return The integer value of the entry
     */
    public int getIntValue(String category, String name) {
        return getIntValue(category + ":" + name);
    }
    
    /**
     * Gets the description of the given entry, or null if it isn't found
     * 
     * @param name
     * @return The description of the entry
     */
    public String getDescription(String name) {
        ConfigEntry entry = entries.get(name);
        
        if(entry == null) return null;
        else return entry.getDescription();
    }
    
    /**
     * Gets the description of the given entry, or null if it isn't found
     * 
     * @param category
     * @param name
     * @return The description of the entry
     */
    public String getDescription(String category, String name) {
        return getDescription(category + ":" + name);
    }
    
    
    
    /*
     * Stuff to get information about the config
     */
    
    /**
     * Gets a set of all entry name in a category
     * 
     * @return The set of entry names in the given category
     */
    public Set<String> getEntryNames(String category) {
        // The streams filter the name sets
        if(category.equals("")) return entries.keySet().stream()
                                                       .filter(s -> !s.contains(":"))
                                                       .collect(Collectors.toSet());
        else return entries.keySet().stream()
                                    .filter(s -> s.startsWith(category + ":"))
                                    .collect(Collectors.toSet());
    }
    
    /**
     * Gets a set of categories in the config
     * 
     * @return The set of categories
     */
    public Set<String> getCategories() {
        HashSet<String> categories = new HashSet<>();
        
        // Get the category from before ':' or none
        for(String fullName : entries.keySet()) {
            if(fullName.contains(":")) categories.add(fullName.substring(0, fullName.indexOf(":")));
            else categories.add("");
        }
        
        return categories;
    }
    
    /**
     * Creates a new Config object with only the specified category
     * <p> The associated file location is changed to prevent lossy overwrites of the original
     * 
     * @param category
     * @return A separate instance of a category
     */
    public Config separateCategory(String category) {
        // Add the category to the file name and preserve the extension
        String newFileLocation = configFileLocation.substring(0, configFileLocation.indexOf('.')) +
                                 "-" + category + configFileLocation.substring(configFileLocation.indexOf('.'));
        
        // New config object
        Config ncgf = new Config(newFileLocation);
        
        // Add the category to the new object
        for(String entryName : getEntryNames(category)) {
            // Remove category from name
            ConfigEntry ce = entries.get(entryName);
            ce.setFullName(entryName.substring(entryName.indexOf(":") + 1));
            
            ncgf.setEntry(ce);
        }
        
        return ncgf;
    }
    
    @Override
    public String toString() {
        String s = "";
        
        for(String n : entries.keySet()) s += String.format("[%s], ", entries.get(n).toString());
        
        return s.substring(0, s.length() - 2);
    }
}


