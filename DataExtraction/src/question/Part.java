package question;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Part {
	private ArrayList<Step> part = new ArrayList<>();
	private int order;
	private String title;
	
	
	public Part(int order, String title, ArrayList<Step> part){
		this.order = order;
		this.part = part;
		this.title = title;
	}
	
	// ======================================================================
	/**
	 * Set order for part
	 * @param order
	 */	
	public void setOrder(int order){
		this.order = order;
	}
	// ======================================================================
	/**
	 * Get order of part
	 * @return order
	 */	
	public int getOrder(){
		return this.order;
	}
	// ======================================================================
	/**
	 * Set title for part
	 * @param title
	 *
	 */	
	public void setTitle(String title){
		this.title = title;
	}
	// ======================================================================
	/**
	 * Get title of part
	 * @return title
	 */	
	public String getTitle(){
		return this.title;
	}
	// ======================================================================
	/**
	 * Set list of steps for part
	 * @param list of steps
	 */	
	public void setPart(ArrayList<Step> listStep){
		this.part = listStep;
	}
	// ======================================================================
	/**
	 * Get list of steps of part
	 * @return list of steps
	 */	
	public ArrayList<Step> getPart(){
		return this.part;
	}
	// ======================================================================
	/**
	 * Return a JSON array that converted from a list of steps
	 * @return JSONArray
	 */
	@SuppressWarnings("unchecked")
	public JSONArray listSteptoJson(){
		JSONArray listStep = new JSONArray();
		if (getPart().size() > 0){
			for (int i=0; i<getPart().size(); i++){
				listStep.add(getPart().get(i).toJson());
			}
		}
		return listStep;
	}
	// ======================================================================
	/**
	 * Return a JSON object that converted from a Part
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJson(){
		JSONObject partJ = new JSONObject();
		partJ.put("Order", getOrder());
		partJ.put("Title", getTitle());
		partJ.put("Part", listSteptoJson());
		return partJ;
	}
}
