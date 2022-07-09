package katas;

import util.DataUtil;

import java.util.*;
import java.util.stream.Collectors;

/*
    Goal: Create a datastructure from the given data:

    We have 2 arrays each containing lists, and videos respectively.
    Each video has a listId field indicating its parent list.
    We want to build an array of list objects, each with a name and a videos array.
    The videos array will contain the video's id and title.
    In other words we want to build the following structure:

    [
        {
            "name": "New Releases",
            "videos": [
                {
                    "id": 65432445,
                    "title": "The Chamber"
                },
                {
                    "id": 675465,
                    "title": "Fracture"
                }
            ]
        },
        {
            "name": "Thrillers",
            "videos": [
                {
                    "id": 70111470,
                    "title": "Die Hard"
                },
                {
                    "id": 654356453,
                    "title": "Bad Boys"
                }
            ]
        }
    ]

    DataSource: DataUtil.getLists(), DataUtil.getVideos()
    Output: the given datastructure
*/
public class Kata10 {
    public static List<Map> execute() {
        List<Map> lists = DataUtil.getLists();
        List<Map> videos = DataUtil.getVideos();

        return lists.stream()
                .map(map -> Map.of(
                        "name", map.get("name"),
                        "videos", getVideo(videos, map.get("id"))
                )).collect(Collectors.toList());
    }

    private static List<Map<String, Object>> getVideo(List<Map> videos, Object map) {
        return videos.stream()
                .filter(map1 -> map.equals(map1.get("listId")))
                .map(map1 -> Map.of(
                        "id", map1.get("id"),
                        "title", map1.get("title")
                )).collect(Collectors.toList());
    }

}
