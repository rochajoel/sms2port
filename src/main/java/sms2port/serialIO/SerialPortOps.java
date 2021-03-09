package sms2port.serialIO;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.util.ArrayList;
import java.util.Enumeration;

public class SerialPortOps {
	public enum DataBits {
		Five	(SerialPort.DATABITS_5),
		Six		(SerialPort.DATABITS_6),
		Seven	(SerialPort.DATABITS_7),
		Eigth	(SerialPort.DATABITS_8);
		
		private final int bits;

		private DataBits(int bits) { this.bits = bits; }

		public int getBits() { return this.bits; }
	}

    /**
     *     Specifies the number of stop bits used on the System.IO.Ports.SerialPort
     *     object.
     */
    public enum StopBits
    {
        /** One stop bit is used. */
        One				(SerialPort.STOPBITS_1),
        /** Two stop bits are used. */
        Two				(SerialPort.STOPBITS_2),
        /** 1.5 stop bits are used. */
        OnePointFive	(SerialPort.STOPBITS_1_5);
        
		private final int bits;

		private StopBits(int bits) { this.bits = bits; }

		public int getBits() { return this.bits; }
    }
    
	
	/** Specifies the parity bit for a System.IO.Ports.SerialPort object. */
    public enum Parity {
    	/** No parity check occurs. */
        None	(SerialPort.PARITY_NONE),
        /** Sets the parity bit so that the count of bits set is an odd number. */
        Odd		(SerialPort.PARITY_ODD),
        /** Sets the parity bit so that the count of bits set is an even number. */
        Even	(SerialPort.PARITY_EVEN),
        /** Leaves the parity bit set to 1. */
        Mark	(SerialPort.PARITY_MARK),
        /** Leaves the parity bit set to 0. */
        Space	(SerialPort.PARITY_SPACE);
        
		private final int bits;

		private Parity(int bits) { this.bits = bits; }

		public int getBits() { return this.bits; }
    }
    
    @Deprecated
    public static SerialPort openSerialPort(String serialPort)
    throws NoSuchPortException, PortInUseException {
    	CommPortIdentifier pId = CommPortIdentifier.getPortIdentifier(serialPort);
    
    	CommPort cPort = pId.open(SerialPortOps.class.getName(), 1000);
    	
    	validSerialPort(cPort);
	
    	return (SerialPort)cPort;
    }
    
    public static SerialPort openSerialPort(CommPortIdentifier pId)
    throws NoSuchPortException, PortInUseException {
    	CommPort cPort = pId.open(SerialPortOps.class.getName(), 1000);
    	
    	validSerialPort(cPort);
    	
    	return (SerialPort)cPort;
    }

	private static void validSerialPort(CommPort cPort) {
		if (!(cPort instanceof SerialPort)) {
    		cPort.close();
    		throw new IllegalArgumentException("The string must identify a COM Port like: COM9");
    	}
	}
    
    
    /**
     * Sets the Serial Port Parameters.
     * 
     * @param baudrate - ex.: 9600, 115200
     * @param d - Data Bits
     * @param s - Stop Bits
     * @param p - Parity Bits
     * @throws UnsupportedCommOperationException
     */
	public static void setSerialPortParams(SerialPort sPort, int baudrate, DataBits d, StopBits s, Parity p)
	throws UnsupportedCommOperationException {
		if (s == StopBits.OnePointFive && d != DataBits.Five)
			throw new IllegalArgumentException("1.5 stop bits requires 5 databits");
		
		sPort.setSerialPortParams(
				baudrate,
				d.getBits(),
				s.getBits(),
				p.getBits());
	}
	
	/**
	 * Sets the baudrate on the specified Serial Port.
	 * 
	 * @param sPort - SerialPort
	 * @param baudRate - ex.: 9600 means 9.6Kbps
	 * @throws UnsupportedCommOperationException
	 */
	public static void setBaudRate(SerialPort sPort, int baudRate)
	throws UnsupportedCommOperationException {
		sPort.setSerialPortParams(
				baudRate,
				sPort.getDataBits(),
				sPort.getStopBits(),
				sPort.getParity());
	}
	
	public static CommPortIdentifier[] getCommPortIdentifiers() {
		ArrayList<CommPortIdentifier> ret = new ArrayList<CommPortIdentifier>();
		Enumeration<?> pids = CommPortIdentifier.getPortIdentifiers();
		
		while(pids.hasMoreElements()) {
			CommPortIdentifier pid = (CommPortIdentifier) pids.nextElement();
			ret.add( pid );
		}
		
		return ret.toArray(new CommPortIdentifier[ret.size()]);
	}
}
