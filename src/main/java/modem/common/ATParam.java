package modem.common;

import util.Enums.NamedInt.IInt;

public abstract class ATParam {
	public static String add(IInt obj) {
		return add( ((obj != null) ? "" + obj.getInt() : "") );
	}
	
	public static String add(String obj) {
		return "," + ((obj != null) ? obj : "");
	}
}
