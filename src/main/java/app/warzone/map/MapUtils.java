package app.warzone.map;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MapUtils {

    Map d_currTargetMap;

    public void editCountry(List<String> p_arguments){

    }

    public void editContinent(List<String> p_arguments){

    }

    public void editNeighbour(List<String> p_arguments){

    }

    public void showMap(){
        d_currTargetMap.printMap(true);
    }

    public void saveMap() {
        try {
            File myObj = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                            .getResource("maps/" + d_currTargetMap.d_mapName + ".map")).getFile());
            FileWriter fw = new FileWriter(myObj);
            BufferedWriter out = new BufferedWriter(fw);

            List<String> lines = new ArrayList<>();

            lines.add("[continents]");
            for (Continent l_continent : d_currTargetMap.d_continents)
                lines.add(l_continent == null ? "" : l_continent.d_continentName);

            lines.add("\n");

            lines.add("[countries]");
            for (Country l_country : d_currTargetMap.d_countries){
                lines.add(String.format("%d %s %d",l_country.d_countryId,l_country.d_countryName,l_country.d_memberOfContinent.d_continentId));
            }

            lines.add("\n");

            lines.add("[borders]");
            for (Country l_country : d_currTargetMap.d_countries) {
                StringBuilder borderInfo = new StringBuilder(String.valueOf(l_country.d_countryId));
                for (Country l_neighbour : l_country.d_neighbours){
                    borderInfo.append("\t").append(String.valueOf(l_neighbour.d_countryId));
                }
                lines.add(borderInfo.toString());
            }

            for(String s : lines)
                out.write(s);
            out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void editMap(List<String> p_arguments){
        try {
            File myObj = new File(Objects.requireNonNull(this.getClass().getClassLoader()
                    .getResource("maps/" + p_arguments.get(0) + ".map")).getFile());

            if(!myObj.exists()){
                d_currTargetMap = new Map(p_arguments.get(0));
               if(myObj.createNewFile()) {
                   System.out.println("File not found created new");
               }
            } else {
                MapFileParser l_fileParser = new MapFileParser(p_arguments.get(0));
                Scanner l_fileScanner = new Scanner(myObj);
                d_currTargetMap = l_fileParser.parseMapFile(l_fileScanner);
                l_fileScanner.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void validateMap(){

    }

}
