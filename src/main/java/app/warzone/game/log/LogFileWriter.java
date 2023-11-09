package app.warzone.game.log;


import app.warzone.game.log.LogEntryBuffer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Observable;
import java.util.Observer;

/**
 * The class updates the Log File with logs from LogEntryBuffer Class.
 */
public class LogFileWriter implements Observer {

    /**
     * The updated LogEntry Buffer Observable Object.
     */
    private static LogEntryBuffer d_logEntryBuffer;



    /**
     * The function writes the updated LogEntryBuffer Object into the Log File.
     *
     * @param p_observable LogEntryBuffer Object.
     * @param p_object Object.
     */
    @Override
    public void update(Observable p_observable, Object p_object) {
        d_logEntryBuffer = (LogEntryBuffer) p_observable;
        File l_logfile = new File("LogFile.txt");
        String l_logMessage = d_logEntryBuffer.getD_logMessage();

        try{
            if(l_logMessage.equals("Welcome to Risk (Warzone) by U6 build1\n\n")) {
                Files.newBufferedWriter(Paths.get("LogFile.txt"), StandardOpenOption.TRUNCATE_EXISTING).write(" ");
            }
            Files.write(Paths.get("LogFile.txt"), l_logMessage.getBytes(StandardCharsets.US_ASCII), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }catch(Exception l_e){
            l_e.printStackTrace();
        }
    }
}

