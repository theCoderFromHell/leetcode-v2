package medium;

import java.util.HashSet;

public class NextClosestTime {
    public String nextClosestTime(String time) {
        String[] times = time.split(":");
        int timeInMinutes = 60 * Integer.parseInt(times[0]) + Integer.parseInt(times[1]);
        int[] validDigits = new int[10];
        for (char c : time.toCharArray())
            if (c != ':')
                validDigits[c - '0'] = 1;
        while (true) {
            timeInMinutes = (timeInMinutes + 1) %(24*60);
            int H1 = (timeInMinutes/60)/10;
            int H2 = (timeInMinutes/60)%10;
            int M1 = (timeInMinutes%60)/10;
            int M2 = (timeInMinutes%60)%10;
            if (validDigits[H1] == 1 && validDigits[H2] == 1
                    && validDigits[M1] == 1 && validDigits[M2] == 1)
                return String.valueOf(H1) + H2 + ':' + M1 + M2;
        }
    }

    public static void main(String[] args) {
        NextClosestTime nextClosestTime = new NextClosestTime();
        System.out.println(nextClosestTime.nextClosestTime("19:34"));
        System.out.println(nextClosestTime.nextClosestTime("23:59"));
    }
}
