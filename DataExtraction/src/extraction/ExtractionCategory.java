package extraction;

import global.Global;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;

import question.Tree;
import question.Tree.Node;
import tool.TParser;

public class ExtractionCategory {
	static Pattern pattern = Pattern.compile("[a-zA-Z]");
	
	// ======================================================================
	/**
	 * Return tree of category
	 * 
	 * @return a tree
	 */
	@SuppressWarnings("rawtypes")
	public Tree categoryExtraction(){
		//Initialization of a category tree with root is general category
	    Tree<String> category = new Tree<String>();
		try {
		      URL url = new URL("http://www.wikihow.com/index.php?title=wikiHow:Categories&action=edit");
		      InputStream inputStream = url.openStream();
		      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		      String line = bufferedReader.readLine();
		      String content = "";
		      while (line != null) {
		        //System.out.println(line);
		        content += line + "\n";
		        line = bufferedReader.readLine();
		      }
		      bufferedReader.close();
		      
		      String category_tem = TParser.getContent(content, "name=\"wpTextbox1\">", "\\[\\[Category:Categorization");
		      category_tem = "\n\n" + category_tem;
		      //System.out.println(category);
		      String[]	categories = category_tem.split("\n\n");
		      //System.out.println("Categories:\n\n");
		      
		      Node<String> root = new Node<String>(Global.GENERAL_CATEGORY);
		      category.setRoot(root);
		      //category.printTree();
		      //List of child node of root
		      ArrayList<Node<String>> list_child_root = new ArrayList<>();
		      for (int i=0; i<categories.length; i++){
		    	  if (pattern.matcher(categories[i]).find()){
		    		  //System.out.println(categories[i]);
		    		  //System.out.println("==========================================");
		    		  
		    		  String [] sub_cate = categories[i].split("\n");
		    		  for (int j=0; j<sub_cate.length; j++){
		    			  if (pattern.matcher(sub_cate[j]).find()){
		    				  sub_cate[j] = sub_cate[j].trim();
		    				  if (sub_cate[j].contains("*******")){
		    					  sub_cate[j] = sub_cate[j].replace("*******", "<7>");
		    				  }else if (sub_cate[j].contains("******")){
		    					  sub_cate[j] = sub_cate[j].replace("******", "<6>");
		    				  }else if (sub_cate[j].contains("*****")){
		    					  sub_cate[j] = sub_cate[j].replace("*****", "<5>");
		    				  }else if (sub_cate[j].contains("****")){
		    					  sub_cate[j] = sub_cate[j].replace("****", "<4>");
		    				  }else if (sub_cate[j].contains("***")){
		    					  sub_cate[j] = sub_cate[j].replace("***", "<3>");
		    				  }else if (sub_cate[j].contains("**")){
		    					  sub_cate[j] = sub_cate[j].replace("**", "<2>");
		    				  }else sub_cate[j] = sub_cate[j].replace("*", "<1>");
		    			  }
		    		  }
		    		  String temp_cate = "";
		    		  for (int j=0; j<sub_cate.length; j++){
		    			  if (pattern.matcher(sub_cate[j]).find()){
		    				  temp_cate += sub_cate[j];
		    			  }
		    		  }
		    		  temp_cate += "<2>";
		    		  //System.out.println(temp_cate);
		    		  String childofroot_value = TParser.getContent(temp_cate, "<1>", "<2>");
		    		  //New child of root
		    		  Node<String> childofroot = new Node<>();
		    		  childofroot.setData(childofroot_value);//Set data for child of root
		    		  childofroot.setParent(category.getRoot());//Set parent for child of root is root
		    		  //Need set children for child of root
		    		  ArrayList<String> second_level = TParser.getContentList(temp_cate, "<2>", "<2>");
		    		  //Initialize children of child of root
		    		  ArrayList<Node<String>> list_second_child = new ArrayList<>();
		    		  if (second_level.size() > 0){
		    			  for (int j=0; j<second_level.size(); j++){
		    				  String second_child_value = "";
		    				  if (second_level.get(j).contains("<3>"))
		    					  second_child_value = second_level.get(j).split("<3>")[0];
		    				  else second_child_value = second_level.get(j);
		    				  //New second child
		    				  Node<String> secondchild = new Node<>();
		    				  secondchild.setData(second_child_value);
		    				  secondchild.setParent(childofroot);
		    				  //Need set children for second_child
		    				  //Initialize children of child of root
				    		  ArrayList<Node<String>> list_third_child = new ArrayList<>();
				    		  if (second_level.get(j).contains("<3>")){
				    			  String[] list_third_child_value = second_level.get(j).split("<3>");
				    			  for (int k=1; k<list_third_child_value.length; k++){
				    				  String third_child_value = "";
				    				  if (list_third_child_value[k].contains("<4>"))
				    					  third_child_value = list_third_child_value[k].split("<4>")[0];
				    				  else third_child_value = list_third_child_value[k];
				    				  //New third child
				    				  Node<String> thirdchild = new Node<>();
				    				  thirdchild.setData(third_child_value);
				    				  thirdchild.setParent(secondchild);
				    				  //Need set children for third child
				    				  //Initialize children of third child
						    		  ArrayList<Node<String>> list_fourth_child = new ArrayList<>();
						    		  if (list_third_child_value[k].contains("<4>")){
						    			  String[] list_fourth_child_value = list_third_child_value[k].split("<4>");
						    			  for (int l=1; l<list_fourth_child_value.length; l++){
						    				  String fourth_child_value = "";
						    				  if (list_fourth_child_value[l].contains("<5>"))
						    					  fourth_child_value = list_fourth_child_value[l].split("<5>")[0];
						    				  else fourth_child_value = list_fourth_child_value[l];
						    				  //New fourth child
						    				  Node<String> fourthchild = new Node<>();
						    				  fourthchild.setData(fourth_child_value);
						    				  fourthchild.setParent(thirdchild);
						    				  //Need set children for fourth child
						    				  //Initialize children of fourth child
								    		  ArrayList<Node<String>> list_fifth_child = new ArrayList<>();
								    		  if (list_fourth_child_value[l].contains("<5>")){
								    			  String[] list_fifth_child_value = list_fourth_child_value[l].split("<5>");
								    			  for (int m=1; m<list_fifth_child_value.length; m++){
								    				  String fifth_child_value = "";
								    				  if (list_fifth_child_value[m].contains("<6>"))
								    					  fifth_child_value = list_fifth_child_value[m].split("<6>")[0];
								    				  else fifth_child_value = list_fifth_child_value[m];
								    				  //New fifth child
								    				  Node<String> fifthchild = new Node<>();
								    				  fifthchild.setData(fifth_child_value);
								    				  fifthchild.setParent(fourthchild);
								    				  //Need set children for fifth child
								    				  //Initialize children of fifth child
										    		  ArrayList<Node<String>> list_sixth_child = new ArrayList<>();
										    		  if (list_fifth_child_value[m].contains("<6>")){
										    			  String[] list_sixth_child_value = list_fifth_child_value[m].split("<6>");
										    			  for (int n=1; n<list_sixth_child_value.length; n++){
										    				  String sixth_child_value = "";
										    				  if (list_sixth_child_value[n].contains("<7>"))
										    					  sixth_child_value = list_sixth_child_value[n].split("<7>")[0];
										    				  else sixth_child_value = list_sixth_child_value[n];
										    				  //New sixth child
										    				  Node<String> sixthchild = new Node<>();
										    				  sixthchild.setData(sixth_child_value);
										    				  sixthchild.setParent(fifthchild);
										    				  //Need set children for sixth child
										    				  //Initialize children of sixth child
												    		  ArrayList<Node<String>> list_seventh_child = new ArrayList<>();
												    		  if (list_sixth_child_value[n].contains("<7>")){
												    			  String[] list_seventh_child_value = list_sixth_child_value[n].split("<7>");
												    			  for (int o=1; o<list_seventh_child_value.length; o++){
												    				  String seventh_child_value = "";
												    				  if (list_seventh_child_value[o].contains("<8>"))
												    					  seventh_child_value = list_seventh_child_value[o].split("<8>")[0];
												    				  else seventh_child_value = list_seventh_child_value[o];
												    				  //New seventh child
												    				  Node<String> seventhchild = new Node<>();
												    				  seventhchild.setData(seventh_child_value);
												    				  seventhchild.setParent(sixthchild);
												    				  //Need set children for fifth child
												    				  //Initialize children of sixth child
														    		  ArrayList<Node<String>> list_eighth_child = new ArrayList<>();
														    		  seventhchild.setChildren(list_eighth_child);
														    		  
														    		  //Add a seventh child node to the list of seventh children
														    		  list_seventh_child.add(seventhchild);
												    			  }
												    		  }
												    		  sixthchild.setChildren(list_seventh_child);
												    		  list_sixth_child.add(sixthchild);
										    			  }
										    		  }
										    		  fifthchild.setChildren(list_sixth_child);
										    		  list_fifth_child.add(fifthchild);
								    			  }
								    		  }
								    		  fourthchild.setChildren(list_fifth_child);
								    		  list_fourth_child.add(fourthchild);
						    			  }
						    		  }
						    		  thirdchild.setChildren(list_fourth_child);
						    		  list_third_child.add(thirdchild);
				    			  }
				    		  }
				    		  secondchild.setChildren(list_third_child);
				    		  list_second_child.add(secondchild);
		    			  }
		    		  }
		    		  childofroot.setChildren(list_second_child);
		    		  list_child_root.add(childofroot);
		    		  //Add a new node: Articles in quality review
		    		  Node<String> specialNode = new Node<>();
		    		  specialNode.setData("Articles in Quality Review");
		    		  specialNode.setParent(category.getRoot());
		    		  list_child_root.add(specialNode);
		    	  }
		    	  
		      }
		      root.setChildren(list_child_root);

		      return category;
		    } catch (MalformedURLException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		return category;
	}
}
