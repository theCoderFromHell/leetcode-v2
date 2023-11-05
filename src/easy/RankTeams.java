package easy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class RankTeams {

    public static String rankTeams(String[] votes) {
        if (votes == null || votes.length == 0) {
            return "";
        }
        int N = votes[0].length();
        String sample = votes[0];
        HashMap<Character, Team> teams = new HashMap<>();
        List<Team> teamList = new ArrayList<>();
        for (char c : sample.toCharArray()) {
            Team team = new Team(c, N);
            teams.put(c, team);
            teamList.add(team);
        }
        for (String vote : votes) {
            char[] charArray = vote.toCharArray();
            for (int i = 0; i < N; i++) {
                char c = charArray[i];
                Team team = teams.get(c);
                team.ranks[i+1]++;
            }
        }
        teamList.sort(new SortByVotes());
        StringBuilder result = new StringBuilder();
        for (Team team : teamList)
            result.append(team.name);
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(rankTeams(new String[] {"BCA","CAB","CBA","ABC","ACB","BAC"}));
//        System.out.println(rankTeams(new String[] {"WXYZ","XYZW"}));
//        System.out.println(rankTeams(new String[] {"ZMNAGUEDSJYLBOPHRQICWFXTVK"}));
    }
}
class Team {
    char name;

    int totalTeams;
    int[] ranks;

    public Team(char name, int totalTeams) {
        this.name = name;
        this.totalTeams = totalTeams;
        this.ranks = new int[totalTeams+1];
    }
}

class SortByVotes implements Comparator<Team> {

    @Override
    public int compare(Team o1, Team o2) {
        int N = o1.totalTeams;
        for (int i = 1; i <= N ; i++) {
            if (o1.ranks[i] == o2.ranks[i])
                continue;
            if (o1.ranks[i] > o2.ranks[i])
                return -1;
            else if (o1.ranks[i] < o2.ranks[i])
                return 1;
        }
        return (o1.name - o2.name);
    }
}






//        int N = result.length;
//        for(int i = 0; i < N; i++) {
//            System.out.println("{" + result[i][0] + "," + result[i][1] + "}");
//        }
//
//        System.out.println("================================================");

/*
private static boolean outOfRange(int[] range, int[] interval) {
        if(range[1] < interval[0] || interval[1] < range[0])
            return true;
        return false;
    }


    private static int[] dx = {-1, 1, 0, 0};
    private static int[] dy = {0, 0, -1, 1};
 */



/*
if(null == nums || nums.length == 0)
            return;


    TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.left.left.left = new TreeNode(1);

        public static void extractStringBeforeAt(String email) {
        int indexOfAt = email.indexOf("@");
        if (indexOfAt > 0) {
            String username = email.substring(0, indexOfAt);
            System.out.println("Username: " + username);
        } else {
            System.out.println("Invalid email address.");
        }
    }


    public static void main(String[] args) {
        extractStringBeforeAt("a@");
    }

    private static void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
    private static void reverse(int[][] nums, int length) {
        int start = 0, end = length-1;
        while (start < end) {
            int[] temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
    System.out.println(eraseOverlapIntervals(new int[][] {
                {-52,31},
                {-73,-26},
                {82,97},
                {-65,-11},
                {-62,-49},
                {95,99},
                {58,95},
                {-31,49},
                {66,98},
                {-63,2},
                {30,47},
                {-40,-26}
        }));



 */

/*
private static ListNode reverse(ListNode head) {
        ListNode current = head;
        ListNode prev = null;
        ListNode next;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }
 */
/*
private static void print(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }
 */


/*

public static int minCostConnectPoints(int[][] points) {
        int N = points.length;
        int[][] adj = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j)
                    adj[i][j] = 0;
                else {
                    adj[i][j] = Math.abs(points[i][0] - points[j][0])
                            + Math.abs(points[i][1] - points[j][1]);
                }
            }
        }
        HashSet<Integer> mstNodes = new HashSet<>();
        int[] weight = new int[N];
        weight[0] = 0;
        for (int i = 0; i < N; i++)
            weight[i] = Integer.MAX_VALUE;
        int result = 0;
        result = mst(0, N, adj, mstNodes, weight, result);
        return result;
    }

    private static Integer mst(int node, int N, int[][] adj, HashSet<Integer> mstNodes, int[] weight, int result) {
        if (mstNodes.contains(node))
            return result;
        for (int i = 0; i < N; i++) {

        }


    }


 */