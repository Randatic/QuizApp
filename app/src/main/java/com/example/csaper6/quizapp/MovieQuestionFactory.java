package com.example.csaper6.quizapp;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Randy Bruner on 3/21/17.
 */
public class MovieQuestionFactory {
    static final String MOVIE_SEARCH_URL = "https://www.omdbapi.com/?";
    static final String TAG = MovieQuestionFactory.class.getSimpleName();

    private String[] movies, askedMovies;
    private String[] questions;
    private Question currentQuestion;
    private String movie, year, rate, imdbRating;
    private String randomRate;
    private String[] ratings;

    public MovieQuestionFactory() {
        questions = new String[]{
                " was released in ", " was rated ", " had an IMDB rating of "
        };
        ratings = new String[]{
                "G", "PG", "PG-13", "R", "NC-17"
        };

        movies = new String[]{
                "Doctor Strange", "Moana", "Zootopia", "The Way Way Back", "Almost Famous", "Frankenstein",
                "Count Dracula", "The Avengers", "The Lego Movie", "The Spectacular Now", "The Great Gatsby",
                "Ferris Bueller's Day Off", "The Theory of Everything", "Ocean's Eleven", "Kiki's Delivery Service",
                "Ponyo", "Secret World of Arrietty", "Batman", "Thor", "Spider-Man", "The Nice Guys",
                "The Wolf of Wall Street", "Grown Ups", "10 Things I Hate About You", "Up", "Lion King",
                "Lilo and Stitch", "The Little Mermaid", "Cinderella", "Snow White", "Beauty and the Beast",
                "Kong: Skull Island", "Life", "Logan", "Get Out", "The Belko Experiment", "The Lego Batman Movie",
                "Hidden Figures", "Split", "Before I Fall", "Rogue One", "Sing", "La La Land", "King Kong",
                "Monster Trucks", "Nine Lives", "XXX", "Rings", "Lord of the Rings", "The Hobbit",
                "Raw", "Pastors", "12 Angry Men", "Fight Club", "The Godfather", "Forrest Gump",
                "The Matrix", "Seven Samurai"
        };

        askedMovies = new String[movies.length];

        currentQuestion = new Question(true, "The sky is blue.");
    }

    public MovieQuestionFactory(String[] movies) {
        this.movies = movies;
        currentQuestion = new Question(true, "The sky is blue.");
    }

    public Question nextQuestion() {
        movie = movies[(int) (Math.random() * movies.length)];
        QuestionMaker maker = new QuestionMaker();
        maker.execute(MOVIE_SEARCH_URL + "t=" + movie.replace(" ", "+"));
        //Log.d(TAG, "nextQuestion: T " + currentQuestion.getQuestion());
        return currentQuestion;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public String getRandomRate(String Rate) {
        String rRate = ratings[(int) (Math.random() * ratings.length)];
        while (Rate.equals(rRate)) {
            rRate = ratings[(int) (Math.random() * ratings.length)];
        }
        return rRate;
    }


    private class QuestionMaker extends AsyncTask<String, Void, JSONObject> {
        String jsonString = "";

        @Override
        protected JSONObject doInBackground(String... urls) {
            //make new URL object
            try {
                URL url = new URL(urls[0]);
                URLConnection connection = url.openConnection();

                InputStream inputStream = connection.getInputStream();
                //build string
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while((line = reader.readLine()) != null) {
                    jsonString += line;
                }
                //log it
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                //Log.d(TAG, "doInBackground: "  + jsonString.toString());
                return new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject s) { //return from doInBackground()
            super.onPostExecute(s);
            boolean answer;
            String question;
            int type = (int) (Math.random()*4);

            if(((int) (Math.random()*2))%2==0) {
                answer = false;
            } else {
                answer = true;
            }

            if(s != null) {
                try {
                    if(s.getString("Response").equals("True")) {
                        //Log.d(TAG, "onPostExecute: T " + s.getString("Title") + "; Y " + s.getString("Year") + "; R "+ s.getString("Rated") + "; IMDB " + s.getString("imdbRating"));
                        if(answer) {
                            if (type==3) {
                                question = movie + questions[0] + s.getString("Year");
                            } else if (type==2) {
                                question = movie + questions[1] + s.getString("Rated");
                            } else {
                                question = movie + questions[2] + s.getString("imdbRating");
                            }
                        } else {
                            if (type==3) {
                                question = movie + questions[0] + (Integer.parseInt(s.getString("Year")) - 2);
                            } else if (type==2) {
                                question = movie + questions[1] + getRandomRate(s.getString("Rated"));
                            } else {
                                if(Double.parseDouble(s.getString("imdbRating")) > 9.5) {
                                    question = movie + questions[2] + (Double.parseDouble(s.getString("imdbRating")) - .5);
                                } else {
                                    question = movie + questions[2] + (Double.parseDouble(s.getString("imdbRating")) + .5);
                                }
                            }
                        }
                        currentQuestion = new Question(answer, question);

                    } else {
                        currentQuestion = new Question(true, "The sky is blue.");
                    }
                } catch (JSONException e) {
                    currentQuestion = new Question(true, "The sky is blue.");
                    e.printStackTrace();
                }
            }
        }
    }
}
