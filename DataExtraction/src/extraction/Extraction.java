package extraction;

import global.Global;

import java.util.ArrayList;
import java.util.regex.Pattern;

import question.Category;
import question.Ingredients;
import question.Method;
import question.Part;
import question.Step;
import question.Things;
import tool.TParser;

public class Extraction {
	
	Pattern pattern = Pattern.compile("[a-zA-Z]");
	
	// ======================================================================
	/**
	 * Return title
	 * @param content
	 * @return title
	 */
	public String extractTitle(String content){
		String title = "";
		title = TParser.getContent(content, "<title>", "</title>");
		
		if (title.contains(" - wikiHow")){
			title = title.substring(0, title.indexOf(" - wikiHow"));
		}
		return title;
	}
	
	// ======================================================================
	/**
	 * Return explanation of question
	 * @param content
	 * @return explanation
	 */
	public String extractExplanation(String content){
		String explanation = "";
		if (content.contains("<p id=\"method_toc\"")){
			String expl = TParser.getContent(content, "<p id=\"method_toc\"", "<div class=\"clearall\">");
			if (expl.contains("<p>")){
				expl = TParser.getContent(expl, "<p", "/p>");
				
				if (expl.length() > 0 && pattern.matcher(expl).find()){
					ArrayList<String> details = TParser.getContentList(expl, ">", "<");
					for (int l=0; l<details.size(); l++){
						if (pattern.matcher(details.get(l)).find())
							explanation += details.get(l);
					}
				}
			}
			
		}
		return explanation;
	}
	
	// ======================================================================
	/**
	 * Return tips
	 * @param content
	 * @return tips
	 */
	public String extractTips(String content){
		String tips = "";
		if (content.contains("<div id=\"tips\" class=\"section_text\">")){
			tips = TParser.getContent(content, "<div id=\"tips\" class=\"section_text\">", "</ul>");
			tips = tips.replaceAll("<ul>\n", "");
			tips = tips.replaceAll("<li>", "");
			tips = tips.replaceAll("</li>", "");
		}
		return tips;
	}
	
	// ======================================================================
	/**
	 * Return warnings
	 * @param content
	 * @return warnings
	 */
	public String extractWarnings(String content){
		String warnings = "";
		if (content.contains("<div id=\"warnings\" class=\"section_text\">")){
			warnings = TParser.getContent(content, "<div id=\"warnings\" class=\"section_text\">", "<div class=\"clearall\">");
			warnings = TParser.getContent(warnings, "<ul>", "</ul>");
			warnings = warnings.replaceAll("<li>", "");
			warnings = warnings.replaceAll("</li>", "");
		}
		return warnings;
	}
	
	// ======================================================================
	/**
	 * Return link of video
	 * @param content
	 * @return link
	 */
	public String extractVideo(String content){
		String video = "";
		if (content.contains("<div id=\"video\" class=\"section_text\">")){
			if (content.contains("<div class=\"embedvideocontainer\">")){
				video = TParser.getContent(content, "<div class=\"embedvideocontainer\">", "frameborder");
				video = TParser.getContent(video, "src=\"", "\"");
			}
			
		}
		return video;
	}
	
	// ======================================================================
	/**
	 * Return list of things you will need
	 * @param content
	 * @return list of things
	 */
	public ArrayList<Things> extractThings(String content){
		ArrayList<Things> things = new ArrayList<>();
		if (content.contains("<div id=\"thingsyoullneed\" class=\"section_text\">")){
			String tmp = TParser.getContent(content, "<div id=\"thingsyoullneed\" class=\"section_text\">", "<div class=\"clearall\">");
			if (tmp.contains("<span class=\"mw-headline\"")){
				ArrayList<String> nameOfMethod = TParser.getContentList(tmp, "<span class=\"mw-headline\"", "</span>");
				ArrayList<String> listOfThings = TParser.getContentList(tmp, "<ul>", "</ul>");
				for (int j=0; j<listOfThings.size(); j++){
					ArrayList<String> list = TParser.getContentList(listOfThings.get(j), "class=\"checkbox-text\">", "</div>");
					Things newThings = new Things(nameOfMethod.get(j), list);
					things.add(newThings);
				}
				
			}else if (tmp.contains("<p>") && !tmp.contains("<span class=\"mw-headline\"")){
				ArrayList<String> nameOfMethod = TParser.getContentList(tmp, "<p>", "</p>");
				ArrayList<String> listOfThings = TParser.getContentList(tmp, "<ul>", "</ul>");
				if (nameOfMethod.size() >= listOfThings.size()){
					for (int j=0; j<listOfThings.size(); j++){
						ArrayList<String> list = TParser.getContentList(listOfThings.get(j), "class=\"checkbox-text\">", "</div>");
						Things newThings = new Things(nameOfMethod.get(j), list);
						things.add(newThings);
					}
				}else{
					ArrayList<String> list = new ArrayList<>();
					for (int i=0; i<listOfThings.size(); i++){
						list.addAll(TParser.getContentList(listOfThings.get(i), "class=\"checkbox-text\">", "</div>"));
					}
					things.add(new Things("", list));
				}
			}
			else{
				ArrayList<String> list = TParser.getContentList(tmp, "class=\"checkbox-text\">", "</div>");
				things.add(new Things("", list));
			}
		}
		return things;
	}
	
	// ======================================================================
	/**
	 * Return list of ingredients you will need
	 * @param content
	 * @return list of ingredients you will need
	 */
	public ArrayList<Ingredients> extractIngredients(String content){
		ArrayList<Ingredients> ingredients = new ArrayList<>();
		if (content.contains("<div id=\"ingredients\" class=\"section_text\">")){
			String tmp = TParser.getContent(content, "<div id=\"ingredients\" class=\"section_text\">", "<div class=\"clearall\">");
			if (tmp.contains("</b></p>")){
				ArrayList<String> nameOfMethod = TParser.getContentList(tmp, "<p><b>", "</b></p>");
				ArrayList<String> listOfThings = TParser.getContentList(tmp, "<ul>", "</ul>");
				for (int j=0; j<nameOfMethod.size(); j++){
					ArrayList<String> list = TParser.getContentList(listOfThings.get(j), "class=\"checkbox-text\">", "</div>");
					Ingredients newThings = new Ingredients(nameOfMethod.get(j), list);
					ingredients.add(newThings);
				}
				
			}else if (tmp.contains("<span class=\"mw-headline\"")){
				ArrayList<String> nameOfMethod = TParser.getContentList(tmp, "<span class=\"mw-headline\"", "</span>");
				ArrayList<String> listOfThings = TParser.getContentList(tmp, "<ul>", "</ul>");
				for (int j=0; j<nameOfMethod.size(); j++){
					ArrayList<String> list = TParser.getContentList(listOfThings.get(j), "class=\"checkbox-text\">", "</div>");
					String name = nameOfMethod.get(j).substring(nameOfMethod.get(j).lastIndexOf(">") + 1);
					Ingredients newThings = new Ingredients(name, list);
					ingredients.add(newThings);
				}
			}
			else{
				ArrayList<String> list = TParser.getContentList(tmp, "class=\"checkbox-text\">", "</div>");
				ingredients.add(new Ingredients("", list));
			}
		}
		return ingredients;
	}
	
	// ======================================================================
	/**
	 * Return list of categories which the article belongs in
	 * @param content
	 * @return list of categories which the article belongs in
	 */
	public ArrayList<Category> extractCategory(String content){
		ArrayList<Category> categories = new ArrayList<>();
		if (content.contains("aria-label=\"Category breadcrumbs\">")){
			String tmp_cate = TParser.getContent(content, "aria-label=\"Category breadcrumbs\">", "</div>");
			String tmp_cate2 = TParser.getContent(tmp_cate, "Categories", "</ul");
			ArrayList<String> cate = TParser.getContentList(tmp_cate2, ">", "<");
			ArrayList<String> cate2 = new ArrayList<>();
			
			for (int k=cate.size()-1; k>=0; k--){
				if (pattern.matcher(cate.get(k)).find() &&
						!cate.get(k).equals(" &raquo; ")){
					String tmp_cate3 = cate.get(k).replace("&", "&amp;");
					tmp_cate3 = tmp_cate3.trim();
					cate2.add(tmp_cate3);
				}
			}
			cate2.add(0, "");
			cate2.add(cate2.size(), Global.GENERAL_CATEGORY);
			cate2.add(cate2.size(), "");
			
			for (int k=1; k<cate2.size()-1; k++){		
				Category newCate = new Category(cate2.get(k), cate2.get(k-1), cate2.get(k+1));
				categories.add(newCate);
			}
			
		}	
		return categories;
	}
	
	
	// ======================================================================
	/**
	 * Return list of methods which are answers for the question
	 * @param content
	 * @return list of methods which are answers for the question
	 */
	public ArrayList<Method> extractAnswer(String content){
		ArrayList<Method> answer = new ArrayList<>();
		//===========================================
		//Check the content divide methods or parts
		if (content.contains("<h2 class=\"hidden\">")){
			//Extract list of methods or parts
			ArrayList<String> listpart = TParser.getContentList(content, "<div class=\"section steps", "<a name=");
			
			//==============================================================
			//If answer includes more than one methods
			if (listpart.get(0).contains("class=\"altblock\">Method")){
				
				for (int j=0; j<listpart.size(); j++){
					ArrayList<Part> parts = new ArrayList<>();
					//Name of method
					String name = TParser.getContent(listpart.get(j), "<span class=\"mw-headline\"", "</span>");
					name = name.substring(name.indexOf(">") + 1);
					
					//===================================
					//Extract list of step in each method
					ArrayList<Step> steps = new ArrayList<>();
					//If the answer has images
					if (listpart.get(j).contains("style=\"max-width:728px\">")){
						ArrayList<String> listofSteps = TParser.getContentList(listpart.get(j), "style=\"max-width:728px\">", "<div class=\"clearall\">");
						int order = 0;
						for (int k=0; k<listofSteps.size(); k++){
							//Extract link of image
							String image = "";
							if (listofSteps.get(k).contains("data-src=\"")){
								image = TParser.getContent(listofSteps.get(k), "data-src=\"", "\"");
							}
							order++;
							//Extract action
							ArrayList<String> actions = TParser.getContentList(listofSteps.get(k), "<div class=\"step\">", "/div>");
							//If for each step, the answer has one image
							if (actions.size() == 1){
								String main_act = TParser.getContent(actions.get(0), "<b class=\"whb\">", "</b");
								String detail_act = "";
								String detail = actions.get(0).substring(actions.get(0).indexOf("</b>") + 3);
								if (detail.length() > 0 && pattern.matcher(detail).find()){
									ArrayList<String> details = TParser.getContentList(detail, ">", "<");
									for (int l=0; l<details.size(); l++){
										if (pattern.matcher(details.get(l)).find())
											detail_act += details.get(l);
									}
								}
								Step newstep = new Step(order, main_act, detail_act, image);
								steps.add(newstep);
							//Else only the first step has image, the orthers don't
							}else{
								for (int l=0; l<actions.size(); l++){
									order += l;
									String main_act = TParser.getContent(actions.get(l), "<b class=\"whb\">", "</b");
									String detail_act = "";
									String detail = actions.get(l).substring(actions.get(l).indexOf("</b>") + 3);
									if (detail.length() > 0 && pattern.matcher(detail).find()){
										ArrayList<String> details = TParser.getContentList(detail, ">", "<");
										for (int h=0; h<details.size(); h++){
											if (pattern.matcher(details.get(h)).find())
												detail_act += details.get(h);
										}
									}
									if (l==0){
										Step newstep = new Step(order, main_act, detail_act, image);
										steps.add(newstep);
									}else{
										Step newstep = new Step(order, main_act, detail_act, "");
										steps.add(newstep);
									}
								}
							}
							
						}
					//Else the answer doesn't have any images
					}else {
						ArrayList<String> actions = TParser.getContentList(listpart.get(j), "<div class=\"step\">", "/div>");
						int order = 0;
						for (int l=0; l<actions.size(); l++){
							order ++;
							String main_act = TParser.getContent(actions.get(l), "<b class=\"whb\">", "</b");
							String detail_act = "";
							String detail = actions.get(l).substring(actions.get(l).indexOf("</b>") + 3);
							if (detail.length() > 0 && pattern.matcher(detail).find()){
								ArrayList<String> details = TParser.getContentList(detail, ">", "<");
								for (int h=0; h<details.size(); h++){
									if (pattern.matcher(details.get(h)).find())
										detail_act += details.get(h);
								}
							}
							
							Step newstep = new Step(order, main_act, detail_act, "");
							steps.add(newstep);
						
						}
					}
					if (steps.size() > 0){
						Part newpart = new Part(1, name, steps);
						parts.add(newpart);
						answer.add(new Method(j+1, name, parts));
					}
					
				}
				
			//============================================================
			//Else The answer include more than one parts
			}else if (listpart.get(0).contains("class=\"altblock\">Part")){
				ArrayList<Part> parts = new ArrayList<>();
				for (int j=0; j<listpart.size(); j++){
					
					//Name of part
					String name = TParser.getContent(listpart.get(j), "<span class=\"mw-headline\"", "</span>");
					name = name.substring(name.indexOf(">") + 1);
					
					//Extract list of step in each method
					ArrayList<Step> steps = new ArrayList<>();
					//If the answer has images
					if (listpart.get(j).contains("style=\"max-width:728px\">")){
						ArrayList<String> listofSteps = TParser.getContentList(listpart.get(j), "style=\"max-width:728px\">", "<div class=\"clearall\">");
						int order = 0;
						for (int k=0; k<listofSteps.size(); k++){
							//Extract link of image
							String image = "";
							if (listofSteps.get(k).contains("data-src=\"")){
								image = TParser.getContent(listofSteps.get(k), "data-src=\"", "\"");
							}
							order++;
							//Extract action
							ArrayList<String> actions = TParser.getContentList(listofSteps.get(k), "<div class=\"step\">", "/div>");
							//If each step has one image
							if (actions.size() == 1){
								String main_act = TParser.getContent(actions.get(0), "<b class=\"whb\">", "</b");
								String detail_act = "";
								String detail = actions.get(0).substring(actions.get(0).indexOf("</b>") + 3);
								if (detail.length() > 0 && pattern.matcher(detail).find()){
									ArrayList<String> details = TParser.getContentList(detail, ">", "<");
									for (int l=0; l<details.size(); l++){
										if (pattern.matcher(details.get(l)).find())
											detail_act += details.get(l);
									}
								}
								Step newstep = new Step(order, main_act, detail_act, image);
								steps.add(newstep);
							//Else only the first step has image, the others don't
							}else{
								for (int l=0; l<actions.size(); l++){
									order += l;
									String main_act = TParser.getContent(actions.get(l), "<b class=\"whb\">", "</b");
									String detail_act = "";
									String detail = actions.get(l).substring(actions.get(l).indexOf("</b>") + 3);
									if (detail.length() > 0 && pattern.matcher(detail).find()){
										ArrayList<String> details = TParser.getContentList(detail, ">", "<");
										for (int h=0; h<details.size(); h++){
											if (pattern.matcher(details.get(h)).find())
												detail_act += details.get(h);
										}
									}
									if (l==0){
										Step newstep = new Step(order, main_act, detail_act, image);
										steps.add(newstep);
									}else{
										Step newstep = new Step(order, main_act, detail_act, "");
										steps.add(newstep);
									}
								}
							}
							
						}
					//========================================
					//Else the answer doesn't have any images
					}else{
						ArrayList<String> actions = TParser.getContentList(listpart.get(j), "<div class=\"step\">", "/div>");
						int order = 0;
						for (int l=0; l<actions.size(); l++){
							order ++;
							String main_act = TParser.getContent(actions.get(l), "<b class=\"whb\">", "</b");
							String detail_act = "";
							String detail = actions.get(l).substring(actions.get(l).indexOf("</b>") + 3);
							if (detail.length() > 0 && pattern.matcher(detail).find()){
								ArrayList<String> details = TParser.getContentList(detail, ">", "<");
								for (int h=0; h<details.size(); h++){
									if (pattern.matcher(details.get(h)).find())
										detail_act += details.get(h);
								}
							}
							
							Step newstep = new Step(order, main_act, detail_act, "");
							steps.add(newstep);
						
						}
					}
					if (steps.size() > 0){
						Part newpart = new Part(j+1, name, steps);
						parts.add(newpart);
					}
					
				}
				if (parts.size() > 0)
					answer.add(new Method(1, "", parts));
				
			//=========================================================
			//Else, the answer also divide in many parts, but doens't have part or method
			}else{
				//Extract list of step in each method
				ArrayList<Step> steps = new ArrayList<>();
				//If the answer includes images
				if (content.contains("style=\"max-width:728px\">")){
					ArrayList<String> listofSteps = TParser.getContentList(content, "style=\"max-width:728px\">", "<div class=\"clearall\">");
					int order = 0;
					for (int k=0; k<listofSteps.size(); k++){
						//Extract link of image
						String image = "";
						if (listofSteps.get(k).contains("data-src=\"")){
							image = TParser.getContent(listofSteps.get(k), "data-src=\"", "\"");
						}
						order++;
						//Extract action
						ArrayList<String> actions = TParser.getContentList(listofSteps.get(k), "<div class=\"step\">", "/div>");
						//Each step has one image
						if (actions.size() == 1){
							String main_act = TParser.getContent(actions.get(0), "<b class=\"whb\">", "</b");
							String detail_act = "";
							String detail = actions.get(0).substring(actions.get(0).indexOf("</b>") +3);
							if (detail.length() > 0 && pattern.matcher(detail).find()){
								ArrayList<String> details = TParser.getContentList(detail, ">", "<");
								for (int l=0; l<details.size(); l++){
									if (pattern.matcher(details.get(l)).find())
										detail_act += details.get(l);
								}
							}
							Step newstep = new Step(order, main_act, detail_act, image);
							steps.add(newstep);
						//Else only the first step has own image, the others don't
						}else{
							for (int l=0; l<actions.size(); l++){
								order += l;
								String main_act = TParser.getContent(actions.get(l), "<b class=\"whb\">", "</b");
								String detail_act = "";
								String detail = actions.get(l).substring(actions.get(l).indexOf("</b>") + 3);
								if (detail.length() > 0 && pattern.matcher(detail).find()){
									ArrayList<String> details = TParser.getContentList(detail, ">", "<");
									for (int h=0; h<details.size(); h++){
										if (pattern.matcher(details.get(h)).find())
											detail_act += details.get(h);
									}
								}
								if (l==0){
									Step newstep = new Step(order, main_act, detail_act, image);
									steps.add(newstep);
								}else{
									Step newstep = new Step(order, main_act, detail_act, "");
									steps.add(newstep);
								}
							}
						}
						
					}
				//Else the answer doesn't include images
				}else{
					
					ArrayList<String> actions = TParser.getContentList(content, "<div class=\"step\">", "/div>");
					int order = 0;
					for (int l=0; l<actions.size(); l++){
						order ++;
						String main_act = TParser.getContent(actions.get(l), "<b class=\"whb\">", "</b");
						String detail_act = "";
						String detail = actions.get(l).substring(actions.get(l).indexOf("</b>") + 3);
						if (detail.length() > 0 && pattern.matcher(detail).find()){
							ArrayList<String> details = TParser.getContentList(detail, ">", "<");
							for (int h=0; h<details.size(); h++){
								if (pattern.matcher(details.get(h)).find())
									detail_act += details.get(h);
							}
						}
						
						Step newstep = new Step(order, main_act, detail_act, "");
						steps.add(newstep);
					
					}
				}
				
				Part newpart = new Part(1, "", steps);
				ArrayList<Part> newlistpart = new ArrayList<>();
				newlistpart.add(newpart);
				Method newmethod = new Method(1, "", newlistpart);
				answer.add(newmethod);
			}
			
		//========================================================================
		//Else the answer has only one method which includes only one part
		}else{
			//Extract list of step in each method
			ArrayList<Step> steps = new ArrayList<>();
			//If the answer has images
			if (content.contains("style=\"max-width:728px\">")){
				ArrayList<String> listofSteps = TParser.getContentList(content, "style=\"max-width:728px\">", "<div class=\"clearall\">");
				int order = 0;
				for (int k=0; k<listofSteps.size(); k++){
					//Extract link of image
					String image = "";
					if (listofSteps.get(k).contains("data-src=\"")){
						image = TParser.getContent(listofSteps.get(k), "data-src=\"", "\"");
					}
					order++;
					//Extract action
					ArrayList<String> actions = TParser.getContentList(listofSteps.get(k), "<div class=\"step\">", "/div>");
					//If each step has its own image
					if (actions.size() == 1){
						String main_act = TParser.getContent(actions.get(0), "<b class=\"whb\">", "</b");
						String detail_act = "";
						String detail = actions.get(0).substring(actions.get(0).indexOf("</b>") + 3);
						
						if (detail.length() > 0 && pattern.matcher(detail).find()){
							ArrayList<String> details = TParser.getContentList(detail, ">", "<");
							
							for (int l=0; l<details.size(); l++){
								if (pattern.matcher(details.get(l)).find())
									detail_act += details.get(l);
							}
						}
						Step newstep = new Step(order, main_act, detail_act, image);
						steps.add(newstep);
						
					//Else only the first step has its own image, the others don't
					}else{
						for (int l=0; l<actions.size(); l++){
							order += l;
							String main_act = TParser.getContent(actions.get(l), "<b class=\"whb\">", "</b");
							String detail_act = "";
							String detail = actions.get(l).substring(actions.get(l).indexOf("</b>") + 3);
							if (detail.length() > 0 && pattern.matcher(detail).find()){
								ArrayList<String> details = TParser.getContentList(detail, ">", "<");
								for (int h=0; h<details.size(); h++){
									if (pattern.matcher(details.get(h)).find())
										detail_act += details.get(h);
								}
							}
							if (l==0){
								Step newstep = new Step(order, main_act, detail_act, image);
								steps.add(newstep);
							}else{
								Step newstep = new Step(order, main_act, detail_act, "");
								steps.add(newstep);
							}
						}
					}
					
				}
			//Else the answer doesn't have any images	
			}else{
				ArrayList<String> actions = TParser.getContentList(content, "<div class=\"step\">", "/div>");
				int order = 0;
				for (int l=0; l<actions.size(); l++){
					order ++;
					String main_act = TParser.getContent(actions.get(l), "<b class=\"whb\">", "</b");
					String detail_act = "";
					String detail = actions.get(l).substring(actions.get(l).indexOf("</b>") + 3);
					if (detail.length() > 0 && pattern.matcher(detail).find()){
						ArrayList<String> details = TParser.getContentList(detail, ">", "<");
						for (int h=0; h<details.size(); h++){
							if (pattern.matcher(details.get(h)).find())
								detail_act += details.get(h);
						}
					}
					
					Step newstep = new Step(order, main_act, detail_act, "");
					steps.add(newstep);
				
				}
			}
			
			Part newpart = new Part(1, "", steps);
			ArrayList<Part> newlistpart = new ArrayList<>();
			newlistpart.add(newpart);
			Method newmethod = new Method(1, "", newlistpart);
			answer.add(newmethod);
		}
		return answer;
	}
	
	
	
	
}
