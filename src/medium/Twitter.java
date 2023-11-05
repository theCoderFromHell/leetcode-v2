package medium;

import java.util.*;

public class Twitter {

    private Integer timeCounter = 0;
    HashMap<Integer, User> users;
    HashMap<Integer, List<Tweet>> userToTweets;
    public Twitter() {
        users = new HashMap<>();
        userToTweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!users.containsKey(userId)) {
            users.put(userId, new User(userId));
            userToTweets.put(userId, new ArrayList<>());
        }
        List<Tweet> tweetsOfUser = userToTweets.get(userId);
        Tweet tweet = new Tweet(tweetId, userId, timeCounter++);
        tweetsOfUser.add(tweet);
        userToTweets.put(userId, tweetsOfUser);
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> tweets = new ArrayList<>();
        if (!users.containsKey(userId))
            return tweets;
        PriorityQueue<Tweet> pq = new PriorityQueue<>((o1, o2) -> o2.time.compareTo(o1.time));
        pq.addAll(userToTweets.get(userId));
        for (Integer followeeId : users.get(userId).followed) {
            User followee = users.get(followeeId);
            if (followee.followedBy.contains(userId))
                pq.addAll(userToTweets.get(followeeId));
        }
        int counter = 0;
        while (counter < 10 && !pq.isEmpty()) {
            Tweet tweet = pq.poll();
            tweets.add(tweet.tweetId);
            counter++;
        }
        return tweets;
    }

    public void follow(int followerId, int followeeId) {
        if (!users.containsKey(followerId)) {
            users.put(followerId, new User(followerId));
            userToTweets.put(followerId, new ArrayList<>());
        }
        if (!users.containsKey(followeeId)) {
            users.put(followeeId, new User(followeeId));
            userToTweets.put(followeeId, new ArrayList<>());
        }
        User follower = users.get(followerId);
        HashSet<Integer> followedList = follower.followed;
        followedList.add(followeeId);

        User followee = users.get(followeeId);
        HashSet<Integer> followedByList = followee.followedBy;
        followedByList.add(followerId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (!users.containsKey(followerId)) {
            users.put(followerId, new User(followerId));
            userToTweets.put(followerId, new ArrayList<>());
        }
        if (!users.containsKey(followeeId)) {
            users.put(followeeId, new User(followeeId));
            userToTweets.put(followeeId, new ArrayList<>());
        }
        User user = users.get(followerId);
        HashSet<Integer> followedList = user.followed;
        followedList.remove(followeeId);

        User followee = users.get(followeeId);
        HashSet<Integer> followedByList = followee.followedBy;
        followedByList.remove(followerId);
    }
}
class User {
    Integer userid;
    HashSet<Integer> followed;
    HashSet<Integer> followedBy;

    public User(Integer userid) {
        this.userid = userid;
        this.followed = new HashSet<>();
        this.followedBy = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userid.equals(user.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid);
    }
}
class Tweet {
    Integer tweetId;
    Integer userId;
    Integer time;

    public Tweet(Integer tweetId, Integer userId, Integer time) {
        this.tweetId = tweetId;
        this.userId = userId;
        this.time = time;
    }
}
