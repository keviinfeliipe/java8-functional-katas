package katas;

import model.BoxArt;
import model.Movie;
import model.MovieList;
import util.DataUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Goal: Retrieve id, title, and a 150x200 box art url for every video
    DataSource: DataUtil.getMovieLists()
    Output: List of ImmutableMap.of("id", "5", "title", "Bad Boys", "boxart": BoxArt)
*/
public class Kata4 {
    public static List<Map> execute() {
        List<MovieList> movieLists = DataUtil.getMovieLists();
        return movieLists.stream()
                .flatMap(movieList -> movieList.getVideos().stream())
                .map(Kata4::getMovie)
                .collect(Collectors.toList());

    }

    private static Map<String, Object> getMovie(Movie movie) {
        return Map.of(
                "id", movie.getId(),
                "title", movie.getTitle(),
                "boxart", getBoxart(movie)
        );
    }

    private static BoxArt getBoxart(Movie movie) {
        return movie.getBoxarts()
                .stream()
                .filter(boxArt -> boxArt.getWidth() == 150)
                .filter(boxArt -> boxArt.getHeight() == 200)
                .findFirst()
                .orElseThrow();
    }
}
