package question;

import global.Global;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;

public class Ingredients {
	private String name_method;
	private ArrayList<String> ingredients = new ArrayList<String>();
	Pattern pattern = Pattern.compile("[a-zA-Z]");
	
	public Ingredients(String name, ArrayList<String> thing){
		this.name_method = name;
		this.ingredients = thing;
	}
	// ======================================================================
	/**
	 * Get name of method/part
	 * @return name of method
	 */
	public String getName(){
		if (!pattern.matcher(name_method).find()){
			return Global.GENERAL_INGREDIENT;
		}
		return name_method;
	}
	// ======================================================================
	/**
	 * Get list of ingredients
	 * @return list of ingredients
	 */
	public ArrayList<String> getIngredients(){
		return ingredients;
	}
	
	@Override
	public String toString(){
		String s = getName() + "\n";
		for (int i=0; i<getIngredients().size(); i++){
			s += getIngredients().get(i) + "\n";
		}
		return s;
	}
	// ======================================================================
	/**
	 * Return a json array that converted from list of ingredients
	 * @return a json array
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getIngredientsJSONArray(){
		JSONArray ingre = new JSONArray();
		if (getIngredients().size() > 0){
			for (int k=0; k<getIngredients().size(); k++){
				ingre.add(getIngredients().get(k));
			}
		}	
		return ingre;
	}
}
