package com.booking.flipkar.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    public static Timestamp convertToTimeStamp(String dateTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a");
        Date parsedDate = dateFormat.parse(dateTime);
        return new Timestamp(parsedDate.getTime());
    }

    public static double findNumberOfHours(Timestamp startTime,Timestamp endTime){
        long millisecondDifference = endTime.getTime() - startTime.getTime();
        return millisecondDifference / (1000.0 * 60 * 60);
    }
}
