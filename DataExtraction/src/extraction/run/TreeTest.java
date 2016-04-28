package extraction.run;

import java.util.ArrayList;

import global.Global;
import question.Tree;
import question.Tree.Node;
import tool.TParser;

public class TreeTest {
	public static void main(String[] args) throws Exception {
		Tree<String> category = new Tree<String>();
	      Node<String> root = new Node<String>(Global.GENERAL_CATEGORY);
	      category.setRoot(root);
	      category.printTree();
	      ArrayList<Node<String>> children = new ArrayList<>();
//	      Node<String> child1 = new Node<>();
//	      child1.setData("today");
//	      child1.setParent(root);
//	      children.add(child1);
//	      Node<String> child2 = new Node("Tomorrow");
//	      children.add(child2);
	      root.setChildren(children);
	      category.printTree();
	      System.out.println(category.getRoot().getData());
	      
	      String test = "<1>hello<2>";
	      ArrayList<String> second_level = TParser.getContentList(test, "<2>", "<2>");
	      System.out.println(second_level.size());
	}
}
