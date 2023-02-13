package CS255;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InitializeUI extends Application {

    private int green_col = 255; //just for the test example

    @Override
    public void start(Stage stage) {
        stage.setTitle("Ray Tracing");

        //We need 3 things to see an image
        //1. We create an image we can write to
        int width = 640;
        int height = 640;
        WritableImage image = new WritableImage(width, height);

        //2. We create a view of that image
        ImageView view = new ImageView(image);

        //3. Add to the pane (below)
        //Create the simple GUI
        Slider g_slider = new Slider(0, 255, green_col);

        //Add all the event handlers
        g_slider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    green_col = newValue.intValue();
                    Render(image);
                });

        //The following is in case you want to interact with the image in any way
        //e.g., for user interaction, or you can find out the pixel position for
        //debugging
        view.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
            System.out.println(event.getX() + " " + event.getY());
            event.consume();
        });
        Render(image);

        GridPane root = new GridPane();
        root.setVgap(12);
        root.setHgap(12);

        //3. (referring to the 3 things we need to display an image)
        //we need to add it to the pane
        root.add(view, 0, 0);
        root.add(g_slider, 0, 1);

        //Display to user
        Scene scene = new Scene(root, 1024, 768);
        stage.setScene(scene);
        stage.show();
    }

    public void Render(WritableImage image) {
        //Get image dimensions, and declare loop variables
        int w = (int) image.getWidth(), h = (int) image.getHeight(), i, j;
        PixelWriter image_writer = image.getPixelWriter();

        double col;
        Vector o = new Vector(0, 0, 0);
        Vector d = new Vector(0, 0, 1);
        Vector cs = new Vector(-100, 0, 0);

        double r = 100;
        Vector p;
        double t;
        double a, b, c;
        Vector v;
        Vector Light = new Vector(250, 250, -200);

        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                o.setX(i - 250);
                o.setY(j - 250);
                o.setZ(-200);
                v = o.sub(cs);

                a = d.dot(d);
                b = 2 * v.dot(d);
                c = v.dot(v) - r * r;

                double disc = b * b - 4 * a * c;
                
                t = (-b - Math.sqrt(disc)) / 2 * a;
                p = o.add(d.mul(t));
                Vector lV = Light.sub(p);
                lV.normalise();
                Vector n = p.sub(cs);
                n.normalise();
                double dp = lV.dot(n);

                if (dp < 0) col = 0.0;
                else col = dp;
                if (col > 1) col = 1.0;

                image_writer.setColor(i, j, Color.color(col, col, col, 1.0));
            } // column loop
        } // row loop
    }

    public static void main(String[] args) {
        launch();
    }

}