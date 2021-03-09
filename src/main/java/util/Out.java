package util;

public class Out {
	public static void println(String a) {
		final StackTraceElement userElement = Thread.currentThread().getStackTrace()[2];
		System.out.print(
				"(" + userElement.getFileName() + ":" + userElement.getLineNumber() + ") ");
		System.out.println(a);
	}
}
