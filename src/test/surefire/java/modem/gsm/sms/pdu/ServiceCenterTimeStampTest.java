package modem.gsm.sms.pdu;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import junit.framework.Assert;
import modem.gsm.sms.pdu.ServiceCenterTimeStamp.TimeStampField;

import org.junit.Test;

public class ServiceCenterTimeStampTest {

	@Test
	public void testPDUServiceCenterTimeStamp1() {
		byte[] date = new byte[] { (byte)0x99, 0x20, 0x21, 0x50, 0x75, 0x03, 0x21 };
		
		ServiceCenterTimeStamp scts = new ServiceCenterTimeStamp(date); 
		
		for (TimeStampField field : TimeStampField.values())
			assertEquals(
					date[field.fieldIndex],
					ServiceCenterTimeStamp.getFieldFromCalendar(field, scts.getCalendar()));
	}
	
	@Test
	public void testPDUServiceCenterTimeStamp2() {
		byte[] date = new byte[] { (byte)0x99, 0x20, 0x21, 0x50, 0x75, 0x03, 0x21 };
		
		ServiceCenterTimeStamp scts = new ServiceCenterTimeStamp(date);

		byte[] actual = scts.getBytes();
		for(int i = 0; i < actual.length; i++)
			assertEquals(
					date[i],
					actual[i]);
		
		scts = new ServiceCenterTimeStamp(scts.getCalendar());
		actual = scts.getBytes();
		for(int i = 0; i < actual.length; i++)
			assertEquals(
					date[i],
					actual[i]);
	}
	
	@Test
	public void testPDUServiceCenterTimeStamp3() {
		byte[] date = new byte[] { 0x11, 0x21, 0x62, 0x41, 0x04, 0x00, 0x00 };
		
		ServiceCenterTimeStamp scts = new ServiceCenterTimeStamp(date); 
		
		for (TimeStampField field : TimeStampField.values())
			assertEquals(
					date[field.fieldIndex],
					ServiceCenterTimeStamp.getFieldFromCalendar(field, scts.getCalendar()));
	}

	@Test
	public void testGetTimezone() {
		ServiceCenterTimeStamp.TimezoneCalendarToTimestampConversion converter =
				new ServiceCenterTimeStamp.TimezoneCalendarToTimestampConversion();

		int actual = converter.toCalendar((byte)0x21);
		int expected = 3 * 60 * 60 * 1000;
		
		assertEquals(expected, actual);
		
		actual = converter.toCalendar((byte)0xA1);
		expected = -3 * 60 * 60 * 1000;
		
		assertEquals(expected, actual);
	}

	@Test
	public void testGetTimezoneByte() {
		ServiceCenterTimeStamp.TimezoneCalendarToTimestampConversion converter =
				new ServiceCenterTimeStamp.TimezoneCalendarToTimestampConversion();
		byte actual = converter.fromCalendar(3 * 60 * 60 * 1000);
		byte expected = 0x21;
		
		assertEquals(expected, actual);
		
		actual = converter.fromCalendar(-3 * 60 * 60 * 1000);
		expected = (byte) 0xA1;
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetTimezoneCalendar() {
		int zoneOffset = 3 * 3600000;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.ZONE_OFFSET, zoneOffset);
		ServiceCenterTimeStamp scts = new ServiceCenterTimeStamp(calendar);

		assertEquals(
				calendar.get(Calendar.ZONE_OFFSET),
				scts.getCalendar().get(Calendar.ZONE_OFFSET));
		
		calendar.set(Calendar.ZONE_OFFSET, -1 * zoneOffset);
		scts = new ServiceCenterTimeStamp(calendar);
		
		assertEquals(
				calendar.get(Calendar.ZONE_OFFSET),
				scts.getCalendar().get(Calendar.ZONE_OFFSET));
	}
	
	@Test
	public void testGetCalendarYear() {
		int year = 2012;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		
		ServiceCenterTimeStamp scts = new ServiceCenterTimeStamp(calendar);
		
		Assert.assertEquals(year % 100, scts.getCalendar().get(Calendar.YEAR));
	}
}
