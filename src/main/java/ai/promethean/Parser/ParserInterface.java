package ai.promethean.parser;

import java.util.List;

// ParserInterface: Interface needs a parse function that takes either a file path or language string as input
// isFile bool denotes whether the string is a file path or not
public interface ParserInterface {
    List<Object> parse(String json, Boolean isFile);
}
