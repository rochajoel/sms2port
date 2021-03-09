package modem.gsm.sms.pdu;

import java.util.Arrays;
import java.util.Calendar;

public class ServiceCenterTimeStamp {	
	private static abstract class CalendarToTimestampConversion {
		public byte fromCalendar(int value) { return SemiOctetsSwappedNibble.toSemiOctet((byte) value); }
		public int toCalendar(byte value) 	{ return SemiOctetsSwappedNibble.fromSemiOctet(value); }
	}
	private static class MonthCalendarToTimestampConversion extends CalendarToTimestampConversion {
		public byte fromCalendar(int value) { return super.fromCalendar(value + 1); }
		public int toCalendar(byte value) 	{ return super.toCalendar(value) - 1; }
	}
	private static class YearCalendarToTimestampConversion extends CalendarToTimestampConversion {
		public byte fromCalendar(int value) { return super.fromCalendar(value % 100); }
	}
	static class TimezoneCalendarToTimestampConversion extends CalendarToTimestampConversion {
		public byte fromCalendar(int millisec) {
			boolean isNegative = millisec < 0;
			
			millisec /= (1000 * 60 * 15 * (isNegative ? -1 : 1));
			
			return (byte) ((isNegative ? 0x80 : 0) |
					super.fromCalendar((byte) millisec));
		}
		public int toCalendar(byte quarters) 	{
			boolean isNegative = (0x80 & quarters) == 0x80;
			
			return (isNegative ? -1 : 1) *
				super.toCalendar( (byte) (quarters & 0x7F) ) * 15 * 60 * 1000;
		}
	}
	
	static enum TimeStampField {
		Year		(0, Calendar.YEAR, new YearCalendarToTimestampConversion()),
		Month		(1, Calendar.MONTH, new MonthCalendarToTimestampConversion()),
		Day_of_Month(2, Calendar.DAY_OF_MONTH),
		Hour_of_Day	(3, Calendar.HOUR_OF_DAY),
		Minute		(4, Calendar.MINUTE),
		Second		(5, Calendar.SECOND),
		TimeZone	(6, Calendar.ZONE_OFFSET, new TimezoneCalendarToTimestampConversion()),
		;
		
		final int fieldIndex;
		private final int calendarField;
		private final CalendarToTimestampConversion convert;
		
		private TimeStampField(int fieldIndex, int calendarField) {
			this(fieldIndex, calendarField, new CalendarToTimestampConversion() {});
		}
		
		private TimeStampField(int fieldIndex, int calendarField,
				CalendarToTimestampConversion convert) {
			this.fieldIndex		= fieldIndex;
			this.calendarField	= calendarField;
			this.convert		= convert;
		}
	}
	
	private final byte[] sctsField;

	public ServiceCenterTimeStamp(Calendar calendar) {
		this.sctsField = new byte[TimeStampField.values().length];
		
		for (TimeStampField field : TimeStampField.values())
			sctsField[field.fieldIndex] = getFieldFromCalendar(field, calendar);
	}
	
	/**
	 * Builds a ServiceCenterTimeStamp.
	 * This constructor accepts a 7 byte field only.
	 * @param sctsField
	 */
	public ServiceCenterTimeStamp(byte[] sctsField) {
		validFieldLength(sctsField);
		this.sctsField = Arrays.copyOf(sctsField, sctsField.length);
		
		validCalendar();		
	}

	private void validCalendar() {
		Calendar calendar = getCalendar();
		
		for (TimeStampField field : TimeStampField.values())
			if (getFieldToCalendar(field, sctsField) != calendar.get(field.calendarField))
				throw new IllegalArgumentException("Check your date field: " + field.name());
		
		for (TimeStampField field : TimeStampField.values())
			if (getFieldFromCalendar(field, calendar) != sctsField[field.fieldIndex])
				throw new IllegalArgumentException("Check your date field: " + field.name());
	}
	
	private void validFieldLength(byte[] sctsField) {
		if (sctsField == null || sctsField.length != 7)
			throw new IllegalArgumentException("This field must have 7 bytes");
	}
	
	/**
	 * Gets a Calendar object from the underlying Timestamp representation.
	 * 
	 * Warning: The YEAR field on the Timestamp object is only 1 byte long
	 * which means that only the last 2 digits of the calendar year are stored
	 * in the underlying representation of the Timestamp field. This results in
	 * returning calendar objects between Year 0-99. The calling method is
	 * responsible for setting the date in the required "Epoch". 
	 * 
	 * @return
	 */
	public Calendar getCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		
		for (TimeStampField field : TimeStampField.values())
			calendar.set(
					field.calendarField,
					getFieldToCalendar(field, sctsField));
		
		return calendar;
	}
	
	public byte[] getBytes() {
		return Arrays.copyOf(this.sctsField, this.sctsField.length);
	}

	private static int getFieldToCalendar(TimeStampField field, byte[] scts) {
		return field.convert.toCalendar(scts[field.fieldIndex]);
	}

	static byte getFieldFromCalendar(TimeStampField field, Calendar calendar) {
		return field.convert.fromCalendar(calendar.get(field.calendarField));
	}
}
