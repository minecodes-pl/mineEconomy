package pl.arturekdev.mineEconomy;

public class Logger {

	public static void warn(String warn) {
		System.out.println(mineEconomy.getInstance().getName() +" WARN: " + warn);
	}

	public static void info(String info) {
		System.out.println(mineEconomy.getInstance().getName() +" INFO: " + info);
	}

	public static void mysql(String mysql) {
		System.out.println(mineEconomy.getInstance().getName() +" MySQL: " + mysql);
	}

	public static void log(String log) {
		System.out.println(mineEconomy.getInstance().getName() +" LOG: " + log);
	}

	
}
