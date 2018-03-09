package com.senti;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class MainClass {
    public static void main(String[] args) throws IOException {
        SentimentAnalyser sentimentAnalyser = new SentimentAnalyser();
        sentimentAnalyser.configuration();
        try {
            Stream<String> stream = Files.lines(Paths.get("1-restaurant-test.csv"));
            for (String review : (Iterable<String>) stream::iterator)
            {
                System.out.println(review.substring(0,15) + " : " + sentimentAnalyser.getSentiment(review));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
