package extraction.run;

import question.Tree;
import extraction.ExtractionCategory;

public class ExtractionCategoryTest {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws Exception {
		ExtractionCategory cate_extraction = new ExtractionCategory();
		Tree category = cate_extraction.categoryExtraction();
		
		System.out.println("Height of tree: " + category.getHeight());
		System.out.println("Size of tree: " + category.getSize());
		
		category.printNodeofTree("Asia");
		      
		System.out.println(category.printRootPath("Japan"));
		      
		if (category.isExist("Family Life")) System.out.println("Exist!");
		else System.out.println("None exists!"); 
	}
}
