package tr.com.macik.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ServletHTMLUtil {
	private static ServletHTMLUtil instance;
	private boolean setNextInputElementReadOnly=false;
	
	private ServletHTMLUtil() {
	}
	
	private static ServletHTMLUtil getInstance() {
		if (instance == null)
			instance = new ServletHTMLUtil();
		return instance;
	}
	
	public static CharSequence getNumberInput(String label, String fieldName, int value) {
		return getNumberInput(label, fieldName, ""+getValue(value));
	}

	public static CharSequence getNumberInput(String label, String fieldName, double value) {
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

	public static CharSequence getDateInput(String label, String fieldName, Date dateValue) {
		return getLabel(label, fieldName)+"<input type='date' name='"+fieldName+"' value='"+getValue(dateValue)+"'><br/>";
	}

	public static CharSequence getBooleanInput(String label, String fieldName, boolean enabled) {
	    // <input type="checkbox" id="ID" name="{id}" checked>
		return  getLabel(label, fieldName)+"<input type='checkbox' name='"+fieldName+"' value='"+getValue(enabled)+"' "+(enabled?"checked":"")+"><br/>";
	}

	public static CharSequence getNumberInputReadOnly(String label, String fieldName, int value) {
		return getNumberInputReadOnly(label, fieldName, getValue(value));
	}

	public static CharSequence getNumberInputReadOnly(String label, String fieldName, String value) {
		return getLabel(label, fieldName)+"<input type='number' name='"+fieldName+"' readonly value='"+getValue(value)+"'><br/>";
	}


	public static CharSequence getCurrencyInput(String label, String fieldName, float value) {
		String input = getLabel(label, fieldName);
		input += "<input type='number' step='0.01' name='"+fieldName+"' value='"+getValue(value)+"'";
		if (getInstance().setNextInputElementReadOnly) {
			input += " readonly";
			getInstance().setNextInputElementReadOnly=false;
		} 
		return input+"><br/>";
	}

	public static byte[] getByteValue(String value) {
		if (value == null)
			return "".getBytes();
		return value.getBytes();
	}

	public static String getLabel(String label, String fieldName) {
		return "<label for='"+fieldName+"'>"+label+":</label>";
	}

	public static String getValue(long value) {
		if (value==-1)
			return "";
		return ""+value;
	}

	public static String getValue(double value) {
		return String.format("%.2f", value);
	}

	public static int getIntValue(String value) {
		if (value==null || "".equals(value))
			return -1;
		return Integer.valueOf(value);
	}

	public static double getDoubleValue(String value) {
		if (value==null || "".equals(value))
			return 0;
		return Double.valueOf(value);
	}

	public static String getValue(Object value) {
		if (value==null)
			return "";
		return value.toString();
	}

	public static boolean getBooleanValue(String value, boolean defaultValue) {
		if (value==null)
			return defaultValue;
		return (value!=null ? true : false);
	}

	public static boolean getBooleanValue(String value) {
		return getBooleanValue(value, false);
	}

	public static CharSequence getSubmitInput(String name) {
		return "<input type='submit' name='submit' value='"+name+"'>";
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

	public static Date getDateTime(String dateStr) {
		if (dateStr==null || "".equals(dateStr))
			return null;
        String[] supportedTimeFormats = {
        		"h",
				"h:m",
				"h:m:s",
        		"hh",
				"hh:mm",
				"hh:mm:ss"
        };
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
        String format;
        for (String formatDate : supportedDateFormats) {
            for (String formatTime : supportedTimeFormats) {
            	format = formatDate + " " + formatTime;
	        	if (isDateStrValidFormat(format, dateStr))
	        		return getDate(format, dateStr);
            }
        }
        
		return null;
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
        	if (ServletHTMLUtil.isDateStrValidFormat(format, dateStr)) {
        		System.out.println(format);
        		return ServletHTMLUtil.getDate(format, dateStr);
        	}
        }
        
    	return getDateTime(dateStr);
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

	public static CharSequence getByteInput(String label, String fieldName, byte[] byteStream) {
		return getLabel(label, fieldName)+"<input type='text' name='"+fieldName+"' value='"+getValue(byteStream)+"'><br/>";
	}

	public static String andWhere(String whereClauseOrig, String column, long value) {
		String whereClause ="";
		if (whereClauseOrig!=null && !"".equals(whereClauseOrig))
			whereClause += " and ";
		if (column==null || "".equals(column.trim()))
			return whereClauseOrig;
		whereClause += " " + column + " = " + value;
		
		return whereClause;		
	}

	public static String andWhere(String whereClauseOrig, String column, String value) {
		String whereClause ="";
		if (whereClauseOrig!=null && !"".equals(whereClauseOrig))
			whereClause += " and ";
		if (column==null || "".equals(column.trim()))
			return whereClauseOrig;
		whereClause += " " + column + " = '" + value + "'";
		
		return whereClause;		
	}

	public static String andWhere(String whereClauseOrig, String column, boolean value) {
			String whereClause ="";
			if (whereClauseOrig!=null && !"".equals(whereClauseOrig))
				whereClause += " and ";
			if (column==null || "".equals(column.trim()))
				return whereClauseOrig;
			whereClause += " " + column + " = '" + (value?"true":"false") + "'";
			
			return whereClause;		
	}

	public static String andWhere(String whereClauseOrig, String column, float value) {
		String whereClause ="";
		if (whereClauseOrig!=null && !"".equals(whereClauseOrig))
			whereClause += " and ";
		if (column==null || "".equals(column.trim()))
			return whereClauseOrig;
		whereClause += " " + column + " = '" + value + "'";
		
		return whereClause;		
	}

	public static ServletHTMLUtil readonly() {
		getInstance().setNextInputElementReadOnly=true;
		return getInstance();
	}
}
