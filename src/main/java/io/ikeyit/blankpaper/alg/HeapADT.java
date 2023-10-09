package io.ikeyit.blankpaper.alg;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Some problems which can be solved with heap.
 * Good examples for the interview
 */
public class HeapADT {

    public static <T> void swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    // 调整一个节点使其成为最大堆。
    public static <T extends Comparable> void heapify(T[] arr, int parent, int len) {
        int left = 2*parent + 1;
        int right = 2*parent + 2;
        int largest = parent;
        T largestValue = arr[parent];
        if (left < len && arr[left].compareTo(largestValue) > 0) {
            largest = left;
            largestValue = arr[left];
        }

        if (right < len && arr[right].compareTo(largestValue) > 0) {
            largest = right;
        }

        if (largest != parent) {
            swap(arr, largest, parent);
            heapify(arr, largest, len);
        }
    }

    // 从叶节点的父节点开始构建堆。从下至上构建
    public static <T extends Comparable> void buildHeap(T[] arr, int parent, int len) {
        for (int i = len / 2 -1; i >= 0; i--) {
            heapify(arr, i, len);
        }
    }

    // 堆排序
    public static <T extends Comparable> void heapSort(T[] arr) {
        buildHeap(arr, 0, arr.length);
        for (int i = 0; i < arr.length; i++) {
            swap(arr, 0, arr.length - i - 1);
            heapify(arr, 0, arr.length - i - 1);
        }
    }

    // Top K问题
    // 先取前k个元素，形成最大堆，然后比较剩余的元素，如果比根节点小就交换到根节点，然后再最大堆化。最后根节点就是第k大元素。
    public static <T extends Comparable> void kthElement(T[] arr, int k) {
        if (k < 0 || k > arr.length){
            throw new IllegalArgumentException("Invalid k!");
        }
        buildHeap(arr, 0, k);
        for (int i = k; i < arr.length; i++) {
            if (arr[i].compareTo(arr[0]) < 0) {
                swap(arr, 0, i);
                heapify(arr, 0, k);
            }
        }

        return ;
    }

    static class WordFrequency implements Comparable<WordFrequency>{
        String word;
        int frequency;

        public WordFrequency(String word) {
            this.word = word;
            this.frequency = 1;
        }

        public String getWord() {
            return word;
        }

        public int getFrequency() {
            return frequency;
        }
        public WordFrequency increaseFrequency() {
            frequency++;
            return this;
        }

        @Override
        public int compareTo(WordFrequency o) {
            return Integer.compare(frequency, o.frequency);
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer(word);
            sb.append(":").append(frequency);
            return sb.toString();
        }
    }

    public static void countWordFrequencyExample() {
        String[] words = {"n","i", "i", "h","a", "o", "n", "i", "k", "k", "n", "i","s", "y", "s", "t", "e", "m"};
        Map<String, WordFrequency> wordFrequencyMap = new HashMap<>();
        for(String word : words) {
            wordFrequencyMap.compute(word, (w, v) -> v == null ? new WordFrequency(w) : v.increaseFrequency());
        }
        System.out.println(wordFrequencyMap);
        WordFrequency[] wordFrequencies = wordFrequencyMap.values().toArray(new WordFrequency[0]);
        kthElement(wordFrequencies, 6);
        System.out.println(Arrays.toString(wordFrequencies));
    }

    public static void heapSortExample() {
        Integer[] data = {3,4,1,2,100,6,1101,43,-1,0,2};
        heapSort(data);
        System.out.println(Arrays.toString(data));
    }

    public static void topKExample() {
        Integer[] data2 = {3,4,1,2,100,6,1101,43,-1,0,2};
        kthElement(data2, 5);
        System.out.println(data2[0]);
        System.out.println(Arrays.toString(data2));
    }

    public static void main(String[] args) {
        heapSortExample();
        topKExample();
        countWordFrequencyExample();
    }
}
