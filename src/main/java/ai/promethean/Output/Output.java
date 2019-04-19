package ai.promethean.Output;

import ai.promethean.API.API;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Output interface requires a writeToFile function that writes a Java object to a file
 * Extensible for any output language type or format
 */
public interface Output {
    static void writeToFile(Object o, String fileDir, String fileIdentifier) {}
}
