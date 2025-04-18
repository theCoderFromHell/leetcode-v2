package medium;

public class FairDistributionOfCookies {
    public int distributeCookies(int[] cookies, int k) {
        int size = cookies.length;
        int[] share = new int[k];
        return distribute(cookies, size, k, share, k, 0);
    }

    private int distribute(int[] cookies, int size, int k, int[] share, int noCookieChildren, int index) {
        if (size - index < noCookieChildren)
            return Integer.MAX_VALUE;
        if (index == size) {
            int unfairness = Integer.MIN_VALUE;
            for (int i = 0; i < k; i++)
                unfairness = Math.max(unfairness, share[i]);
            return unfairness;
        }
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            noCookieChildren -= (share[i] == 0 ? 1 : 0);
            share[i] += cookies[index];
            result = Math.min(result, distribute(cookies, size, k, share, noCookieChildren, index+1));
            share[i] -= cookies[index];
            noCookieChildren += (share[i] == 0 ? 1 : 0);
        }
        return result;
    }

    public static void main(String[] args) {
        FairDistributionOfCookies F = new FairDistributionOfCookies();
        System.out.println(F.distributeCookies(new int[]{8,15,10,20,8}, 2));
        System.out.println(F.distributeCookies(new int[]{6,1,3,2,2,4,1,2}, 3));
    }
}
