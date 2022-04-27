package com.thealgorithms.maths;
import org.checkerframework.checker.index.qual.NonNegative;

import java.math.BigInteger;
import java.util.*;

public class KaprekarNumbers {

	/* This program demonstrates if a given number is Kaprekar Number or not.
	Kaprekar Number: A Kaprekar number is an n-digit number which its square can be split into two parts where the right part has n
	digits and sum of these parts is equal to the original number. */

	// Provides a list of kaprekarNumber in a range
	public static ArrayList<Long> kaprekarNumberInRange(long start, long end) throws Exception {
		long n = end-start;
		if (n <0) throw new Exception("Invalid range");
		ArrayList<Long> list = new ArrayList<>();

		for (long i = start; i <= end; i++) {
			if (isKaprekarNumber(i)) list.add(i);
		}

		return list;
	}

	// Checks whether a given number is Kaprekar Number or not
	public static boolean isKaprekarNumber(long num) {
		String number = Long.toString(num);
		BigInteger originalNumber = new BigInteger(number);
		BigInteger numberSquared = originalNumber.multiply(originalNumber);
		if(number.length() == numberSquared.toString().length()){
			return number.equals(numberSquared.toString());
		}
		else{
			BigInteger leftDigits1 = new BigInteger("0");
			BigInteger leftDigits2;
			if(numberSquared.toString().contains("0")){
//				can't be -1 if contained
				@SuppressWarnings("index")
				@NonNegative int endIndex = numberSquared.toString().indexOf("0");
				leftDigits1 = new BigInteger(
						numberSquared.toString().
								substring(0, endIndex
								)
				);
			}
//			can't be negative since numberSquared is number^2 so it has length greater or equal to it
			@SuppressWarnings("index")
			@NonNegative int endIndex = numberSquared.toString().length() - number.length();
			leftDigits2 = new BigInteger(
					numberSquared.toString()
							.substring(0, endIndex)
			);
			BigInteger rightDigits = new BigInteger(numberSquared.toString().substring(endIndex));
			String x = leftDigits1.add(rightDigits).toString();
			String y = leftDigits2.add(rightDigits).toString();
			return (number.equals(x)) || (number.equals(y));
		}		
	}

}
