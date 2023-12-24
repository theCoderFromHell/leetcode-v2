package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubdomainVisitCount {
    public static List<String> subdomainVisits(String[] cpdomains) {
        List<String> result = new ArrayList<>();
        if (cpdomains == null || cpdomains.length == 0)
            return result;
        int N = cpdomains.length;
        HashMap<String, Integer> hash = new HashMap<>();
        for (int i = 0; i < N; i++) {
            String[] countAndDomains = cpdomains[i].split(" ");
            int count = Integer.parseInt(countAndDomains[0]);
            String[] subdomains = countAndDomains[1].split("\\.");
            String current = subdomains[subdomains.length-1];
            hash.put(current, hash.getOrDefault(current, 0) + count);
            for (int j = subdomains.length-2; j >= 0 ; j--) {
                current = subdomains[j] + "." + current;
                hash.put(current, hash.getOrDefault(current, 0) + count);
            }
        }
        for (String key : hash.keySet())
            result.add(hash.get(key) + " " + key);
        return result;
    }
    public static void main(String[] args) {
        System.out.println(subdomainVisits(new String[] {
                "900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org"
        }));
    }
    /*
    Example 1:

Input: cpdomains = ["9001 discuss.leetcode.com"]
Output: ["9001 leetcode.com","9001 discuss.leetcode.com","9001 com"]
Explanation: We only have one website domain: "discuss.leetcode.com".
As discussed above, the subdomain "leetcode.com" and "com" will also be visited. So they will all be visited 9001 times.
Example 2:

Input: cpdomains = ["900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org"]
Output: ["901 mail.com","50 yahoo.com","900 google.mail.com","5 wiki.org","5 org","1 intel.mail.com","951 com"]
Explanation: We will visit "google.mail.com" 900 times, "yahoo.com" 50 times, "intel.mail.com" once and "wiki.org" 5 times.
For the subdomains, we will visit "mail.com" 900 + 1 = 901 times, "com" 900 + 50 + 1 = 951 times, and "org" 5 times.
     */
}
