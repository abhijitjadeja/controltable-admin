package software.pinklady.controltableadmin;

import java.nio.file.*;

public class TestUtils {

    public static String readTestResource(String fileName) {
        try {
            return Files.readString(Paths.get(TestUtils.class.getResource(fileName).toURI()));
        }
        catch(Exception e) {
            throw new RuntimeException("Error getting resource ",e);
        }
    }
}