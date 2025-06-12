package interviews.others;

public class MoveInSyncRoundOne {

    public static int getColumnNumber (String columnName) {
        int length = columnName.length();
        int index = 0;
        int result = 0;
        for (int i = length-1; i >= 0; i--) {
            result += ((columnName.charAt(i) - 'A' + 1) * (int)Math.pow(26, index++));
        }
        return result;
    }
    public static String getColumnName (int columnNumber) {
        StringBuilder str = new StringBuilder();
        columnNumber--;
        while (columnNumber >= 0) {
            int Q = columnNumber/26;
            int rem = columnNumber % 26;
            str.append((char)(rem + 'A'));
            columnNumber = Q-1;
        }
        return str.reverse().toString();
    }



    public static void main(String[] args) {
        System.out.println(getColumnNumber("B"));
        System.out.println(getColumnNumber("AA"));
        System.out.println(getColumnNumber("ZZ"));
        System.out.println(getColumnNumber("ZCB"));
        System.out.println(getColumnNumber("BA"));
        System.out.println(getColumnNumber("AZ"));

        System.out.println("###############################");

        System.out.println(getColumnName(2));
        System.out.println(getColumnName(27));
        System.out.println(getColumnName(702));
        System.out.println(getColumnName(17656));
        System.out.println(getColumnName(52));
        System.out.println(getColumnName(53));

    }
}

/*

AA -> 26^1* 1 + 26^0 * 1 = 27
ZZ -> 26^1* 26 + 26^0 * 26 = 676 + 26
B -> 26^0 * 2 = 2

ZCB -> 26^2 * 26 + 26^1 * 3 + 26^0 * 2 = 26^3 + 78 + 2 =

 */



/*
Functional Requirements:
    - book tickets
        - source
        - destination
        - fare



Vehicle Table :
    - id
    - vehicleType (BUS/TAXI)
    - startTime
    - source
    - destination
    - ratePerKm


Stops
    - id
    - name
    - stopCode (ALLAHABAD VARANASI)
    - vehicleId
    - scheduledTime
    - direction (east)


SeatsMap
    - id
    - vehicleId
    - availableSeats
    - bookedSeats
    - journeyId


Booking
    - id
    - vehicleId
    - seatId
    - journeyId
    - userId
    - timestamp
    - status


 */

