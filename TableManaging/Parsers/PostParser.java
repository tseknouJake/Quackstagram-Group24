package TableManaging.Parsers;

import java.util.ArrayList;

import Logic.TimeUtil;

import java.time.LocalDateTime;

import ParameterClasses.Post;

/**
 * A parser for the Parameter Object Post
 * see Parser interface for method comments
 */
public class PostParser implements Parser<Post>{

    @Override
    public Post parseRow(String csvLine) {
        String[] csvList = csvLine.split(",");

        String username = csvList[0];
        int id = Integer.parseInt(csvList[1]);
        String caption = csvList[2];
        String path = csvList[3];
        LocalDateTime timePosted = TimeUtil.parseTimestamp(csvList[4]);

        ArrayList<String> likedUsers = new ArrayList<>();
        for (int i = 5; i < csvList.length; i++) {
            likedUsers.add(csvList[i]);
        }

        return new Post(username, id, path, caption, timePosted, likedUsers);
    }

    @Override
    public String toCSV(Post row) {
        return row.toString();
    }

}
