package edu.umb.cs681.hw06.primes;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;

public class RunnableCancellablePrimeGeneratorTest {
	@Test
	public void cancelPrimeGenerating() {
		RunnableCancellablePrimeGenerator gen = new RunnableCancellablePrimeGenerator(1, 100);
		Thread thread = new Thread(gen);
		thread.start();
		gen.setDone();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		gen.getPrimes().forEach((Long prime) -> System.out.print(prime + ", "));


		ArrayList<Long> actual = new ArrayList<>();

		assertArrayEquals(actual.toArray(), gen.getPrimes().toArray());
	}

	@Test
	public void cancelPrimeGeneratingSleep() throws InterruptedException {
		RunnableCancellablePrimeGenerator gen = new RunnableCancellablePrimeGenerator(1, 100);
		Thread thread = new Thread(gen);
		thread.start();
		Thread.sleep(100);
		gen.setDone();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		gen.getPrimes().forEach((Long prime) -> System.out.print(prime + ", "));


		LinkedList<Long> actual =
				new LinkedList<>(Arrays.asList(2l, 3l, 5l, 7l, 11l, 13l, 17l, 19l, 23l, 29l, 31l,
						37l, 41l, 43l, 47l, 53l, 59l, 61l, 67l, 71l, 73l, 79l, 83l, 89l, 97l));

		assertEquals(actual, gen.getPrimes());
	}
}
