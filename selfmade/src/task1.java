import com.sun.tools.javac.util.ArrayUtils;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by laureenschausberger on 12.08.17.
 */



class Solution {
        public static int solution(int[] A, int[] B, int M, int X, int Y) {

            if ( A.length == 0 || A == null || B.length == 0 || B == null || A.length != B.length ||M <= 0 || X <= 0 || Y <= 0) {
                // check if input values are valid
                return -1;
            }

            int stops = 0;

            for(int i = 0; i < A.length; i++) {

                int capacity = 0;
                long weight = 0;

                while (weight < Y && capacity < X && (i+capacity < A.length) ) {
                    if(A[i + capacity] > Y || A[i + capacity] < 0 || B[i + capacity] > M || B[i + capacity] <= 0) {
                        // check if some array-values are out of the normal range
                        return -1;
                    }
                    weight += A[i + capacity];
                    capacity++;
                }

                if(weight > Y) {
                    capacity--;
                }

                int inner_stops = 0;
                ArrayList<Integer> array = new ArrayList<Integer>();

                for(int j = i; j < i+capacity; j++) {
                    if (!array.contains(B[j])) {
                        // if value is not in array, the elevator has to stop at another floor
                        array.add(B[j]);
                        stops++;
                    }
                }

                i += capacity-1;
                stops += inner_stops+1;
            }

            return stops;
        }


        public int solution(int[] A) {

            if ( A.length == 0 || A == null) {
                return -1;
            }

            int counter = 0;
            for (int i = 0; i < A.length; i++) {
                if()
                for(int j = i+1; j < A.length; j++) {
                    if (A[i] == A[j]) {
                        counter ++;
                    }
                }
            }

            if(counter > 1000000000){
                return 1000000000;
            }
            else {
                return counter;
            }
        }
    }


    class Solution2 {
        public int solution(int[] A) {

            if(A.length == 0 || A == null) {
                return -1;
            }

            for(int i = 0; i < A.length; i++) {

                long result_lower = 0;
                if(i != 0){
                    for(int j = i-1; j >= 0; j--) {
                        result_lower += A[j];
                    }
                }

                long result_higher = 0;
                if(i != A.length-1){
                    for(int j = i+1; j < A.length; j++) {
                        result_higher += A[j];
                    }
                }

                if (result_higher == result_lower) {
                    return i;
                }


            }

            return -1;

        }
    }

