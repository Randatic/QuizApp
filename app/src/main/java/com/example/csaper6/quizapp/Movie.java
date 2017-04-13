package com.example.csaper6.quizapp;

/**
 * Created by Randy Bruner on 3/27/17.
 */
public class Movie {
    private String title, rated;
    private int year;
    private double imdbRating;

    public Movie (String title, String rated, int year, double imdbRating) {
        this.title = title;
        this.rated = rated;
        this.year = year;
        this.imdbRating = imdbRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }
}
