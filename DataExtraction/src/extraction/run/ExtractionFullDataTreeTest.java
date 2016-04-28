package extraction.run;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import question.Tree;
import extraction.ExtractionFullDataTree;

public class ExtractionFullDataTreeTest {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws ClassNotFoundException, IOException, ParseException {
		String directory = "D:\\Study in IMPRS\\Research\\Master Thesis\\Crawling\\questions.json";
		ExtractionFullDataTree extraction = new ExtractionFullDataTree();
		Tree category = extraction.extractFullDataTree(directory);
		
		//Get a full tree
		//Test
		category.printNodeofTree("In Laws");
	      
		System.out.println(category.printRootPath("Halloween Pranks"));
		      
		if (category.isExist("Family Life")) System.out.println("Exist!");
		else System.out.println("None exists!"); 
	}
}
