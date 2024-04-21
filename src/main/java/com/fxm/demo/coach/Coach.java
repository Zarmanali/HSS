package com.fxm.demo.coach;

import com.fxm.demo.lesson.Lesson;

import java.util.List;

public class Coach {
    private String name;
    private List<Lesson> lessonsTaught;

    public Coach(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lesson> getLessonsTaught() {
        return lessonsTaught;
    }

    public void setLessonsTaught(List<Lesson> lessonsTaught) {
        this.lessonsTaught = lessonsTaught;
    }

    public double calculateAverageRating(){
        return 0;
    }
}
