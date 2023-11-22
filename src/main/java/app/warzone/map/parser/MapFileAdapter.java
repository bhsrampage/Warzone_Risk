package app.warzone.map.parser;

import app.warzone.map.Map;

import java.util.Scanner;

/**
 * Adapter that translates ConquestFileParser class to MapFileParser
 * @see MapFileParser
 * @see ConquestFileParser
 */
public class MapFileAdapter extends MapFileParser {

    private final ConquestFileParser conquestFileParser;

    /**
     * Constructs a MapFileParser for parsing a map file with the given name.
     *
     * @param p_parser The name of the map.
     */
    public MapFileAdapter(ConquestFileParser p_parser) {
        super(p_parser.d_parsedMap.getD_mapName());
        conquestFileParser = p_parser;
    }

    /**
     * Translator for Conquest to Map
     * @param p_fileScan The scanner for reading the map file.
     * @return mapObject
     */
    public Map parseMapFile(Scanner p_fileScan) {
        return conquestFileParser.parseMapFile(p_fileScan);
    }

}
