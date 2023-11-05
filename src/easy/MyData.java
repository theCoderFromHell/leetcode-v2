package easy;

import java.util.ArrayList;
import java.util.List;

public class MyData {

    private String name;
    private Integer id;
    private List<MyData> otherData;

    public List<String> getAllNames(MyData myData){
        if(myData == null || myData.otherData == null || myData.otherData.isEmpty())
            return new ArrayList<>();
        List<String> allNames = new ArrayList<>();
        for(MyData myDataFromList : myData.otherData) {
            allNames.add(myDataFromList.name);
            for(MyData m : myDataFromList.otherData) {
                List<String> names = getAllNames(m);
                allNames.addAll(names);
            }
        }
        return allNames;
    }

}


/*

Cricket website
        - serve latest top 5 commentory
        - See More
        - update

    BallCommentory
        - id
        - ballNumber
        - text
        - type (WICKET/SIX/FOUR/REGULAR)
         - emojisReactions : {
                    emoji:
                    count:
         }
    Match
    - ...
    - PriorityQueue<BallCommentory> /* PriorityQueue

GET /isNewCommAvailable
    Request: matchId
    Response: Yes/No

GET /latestComm
    Request: matchId
    Response : BallCommentory

1:10000
    - update the count in DB at certain interval (1 min)
    - update the latest count in redis to show the user (maynot be consistent)
    - cache layer

*/