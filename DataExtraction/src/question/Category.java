package question;

public class Category {
	private String category;
	private String parent;
	private String child;
	
	public Category(){
		this.category = "";
		this.parent = "";
		this.child = "";
	}
	public Category(String cate, String parent, String child){
		this.category = cate;
		this.parent = parent;
		this.child = child;
	}
	// ======================================================================
	/**
	 * Get category
	 * @return category
	 */
	public String getCategory(){
		return category;
	}
	// ======================================================================
	/**
	 * Set category
	 * @param category
	 */
	public void setCategory(String cate){
		this.category = cate;
	}
	// ======================================================================
	/**
	 * Get parent of current category
	 * @return category
	 */
	public String getParent(){
		return this.parent;
	}
	// ======================================================================
	/**
	 * Set parent of current category
	 * @param parent of category
	 */
	public void setParent(String parent){
		this.parent = parent;
	}
	// ======================================================================
	/**
	 * Get child of current category
	 * @return category
	 */
	public String getChild(){
		return this.child;
	}
	// ======================================================================
	/**
	 * Set child of current category
	 * @param child of category
	 */
	public void setChild(String child){
		this.child = child;
	}
	
	public String toString(){
		return getCategory();
	}
}
