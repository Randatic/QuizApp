package com.example.csaper6.quizapp;

public class Question {
    private int questionId;
    private boolean isAnswerTrue;
    private String question;


    public Question(boolean isAnswerTrue, String question) {
        this.isAnswerTrue = isAnswerTrue;
        this.question = question;
    }

    /**
     * If answerGiven and isAnswerTrue match, it returns true
     * otherwise, return false
     *
     * @param answerGiven the answer clicked
     * @return true if they got the answer right
     */
    public boolean checkAnswer(boolean answerGiven){
        //returns true if answerGiven is equal to answer
        return answerGiven==isAnswerTrue;
    }

    public String getQuestion() {
        return question;
    }

    public int getQuestionId() {
        return questionId;
    }

    public boolean isAnswerTrue() {
        return isAnswerTrue;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setAnswerTrue(boolean answerTrue) {
        isAnswerTrue = answerTrue;
    }



}

