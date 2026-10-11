package net.minheur.keepFocus.pages;

import atlantafx.base.controls.RingProgressIndicator;
import atlantafx.base.theme.Styles;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import net.minheur.keepFocus.content.FocusController;
import net.minheur.keepFocus.content.FocusSession;
import net.minheur.keepFocus.content.FocusTimer;
import net.minheur.potoflux.translations.Translations;
import org.jetbrains.annotations.Nullable;

import static net.minheur.potoflux.Functions.formatMessage;

public class TimerPane extends VBox {
    private final FocusSession session;

    private final Label
            task = new Label(),
            timer = new Label("25:00"),
            sessionStage = new Label();

    private final RingProgressIndicator timerRing = new RingProgressIndicator(1, true);

    private final Button
            start = new Button(Translations.get("keep_focus:tabs.focus.button.start")),
            pause = new Button(Translations.get("keep_focus:tabs.focus.button.pause")),
            stop = new Button(Translations.get("keep_focus:tabs.focus.button.stop")),
            finished = new Button(Translations.get("keep_focus:earlyFinished.button"));

    public TimerPane(int totalSessions, String objective, Duration sessionDuration, Duration pauseDuration, Duration endDuration) {
        super(20);

        session = new FocusSession(
                totalSessions, objective, new FocusTimer(this::updateTimer), this::updateObjectiveLabel, sessionDuration, pauseDuration, endDuration
        );

        Label taskTitle = new Label(Translations.get("keep_focus:tabs.focus.task"));
        VBox taskBox = new VBox(5, taskTitle, task);
        taskBox.setMaxWidth(350);

        timerRing.getStyleClass().add("timer");
        timer.getStyleClass().add(Styles.TITLE_4);
        timer.setAlignment(Pos.CENTER);

        timerRing.setGraphic(timer);

        stop.setDisable(true);
        pause.setDisable(true);

        start.setOnAction(event -> startSession());
        pause.setOnAction(event -> pauseSession());
        stop.setOnAction(event -> stopSession());
        finished.setOnAction(event -> FocusController.finished()); // TODO: local

        updateSessionStage(1, 4);

        HBox buttons = new HBox(10, start, pause, stop);
        buttons.setAlignment(Pos.CENTER);

        getStyleClass().add("card");
        setAlignment(Pos.CENTER);
        setMaxWidth(420);

        getChildren().addAll(
                taskBox,
                timerRing,
                buttons,
                finished,
                sessionStage
        );
    }

    private void startSession() {
        updateButtonStates(false, true, true);
        session.start();
    }
    private void pauseSession() {
        updateButtonStates(true, false, null);
        session.timer.pause();
    }
    private void stopSession() {
        updateButtonStates(true, false, false);
        session.timer.cancel();
        resetTime();
    }

    private void resetTime() {
        // todo
    }

    public void updateSessionStage(int actual, int total) {
        sessionStage.setText(formatMessage(Translations.get("keep_focus:tabs.focus.sessionLabel"), actual, total));
    }
    public void makeSessionEarlyFinished() {
        sessionStage.setText(Translations.get("keep_focus:earlyFinished.session"));
    }

    public void updateButtonStates(@Nullable Boolean startEnabled, @Nullable Boolean pauseEnabled, @Nullable Boolean stopEnabled) {
        if (startEnabled != null) start.setDisable(!startEnabled);
        if (pauseEnabled != null) pause.setDisable(!pauseEnabled);
        if (stopEnabled != null) stop.setDisable(!stopEnabled);
    }
    public void updateFinishedButton(boolean enabled) {
        finished.setDisable(!enabled);
    }
    public void updateTimer(String content, double progress) {
        timer.setText(content);
        timerRing.setProgress(progress);
    }
    public void updateObjectiveLabel(String content) {
        task.setText(content);
    }
}
