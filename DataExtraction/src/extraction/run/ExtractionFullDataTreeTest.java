package extraction.run;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.json.simple.parser.ParseException;

import question.Tree;
import question.Tree.Node;
import extraction.ExtractionFullDataTree;
import global.Global;

public class ExtractionFullDataTreeTest {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws ClassNotFoundException, IOException, ParseException {
		String directory = Global.JSON_FILE;
		ExtractionFullDataTree extraction = new ExtractionFullDataTree();
		Tree category = extraction.extractFullDataTree(directory);
		
		//Get a full tree
		//Test
		category.printNodeofTree("In Laws");
		
		System.out.println("Size of tree: " + category.getSize());
	      
		System.out.println(category.inheritedHypernymy("Halloween Pranks"));
		      
		if (category.isExist("Family Life")) System.out.println("Exist!");
		else System.out.println("None exists!");
		try{
			Writer cateout = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream(Global.CATEGORY_FILE), "utf-8"));
			cateout.write(category.getRoothPathofCategories(category.getRoot()));
			cateout.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
