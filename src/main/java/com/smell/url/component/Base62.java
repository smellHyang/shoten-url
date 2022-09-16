package com.smell.url.component;

import org.springframework.stereotype.Component;

@Component
public class Base62 {

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String encoding(int number){
        StringBuilder sb = new StringBuilder();

        while(number > 0){
            sb.append(BASE62.charAt((int)(number % 62)));
            number /= 62;
        }
        return sb.toString();

    }

    public static int decoding(String shorten){
        int sum = 0;
        int power = 1;
        for(int i = 0; i < shorten.length(); i++){
            sum += BASE62.indexOf(shorten.charAt(i)) * power;
            power += 62;
        }
        return sum;
    }
}
