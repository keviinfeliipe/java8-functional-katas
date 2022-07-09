package katas;

import model.BoxArt;
import model.Movie;
import util.DataUtil;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;

/*
    Goal: Retrieve the url of the largest boxart using map() and reduce()
    DataSource: DataUtil.getMovieLists()
    Output: String
*/
public class Kata6 {
    public static String execute() {

        List<Movie> movies = DataUtil.getMovies();

        return movies.stream()
                .map(Movie::getBoxarts)
                .reduce(maxWidthBoxArt::apply)
                .orElseThrow()
                .stream()
                .map(BoxArt::getUrl)
                .findFirst()
                .orElseThrow();
    }

    static BinaryOperator<List<BoxArt>> maxWidthBoxArt = (boxArt, boxArt2) -> {
        var box1 = getBoxMaxWidth(boxArt);
        var box2 = getBoxMaxWidth(boxArt2);
        var box = (box1.getWidth() > box2.getWidth() ? box1 : box2);
        return Collections.singletonList(box);
    };

    private static BoxArt getBoxMaxWidth(List<BoxArt> boxArt) {
        return boxArt.stream()
                .max(Comparator.comparing(BoxArt::getWidth))
                .orElseThrow();
    }

}
