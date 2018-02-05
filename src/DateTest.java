import java.text.ParseException;
import java.util.Date;

public class DateTest {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		Date date = DateUtils.getFormatDateByPattern(DateUtils.FORMATE_YYYY_MM_DD, "0000-00-00");
		date.setTime(0);
		System.out.println(date.getDate());
	}

}
