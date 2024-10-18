package medium;

public class LongestTurbulentSubarray {
    public int maxTurbulenceSize(int[] arr) {
        int N = arr.length;
        int odd = 1, even = 1;
        int result = 1;
        for (int i = 1; i < N; i++) {
            if (i % 2 == 1) {
                if (arr[i-1] > arr[i]) {
                    even++;
                    odd = 1;
                }
                else if (arr[i-1] < arr[i]) {
                    odd++;
                    even = 1;
                } else {
                    odd = 1;
                    even = 1;
                }
            } else {
                if (arr[i-1] > arr[i]) {
                    odd++;
                    even = 1;
                }
                else if (arr[i-1] < arr[i]) {
                    even++;
                    odd = 1;
                } else {
                    odd = 1;
                    even = 1;
                }
            }
            result = Math.max(result, Math.max(odd, even));
        }
        return result;
    }

    public static void main(String[] args) {
        LongestTurbulentSubarray L = new LongestTurbulentSubarray();
        System.out.println(L.maxTurbulenceSize(new int[]{9,4,2,10,7,8,8,1,9}));
        System.out.println(L.maxTurbulenceSize(new int[]{4,8,12,16}));
        System.out.println(L.maxTurbulenceSize(new int[]{100}));
    }
}
