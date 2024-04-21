package com.fxm.demo.review;

import com.fxm.demo.learner.Learner;
import com.fxm.demo.lesson.Lesson;

public class Review {
    private int rating;
    private String comment;
    private Learner reviewer;
    private Lesson lesson;


    public Review(Learner reviewer, int rating, String comment) {
        this.reviewer = reviewer;
        this.rating = rating;
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Learner getLearner() {
        return reviewer;
    }

    public void setLearner(Learner reviewer) {
        this.reviewer = reviewer;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
