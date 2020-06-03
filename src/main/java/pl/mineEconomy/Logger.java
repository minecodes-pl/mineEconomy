package pl.mineEconomy;

public class Logger {

	public static void warn(String warn) {
		System.out.println(Main.getInst().getName() +" WARN: " + warn);
	}

	public static void info(String info) {
		System.out.println(Main.getInst().getName() +" INFO: " + info);
	}

	public static void mysql(String mysql) {
		System.out.println(Main.getInst().getName() +" MySQL: " + mysql);
	}

	public static void log(String log) {
		System.out.println(Main.getInst().getName() +" LOG: " + log);
	}

	
}
