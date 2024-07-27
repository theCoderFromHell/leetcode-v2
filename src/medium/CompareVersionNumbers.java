package medium;

public class CompareVersionNumbers {
    public int compareVersion(String version1, String version2) {
        String[] versions1 = version1.split("\\.");
        String[] versions2 = version2.split("\\.");
        int version1Size = versions1.length;
        int version2Size = versions2.length;
        int i = 0, j = 0;
        while (i < version1Size  || j < version2Size) {
            int v1, v2;
            v1 = (i < version1Size) ? Integer.parseInt(versions1[i]) : 0;
            v2 = (j < version2Size) ? Integer.parseInt(versions2[j]) : 0;

            if (v1 < v2)
                return -1;
            else if (v1 > v2)
                return 1;
            i++;
            j++;
        }
        return 0;
    }

    public static void main(String[] args) {
        CompareVersionNumbers C = new CompareVersionNumbers();
        System.out.println(C.compareVersion("1.2", "1.10"));
        System.out.println(C.compareVersion("1.01", "1.001"));
        System.out.println(C.compareVersion("1.0", "1.0.0.0"));
        System.out.println(C.compareVersion("1", "1.0.1.0"));
    }
}
