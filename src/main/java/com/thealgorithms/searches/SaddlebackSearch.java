package com.thealgorithms.searches;

import org.checkerframework.checker.index.qual.*;
import org.checkerframework.checker.units.qual.Length;
import org.checkerframework.common.value.qual.ArrayLen;
import org.checkerframework.common.value.qual.IntRange;

import java.util.Scanner;

/**
 * Program to perform Saddleback Search Given a sorted 2D array(elements are
 * sorted across every row and column, assuming ascending order) of size n*m we
 * can search a given element in O(n+m)
 *
 * <p>
 * we start from bottom left corner if the current element is greater than the
 * given element then we move up else we move right Sample Input: 5 5
 * ->Dimensions -10 -5 -3 4 9 -6 -2 0 5 10 -4 -1 1 6 12 2 3 7 8 13 100 120 130
 * 140 150 140 ->element to be searched output: 4 3 // first value is row,
 * second one is column
 *
 * @author Nishita Aggarwal
 */
public class SaddlebackSearch {

    /**
     * This method performs Saddleback Search
     *
     * @param arr The **Sorted** array in which we will search the element.
     * @param row the current row.
     * @param col the current column.
     * @param key the element that we want to search for.
     * @return The index(row and column) of the element if found. Else returns
     * -1 -1.
     */
    private static int @ArrayLen(2) [] find(int[][] arr,  final @LTLengthOf("#1") int row, final @NonNegative int col, int key) {

        // array to store the answer row and column
        int @ArrayLen(2) [] ans = {-1, -1};
        if (row < 0 || col >= arr[row].length) {
            return ans;
        }
//        need this to add annotation
        final @NonNegative int checkedRow = row;
//        needed because it doesn't understand that arr[checkedRow] == a[row]
        @SuppressWarnings("index")
        final @IndexFor(value = "arr[checkedRow]") int checkedCol = col;

        int[] a = arr[checkedRow];
        int b = arr[checkedRow][checkedCol];

        if (arr[checkedRow][checkedCol] == key) {
            ans[0] = checkedRow;
            ans[1] = checkedCol;
            return ans;
        } // if the current element is greater than the given element then we move up
        else if (arr[checkedRow][checkedCol] > key) {
            return find(arr, checkedRow - 1, checkedCol, key);
        }
        // else we move right
        return find(arr, checkedRow, checkedCol + 1, key);
    }

    /**
     * Main method
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);
        int i;
//        here the tool is rightfully complaining because there is no check on the user input
        @SuppressWarnings("index")
        @Positive int rows = sc.nextInt();
        @SuppressWarnings("index")
        @Positive  int col = sc.nextInt();
        int[][] arr = new int[rows][col];
        for (i = 0; i < rows; i++) {
            for (int j = 0; j < col; j++) {
                @SuppressWarnings("index")
                @IndexFor("arr[i]")
                final int tmp_j = j;
                 arr[i][tmp_j] = sc.nextInt();
            }
        }


        int ele = sc.nextInt();
        // we start from bottom left corner
        int[] ans = find(arr, rows - 1, 0, ele);
        System.out.println(ans[0] + " " + ans[1]);
        sc.close();
    }
}
