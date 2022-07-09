package katas;

import util.DataUtil;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
    Goal: Create a datastructure from the given data:

    This time we have 4 seperate arrays each containing lists, videos, boxarts, and bookmarks respectively.
    Each object has a parent id, indicating its parent.
    We want to build an array of list objects, each with a name and a videos array.
    The videos array will contain the video's id, title, bookmark time, and smallest boxart url.
    In other words we want to build the following structure:

    [
        {
            "name": "New Releases",
            "videos": [
                {
                    "id": 65432445,
                    "title": "The Chamber",
                    "time": 32432,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/TheChamber130.jpg"
                },
                {
                    "id": 675465,
                    "title": "Fracture",
                    "time": 3534543,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/Fracture120.jpg"
                }
            ]
        },
        {
            "name": "Thrillers",
            "videos": [
                {
                    "id": 70111470,
                    "title": "Die Hard",
                    "time": 645243,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/DieHard150.jpg"
                },
                {
                    "id": 654356453,
                    "title": "Bad Boys",
                    "time": 984934,
                    "boxart": "http://cdn-0.nflximg.com/images/2891/BadBoys140.jpg"
                }
            ]
        }
    ]

    DataSource: DataUtil.getLists(), DataUtil.getVideos(), DataUtil.getBoxArts(), DataUtil.getBookmarkList()
    Output: the given datastructure
*/
public class Kata11 {
    public static List<Map> execute() {
        List<Map> lists = DataUtil.getLists();
        List<Map> videos = DataUtil.getVideos();
        List<Map> boxArts = DataUtil.getBoxArts();
        List<Map> bookmarkList = DataUtil.getBookmarkList();

        return lists.stream().map(listMap ->
                Map.of(
                        "name", listMap.get("name"),
                        "videos", getListVideoById(listMap.get("id"),videos, boxArts, bookmarkList)
                                .collect(Collectors.toList())
                )
        ).collect(Collectors.toList());

    }

    private static Stream<Map<String, Object>> getListVideoById(Object listId, List<Map> videos, List<Map> boxArts, List<Map> bookmarkList) {
        return videos.stream()
                .filter(videosMap -> listId.equals(videosMap.get("listId")))
                .map(videosMap ->
                        Map.of(
                                "id", videosMap.get("id"),
                                "title", videosMap.get("title"),
                                "time", getTimeById(bookmarkList, videosMap.get("id")),
                                "boxart", getBoxartById(boxArts, videosMap.get("id"))
                        )
                );
    }

    private static Object getBoxartById(List<Map> boxArts, Object videoId) {
        return boxArts.stream()
                .filter(boxMap -> videoId.equals(boxMap.get("videoId")))
                .map(boxMap -> boxMap.get("url"))
                .sorted()
                .findFirst()
                .orElseThrow();
    }

    private static Object getTimeById(List<Map> bookmarkList, Object videoId) {
        return bookmarkList.stream()
                .filter(bookMap -> videoId.equals(bookMap.get("videoId")))
                .map(bookMap -> bookMap.get("time"))
                .findFirst()
                .orElseThrow();
    }

}
