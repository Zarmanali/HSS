package com.fxm.demo.timetable;

import com.fxm.demo.coach.Coach;
import com.fxm.demo.learner.Learner;
import com.fxm.demo.lesson.Lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Timetable {

    private List<Lesson> lessons;

    public Timetable() {
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    public List<Lesson> viewByDay(String day) {
        return lessons.stream()
                .filter(lesson -> lesson.getDayOfWeek().equalsIgnoreCase(day))
                .collect(Collectors.toList());
    }

    public List<Lesson> viewByGrade(int gradeLevel) {
        return lessons.stream()
                .filter(lesson -> lesson.getGradeLevel() == gradeLevel)
                .collect(Collectors.toList());
    }

    public List<Lesson> viewByCoach(String coachName) {
        return lessons.stream()
                .filter(lesson -> lesson.getCoach().getName().equalsIgnoreCase(coachName))
                .collect(Collectors.toList());
    }

    public void generateLearnerReports(List<Learner> learners) {
        for (Learner learner : learners) {
            System.out.println(learner + " Booked Lessons: " + learner.getBookedLessons().size());

        }
    }

    public void generateCoachReports(List<Coach> coaches) {
        for (Coach coach : coaches) {
            System.out.println(coach + " Average Rating: " + coach.calculateAverageRating());
        }
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "lessons=" + lessons +
                '}';
    }
}
