package katas;

import com.google.common.collect.ImmutableMap;
import model.BoxArt;
import model.Movie;
import model.MovieList;
import util.DataUtil;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Goal: Retrieve the id, title, and smallest box art url for every video
    DataSource: DataUtil.getMovieLists()
    Output: List of ImmutableMap.of("id", "5", "title", "Bad Boys", "boxart": "url)
*/
public class Kata7 {
    public static List<Map> execute() {
        List<MovieList> movieLists = DataUtil.getMovieLists();

        return movieLists.stream()
                .flatMap(movieList -> movieList.getVideos().stream())
                .map(Kata7::getMovie)
                .collect(Collectors.toList());
    }

    private static Map<String, ? extends Serializable> getMovie(Movie movie) {
        return ImmutableMap.of(
                "id", movie.getId(),
                "title", movie.getTitle(),
                "boxart", getUrl(movie));
    }

    private static String getUrl(Movie movie) {
        return movie.getBoxarts().stream()
                .min(Comparator.comparing(BoxArt::getWidth))
                .orElseThrow()
                .getUrl();
    }
}
