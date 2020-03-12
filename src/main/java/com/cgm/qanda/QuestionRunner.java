package com.cgm.qanda;

import com.cgm.qanda.service.QuestionAnswerService;
import com.cgm.qanda.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

@Component
@Profile("!test")
public class QuestionRunner implements CommandLineRunner {

    @Autowired
    QuestionAnswerService service;

    @Override
    public void run(String... args) throws Exception {
        Scanner inputKeys = new Scanner(System.in);
        System.out.println("Please enter 1 to ask a question: ");
        System.out.println("Please enter 2 to submit an answer: ");
        System.out.println("Please enter any other number to exit: ");
        try {
            int input = inputKeys.nextInt();

            while (true) {
                if (input == 1) {
                    System.out.println("What is your question : ");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String question = reader.readLine();
                    printAnswer(question);
                    System.out.println("\n" + "Next input please.");
                    input = inputKeys.nextInt();
                } else if (input == 2) {
                    System.out.println("Lets submit an answer");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String answer = reader.readLine();
                    addAnswer(answer);
                    System.out.println("\n" + "Next input please.");
                    input = inputKeys.nextInt();
                } else {
                    System.out.println("Exit command received.Bye.");
                    inputKeys.close();
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid user input, exiting program.Bye.");
            System.exit(1);
        }
    }

    private void printAnswer(String question) {
        if (!question.isEmpty()) {
            List<String> answers = service.getAnswers(question);
            for (String answer : answers) {
                System.out.println(answer);
            }
        }
    }

    private void addAnswer(String answer) {
        String[] answerArr = answer.split("\\?");
        if (answerArr.length > 1) {
            String question = answerArr[0];
            String answers = answerArr[1];
            if (ValidationUtil.validateAnswerFormat(answers)) {
                service.addQuestion(question, answers);
            } else {
                System.out.println("Invlid answer format. Format should be " + "\"" + "<ans1>" + "\"" + "<ans2>" + "\"" + "...");
            }
        } else {
            System.out.println("Invlid format. Format should be " + "<que>" + "?" + "\"" + "<ans1>" + "\"" + "<ans2>" + "\"" + "...");
        }
    }
}
