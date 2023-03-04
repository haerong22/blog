package com.example.upload;

import org.junit.jupiter.api.Test;

public class Tests {

    @Test
    void heap_memory() {
        long gb = 1024L * 1024L * 1024L;
        long max = Runtime.getRuntime().maxMemory();
        System.out.println((double) max / gb);
    }
}
