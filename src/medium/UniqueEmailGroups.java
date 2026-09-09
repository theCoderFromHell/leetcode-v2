package medium;

import java.util.HashSet;
// https://leetcode.com/problems/unique-email-groups/
public class UniqueEmailGroups {
    public int uniqueEmailGroups(String[] emails) {
        int size = emails.length;
        HashSet<String> normalisedEmails = new HashSet<>();
        for (int i = 0; i < size; i++) {
            String originalEmail = emails[i];
            String[] parts = originalEmail.split("@");
            String localName = parts[0];
            String domainName = parts[1];
            String[] localParts = localName.split("\\+");
            localName = localParts[0].replace(".", "").toLowerCase();
            domainName = domainName.toLowerCase();
            normalisedEmails.add(localName + "@" + domainName);
        }
        return normalisedEmails.size();
    }

    /*
     * Pattern: String normalization + HashSet deduplication
     * Key Insight: Normalize each email to a canonical form (strip dots, drop
     *              +suffix, lowercase), then count unique strings via a HashSet.
     * Gotchas:
     *   - split("\\+") needs the escaped regex — split("+") throws PatternSyntaxException.
     *   - Dots must be removed AFTER splitting on '+', not before (to avoid
     *     accidentally stripping dots from the domain name).
     *   - LeetCode's editor does not import java.util.Locale, so toLowerCase(Locale.ROOT)
     *     fails to compile there. Plain toLowerCase() is fine here because emails are
     *     ASCII; reach for Locale.ROOT only when input may contain Turkish dotted I.
     * Template:
     *   split on '@' → local, domain
     *   split local on '\\+' → take [0]
     *   local.replace(".", "").toLowerCase()
     *   domain.toLowerCase()
     *   add to HashSet, return size
     */
    public static void main(String[] args) {
        UniqueEmailGroups U = new UniqueEmailGroups();
        System.out.println("Test 1: " + U.uniqueEmailGroups(new String[]{"test.email+alex@leetcode.com", "test.e.mail+bob.cathy@leetcode.com", "testemail+david@lee.tcode.com"}) + " (Expected: 2)");
        System.out.println("Test 2: " + U.uniqueEmailGroups(new String[]{"A@B.com", "a@b.com", "ab+xy@b.com", "a.b@b.com"}) + " (Expected: 2)");
        System.out.println("Test 3: " + U.uniqueEmailGroups(new String[]{"hello@domain.com"}) + " (Expected: 1)");
        System.out.println("Test 4: " + U.uniqueEmailGroups(new String[]{"a.b.c@x.com", "abc@x.com", "a.bc@x.com"}) + " (Expected: 1)");
        System.out.println("Test 5: " + U.uniqueEmailGroups(new String[]{"abc+tag@x.com", "abc+other@x.com", "abc@x.com"}) + " (Expected: 1)");
        System.out.println("Test 6: " + U.uniqueEmailGroups(new String[]{"ABC@DOMAIN.COM", "abc@domain.com"}) + " (Expected: 1)");
    }
}
