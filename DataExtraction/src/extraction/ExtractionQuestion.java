package extraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import crawler.CrawledPage;

import question.Category;
import question.Ingredients;
import question.Method;
import question.Part;
import question.Question;
import question.Step;
import question.Things;

public class ExtractionQuestion {
	// ======================================================================
	/**
	 * Return list of article extract from crawled data
	 * @param directory that saves all crawled data
	 * @return a list of article
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	
	@SuppressWarnings("unchecked")
	public ArrayList<Question> extractionQuestion(String directory) throws IOException, ClassNotFoundException{
		ArrayList<Question> allQuestions = new ArrayList<>();
		File[] files = new File(directory).listFiles();
		for (File curFile : files) {
			String filePath = curFile.getAbsolutePath();
			List<CrawledPage> lcp;
			FileInputStream fis = new FileInputStream(filePath);
			GZIPInputStream gzipin = new GZIPInputStream(fis);
			ObjectInputStream objectIn = new ObjectInputStream(gzipin);
			lcp = (List<CrawledPage>) objectIn.readObject();
			objectIn.close();
			for (int i = 0; i < lcp.size(); i++) {
				String link = lcp.get(i).url;

				if (!link.contains("wikihow.com/Main-Page")
						&& link.lastIndexOf(":") < 6 && !link.contains("?")
						&& link.lastIndexOf("/") < 24
						&& !link.contains("Powered-and-Inspired-by-Mediawiki")
						&& !link.equals("http://www.wikihow.com")
						&& !link.equals("http://www.wikihow.com/")
						&& !link.equals("http://www.wikihow.com/About-wikiHow")
						&& !link.equals("http://www.wikihow.com/Terms-of-Use")
						&& !link.equals("http://www.wikihow.com/Writer%27s-Guide")
						&& !link.equals("http://www.wikihow.com/Picture-Patrol")
						&& !link.equals("http://www.wikihow.com/Categories")
						) {
					System.out.println(link);
					// String content = lcp.get(i).content;
					// System.out.println(content);
					// Do something with the content
					
					
					String content = lcp.get(i).content;
					
					if (!content.equals("")){
						
						Extraction extraction = new Extraction();
						//Extraction title
						String title = extraction.extractTitle(content);
						
						//Extract ques_explanation: Not really clean
						String expl = extraction.extractExplanation(content);
						
						//Extract tips
						String tips = extraction.extractTips(content);	
					
						//Extract warnings
						String warnings = extraction.extractWarnings(content);
						
						//Extract videos
						String video = extraction.extractVideo(content);
						
						//Extract things
						ArrayList<Things> things = extraction.extractThings(content);					
						
						//Extract ingredients
						ArrayList<Ingredients> ingredients = extraction.extractIngredients(content);
			
						//Extract category
						ArrayList<Category> categories = extraction.extractCategory(content);

						//Extract answer
						ArrayList<Method> answer = extraction.extractAnswer(content);

						Question newQuestion = new Question(title, expl, answer,
								categories, link, tips, warnings, video, things, ingredients);
						
						allQuestions.add(newQuestion);
					
					}
				}
			}
		}
		return allQuestions;
	}
	
	// ======================================================================
	/**
	 * Return list of article extract from file with json format
	 * @param directory that saves article in json format
	 * @return a list of article
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public ArrayList<Question> extractQuestionFromJSONFile(String directory) throws IOException, ClassNotFoundException, ParseException{
		ArrayList<Question> allQuestions = new ArrayList<>();
		
		JSONParser parser = new JSONParser();
		try (BufferedReader br = new BufferedReader(new FileReader(directory))) {

			String sCurrentLine;
			
			while ((sCurrentLine = br.readLine()) != null) {
				Object obj = parser.parse(sCurrentLine);
				JSONObject jsonObject = (JSONObject) obj;
				Question newQuestion = jsonToQuestion(jsonObject);
				allQuestions.add(newQuestion);
			}
		}
		return allQuestions;
	}
	
	
	// ======================================================================
	/**
	 * Convert a Json object to Question Object
	 * @param JSON Object
	 * @return Question Object 
	 */
	@SuppressWarnings("unchecked")
	public Question jsonToQuestion(JSONObject jsonobj){
		//Extract link
		String link = (String) jsonobj.get("Link");
		
		//Extract title
		String title = (String) jsonobj.get("Title");
		
		//Extract explanation
		String exp = (String) jsonobj.get("Explanation");
		
		//Extract tips
		String tips = (String) jsonobj.get("Tips");
		
		//Extract warnings
		String warnings = (String) jsonobj.get("Warnings");
		
		//Extract link of video
		String video = (String) jsonobj.get("Video");
		
		//Extract category
		ArrayList<Category> category = new ArrayList<Category>();
		ArrayList<String> cate_string = new ArrayList<String>();
		JSONArray categoryJ = (JSONArray) jsonobj.get("Category");
		Iterator<String> iteratorC = categoryJ.iterator();
		while (iteratorC.hasNext()) {
			cate_string.add(iteratorC.next());
		}
		cate_string.add(0, "");
		cate_string.add(cate_string.size(), "");
		for (int k=1; k<cate_string.size()-1; k++){		
			Category newCate = new Category(cate_string.get(k), cate_string.get(k-1), cate_string.get(k+1));
			category.add(newCate);
		}
		
		//Extract things
		ArrayList<Things> things = new ArrayList<Things>();
		JSONArray thingJ = (JSONArray) jsonobj.get("Things");
		Iterator<JSONObject> iteratorT = thingJ.iterator();
		while (iteratorT.hasNext()) {
			JSONObject thing = iteratorT.next();
			//Title of method
			String title_thing = (String) thing.get("Title");
			//List of things of this method
			ArrayList<String> listthing	 = new ArrayList<String>();
			//List things of a method
			JSONArray thing_method = (JSONArray) thing.get("Things");
			Iterator<String> iteratorTM = thing_method.iterator();
			while (iteratorTM.hasNext()) {
				listthing.add(iteratorTM.next());
			}
			Things newThing = new Things(title_thing, listthing);
			things.add(newThing);
		}
		
		
		//Extract ingredients
		ArrayList<Ingredients> ingredients = new ArrayList<Ingredients>();
		JSONArray ingredientJ = (JSONArray) jsonobj.get("Ingredients");
		Iterator<JSONObject> iteratorI = ingredientJ.iterator();
		while (iteratorI.hasNext()) {
			JSONObject ingre = iteratorI.next();
			//Title of method
			String title_ingre = (String) ingre.get("Title");
			//List of things of this method
			ArrayList<String> listingre	 = new ArrayList<String>();
			//List things of a method
			JSONArray ingre_method = (JSONArray) ingre.get("Ingredients");
			Iterator<String> iteratorIM = ingre_method.iterator();
			while (iteratorIM.hasNext()) {
				listingre.add(iteratorIM.next());
			}
			Ingredients newIngre = new Ingredients(title_ingre, listingre);
			ingredients.add(newIngre);
		}
		
		//Extract answer
		// loop array
		//Extract list of methods
		ArrayList<Method> answerJ = new ArrayList<Method>();
		JSONArray answer = (JSONArray) jsonobj.get("Answer");
		Iterator<JSONObject> iterator = answer.iterator();
		while (iterator.hasNext()) {
			// loop method array
			JSONObject method = iterator.next();
			//Extract name of method
			String title_method = (String) method.get("Title");
			//Extract order of method
			int order_method = (int) (long) method.get("Order");
			
			//extract list of part
			ArrayList<Part> listPart = new ArrayList<>();
			JSONArray listofPart = (JSONArray) method.get("Method");
			
			Iterator<JSONObject> iteratorPart = listofPart.iterator();
			while(iteratorPart.hasNext()){
				// loop part array
				JSONObject part = iteratorPart.next();
				//Extract name of part
				String title_part = (String) part.get("Title");
				//Extract order of part
				int order_part = (int) (long) part.get("Order");
				
				//Extract list of steps
				ArrayList<Step> listStep = new ArrayList<>();
				JSONArray listofStep = (JSONArray) part.get("Part");
				
				Iterator<JSONObject> iteratorStep = listofStep.iterator();
				while(iteratorStep.hasNext()){
					//loop step array
					JSONObject step = iteratorStep.next();
					//Extract main action
					String main_act = (String) step.get("Main_act");
					//Extract order of step
					int order_step = (int) (long) step.get("Order");
					//Extract detail action
					String detail_act = (String) step.get("Detail_act");
					//Extract link of image
					String image = (String) step.get("Image");
					
					Step newStep = new Step(order_step, main_act, detail_act, image);
					listStep.add(newStep);
				}
				Part newPart = new Part(order_part, title_part, listStep);
				listPart.add(newPart);
			}
			Method newMethod = new Method(order_method, title_method, listPart);
			answerJ.add(newMethod);
		}
		
		Question newQuestion = new Question(title, exp, answerJ, 
				category, link, tips, warnings, video, things, ingredients);
		return newQuestion;
	}
	
	
	
	
}
