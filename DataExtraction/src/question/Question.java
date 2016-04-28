package question;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Question {
	private String title;
	private String ques_explanation;
	private ArrayList<Method> answer = new ArrayList<>();
	private ArrayList<Category> category = new ArrayList<>();
	private String link;
	private String tips;
	private String warning;
	private String video;
	private ArrayList<Things> things = new ArrayList<>();
	private ArrayList<Ingredients> ingredients = new ArrayList<>();
	
	
	public Question(String title, String ques_explanation, 
			ArrayList<Method> answer, ArrayList<Category> cate,
			String link, String tips, String warning, String video,
			ArrayList<Things> things, ArrayList<Ingredients> ingre){
		this.title = title;
		this.answer = answer;
		this.category = cate;
		this.ques_explanation = ques_explanation;
		this.link = link;
		this.tips = tips;
		this.warning = warning;
		this.video = video;
		this.things = things;
		this.ingredients = ingre;
	}
	// ======================================================================
	/**
	 * Get title of question
	 * @return title
	 */	
	public String getTitle(){
		return title;
	}
	// ======================================================================
	/**
	 * Set title of question
	 * @param title
	 */	
	public void setTitle(String title){
		this.title = title;
	}
	// ======================================================================
	/**
	 * Get explanation of question
	 * @return explanation
	 */	
	public String getQues_Explanation(){
		return ques_explanation;
	}
	// ======================================================================
	/**
	 * Set explanation of question
	 * @param explanation
	 */	
	public void setQues_Explanation(String expl){
		this.ques_explanation = expl;
	}
	// ======================================================================
	/**
	 * Get link of question
	 * @return link
	 */	
	public String getLink(){
		return link;
	}
	// ======================================================================
	/**
	 * Set link of question
	 * @param link
	 */	
	public void setLink(String link){
		this.link = link;
	}
	// ======================================================================
	/**
	 * Get tips of question
	 * @return tips
	 */	
	public String getTips(){
		return tips;
	}
	// ======================================================================
	/**
	 * Set tips of question
	 * @param tips
	 */	
	public void setTips(String tip){
		this.tips = tip;
	}
	// ======================================================================
	/**
	 * Get warnings of question
	 * @return warnings
	 */	
	public String getWarning(){
		return warning;
	}
	// ======================================================================
	/**
	 * Set warnings of question
	 * @param warnings
	 */	
	public void setWarning(String warn){
		this.warning = warn;
	}
	// ======================================================================
	/**
	 * Get video of question
	 * @return link of video
	 */		
	public String getVideo(){
		return video;
	}
	// ======================================================================
	/**
	 * Set video of question
	 * @param link of video
	 */	
	public void setVideo(String video){
		this.video = video;
	}
	// ======================================================================
	/**
	 * Get list of things of question
	 * @return list of things you will need
	 */	
	public ArrayList<Things> getThings(){
		return things;
	}
	// ======================================================================
	/**
	 * Set list of things you need of question
	 * @param list of things
	 */	
	public void setThings(ArrayList<Things> things){
		this.things = things;
	}
	// ======================================================================
	/**
	 * Get list of ingredients you will need
	 * @return list of ingredients
	 */	
	public ArrayList<Ingredients> getIngredients(){
		return ingredients;
	}
	// ======================================================================
	/**
	 * Set list of ingredients of question
	 * @param list of ingredients
	 */	
	public void setIngredients(ArrayList<Ingredients> things){
		this.ingredients = things;
	}
	// ======================================================================
	/**
	 * Get answer of question
	 * @return list of methods
	 */	
	public ArrayList<Method> getAnswer(){
		return answer;
	}
	// ======================================================================
	/**
	 * Set answer of question
	 * @param answer
	 */	
	public void setAnswer(ArrayList<Method> ans){
		this.answer = ans;
	}
	// ======================================================================
	/**
	 * Get category of question
	 * @return category
	 */		
	public String getCategory(){
		return category.get(0).getCategory();
	}
	// ======================================================================
	/**
	 * Get list of categories of question
	 * @return list of categories
	 */	
	public ArrayList<Category> getCategoryAll(){
		return category;
	}
	// ======================================================================
	/**
	 * Get parent of category of question
	 * @return parent of category
	 */	
	public String getParentOfCategory(){
		return category.get(0).getParent();
	}
	// ======================================================================
	/**
	 * Get child of category of question
	 * @return child of category
	 */	
	public String getChildOfCategory(){
		return category.get(0).getChild();
	}
	// ======================================================================
	/**
	 * Set category of question
	 * @param list of category
	 */	
	public void setCategory(ArrayList<Category> cate){
		this.category = cate;
	}
	// ======================================================================
	/**
	 * Return a String that convert from the answer
	 * @return String
	 */
	public String answertoString(){
		String ans = "Answers:\n";
		for (int i=0; i<getAnswer().size(); i++){
			ans += "\t\tMethod " + getAnswer().get(i).getOrder() + ": " + getAnswer().get(i).getTitle() + "\n";
			for (int j=0; j<getAnswer().get(i).getMethod().size(); j++){
				ans += "\t\tPart " + getAnswer().get(i).getMethod().get(j).getOrder() + 
						": " + getAnswer().get(i).getMethod().get(j).getTitle() + "\n";
				for (int k=0; k<getAnswer().get(i).getMethod().get(j).getPart().size(); k++){
					ans += "\t\t\tStep " + getAnswer().get(i).getMethod().get(j).getPart().get(k).getOrder() + ": " +
							getAnswer().get(i).getMethod().get(j).getPart().get(k).getMain_Act() + "\n" +
							"\t\t\t\t" + getAnswer().get(i).getMethod().get(j).getPart().get(k).getDetail_Act() + "\n"+
							"\t\t\t\tImage: " + getAnswer().get(i).getMethod().get(j).getPart().get(k).getImage() +"\n";
				}
			}
		}
		
		return ans;
	}
	// ======================================================================
	/**
	 * Return a String that convert from list of categories
	 * @return String
	 */
	public String categorytoString(){
		String cate = "";
		if (getCategoryAll().size() > 0){
			for (int i=getCategoryAll().size() - 1; i>=0; i--){
				if (i==0){
					cate += getCategoryAll().get(i).getCategory();
				}
				else {
					cate += getCategoryAll().get(i).getCategory() + " >> ";
				}
			}
		}
		return cate;
	}
	// ======================================================================
	/**
	 * Return a String that convert from list of things
	 * @return String
	 */
	public String thingstoString(){
		String thingtostring = "";
		if (getThings().size() > 0){
			for (int k=0; k<getThings().size(); k++){
				thingtostring += getThings().get(k).toString();
			}
		}	
		return thingtostring;
	}
	// ======================================================================
	/**
	 * Return a String that convert from list of ingredients
	 * @return String
	 */
	public String ingredientstoString(){
		String ingredientstostring = "";
		if (getIngredients().size() > 0){
			for (int k=0; k<getIngredients().size(); k++){
				ingredientstostring += getIngredients().get(k).toString();
			}
		}	
		return ingredientstostring;
	}
	// ======================================================================
	/**
	 * Return a String that convert from a question
	 * @return String
	 */
	public String toString(){
		String question = "";
		question += getLink() + "\n";
		question += "\tTitle: " + getTitle() + "\n";
		question += "\tCategory: " + categorytoString() + "\n";
		question += "\tExplanation: " + getQues_Explanation() + "\n";
		question += "\t" + answertoString();
		question += "\tTips: " + getTips() + "\n";
		question += "\tWarnings: " + getWarning() + "\n";
		question += "\tVideo: " + getVideo() + "\n";
		question += "\tThings Youll Need: " + thingstoString() + "\n";
		question += "\tIngredients: " + ingredientstoString() + "\n";
		return question;
	}
	// ======================================================================
	/**
	 * Return Json array object that convert from list of categories
	 * @return  JSONArray
	 */
	@SuppressWarnings("unchecked")
	public JSONArray categorytoJsonArray(){
		JSONArray category = new JSONArray();
		if (getCategoryAll().size() > 0){
			for (int i=0; i<getCategoryAll().size(); i++){
				category.add(getCategoryAll().get(i).getCategory());
			}
		}
		return category;
	}
	// ======================================================================
	/**
	 * Return Json array object that convert from list of things
	 * @return JSONArray
	 */
	@SuppressWarnings("unchecked")
	public JSONArray thingstoJsonArray(){
		JSONArray things = new JSONArray();
		if (getThings().size() > 0){
			for (int k=0; k<getThings().size(); k++){
				JSONObject newthing = new JSONObject();
				newthing.put("Title", getThings().get(k).getName());
				newthing.put("Things", getThings().get(k).getThingsJSONArray());
				things.add(newthing);
			}
		}	
		return things;
	}
	// ======================================================================
	/**
	 * Return Json array object that convert from list of ingredients
	 * @return JSONArray
	 */
	@SuppressWarnings("unchecked")
	public JSONArray ingredientstoJsonArray(){
		JSONArray ingredient = new JSONArray();
		if (getIngredients().size() > 0){
			for (int k=0; k<getIngredients().size(); k++){
				JSONObject newingre = new JSONObject();
				newingre.put("Title", getIngredients().get(k).getName());
				newingre.put("Ingredients", getIngredients().get(k).getIngredientsJSONArray());
				ingredient.add(newingre);
			}
		}	
		return ingredient;
	}
	// ======================================================================
	/**
	 * Return Json array object that convert from list of methods
	 * @return JSONArray
	 */
	@SuppressWarnings("unchecked")
	public JSONArray answertoJson(){
		JSONArray listMethod = new JSONArray();
		if (getAnswer().size() > 0){
			for (int i=0; i<getAnswer().size(); i++){
				listMethod.add(getAnswer().get(i).toJson());
			}
		}
		return listMethod;
	}
	// ======================================================================
	/**
	 * Return Json object that convert from a question
	 * @return JSONObject
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJson(){
		JSONObject obj = new JSONObject();
		obj.put("Link", getLink());
		obj.put("Title", getTitle());
		obj.put("Category", categorytoJsonArray());
		obj.put("Explanation", getQues_Explanation());
		obj.put("Answer", answertoJson());
		obj.put("Tips", getTips());
		obj.put("Warnings", getWarning());
		obj.put("Video", getVideo());
		obj.put("Things", thingstoJsonArray());
		obj.put("Ingredients", ingredientstoJsonArray());
		return obj;
	}
	
}	
