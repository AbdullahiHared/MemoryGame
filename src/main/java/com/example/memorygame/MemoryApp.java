package com.example.memorygame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MemoryApp extends Application {
    private Label statusLabel = new Label("Find a pair!");
    private List<Card> cardList = new ArrayList<>();
    private int attempts = 0;
    private int matches = 0;
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        cardList = new ArrayList<>();

        Pane cardPane = new Pane();
        cardPane.setPrefSize(500, 400);
        Image bgImage = new Image(
                getClass().getResourceAsStream("/com/example/memorygame/card_back.png")
        );
        ImageView bgView = new ImageView(bgImage);
        bgView.fitWidthProperty().bind(cardPane.prefWidthProperty());
        bgView.fitHeightProperty().bind(cardPane.prefHeightProperty());
        cardPane.getChildren().add(bgView);
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE};
        for (Color c : colors) {
            cardList.add(new Card(c));
            cardList.add(new Card(c));
        }

        Collections.shuffle(cardList);

        int[][] positions = {
                {30,50}, {150,50}, {270,50}, {390,50},
                {30,200}, {150,200}, {270,200}, {390,200}
        };
        for (int i = 0; i < cardList.size(); i++) {
            cardList.get(i).setLayoutX(positions[i][0]);
            cardList.get(i).setLayoutY(positions[i][1]);
            cardPane.getChildren().add(cardList.get(i));
        }

        root.setCenter(cardPane);

        Button checkBtn = new Button("Check");
        checkBtn.setOnAction(e -> {
            List<Card> faceUp = new ArrayList<>();
            for(Card c: cardList) {
                if(c.isFaceUp() && !c.isDisabled()) faceUp.add(c);
            }

            if(faceUp.size() != 2) {
                statusLabel.setText("Flip exactly 2 cards first");
                return;
            }

            attempts++;
            if(faceUp.get(0).getColor().equals(faceUp.get(1).getColor())) {
                matches++;
                statusLabel.setText("Match! " + matches + " pair(s) found");
                faceUp.forEach(c-> c.setDisable(true));
            } else {
                statusLabel.setText("No match. Try Again!");
                faceUp.forEach(c->c.flip());
            }
        });

        HBox bottom = new HBox(10, statusLabel, checkBtn);
        bottom.setPadding(new Insets(8));
        root.setBottom(bottom);


        Scene scene = new Scene(root, 520, 480);
        stage.setTitle("Memory Game");
        stage.setScene(scene);
        cardPane.prefWidthProperty().bind(stage.widthProperty().subtract(20));
        cardPane.prefHeightProperty().bind(stage.heightProperty().subtract(60));
        stage.show();
    }
}
