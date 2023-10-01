package app.warzone.map;

import java.util.Scanner;

public class MapFileParser {
    Map d_parsedMap;

    public MapFileParser(String name){
        d_parsedMap = new Map(name);
    }
    String[] lineExtractor(StringBuilder p_file, String p_start, String p_end){
        int l_sectionStart = p_file.indexOf(p_start);
        if(p_end != null) {
            int l_sectionEnd = p_file.indexOf(p_end);
            return p_file.substring(l_sectionStart, l_sectionEnd).split("\n");
        }

        return p_file.substring(l_sectionStart).split("\n");
    }
    void parseContinents(StringBuilder p_fileText){
        String[] l_continentLines = lineExtractor(p_fileText,"[continents]", "[countries]" );
        int i = 1;
        for(String l_continentInfo : l_continentLines){
            String[] l_infoTokens = l_continentInfo.split(" ");
            d_parsedMap.addContinent(new Continent(i,l_infoTokens[0], Integer.parseInt(l_infoTokens[1])));
            i++;
        }
    }

    void parseCountries(StringBuilder p_fileText){
        String[] l_countryLines = lineExtractor(p_fileText,"[countries]","[borders]");
        for(String l_countryInfo : l_countryLines){
            String[] l_infoTokens = l_countryInfo.split(" ");
            d_parsedMap.addCountry(
                    new Country(
                            Integer.parseInt(l_infoTokens[0]),
                            l_infoTokens[1],
                            d_parsedMap.getContinentById(
                                    Integer.parseInt(l_infoTokens[2])
                            )
                    )
            );
        }
    }

    void parseConnections(StringBuilder p_fileText){
        String[] l_connectionLines = lineExtractor(p_fileText,"[borders]",null);
        for(String l_connectionInfo : l_connectionLines){
            String[] l_infoTokens = l_connectionInfo.split(" ");
            Country l_targetCountry = d_parsedMap.getCountryById(Integer.parseInt(l_infoTokens[0]));
            for(int l_i = 1 ; l_i < l_infoTokens.length; l_i++) {
                l_targetCountry.addRemoveNeighbour(
                        d_parsedMap.getCountryById(Integer.parseInt(l_infoTokens[l_i])),true
                );
            }
        }
    }

    public Map parseMapFile(Scanner p_fileScan){
        StringBuilder file = new StringBuilder();
        while (p_fileScan.hasNextLine()) {
            file.append(p_fileScan.nextLine());
        }
        parseContinents(file);
        parseCountries(file);
        parseConnections(file);

        return d_parsedMap;
    }
}
