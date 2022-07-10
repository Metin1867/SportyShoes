package tr.com.macik.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ServletHTMLUtil {

	
	public static CharSequence getNumberInput(String label, String fieldName, int value) {
		return getNumberInput(label, fieldName, ""+getValue(value));
	}

	public static CharSequence getNumberInput(String label, String fieldName, String value) {
		return getLabel(label, fieldName)+"<input type='number' name='"+fieldName+"' value='"+getValue(value)+"'><br/>";
	}

	public static CharSequence getTextInput(String label, String fieldName, String value) {
		return getLabel(label, fieldName)+"<input type='text' name='"+fieldName+"' value='"+getValue(value)+"'><br/>";
	}

	public static CharSequence getTextInput(String label, String fieldName, Date value) {
		return getTextInput(label, fieldName, getValue(value));
	}

	public static CharSequence getNumberInputReadOnly(String label, String fieldName, int value) {
		return getNumberInputReadOnly(label, fieldName, getValue(value));
	}

	public static CharSequence getNumberInputReadOnly(String label, String fieldName, String value) {
		return getLabel(label, fieldName)+"<input type='number' name='"+fieldName+"' readonly value='"+getValue(value)+"'><br/>";
	}

	public static String getLabel(String label, String fieldName) {
		return "<label for='"+fieldName+"'>"+label+":</label>";
	}

	public static String getValue(long value) {
		if (value==-1)
			return "";
		return ""+value;
	}

	public static int getIntValue(String value) {
		if (value==null || "".equals(value))
			return -1;
		return Integer.valueOf(value);
	}

	public static String getValue(Object value) {
		if (value==null)
			return "";
		return value.toString();
	}

	public static CharSequence getSubmitInput(String name) {
		return "<input type='submit' name='submit' value='"+name+"'><br/>";
	}

	public static CharSequence startForm(String servletName, String method) {
		return "<form action='"+servletName+"' method='"+method+"'>";
	}

	public static CharSequence endForm() {
		return "</form>";
	}

	public static CharSequence startFormPost(String servletName) {
		return startForm(servletName, "post");
	}

	public static CharSequence startFormGet(String servletName) {
		return startForm(servletName, "get");
	}

	public static Date getDate(String dateStr) {
		if (dateStr==null || "".equals(dateStr))
			return null;
        String[] supportedDateFormats = {
        		// getDate("2013-09-25");
				"yyyy-MM-dd",
				"yyyy-M-d",
				"yy-MM-dd",
				"yy-M-d",
		        // getDate("2013/09/25");
				"yyyy/MM/dd",
				"yyyy/M/d",
				"yy/MM/dd",
				"yy/M/d",
		        // getDate("20130925");
				"yyyyMMdd",
				"yyMMdd",
		        // getDate("2013.09.25");
				"yyyy.MM.dd",
				"yyyy.M.d",
				"yy.MM.dd",
				"yy.M.d",
				// getDate("2013 09 25");
				"yyyy MM dd",
				"yyyy M d",
				"yy MM dd",
				"yy M d",
		
				// getDate("25.09.2013");
				"dd.MM.yyyy",
				"d.M.yyyy",
				"dd.MM.yy",
				"d.M.yy",
		        // getDate("25-09-2013");
				"dd-MM-yyyy",
				"d-M-yyyy",
				"dd-MM-yy",
				"d-M-yy",
		        // getDate("25/09/2013");
				"dd/MM/yyyy",
				"d/M/yyyy",
				"dd/MM/yy",
				"d/M/yy",
		        //! getDate("25 09 2013");
				"dd MM yyyy",
				"d M yyyy",
				"dd MM yy",
				"d M yy",
		        // getDate("25092013");
				"ddMMyyyy",
				"ddMMyy"
				};
        for (String format : supportedDateFormats) {
        	if (ServletHTMLUtil.isDateStrValidFormat(format, dateStr))
        		return ServletHTMLUtil.getDate(format, dateStr);
        }
        
		return null;
	}

	public static Date getDate(String format, String dateStr) {
        java.util.Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
            if (!dateStr.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
           // not needed! ex.printStackTrace();
        }
		return new Date(date.getTime());
	}

    public static boolean isDateStrValidFormat(String format, String dateStr) {
        java.util.Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
            if (!dateStr.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
           // not needed! ex.printStackTrace();
        }
        return date != null;
    }
}
