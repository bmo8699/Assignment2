package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Timer {
    private static final int STARTTIME = 12100;
    private Timeline timeline;
    public int time = STARTTIME;
    public void setTimer(Label timeLeft) {
        if (timeline != null) {
            timeline.stop();
        }
        time = STARTTIME;

        // update timerLabel
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(10),
                        new EventHandler() {
                            // KeyFrame event handler
                            int minutes, seconds, centiseconds;
                            public void handle(Event event) {
                                --time;
                                minutes = time / 6000;
                                seconds =  time % 6000 / 100;
                                centiseconds = time % 6000 % 100;
                                // update timerLabel
                                timeLeft.setText(minutes + ":" + seconds + ":" + centiseconds );
                                if (time <= 0) {
                                    timeline.stop();
                                }
                            }
                        }));
        timeline.playFromStart();
    }
    public void stopTimer() {
            timeline.stop();
    }
}
