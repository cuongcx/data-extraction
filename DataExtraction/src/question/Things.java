package question;

import global.Global;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;

public class Things {
	private String name_method;
	private ArrayList<String> things = new ArrayList<String>();
	Pattern pattern = Pattern.compile("[a-zA-Z]");
	public Things(String name, ArrayList<String> thing){
		this.name_method = name;
		this.things = thing;
	}
	// ======================================================================
	/**
	 * Get name of method/part
	 * @return name of method
	 */
	public String getName(){
		if (!pattern.matcher(name_method).find()){
			return Global.GENERAL_THING;
		}
		return name_method;
	}
	// ======================================================================
	/**
	 * Get list of things you will need
	 * @return list of things
	 */	
	public ArrayList<String> getThings(){
		return things;
	}
	@Override
	public String toString(){
		String s = getName() + "\n";
		for (int i=0; i<getThings().size(); i++){
			s += getThings().get(i) + "\n";
		}
		return s;
	}
	// ======================================================================
	/**
	 * Return a json array that converted from list of things
	 * @return JSONArray
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getThingsJSONArray(){
		JSONArray things = new JSONArray();
		if (getThings().size() > 0){
			for (int k=0; k<getThings().size(); k++){
				things.add(getThings().get(k));
			}
		}	
		return things;
	}
}
