package TableManaging.Parsers;

import ParameterClasses.User;

/**
 * A parser for the Parameter Object User
 * see Parser interface for method comments
 */
public class UserParser implements Parser<User>{

    @Override
    public User parseRow(String csvLine) {
        String[] csvList = csvLine.split(","); 

        String username = csvList[0];
        String password = csvList[1];
        int postsCount = Integer.parseInt(csvList[2]);
        int followersCount = Integer.parseInt(csvList[3]);
        int followingCount = Integer.parseInt(csvList[4]);
        String bio = csvList[5];
        String profilePicPath = csvList[6];

        return new User(username, password, postsCount, followersCount,followingCount, bio, profilePicPath);
    }

    @Override
    public String toCSV(User row) {
        return row.toString();
    }

}
