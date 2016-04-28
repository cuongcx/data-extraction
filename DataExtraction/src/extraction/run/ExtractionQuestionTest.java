package extraction.run;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import question.Question;
import extraction.ExtractionQuestion;

public class ExtractionQuestionTest {

	@SuppressWarnings({ })
	public static void main(String[] args) throws Exception {
		String directory = "D:\\Study in IMPRS\\Research\\Master Thesis\\Crawling\\WikiHow";
		try{
			Writer jsonout = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream("D:\\Study in IMPRS\\Research\\Master Thesis\\Crawling\\questions.json"), "utf-8"));
			Writer textout = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream("D:\\Study in IMPRS\\Research\\Master Thesis\\Crawling\\questions.txt"), "utf-8"));
			
			ExtractionQuestion extractQues = new ExtractionQuestion();
			ArrayList<Question> allQuestions = extractQues.extractionQuestion(directory);
			for (int i=0; i<allQuestions.size(); i++){
				//System.out.println(newQuestion.toString());
				jsonout.write(allQuestions.get(i).toJson().toJSONString() + "\n");
				textout.write(allQuestions.get(i).toString());
			}
			System.out.println("Total of articles: " + allQuestions.size());
			jsonout.close();
			textout.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
