package utils;

import java.text.SimpleDateFormat;
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

	// Generate random number
	public static String getRandomNumber(int length) {
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
	public static String generateRandomDOB() {
		try {
			long randomMillis = ThreadLocalRandom.current().nextLong();
			Date randomDate = new Date(randomMillis);

			SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd");
			return isoFormatter.format(randomDate);
		} catch (Exception e) {
			throw new RuntimeException("Failed to generate random DOB", e);
		}
	}

}
