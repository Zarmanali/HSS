package com.fxm.demo.lesson;

import com.fxm.demo.coach.Coach;
import com.fxm.demo.learner.Learner;
import com.fxm.demo.review.Review;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private int gradeLevel;
    private LocalTime startTime;
    private LocalTime endTime;
    private String dayOfWeek;
    private Coach coach;
    private List<Learner> attendees;
    private List<Review> reviews;
    private static final int MAX_LEARNERS = 4;

    public Lesson(int gradeLevel,String dayOfWeek,LocalTime startTime, Coach coach) {
        this.gradeLevel = gradeLevel;
        this.startTime = startTime;
        this.endTime = startTime.plusHours(1); // Each lesson lasts for one hour
        this.dayOfWeek = dayOfWeek;
        this.coach = coach;
        this.attendees = new ArrayList<>();
        this.reviews = new ArrayList<>();

    }

    public Lesson() {
        this.attendees = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getVacancies() {
        return MAX_LEARNERS - this.attendees.size();
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public List<Learner> getParticipants() {
        return attendees;
    }

    public void setParticipants(List<Learner> participants) {
        this.attendees = participants;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> review) {
        this.reviews = review;
    }

    public boolean checkVacancy() {
        return this.attendees.size() < MAX_LEARNERS;
    }

    public void addAttendees(Learner learner) {
        if (checkVacancy()) {
            this.attendees.add(learner);
        } else {
            System.out.println("No vacancies available for this lesson.");
        }
    }

    public void removeAttendees(Learner learner) {

        if (this.attendees.contains(learner)) {
            this.attendees.remove(learner);
        } else {
            System.out.println("This learner is not an attendee of this lesson.");
        }

    }

    public void addReview(Review review) {
        if (!reviews.contains(review)) {
            reviews.add(review);
        }
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "gradeLevel=" + gradeLevel +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", coach=" + coach +
                ", attendees=" + attendees +
                ", reviews=" + reviews +
                '}';
    }
}
