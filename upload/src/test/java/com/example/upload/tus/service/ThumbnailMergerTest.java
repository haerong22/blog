package com.example.upload.tus.service;

import org.junit.jupiter.api.Test;

class ThumbnailMergerTest {

    @Test
    void merge() {

        String filename = "video/2023-06-05/9d49299db3c7469a9faff4f35f400ce7";

        ThumbnailMerger.merge(filename, 40);
    }

}