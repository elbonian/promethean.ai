package ai.promethean.Output;

/**
 * Output interface requires a writeToFile function that writes a Java object to a file
 * Extensible for any output language type or format
 */
public interface Output {
    void writeToFile(Object o, String filePath);
}
