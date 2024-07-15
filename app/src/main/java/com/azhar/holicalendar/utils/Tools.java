package com.azhar.holicalendar.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Created by Azhar Rivaldi on 12-02-2024
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public class Tools {

    public static String setDate(String strDate) {
        SimpleDateFormat formatDefault = new SimpleDateFormat("yyyy-MM-d");
        SimpleDateFormat formatTime = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        try {
            Date timesFormatCreated = formatDefault.parse(strDate);
            strDate = formatTime.format(timesFormatCreated);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strDate;
    }

}
