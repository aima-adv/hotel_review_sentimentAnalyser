package com.senti;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;

public class SentimentAnalyser {
    StanfordCoreNLP pipeline;

    public void configuration() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);

    }
    public String getSentiment(String review){
        int sentiment = 0;
        if (review.length() > 0) {
            int len = 0;
            Annotation annotation = pipeline.process(review);
            for (CoreMap sentence : annotation
                    .get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence
                        .get(SentimentCoreAnnotations.AnnotatedTree.class);
                int in_sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                int token_sentiment = sentence.get(CoreAnnotations.TokensAnnotation.class).size();
                String text = sentence.toString();
                if (text.length() > len) {
                    sentiment = in_sentiment*token_sentiment;
                    len = text.length();
                }

            }
        }
        return ratings(sentiment);
    }
    public String ratings(int sentimentValue){
        if(sentimentValue == 0){
            return "Unsatisfactory";
        }
        else if(sentimentValue > 0 && sentimentValue <=30){
            return "Bad";
        }
        else if(sentimentValue >30 && sentimentValue <=60){
            return "Good";
        }
        return "Extraordinary";
    }
}

