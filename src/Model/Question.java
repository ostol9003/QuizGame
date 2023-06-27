package Model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Question {

    private final int questionNumber;
    private final String question;
    private final HashMap<String, Boolean> answers;

    public Question(int questionNumber, String question, HashMap<String, Boolean> answers) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.answers = answers;
    }

    public static ArrayList<Question> loadQuestions(String filePath) {
        ArrayList<Question> questions = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();

        try (BufferedReader loadFile = new BufferedReader(new FileReader(filePath))) {
            Object obj = jsonParser.parse(loadFile);
            JSONObject questionJson = (JSONObject) obj;

            for (Object questionNumberObj : questionJson.keySet()) {
                String questionNumberStr = (String) questionNumberObj;
                JSONObject questionObj = (JSONObject) questionJson.get(questionNumberStr);

                int questionNumber = Integer.parseInt(questionNumberStr);
                String questionText = (String) questionObj.get("question");
                JSONObject answersObj = (JSONObject) questionObj.get("answers");

                HashMap<String, Boolean> answers = new HashMap<>();
                for (Object answerObj : answersObj.keySet()) {
                    String answer = (String) answerObj;
                    Boolean isCorrect = (Boolean) answersObj.get(answer);
                    answers.put(answer, isCorrect);
                }

                Question question = new Question(questionNumber, questionText, answers);
                questions.add(question);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public String getQuestion() {
        return question;
    }

    public HashMap<String, Boolean> getAnswers() {
        return answers;
    }

    public boolean isCorrectAnswer(String selectedAnswer) {
        for (Map.Entry<String, Boolean> entry : answers.entrySet()) {
            String answerText = entry.getKey();
            boolean isCorrect = entry.getValue();

            if (answerText.equals(selectedAnswer)) {
                return isCorrect;
            }
        }

        return false;
    }

}

