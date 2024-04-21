package com.fxm.demo;

import com.fxm.demo.coach.Coach;
import com.fxm.demo.learner.Learner;
import com.fxm.demo.lesson.Lesson;
import com.fxm.demo.review.Review;
import com.fxm.demo.timetable.Timetable;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingSystem extends Application {
    private List<Learner> learners = new ArrayList<>();
    private List<Coach> coaches = new ArrayList<>();
    private Timetable timetable = new Timetable();
    private TableView<Lesson> lessonTableView = new TableView<>();
    private TableView<Lesson> bookedLessonsTableView = new TableView<>(); // Table for booked lessons
    private ObservableList<Lesson> bookedLessons = FXCollections.observableArrayList(); // List to store booked lessons


    @Override
    public void start(Stage stage) {
        setupData();
        setupTableView();
        setupBookedLessonsTableView(); // Setup the table for booked lessons
        stage.setTitle("Hatfield Junior Swimming School Booking System");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(buildViewPane(), buildBookingPane(), lessonTableView,bookedLessonsTableView);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    private void setupBookedLessonsTableView() {
        TableColumn<Lesson, String> dayColumn = new TableColumn<>("Day");
        dayColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDayOfWeek()));

        TableColumn<Lesson, String> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setCellValueFactory(cellData -> {
            int gradeLevel = cellData.getValue().getGradeLevel();
            return new SimpleStringProperty(Integer.toString(gradeLevel));
        });

        TableColumn<Lesson, String> startTimeColumn = new TableColumn<>("Start Time");
        startTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartTime().toString()));

        TableColumn<Lesson, String> coachColumn = new TableColumn<>("Coach");
        coachColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCoach().getName()));

        bookedLessonsTableView.getColumns().addAll(gradeColumn, dayColumn, startTimeColumn, coachColumn);
        bookedLessonsTableView.setItems(bookedLessons);
        bookedLessonsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    private void setupData() {
        Coach coach1 = new Coach("Helen");
        Coach coach2 = new Coach("Mark");
        Coach coach3 = new Coach("Linda");
        coaches.addAll(List.of(coach1, coach2, coach3));

        for (int i = 1; i <= 15; i++) {
            learners.add(new Learner("Learner" + i, "Male", 6, "1234567890", 1));
        }

        String[] days = {"Monday", "Wednesday", "Friday", "Saturday"};
        LocalTime[] startTimes = {LocalTime.of(16, 0), LocalTime.of(17, 0), LocalTime.of(18, 0), LocalTime.of(14, 0), LocalTime.of(15, 0)};
        int[] grades = {1, 2, 3, 4, 5};

        for (String day : days) {
            for (LocalTime startTime : startTimes) {
                for (int grade : grades) {
                    for (Coach coach : coaches) {
                        Lesson lesson = new Lesson(grade, day, startTime, coach);
                        timetable.addLesson(lesson);
                    }
                }
            }
        }
    }

    private VBox buildViewPane() {
        VBox layout = new VBox(10);
        ComboBox<String> viewOptions = new ComboBox<>();
        viewOptions.getItems().addAll("View by Day", "View by Grade", "View by Coach");

        ComboBox<String> dayOptions = new ComboBox<>();
        dayOptions.getItems().addAll("Monday", "Wednesday", "Friday", "Saturday");

        ComboBox<Integer> gradeOptions = new ComboBox<>();
        for (int i = 1; i <= 5; i++) {
            gradeOptions.getItems().add(i);
        }

        ComboBox<String> coachOptions = new ComboBox<>();
        coaches.forEach(coach -> coachOptions.getItems().add(coach.getName()));

        // Setup action listeners for each ComboBox
        dayOptions.setOnAction(e -> updateViewByDay(dayOptions.getValue()));
        gradeOptions.setOnAction(e -> updateViewByGrade(gradeOptions.getValue()));
        coachOptions.setOnAction(e -> updateViewByCoach(coachOptions.getValue()));

        // Update view on action for viewOptions ComboBox
        viewOptions.setOnAction(e -> {
            String selectedOption = viewOptions.getValue();
            switch (selectedOption) {
                case "View by Day":
                    updateViewByDay(dayOptions.getValue());
                    break;
                case "View by Grade":
                    updateViewByGrade(gradeOptions.getValue());
                    break;
                case "View by Coach":
                    updateViewByCoach(coachOptions.getValue());
                    break;
            }
        });

        Button clearSelections = new Button("Clear Selections");
        clearSelections.setOnAction(e -> {

            dayOptions.getSelectionModel().clearSelection();
            gradeOptions.getSelectionModel().clearSelection();
            coachOptions.getSelectionModel().clearSelection();
            viewOptions.getSelectionModel().clearSelection();
            lessonTableView.getItems().clear();
        });

        layout.getChildren().addAll(new Label("View Lessons:"), viewOptions, dayOptions, gradeOptions, coachOptions, clearSelections);
        return layout;
    }



    private void updateViewByDay(String day) {
        lessonTableView.getItems().clear();
        if (day != null) {
            lessonTableView.getItems().addAll(timetable.viewByDay(day));
        }
    }

    private void updateViewByGrade(Integer grade) {
        lessonTableView.getItems().clear();
        if (grade != null) {
            lessonTableView.getItems().addAll(timetable.viewByGrade(grade));
        }
    }

    private void updateViewByCoach(String coachName) {
        lessonTableView.getItems().clear();
        if (coachName != null) {
            lessonTableView.getItems().addAll(timetable.viewByCoach(coachName));
        }
    }


    private void setupTableView() {
        TableColumn<Lesson, String> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setCellValueFactory(cellData -> {
            int gradeLevel = cellData.getValue().getGradeLevel();
            return new SimpleStringProperty(Integer.toString(gradeLevel));
        });


        TableColumn<Lesson, String> dayColumn = new TableColumn<>("Day");
        dayColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDayOfWeek()));

        TableColumn<Lesson, String> startTimeColumn = new TableColumn<>("Start Time");
        startTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartTime().toString()));

        TableColumn<Lesson, String> coachColumn = new TableColumn<>("Coach");
        coachColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCoach().getName()));

        lessonTableView.getColumns().addAll(gradeColumn, dayColumn, startTimeColumn, coachColumn);
        lessonTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void updateView(String viewOption) {
        lessonTableView.getItems().clear();
        switch (viewOption) {
            case "View by Day":
                lessonTableView.getItems().addAll(timetable.viewByDay("Monday"));
                break;
            case "View by Grade":
                lessonTableView.getItems().addAll(timetable.viewByGrade(1));
                break;
            case "View by Coach":
                lessonTableView.getItems().addAll(timetable.viewByCoach("Helen"));
                break;
            default:
                // Optionally handle the default case
                break;
        }
    }


    private VBox buildBookingPane() {
        Button bookButton = new Button("Book Lesson");
        Button cancelButton = new Button("Cancel Booking");
        Button writeReviewButton = new Button("Write Review");

        bookButton.setDisable(true);
        cancelButton.setDisable(true);
        writeReviewButton.setDisable(true);

        lessonTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            bookButton.setDisable(newSelection == null);
        });

        bookedLessonsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            cancelButton.setDisable(newSelection == null || !bookedLessons.contains(newSelection));
        });

        bookedLessonsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            writeReviewButton.setDisable(newSelection == null);
        });

        writeReviewButton.setOnAction(e -> {
            Lesson selectedLesson = bookedLessonsTableView.getSelectionModel().getSelectedItem();
            if (selectedLesson != null) {
                showReviewDialog(selectedLesson);
            }
        });

        bookButton.setOnAction(e -> {
            Lesson selectedLesson = lessonTableView.getSelectionModel().getSelectedItem();
            if (selectedLesson != null) {
                bookLesson(selectedLesson);
            }
        });

        cancelButton.setOnAction(e -> {
            Lesson selectedLesson = lessonTableView.getSelectionModel().getSelectedItem();
            if (selectedLesson != null && confirmCancellation()) {
                cancelLesson(selectedLesson);
            }
        });

        HBox buttonLayout = new HBox(10, bookButton, cancelButton, writeReviewButton);
        VBox layout = new VBox(10, lessonTableView, buttonLayout);
        layout.setPadding(new Insets(10));
        return layout;
    }

    private void bookLesson(Lesson selectedLesson) {
        if (selectedLesson.checkVacancy()) {
            if (!bookedLessons.contains(selectedLesson)) {
                Learner sampleLearner = learners.get(0);
                selectedLesson.addAttendees(sampleLearner);
                bookedLessons.add(selectedLesson);
                showAlert(Alert.AlertType.INFORMATION, "Booking Confirmation", "Lesson booked successfully!");
                lessonTableView.refresh();
                bookedLessonsTableView.refresh();
            } else {
                showAlert(Alert.AlertType.WARNING, "Booking Warning", "This lesson has already been booked.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Booking Error", "Failed to book lesson. No vacancies available.");
        }
    }

    private void cancelLesson(Lesson selectedLesson) {
        if (bookedLessons.contains(selectedLesson)) {
            bookedLessons.remove(selectedLesson);
            showAlert(Alert.AlertType.INFORMATION, "Cancellation Confirmation", "Lesson cancellation successful.");
            bookedLessonsTableView.refresh();
        } else {
            showAlert(Alert.AlertType.ERROR, "Cancellation Error", "This lesson was not booked or already cancelled.");
        }
    }

    private void showReviewDialog(Lesson lesson) {
        Dialog<Review> dialog = new Dialog<>();
        dialog.setTitle("Write Review");
        dialog.setHeaderText("Review for Lesson on " + lesson.getDayOfWeek() + " with " + lesson.getCoach().getName());

        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        VBox vbox = new VBox();
        TextArea commentField = new TextArea();
        commentField.setPromptText("Write your review here");

        ComboBox<Integer> ratingComboBox = new ComboBox<>();
        ratingComboBox.getItems().addAll(1, 2, 3, 4, 5);
        ratingComboBox.setPromptText("Rating");

        ComboBox<Learner> learnerComboBox = new ComboBox<>();
        learnerComboBox.getItems().addAll(learners);
        learnerComboBox.setPromptText("Select Learner");

        vbox.getChildren().addAll(new Label("Learner:"), learnerComboBox, new Label("Review:"), commentField, new Label("Rating:"), ratingComboBox);
        vbox.setSpacing(10);

        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType && ratingComboBox.getValue() != null && learnerComboBox.getValue() != null) {
                Review review = new Review(learnerComboBox.getValue(), ratingComboBox.getValue(), commentField.getText());
                review.setLesson(lesson);
                return review;
            }
            return null;
        });

        Optional<Review> result = dialog.showAndWait();

        result.ifPresent(review -> {
            System.out.println("Review: " + review.getComment() + ", Rating: " + review.getRating() + ", by " + review.getLearner().getName());
            lesson.addReview(review);
            showAlert(Alert.AlertType.INFORMATION, "Review Added", "Your review has been successfully added to the lesson.");
        });
    }


    private boolean confirmCancellation() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this booking?", ButtonType.YES, ButtonType.NO);
        confirmation.setHeaderText(null);
        confirmation.setTitle("Confirm Cancellation");
        Optional<ButtonType> result = confirmation.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
