package tr.com.macik.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SqlUtil {

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

	public static Timestamp getCurrentDate() {
		return Timestamp.valueOf(LocalDateTime.now());
	}

}
