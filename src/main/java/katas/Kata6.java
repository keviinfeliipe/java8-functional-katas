package katas;

import model.BoxArt;
import model.Movie;
import util.DataUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

/*
    Goal: Retrieve the url of the largest boxart using map() and reduce()
    DataSource: DataUtil.getMovieLists()
    Output: String
*/
public class Kata6 {
    public static String execute() {

        List<Movie> movies = DataUtil.getMovies();

        BiFunction<List<BoxArt>, List<BoxArt>, List<BoxArt>> maxWidthBoxArt = (boxArt, boxArt2) -> {
            var box1 = boxArt.stream()
                    .max(Comparator.comparing(BoxArt::getWidth))
                    .orElseThrow();
            var box2 = boxArt2.stream()
                    .max(Comparator.comparing(BoxArt::getWidth))
                    .orElseThrow();
            return Collections.singletonList((box1.getWidth() > box2.getWidth() ? box1 : box2));
        };

        return movies.stream()
                .map(Movie::getBoxarts)
                .reduce(maxWidthBoxArt::apply)
                .orElseThrow()
                .stream()
                .map(boxArt -> boxArt.getUrl())
                .findFirst()
                .orElseThrow();
    }

}
