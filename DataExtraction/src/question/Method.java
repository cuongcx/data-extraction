package question;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Method {
	private ArrayList<Part> method = new ArrayList<>();
	private int order;
	private String title;
	
	
	public Method(int order, String title, ArrayList<Part> method){
		this.method = method;
		this.order = order;
		this.title = title;
	}
	// ======================================================================
	/**
	 * Set order for method
	 * @param order
	 */	
	public void setOrder(int order){
		this.order = order;
	}
	// ======================================================================
	/**
	 * Get order of method
	 * @return order
	 */	
	public int getOrder(){
		return this.order;
	}
	// ======================================================================
	/**
	 * Set title for method
	 * @param title
	 */	
	public void setTitle(String title){
		this.title = title;
	}
	// ======================================================================
	/**
	 * Get title of method
	 * @return title
	 */	
	public String getTitle(){
		return this.title;
	}
	// ======================================================================
	/**
	 * Set list of parts for method
	 * @param list of parts
	 */	
	public void setMethod(ArrayList<Part> listPart){
		this.method = listPart;
	}
	// ======================================================================
	/**
	 * Get list of parts of method
	 * @return list of parts
	 */	
	public ArrayList<Part> getMethod(){
		return this.method;
	}
	// ======================================================================
	/**
	 * Return a string that converted from a method
	 * @return String
	 */
	public String answertoString(){
		String ans = "";
		ans += "Method " + getOrder() + ": " + getTitle() + "\n";
		for (int j=0; j<getMethod().size(); j++){
			ans += "Part " + getMethod().get(j).getOrder() + 
					": " + getMethod().get(j).getTitle() + "\n";
			for (int k=0; k<getMethod().get(j).getPart().size(); k++){
				ans += "Step " + getMethod().get(j).getPart().get(k).getOrder() + ": " +
						getMethod().get(j).getPart().get(k).getMain_Act() + "\n" +
						"\t" + getMethod().get(j).getPart().get(k).getDetail_Act() + "\n"+
						"\tImage: " + getMethod().get(j).getPart().get(k).getImage() +"\n";
			}
		}
		
		return ans;
	}
	// ======================================================================
	/**
	 * Return a JSON array that converted from a list of part
	 * @return JSONArray
	 */
	@SuppressWarnings("unchecked")
	public JSONArray listParttoJson(){
		JSONArray listPart = new JSONArray();
		if (getMethod().size() > 0){
			for (int i=0; i<getMethod().size(); i++){
				listPart.add(getMethod().get(i).toJson());
			}
		}
		return listPart;
	}
	// ======================================================================
	/**
	 * Return a JSON object that converted from a method
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJson(){
		JSONObject methodJ = new JSONObject();
		methodJ.put("Order", getOrder());
		methodJ.put("Title", getTitle());
		methodJ.put("Method", listParttoJson());
		return methodJ;
	}
}
