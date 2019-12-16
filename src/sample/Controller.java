package sample;


import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Controller {
    @FXML
    private Pane menuPane, gameplayPane, difficultyPane, lockPane;

    @FXML
    private Button newGameButton, difficultyButton, mainMenuButton1, mainMenuButton2,
                    easyButton, mediumButton, hardButton,
                    playAgainButton, quitButton;

    @FXML
    private Label levelText, timeLeft, scoreLabel, messageLabel;

    @FXML
    private ImageView img00,img01,img02,img03,img04,
                        img10,img11,img12,img13,img14,
                        img20,img21,img22,img23,img24,
                        img30,img31,img32,img33,img34;


    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == newGameButton || event.getSource() == playAgainButton) {
            gameplayPane.toFront();
            setTimer();
            loadCard();
            checkFinishedGame();
            dumpMatchedList();
        } else if (event.getSource() == difficultyButton) {
            difficultyPane.toFront();
        } else if (event.getSource() == mainMenuButton2 ) {
            dumpMatchedList();
            menuPane.toFront();
            delay.stop();
        } else if (event.getSource() == mainMenuButton1 ) {
            menuPane.toFront();
            delay.stop();
        } else if (event.getSource() == quitButton) {
            System.exit(0);
        }
    }

    Game newGame = new Game();
    @FXML
    private void handleDifficultyLevel(ActionEvent event) {
        if (event.getSource() == easyButton) {
            levelText.setText("Easy mode selected");
            newGame.levelTime = 3000;
            System.out.println(newGame.levelTime);
        } else if (event.getSource() == mediumButton) {
            levelText.setText("Medium mode selected");
            newGame.levelTime = 2000;
            System.out.println(newGame.levelTime);
        } else if (event.getSource() == hardButton) {
            levelText.setText("Hard mode selected");
            newGame.levelTime = 1000;
            System.out.println(newGame.levelTime);
        }
    }

    int imageCounter = 0;
    ImageView currentImage, previousImage = null;
    FadeTransition ft2 = null;
    PauseTransition delay2 = null;
    ImageView[] matchedList = new ImageView[20];
    int i = 0;
    int score = 0;
    @FXML
    private void handleCardClicked(MouseEvent event) {
        ++imageCounter;
        if (imageCounter <= 2) {
            FadeTransition ft = new FadeTransition();
            ft.setDuration(Duration.millis(1));
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setDelay(Duration.millis(newGame.levelTime));
            currentImage = (ImageView)event.getPickResult().getIntersectedNode();
            currentImage.setOpacity(1);
            ft.setNode(currentImage);
            ft.playFromStart();
            for (ImageView a:matchedList) {
                if (currentImage == a) {
                    ft.stop();
                    --imageCounter;
                    return;
                }
            }
            PauseTransition delay = new PauseTransition(Duration.millis(newGame.levelTime));
            delay.setOnFinished( e -> {
                --imageCounter;
            });
            delay.playFromStart();
            if (currentImage == previousImage) {
                delay.stop();
                --imageCounter;
            }
            if (imageCounter == 2) {
                if (checkMatchingImage(previousImage,currentImage)) {
                    delay.stop();
                    delay2.stop();
                    imageCounter = 0;
                    if (i < 20) {
                        ft.stop();
                        ft2.stop();
                        matchedList[i] = previousImage;
                        matchedList[i+1] = currentImage;
                        if (i == 18) {
                            stopTimer();
                            score = score + timer.time / 100;
                            scoreLabel.setText(Integer.toString(score));
                            lockPane.toFront();
                            i = 0;
                            return;
                        }
                        i = i + 2;
                    }
                }
            }
            previousImage = currentImage;
            ft2 = ft;
            delay2 = delay;
        } else {
            --imageCounter;
        }
    }

    final URL mediaURL = getClass().getResource("Audio/WorldCup2010.mp3");
    final String path = mediaURL.toExternalForm();
    Media media = new Media(path);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    Boolean sound = false;
    public void soundSettings(ActionEvent event) {
        if (sound == false) {
            mediaPlayer.play();
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            sound = true;
        } else {
            mediaPlayer.pause();
            sound = false;
        }
    }

    Timer timer = new Timer();
    public void setTimer() {
        timer.setTimer(timeLeft);
    }

    public void stopTimer() {
        timer.stopTimer();
    }

    ArrayList<String> imagePathList = new ArrayList<String>();
    public void loadCard() {
        for (int a = 0; a < 2; a++) {
            for (int i = 1; i <= 10; i++) {
                imagePathList.add("Images/Card/" + i + ".jpg");
            }
        }
        Collections.shuffle(imagePathList);
        img00.setImage(new Image(getClass().getResource(imagePathList.get(0)).toExternalForm()));
        img01.setImage(new Image(getClass().getResource(imagePathList.get(1)).toExternalForm()));
        img02.setImage(new Image(getClass().getResource(imagePathList.get(2)).toExternalForm()));
        img03.setImage(new Image(getClass().getResource(imagePathList.get(3)).toExternalForm()));
        img04.setImage(new Image(getClass().getResource(imagePathList.get(4)).toExternalForm()));
        img10.setImage(new Image(getClass().getResource(imagePathList.get(5)).toExternalForm()));
        img11.setImage(new Image(getClass().getResource(imagePathList.get(6)).toExternalForm()));
        img12.setImage(new Image(getClass().getResource(imagePathList.get(7)).toExternalForm()));
        img13.setImage(new Image(getClass().getResource(imagePathList.get(8)).toExternalForm()));
        img14.setImage(new Image(getClass().getResource(imagePathList.get(9)).toExternalForm()));
        img20.setImage(new Image(getClass().getResource(imagePathList.get(10)).toExternalForm()));
        img21.setImage(new Image(getClass().getResource(imagePathList.get(11)).toExternalForm()));
        img22.setImage(new Image(getClass().getResource(imagePathList.get(12)).toExternalForm()));
        img23.setImage(new Image(getClass().getResource(imagePathList.get(13)).toExternalForm()));
        img24.setImage(new Image(getClass().getResource(imagePathList.get(14)).toExternalForm()));
        img30.setImage(new Image(getClass().getResource(imagePathList.get(15)).toExternalForm()));
        img31.setImage(new Image(getClass().getResource(imagePathList.get(16)).toExternalForm()));
        img32.setImage(new Image(getClass().getResource(imagePathList.get(17)).toExternalForm()));
        img33.setImage(new Image(getClass().getResource(imagePathList.get(18)).toExternalForm()));
        img34.setImage(new Image(getClass().getResource(imagePathList.get(19)).toExternalForm()));
        img00.setOpacity(0);
        img01.setOpacity(0);
        img02.setOpacity(0);
        img03.setOpacity(0);
        img04.setOpacity(0);
        img10.setOpacity(0);
        img11.setOpacity(0);
        img12.setOpacity(0);
        img13.setOpacity(0);
        img14.setOpacity(0);
        img20.setOpacity(0);
        img21.setOpacity(0);
        img22.setOpacity(0);
        img23.setOpacity(0);
        img24.setOpacity(0);
        img30.setOpacity(0);
        img31.setOpacity(0);
        img32.setOpacity(0);
        img33.setOpacity(0);
        img34.setOpacity(0);
        messageLabel.setText("Your score:");
        imagePathList.clear();
    }

    public boolean checkMatchingImage(ImageView img1, ImageView img2) {
        if (img1 != img2) {
            if (img1.getImage().getUrl().equals(img2.getImage().getUrl())) {
                return true;
            }
        }
        return false;
    }

    public void dumpMatchedList() {
        for (int x = 0; x < 20; x++) {
            matchedList[x] = null;
        }
    }

    PauseTransition delay = new PauseTransition(Duration.seconds(120));
    public void checkFinishedGame() {
        delay.setOnFinished( event -> {
            lockPane.toFront();
            if (matchedList[19] == null) {
                scoreLabel.setText("Better luck next time");
                messageLabel.setText("You lost");
            }
            dumpMatchedList();
            score = 0;

        });
        delay.playFromStart();
    }
}
