package com.xxj.dubboprovider;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author  xuxiaojuan
 * @date  2020/10/10 10:19 上午
 */

@SpringBootTest
public class LeetCodeTest {

    @Test
    public void isPalindrome() {
        String s  =   "A man, a plan, a canal: Panama";
        char[] chars = s.toLowerCase().toCharArray();
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < chars.length; i++) {
            if(Character.isLetterOrDigit(chars[i])) {
                res.append(chars[i]);
            }
        }
        char[] chars1 = res.toString().toLowerCase().toCharArray();
        for(int i = 0; i < chars1.length/2; i++) {
            if(chars1[i] != chars1[res.length() - i - 1 ])  {
                System.out.println(false) ;
            }
        }
        System.out.println(true);
    }


    @Test
    public void majorityElement() {
        int[] nums = {2,2,1,1,1,2,2};
        Map<Integer,Integer> map = new HashMap<>();

        for(int i = 0; i < nums.length; i++) {

            if(map.get(nums[i]) != null ) {
                int count = map.get(nums[i]);
                map.put(nums[i],count+=1);
            } else {
                map.put(nums[i],1);
            }
        }
        int max = nums.length / 2;
        for(Map.Entry<Integer,Integer> entry : map.entrySet()) {
            if(entry.getValue() > max) {
                System.out.println(entry.getKey());
            }
        }
    }


    @Test
    public void titleToNumber() {
        String s = "ZY";
        char[] chars = s.toCharArray();
        int res = 0;
        for (int i = 0; i < chars.length; i++) {
            res = res * 26 + (chars[i] - 'A' + 1);
        }
        System.out.println(res);
    }

    @Test
    public void numberToTitle() {
        int num = 701;
        StringBuilder s = new StringBuilder();
        while (num != 0) {
            num -- ;
            s.append((char) ('A' + num % 26));
            num = num / 26;
        }
        System.out.println(s.reverse().toString());

    }


    @Test
    public void trailingZeroes() {
        int n = 5;
        int zeroCount = 0;
        long currentMultiple = 5;
        while (n > 0) {
            n /= currentMultiple;
            zeroCount += n;
        }

        System.out.println(zeroCount);
    }


    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();

        while(n != 1 && !set.contains(n)) {
            set.add(n);
            n = getNext(n);
        }
        return n == 1;
    }

    public int getNext(int n) {
        int sum = 0;

        while(n > 0) {
            int m = n % 10;
            sum = sum + m*m;
            n = n / 10;

        }
        return sum;
    }



    @Test
    public void CountPrimes()
    {
        int n = 10;
        int count = 0;
        boolean signs[] = new boolean[10];
        for (int i = 2; i < n; i++)
        {
            if (!signs[i])
            {
                count ++;
                for (int j = i + i; j < n; j += i)
                {
                    signs[j] = true;
                }
            }
        }
        System.out.println(count);
    }


}

