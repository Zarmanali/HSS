package com.fxm.demo.learner;

import com.fxm.demo.lesson.Lesson;
import com.fxm.demo.review.Review;

import java.util.ArrayList;
import java.util.List;

public class Learner {
    private String name;
    private String gender;
    private int age;
    private String emergencyContactNumber;
    private int currentGradeLevel;

    private List<Lesson> bookedLessons;

    public Learner(String name, String gender, int age, String emergencyContactNumber, int currentGradeLevel) {
        this.name = name;
        this.gender = gender;
        setAge(age);
        this.emergencyContactNumber = emergencyContactNumber;
        setCurrentGradeLevel(currentGradeLevel);
    }

    public Learner() {
        this.bookedLessons = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 4 && age <= 11) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Age must be between 4 and 11.");
        }
    }

    public List<Lesson> getBookedLessons() {
        return bookedLessons;
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public int getCurrentGradeLevel() {
        return currentGradeLevel;
    }

    public void setCurrentGradeLevel(int currentGradeLevel) {
        if (currentGradeLevel >= 0 && currentGradeLevel <= 5) {
            this.currentGradeLevel = currentGradeLevel;
        } else {
            throw new IllegalArgumentException("Grade level must be between 0 and 5.");
        }
    }

    public void bookLesson(Lesson lesson){
        if (lesson.checkVacancy() && !bookedLessons.contains(lesson) &&
                (lesson.getGradeLevel() == this.currentGradeLevel || lesson.getGradeLevel() == this.currentGradeLevel + 1)) {
            lesson.addAttendees(this); // Add this learner to the lesson's attendees
            bookedLessons.add(lesson); // Add the lesson to this learner's booked lessons
        } else {
            System.out.println("Unable to book the lesson. No vacancies, already booked, or grade level inappropriate.");
        }
    }

    public void changeBooking(Lesson oldLesson, Lesson newLesson){
        if (bookedLessons.contains(oldLesson) && newLesson.checkVacancy()) {
            cancelBooking(oldLesson);
            bookLesson(newLesson);
        } else {
            System.out.println("Unable to change booking. Check if old lesson is booked and new lesson has vacancies.");
        }
    }

    public void cancelBooking(Lesson lesson){
        if (bookedLessons.contains(lesson)) {
            lesson.removeAttendees(this);
            bookedLessons.remove(lesson);
        } else {
            System.out.println("Unable to cancel booking. Lesson not found in booked lessons.");
        }
    }

    public void writeReview(Lesson lesson, int rating, String text){
        if (bookedLessons.contains(lesson)) {
            lesson.addReview(new Review(this, rating, text));
        } else {
            System.out.println("Unable to write review. Lesson must be booked to write a review.");
        }

    }

    @Override
    public String toString() {
        return "Learner{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", emergencyContactNumber='" + emergencyContactNumber + '\'' +
                ", currentGradeLevel=" + currentGradeLevel +
                '}';
    }
}
