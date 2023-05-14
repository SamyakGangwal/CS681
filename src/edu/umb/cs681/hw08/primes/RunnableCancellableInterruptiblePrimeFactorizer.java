package edu.umb.cs681.hw08.primes;

import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellableInterruptiblePrimeFactorizer
		extends RunnableCancellablePrimeFactorizer {
	private final ReentrantLock lock = new ReentrantLock();

	public RunnableCancellableInterruptiblePrimeFactorizer(long dividend, long from, long to) {
		super(dividend, from, to);
	}

	public void setDone() {
		lock.lock();
		try {
			done = true;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void generatePrimeFactors() {
		long divisor = from;
		while (dividend != 1 && divisor <= to) {
			lock.lock();
			try {
				if (done) {
					System.out.println("Stopped generating prime factors.");
					this.factors.clear();
					break;
				}

				if (divisor > 2 && isEven(divisor)) {
					divisor++;
					continue;
				}

				if (dividend % divisor == 0) {
					factors.add(divisor);
					dividend /= divisor;
				} else {
					if (divisor == 2) {
						divisor++;
					} else {
						divisor += 2;
					}
				}
			} finally {
				lock.unlock();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.toString());
				continue;
			}
		}
	}

	public static void main(String[] args) {
		RunnableCancellableInterruptiblePrimeFactorizer fac =
				new RunnableCancellableInterruptiblePrimeFactorizer(5745, 2, 100);

		Thread thread = new Thread(fac);
		thread.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fac.setDone();
		thread.interrupt();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fac.getPrimeFactors().forEach((Long prime) ->System.out.print(prime + ", ") );
		System.out.println("\n" + fac.getPrimeFactors().size() + " prime numbers are found.");
	}
}
