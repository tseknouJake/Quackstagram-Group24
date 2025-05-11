package TableManaging.Parsers;

import ParameterClasses.Following;
import ParameterClasses.Likes;

import java.util.ArrayList;
/**
 * A parser for the Parameter Object Like
 * see Parser interface for method comments
 */
public class LikeParser implements Parser<Likes>{

    @Override
    public Likes parseRow(String csvLine) {
        return null;
    }

    @Override
    public String toCSV(Likes row) {
        return "";
    }
}

///**
// * A parser for the Parameter Object Follow
// * see Parser interface for method comments
// */
//public class FollowParser implements Parser<Following>{
//
//    @Override
//    public Following parseRow(String csvLine) {
//        String[] csvList = csvLine.split(",");
//
//        String username = csvList[0];
//
//        ArrayList<String> followingList = new ArrayList<>();
//        for (int i = 1; i < csvList.length; i++) {
//            followingList.add(csvList[i]);
//        }
//
//        return new Following(username, followingList);
//    }
//
//    @Override
//    public String toCSV(Following row) {
//        return row.toString();
//    }
//}
