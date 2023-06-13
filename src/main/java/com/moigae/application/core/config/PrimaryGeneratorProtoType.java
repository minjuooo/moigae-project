package com.moigae.application.core.config;

import java.util.Random;

public class PrimaryGeneratorProtoType {
    /**
     * 의미가 불분명한 매직 넘버를 상수로 선언하면 어떨까요? (홍정완) -> 최욱재
     * 일단 기능 다되고 해보겠습니다 (최욱재) -> 홍정완
     */
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    public static String generate() {
        return "c" + encodeTimestamp(System.currentTimeMillis()) + generateRandomString(10);
    }

    public static String encodeTimestamp(long timestamp) {
        StringBuilder result = new StringBuilder();
        while (timestamp > 0) {
            result.append(ALPHABET.charAt((int) (timestamp % ALPHABET.length())));
            timestamp /= ALPHABET.length();
        }
        while (result.length() < 8) {
            result.append('0');
        }
        return result.toString();
    }

    public static String generateRandomString(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return result.toString();
    }
}