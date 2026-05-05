package com.example.memorygame;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Card extends Pane {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 100;

    private Rectangle front;
    private Rectangle back;
    private boolean faceUp = false;

    public Card(Color color) {
        // front: colored rectangle
        front = new Rectangle(WIDTH, HEIGHT, color);
        front.setVisible(false);

        // back: grey rectangle
        back = new Rectangle(WIDTH, HEIGHT, Color.DARKGRAY);
        back.setStroke(Color.WHITE);
        back.setStrokeWidth(2);

        getChildren().addAll(front, back);

        setOnMouseClicked(e -> flip());

    }

    public void flip() {
        faceUp = !faceUp;
        front.setVisible(faceUp);
        back.setVisible(!faceUp);
    }

    public Color getColor() {
        return (Color) front.getFill();
    }

    public boolean isFaceUp() {
        return faceUp;
    }
}
