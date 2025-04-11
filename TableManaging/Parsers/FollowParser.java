package TableManaging.Parsers;

import java.util.ArrayList;
import ParameterClasses.Following;

/**
 * A parser for the Parameter Object Follow
 * see Parser interface for method comments
 */
public class FollowParser implements Parser<Following>{
    
    @Override
    public Following parseRow(String csvLine) {
        String[] csvList = csvLine.split(",");
        
        String username = csvList[0];
        
        ArrayList<String> followingList = new ArrayList<>();
        for (int i = 1; i < csvList.length; i++) {
            followingList.add(csvList[i]);
        }

        return new Following(username, followingList);
    }

    @Override
    public String toCSV(Following row) {
        return row.toString();
    }
}
