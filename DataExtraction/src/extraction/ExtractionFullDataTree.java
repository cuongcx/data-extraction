package extraction;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import question.Category;
import question.Question;
import question.Tree;
import question.Tree.Node;

public class ExtractionFullDataTree {
	// ======================================================================
	/**
	 * Return tree of data that has leaves are articles and inner nodes are categories
	 * @param directory that store data in json format
	 * @return a tree
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Tree extractFullDataTree(String directory) throws ClassNotFoundException, IOException, ParseException{
		
	    ExtractionQuestion extractQues = new ExtractionQuestion();
		//Extract all question
		ArrayList<Question> allQuestions = extractQues.extractQuestionFromJSONFile(directory);
		ExtractionCategory cate_extraction = new ExtractionCategory();
		//Extract tree of category
		Tree<String> category = cate_extraction.categoryExtraction();
		//category.printTree();
		//Match all articles to the category tree
		for (int i=0; i<allQuestions.size(); i++){
			//System.out.println("=================================");
			ArrayList<Category> allCate = allQuestions.get(i).getCategoryAll();
			if (allCate.size() > 0){
				//System.out.println(allQuestions.get(i).getLink());
				String cate = allQuestions.get(i).getCategory();
				Node<String> newarticle = new Node<>();
				newarticle.setData(allQuestions.get(i).getLink());
				
				//System.out.println(cate);
				//System.out.println(allQuestions.get(i).getLink());
				
				if (category.isExist(cate)){
					//System.out.println(category.returnNode(cate).getData());
					newarticle.setParent(category.returnNode(cate));
					category.returnNode(cate).addChild(newarticle);	
				}else{
					//System.out.println("********************************************");
					Node<String> newCate = new Node<String>();
					newCate.setData(cate);
					newCate.addChild(newarticle);
					newarticle.setParent(newCate);
					Node<String> tmp_cate = newCate;
					if (allCate.size() == 2){
						category.getRoot().addChild(newCate);
					}else{
						int j=1;
						Node<String> newCate_2 = newCate;
						while(!category.isExist(allCate.get(j).getCategory()) 
								&& j < allCate.size() - 1){
							newCate_2.setData(allCate.get(i).getCategory());
							newCate_2.addChild(tmp_cate);
							if (!category.isExist(allCate.get(j+1).getCategory())){
								Node<String> newCate_3 = new Node<String>();
								newCate_3.setData(allCate.get(j+1).getCategory());
								newCate_3.addChild(newCate_2);
								newCate_2.setParent(newCate_3);
							}
							j++;
							tmp_cate = newCate_2;
						}
						newCate_2.setParent(category.returnNode(allCate.get(j).getCategory()));
						category.returnNode(allCate.get(j).getCategory()).addChild(newCate_2);
					}
				}
			}
		}
	    return category;
	}
}
