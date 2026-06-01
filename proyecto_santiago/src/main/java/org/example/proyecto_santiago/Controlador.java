package org.example.proyecto_santiago;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Controlador {

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Slider sizeSlider;

    @FXML
    private Label toolLabel;

    private GraphicsContext gc;

    private Modelo currentTool = Modelo.PENCIL;

    private double startX;
    private double startY;

    @FXML
    public void initialize() {

        gc = canvas.getGraphicsContext2D();

        clearCanvas();

        gc.setLineWidth(3);

        setupCanvasEvents();
    }

    private void setupCanvasEvents() {

        canvas.setOnMousePressed(e -> {

            startX = e.getX();
            startY = e.getY();

            gc.setLineWidth(sizeSlider.getValue());

            if (currentTool == Modelo.ERASER) {
                gc.setStroke(Color.WHITE);
            } else {
                gc.setStroke(colorPicker.getValue());
            }

            switch (currentTool) {

                case PENCIL:
                case ERASER:

                    gc.beginPath();
                    gc.moveTo(startX, startY);
                    gc.stroke();
                    break;
            }
        });

        canvas.setOnMouseDragged(e -> {

            switch (currentTool) {

                case PENCIL:
                case ERASER:

                    gc.lineTo(e.getX(), e.getY());
                    gc.stroke();
                    break;
            }
        });

        canvas.setOnMouseReleased(e -> {

            double endX = e.getX();
            double endY = e.getY();

            gc.setStroke(colorPicker.getValue());

            switch (currentTool) {

                case RECTANGLE:

                    gc.strokeRect(
                            Math.min(startX, endX),
                            Math.min(startY, endY),
                            Math.abs(endX - startX),
                            Math.abs(endY - startY)
                    );
                    break;

                case CIRCLE:

                    gc.strokeOval(
                            Math.min(startX, endX),
                            Math.min(startY, endY),
                            Math.abs(endX - startX),
                            Math.abs(endY - startY)
                    );
                    break;

                case LINE:

                    gc.strokeLine(startX, startY, endX, endY);
                    break;
            }
        });
    }

    @FXML
    private void usePencil() {
        currentTool = Modelo.PENCIL;
        toolLabel.setText("Herramienta: Lápiz");
    }

    @FXML
    private void useEraser() {
        currentTool = Modelo.ERASER;
        toolLabel.setText("Herramienta: Borrador");
    }

    @FXML
    private void useRectangle() {
        currentTool = Modelo.RECTANGLE;
        toolLabel.setText("Herramienta: Rectángulo");
    }

    @FXML
    private void useCircle() {
        currentTool = Modelo.CIRCLE;
        toolLabel.setText("Herramienta: Círculo");
    }

    @FXML
    private void useLine() {
        currentTool = Modelo.LINE;
        toolLabel.setText("Herramienta: Línea");
    }

    @FXML
    private void clearCanvas() {

        gc.setFill(Color.WHITE);

        gc.fillRect(
                0,
                0,
                canvas.getWidth(),
                canvas.getHeight()
        );
    }

    @FXML
    private void saveImage() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Guardar imagen");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {

            try {

                WritableImage image = canvas.snapshot(
                        new SnapshotParameters(),
                        null
                );

                String extension = "png";

                if (file.getName().endsWith(".jpg")) {
                    extension = "jpg";
                }

                ImageIO.write(
                        SwingFXUtils.fromFXImage(image, null),
                        extension,
                        file
                );

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setHeaderText(null);
                alert.setContentText("Imagen guardada correctamente");

                alert.showAndWait();

            } catch (IOException ex) {

                ex.printStackTrace();
            }
        }
    }
}