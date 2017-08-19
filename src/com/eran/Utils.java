package com.eran;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by user on 25/07/2017.
 */
public class Utils {
    public static List<String> buildDataListFromFile(String inputFile) throws IOException {
        Stream<String> stream = Files.lines(Paths.get(inputFile));
        List<String> mylist = stream.collect(Collectors.toList());
        return mylist;
    }
}
