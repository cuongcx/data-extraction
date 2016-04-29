package question;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
    private Node<T> root;

    public Tree(){
    	root = new Node<T>();
    }
    public Tree(T rootData) {
        root = new Node<T>();
        root.setData(rootData);
        root.setChildren(new ArrayList<Node<T>>());
    }
    // ======================================================================
 	/**
 	 * Get root of tree
 	 * @return root of tree
 	 */
    public Node<T> getRoot(){
    	return root;
    }
    // ======================================================================
 	/**
 	 * Set root of tree
 	 * @param root node
 	 */
    public void setRoot(Node<T> root){
    	this.root = root;
    }

    // ======================================================================
 	/**
 	 * Check a node whether exists in the tree
 	 * @return true/false
 	 */
    public boolean isExist(T node_data){
    	return getRoot().isExist(node_data);
    }
    // ======================================================================
 	/**
 	 * Get a node, given the data of this node
 	 * @param the data of node
 	 * @return a node/null
 	 */
    public Node<T> returnNode(T node_data){
    	return getRoot().returnNode(node_data);
    }
    // ======================================================================
 	/**
 	 * Return height of tree
 	 * @return height of tree
 	 * 
 	 */   		
    public int getHeight(){
    	return getRoot().getHeigth();
    }
    // ======================================================================
  	/**
  	 * Return size of tree
  	 * @return size of tree
  	 * 
  	 */   		
     public int getSize(){
     	return getRoot().getSize();
     }
    // ======================================================================
 	/**
 	 * Get the path from a node to root
 	 * @param the data of node
 	 * @return list of node from this node to root
 	 */
    public ArrayList<Node<T>> getRootPath(T node_data){
    	ArrayList<Node<T>> rootpath = new ArrayList<>();
    	if (isExist(node_data)){
    		Node<T> currentNode = returnNode(node_data);
    		rootpath.add(currentNode);
    		 
    		Node<T> parentNode = currentNode.getParent();
    		
    		while (parentNode != null){
    			rootpath.add(parentNode);
    			currentNode = parentNode.getParent();
    			parentNode = currentNode;
    		}
    	}
    	return rootpath;
    }
    // ======================================================================
 	/**
 	 * Get String of root path
 	 * @param the data of node
 	 * @return string of root path
 	 */
    public String inheritedHypernymy(T node_data){
    	String rootpathtoString = "";
    	ArrayList<Node<T>> rootpath = getRootPath(node_data);
    	if (rootpath.size() == 0){
    		rootpathtoString = "There is no path to root. The node doesn't exist!";
    	}else{
    		for (int i=0; i<rootpath.size()-1; i++){
    			rootpathtoString += rootpath.get(i).getData() + "<==";
    		}
    		rootpathtoString += getRoot().getData();
    	}
    	return rootpathtoString;
    }
    // ======================================================================
 	/**
 	 * print tree
 	 * 
 	 */
    public void printTree(){
    	getRoot().printNode();
    }
    // ======================================================================
 	/**
 	 * print rootpath of all category
 	 * 
 	 */
    @SuppressWarnings("unchecked")
	public String getRoothPathofCategories(Node<String> node){
		String res = "";
		if (!node.getData().contains("http://www.wikihow.com/")){
			res = res + node.getData() + ":" + inheritedHypernymy((T) node.getData()) + "\n";
			if (node.getChildren().size() > 0){
				for (Node<String> child: node.getChildren())
					res += getRoothPathofCategories(child);
			}
		}
		return res;
	}
    
    // ======================================================================
 	/**
 	 * print the subtree which is rooted at a node
 	 * @param the data of node
 	 * 
 	 */
    public void printNodeofTree(T node_data){
    	Node<T> node = returnNode(node_data);
    	if (node == null)
    		System.err.println("Category doesn't exists!");
    	else
    		returnNode(node_data).printNode();
    }
    //====================================================================
    //Class Node
    
    public static class Node<T> {
        private T data;
        private Node<T> parent;
        private List<Node<T>> children;
        
        public Node(){
        	data = null;
        	parent = null;
        	children = new ArrayList<Node<T>>();
        }
        public Node(T data){
        	this.data = data;
        	parent = null;
        	children = new ArrayList<Node<T>>();
        }
        // ======================================================================
     	/**
     	 * Get data of node
     	 * @return data of node
     	 */
        public T getData() {
            return data;
        }
        // ======================================================================
     	/**
     	 * Set data of node
     	 * @param data
     	 * 
     	 */
        public void setData(T data) {
            this.data = data;
        }
        // ======================================================================
     	/**
     	 * Get parent of node
     	 * @return parent of node
     	 */
        public Node<T> getParent() {
            return parent;
        }
        // ======================================================================
     	/**
     	 * Set parent of node
     	 * @param parent node
     	 * 
     	 */
        public void setParent(Node<T> parent) {
            this.parent = parent;
        }
        // ======================================================================
     	/**
     	 * Get children of node
     	 * @return list of children node
     	 */
        public List<Node<T>> getChildren() {
            return children;
        }
        // ======================================================================
     	/**
     	 * Set children of node
     	 * @param list of node
     	 * 
     	 */
        public void setChildren(List<Node<T>> children) {
            this.children = children;
            
        }
        // ======================================================================
     	/**
     	 * Add a child of node
     	 * @param a node
     	 * 
     	 */
        public void addChild(Node<T> newchild) {
        	if(newchild!=null)
        		this.children.add(newchild);
        	
        }
        // ======================================================================
     	/**
     	 * Add a list of children of node
     	 * @param list of children
     	 * 
     	 */
        public void addChildren(List<Node<T>> children) {
        	for(Node<T> child: children) addChild(child);
        }
        // ======================================================================
     	/**
     	 * Remove a child of a node
     	 * @param a node
     	 * 
     	 */
        public void removeChild(T child_data) {
            Node<T> node = returnNode(child_data);
            if (node != null){
            	this.children.remove(node);
            }
        }
        // ======================================================================
     	/**
     	 * Remove a list of children of node
     	 * @param list of children
     	 * 
     	 */
        public void removeChildren(List<T> children) {
        	for (int i=0; i<children.size(); i++){
        		removeChild(children.get(i));
        	}
        }
        // ======================================================================
     	/**
     	 * Check whether a node exists in a subtree
     	 * @param the data of node
     	 * @return true/false
     	 */
        public boolean isExist(T node_data){
        	if (getData().equals(node_data))
        		return true;
        	ArrayList<Node<T>> children = (ArrayList<Node<T>>) getChildren(); 
            boolean res = false;
            for (int i = 0; res == false && i < children.size(); i++) {         
                res = children.get(i).isExist(node_data);
            }
            return res;
        }
        // ======================================================================
     	/**
     	 * Get a node given the data of this node
     	 * @param the data of the node
     	 * @return the node
     	 */
        public Node<T> returnNode(T node_data){
        	if (getData().equals(node_data))
        		return this;
        	ArrayList<Node<T>> children = (ArrayList<Node<T>>) getChildren(); 
            Node<T> res = null;
            for (int i = 0; res == null && i < children.size(); i++) {         
                res = children.get(i).returnNode(node_data);
            }
            return res;
        }
     // ======================================================================
      	/**
      	 * Get size of subtree
      	 * 
      	 * @return size of subtree
      	 */
        public int getSize(){
        	int h = 0;
        	if (getChildren().size() > 0){
        		for (int i=0; i<getChildren().size(); i++){
        			h = h + getChildren().get(i).getSize();
        		}
        	}
        	return h + 1;
        }
        // ======================================================================
      	/**
      	 * Get height of node
      	 * 
      	 * @return height of node
      	 */
        public int getHeigth(){
        	int h = 0;
        	if (getChildren().size() > 0){
        		for (int i=0; i<getChildren().size(); i++){
        			h = Math.max (h, getChildren().get(i).getHeigth());
        		}
        	}
        	return h + 1;
        }
        // ======================================================================
     	/**
     	 * Print the subtree that has the root is a specific node
     	 * 
     	 */
        public void printNode(){
        	//String subtree = "";
        	System.out.println(getData());
        	if (getChildren().size() > 0)
	        	for (int i=0; i<getChildren().size(); i++){
	        		getChildren().get(i).printNode();
	        	}
        	//return subtree;
        }
        
    }
}