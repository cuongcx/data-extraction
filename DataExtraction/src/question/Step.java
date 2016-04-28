package question;

import org.json.simple.JSONObject;

public class Step {
	
	private int order;
	private String main_act;
	private String detail_act;
	private String image;
	
	public Step(int order, String main, String detail, String image){
		this.order = order;
		this.main_act = main;
		this.detail_act = detail;
		this.image = image;
	}
	// ======================================================================
	/**
	 * Set order for step
	 * @param order
	 */	
	public void setOrder(int order){
		this.order = order;
	}
	// ======================================================================
	/**
	 * Get order of step
	 * @return order
	 */	
	public int getOrder(){
		return this.order;
	}
	// ======================================================================
	/**
	 * Set main action for step
	 * @param main action
	 */	
	public void setMain_Act(String main_act){
		this.main_act = main_act;
	}
	// ======================================================================
	/**
	 * Get main action of step
	 * @return main action
	 */	
	public String getMain_Act(){
		return this.main_act;
	}
	// ======================================================================
	/**
	 * Set detailed action for step
	 * @param detailed action
	 */		
	public void setDetail_Act(String detail){
		this.detail_act = detail;
	}
	// ======================================================================
	/**
	 * Get detailed action of step
	 * @return detail action
	 */	
	public String getDetail_Act(){
		return this.detail_act;
	}
	// ======================================================================
	/**
	 * Set image for step
	 * @param image
	 */		
	public void setImage(String image){
		this.image = image;
	}
	// ======================================================================
	/**
	 * Get image of step
	 * @return link of image
	 */	
	public String getImage(){
		return this.image;
	}
	// ======================================================================
	/**
	 * Return a string that converted from a step
	 * @return String
	 */
	@Override
	public String toString(){
		return Integer.toString(order) + ": " + main_act + ": " +
						detail_act + ": " + image;
	}
	
	// ======================================================================
	/**
	 * Return a JSON object that converted from a step
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJson(){
		JSONObject stepJ = new JSONObject();
		stepJ.put("Order", getOrder());
		stepJ.put("Main_act", getMain_Act());
		stepJ.put("Detail_act", getDetail_Act());
		stepJ.put("Image", getImage());
		return stepJ;
	}
}
