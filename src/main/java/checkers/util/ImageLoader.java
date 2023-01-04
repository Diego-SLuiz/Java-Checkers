package checkers.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;


public class ImageLoader {
    public static ImageView load(String path, Integer width, Integer height) {
        String homePath = new File("").getAbsolutePath();
        String resourcePath = "\\src\\main\\resources\\checkers\\";
        String imagePath = homePath.concat(resourcePath).concat(path);

        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(imagePath);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        return imageView;
    }

}
