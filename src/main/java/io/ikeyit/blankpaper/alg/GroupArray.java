package io.ikeyit.blankpaper.alg;

import java.util.LinkedList;
import java.util.List;

public class GroupArray {

    public static void output(int[] numbers, int maxSum, int maxGroupCount) {
        List<Integer> wholeGroup = new LinkedList<>();
        LinkedList<Integer> curGroup = new LinkedList<>();
        int curSum = 0;
        int curCount = 0;
        for (int n : numbers) {
            curGroup.add(n);
            curSum += n;
            if (curGroup.size() == 3) {
                if (curSum > maxSum) {
                    int first = curGroup.poll();
                    curSum-=first;
                    curCount++;
                    wholeGroup.add(first);
                } else {
                    if (curCount >= maxGroupCount) {
                        wholeGroup.add(curGroup.poll());
                        wholeGroup.add(curGroup.poll());
                        System.out.println(wholeGroup);
                        curSum = curGroup.getLast();
                    } else {
                        int first = curGroup.poll();
                        curSum -= first;
                    }
                    wholeGroup.clear();
                    curCount = 0;
                }
            }
        }

        if (curCount >= maxGroupCount) {
            wholeGroup.addAll(curGroup);
            System.out.println(wholeGroup);
        }

    }

    public static void main(String[] args) {
        output(new int[]{100, 40, 2, 90, 79, 3, 90, 60, 80, 20, 3, 4, 6,7,8,90,55,55,55,66}, 100, 4);
    }
}
