package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.*;
import util.DataUtil;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Goal: Retrieve each video's id, title, middle interesting moment time, and smallest box art url
    DataSource: DataUtil.getMovies()
    Output: List of ImmutableMap.of("id", 5, "title", "some title", "time", new Date(), "url", "someUrl")
*/
public class Kata9 {
    public static List<Map> execute() {

        List<MovieList> movieLists = DataUtil.getMovieLists();

        return movieLists.stream()
                .flatMap(movieList -> movieList.getVideos().stream())
                .map(Kata9::getVideo)
                .collect(Collectors.toList());

    }

    private static Map<String, ? extends Serializable> getVideo(Movie movie) {
        return ImmutableMap.of(
                "id", movie.getId(),
                "title", movie.getTitle(),
                "time", getTime(movie),
                "url", getUrl(movie)
        );
    }

    private static String getUrl(Movie movie) {
        return movie.getBoxarts().stream()
                .min(Comparator.comparing(BoxArt::getWidth))
                .orElseThrow()
                .getUrl();
    }

    private static Date getTime(Movie movie) {
        return movie.getInterestingMoments()
                .stream()
                .map(InterestingMoment::getTime)
                .mapToDouble(Date::getTime)
                .average()
                .stream()
                .mapToObj(operand -> new Date((long) operand))
                .findFirst()
                .orElseThrow();
    }
}
