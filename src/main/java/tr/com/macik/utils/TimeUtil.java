package tr.com.macik.utils;

import java.sql.Date;

public class TimeUtil {
    public static Date jutilToSqlDate(java.util.Date jutilDate) {
        Date sqlDate = new Date(jutilDate.getTime());
        return sqlDate;
      }
    
    public static java.util.Date sqlToJUtilDate(java.util.Date date) {
        java.util.Date jutilDate = new java.util.Date(date.getTime());
        return jutilDate;
      }

}
