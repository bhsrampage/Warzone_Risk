package app.warzone.map.writer;

/**
 * The MapWriterAdapter class serves as an adapter to allow using the ConquestFileWriter as a MapFileWriter.
 * It extends the functionality of MapFileWriter to adapt and delegate the saveMap method to ConquestFileWriter.
 */
public class MapWriterAdapter extends MapFileWriter {

    /** The ConquestFileWriter instance used for adapting. */
    private final ConquestFileWriter fileWriter;

    /**
     * Constructor for the MapWriterAdapter class.
     *
     * @param p_fileWriter The ConquestFileWriter to be adapted.
     */
    public MapWriterAdapter(ConquestFileWriter p_fileWriter) {
        super(p_fileWriter.d_targetMap);
        fileWriter = p_fileWriter;
    }

    /**
     * Saves the map using the adapted ConquestFileWriter.
     * Delegates the saveMap method to the ConquestFileWriter instance.
     */
    public void saveMap() {
        fileWriter.saveMap();
    }
}
