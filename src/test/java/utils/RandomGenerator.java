package utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {

	// Generate random name with alphabets
	public static String generateRandomName(int length) {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(length);
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
		}
		return sb.toString();
	}

	// Generate random email
	public static String generateRandomEmail() {
		StringBuilder sb = new StringBuilder();
		sb.append(generateRandomName(5)).append(".").append(generateRandomNumber(3)).append("@numpy.com");
		return sb.toString();
	}

	// Generate random number
	public static String generateRandomNumber(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	// Generate random DOB
	public static String generateRandomDOB(String startDateStr, String endDateStr) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = formatter.parse(startDateStr);
			Date endDate = formatter.parse(endDateStr);

			long randomMillis = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
			Date randomDate = new Date(randomMillis);

			SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000'Z'");
			return isoFormatter.format(randomDate);
		} catch (Exception e) {
			throw new RuntimeException("Failed to generate random DOB", e);
		}
	}

	// Generate random DOB in yyyy-MM-dd date format
	public static String generateRandomDOBInYYYYMMDD() {
		LocalDate start = LocalDate.of(1950, 1, 1);
		LocalDate end = LocalDate.of(2025, 07, 31);

		long startEpochDay = start.toEpochDay();
		long endEpochDay = end.toEpochDay();

		long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
		LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return randomDate.format(formatter);

	}

}
