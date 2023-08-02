package io.ikeyit.blankpaper.concurrency;

import com.google.common.base.Predicate;

/**
 * 3 threads to print number orderly just like this:
 * T1   T2  T3
 * 0
 *      1
 *          2
 *      3
 * 4
 *      5
 *          6
 *      7
 * 8
 */
public class SOutputThread extends Thread {
    private static Object lock = new Object();
    private static int seq = 0;
    private Predicate<Integer> predicate;
    public SOutputThread(String name, Predicate<Integer> predicate) {
        super(name);
        this.predicate = predicate;
    }

    @Override
    public void run() {
        synchronized (lock) {
            while(!shouldExit()) {
                if (predicate.apply(seq)) {
                    System.out.println(getName() + ": " + seq);
                    seq++;
                    lock.notifyAll();
                } else {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println(getName() + ": exited");
                        break;
                    }
                }
            }
        }
    }

    public boolean shouldExit() {
        return seq > 100;
    }

    public static void main(String[] args) {
        Thread thread1 = new SOutputThread("T1", seq -> seq % 4 == 0);
        Thread thread2 = new SOutputThread("T2", seq -> seq % 2 == 1);
        Thread thread3 = new SOutputThread("T3", seq -> seq % 4 == 2);
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
