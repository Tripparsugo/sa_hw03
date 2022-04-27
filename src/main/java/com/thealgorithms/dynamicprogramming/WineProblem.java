package com.thealgorithms.dynamicprogramming;

import org.checkerframework.checker.index.qual.*;
import org.checkerframework.common.value.qual.IntRange;
import org.checkerframework.common.value.qual.MinLen;

/**
 * Imagine you have a collection of N wines placed next to each other on the
 * shelf. The price of ith wine is pi(Prices of different wines are different).
 * Because wine gets better every year supposing today is year 1, on year y the
 * price would be y*pi i.e y times the value of the initial year. You want to
 * sell all wines but you have to sell one wine per year. One more constraint on
 * each year you are allowed to sell either leftmost or rightmost wine on the
 * shelf. You are not allowed to reorder. You have to find the maximum profit
 */
public class WineProblem {

    // Method 1: Using Recursion
    // Time Complexity=0(2^N) Space Complexity=Recursion extra space
    public static int WPRecursion(int @MinLen(1) [] arr, @IndexFor("#1") int si, @IndexFor("#1") int ei) {
        int n = arr.length;
        int year = (n - (ei - si + 1)) + 1;
        if (si == ei) {
            return arr[si] * year;
        }

//        Ideally one would be able to add an annotation indicating that si <= ei directly
//        in the arguments which would then be combined with !(si == ei) to establish that
//        both si_new and ei_new are still valid indexes.
//
//        I could only find @LessThan(String[] values)
        @SuppressWarnings("index")
        @LTLengthOf("arr") int si_new = si + 1;
        @SuppressWarnings("index")
        @NonNegative int ei_new = ei - 1;

        int start = WPRecursion(arr, si_new, ei) + arr[si] * year;
        int end = WPRecursion(arr, si, ei_new) + arr[ei] * year;

        int ans = Math.max(start, end);

        return ans;
    }

    // Method 2: Top-Down DP(Memoization)
    // Time Complexity=0(N*N) Space Complexity=0(N*N)+Recursion extra space
    public static int WPTD(final int @MinLen(1) [] arr, final @IndexFor("#1") @LessThan("#3 + 1") int si, final @IndexFor("#1")int ei, final int @SameLen("#1") [] @SameLen("#1") [] strg) {
        int n = arr.length;
        int year = (n - (ei - si + 1)) + 1;
        if (si == ei) {
            return arr[si] * year;
        }

//        If I don't add this the checker doesn't realize that strg[si][ei] is safe apparently.
//        I have no idea of why this happens.
        int[] a = strg[si];
        int b = a[ei];

        if (strg[si][ei] != 0) {
            return strg[si][ei];
        }

//        I found no tags to make it understand that 0 <= si < ei <= arr.length if we reach this point of the program
//        since  we have !(si == ei) && si <= ei



        @SuppressWarnings("index")
        @LTLengthOf("arr") int si_new = si + 1;
        int start = WPRecursion(arr, si_new, ei) + arr[si] * year;
        int end = WPRecursion(arr, si, ei - 1) + arr[ei] * year;

        int ans = Math.max(start, end);

//       same here it apparently forgets after the @SuppressWarnings("index")?
        int[] c = strg[si];
        int[] d = strg[ei];

        strg[si][ei] = ans;

        return ans;
    }

    // Method 3: Bottom-Up DP(Tabulation)
    // Time Complexity=0(N*N/2)->0(N*N) Space Complexity=0(N*N)
//    Without changing the code the checker struggles to understand indexes for a matrix modified by the loop
//    so I added the @SuppressWarnings("index")
    @SuppressWarnings("index")
    public static int WPBU(int @MinLen(1)[] arr) {
        @LengthOf("arr") int n = arr.length;
        int[][] strg = new int[n][n];


        for (int slide = 0; slide <= n - 1; slide++) {
            for (int si = 0; si <= n - slide - 1; si++) {
                int  ei = si + slide;
                int year = (n - (ei - si + 1)) + 1;
                if (si == ei) {

//                    int[] a = strg[ei];
//                   This doesn't work  despite the array being initialized with  new int[n][n]
//                    int  b = a[ei];

                    strg[si][ei] = arr[si] * year;
                } else {
                    @LTLengthOf("arr") int si_new = si + 1;
                    @NonNegative int ei_new = ei - 1;

                    int start = strg[si_new][ei] + arr[si] * year;
                    int end = strg[si][ei_new] + arr[ei] * year;

                    strg[si][ei] = Math.max(start, end);

                }
            }
        }
        return strg[0][n - 1];
    }

    @SuppressWarnings("index")
    public static void main(String[] args) {
        int @MinLen(1)  [] arr = {2, 3, 5, 1, 4, 6};
        System.out.println("Method 1: " + WPRecursion(arr, 0, arr.length - 1));
//       This seems to be bugged as I get the following:
//          found   : @SameLenUnknown int @SameLenUnknown [] @SameLen("arr") []
//          required: @SameLenUnknown int @SameLen("arr") [] @SameLen("arr") []
//        so I added @SuppressWarnings("index") to the method
        int @SameLen("arr") [] @SameLen("arr")[] tmp = new int[arr.length][arr.length];
        System.out.println("Method 2: " + WPTD(arr, 0, arr.length - 1, tmp));
        System.out.println("Method 3: " + WPBU(arr));

    }

}
// Memoization vs Tabulation : https://www.geeksforgeeks.org/tabulation-vs-memoization/
// Question Link : https://www.geeksforgeeks.org/maximum-profit-sale-wines/
