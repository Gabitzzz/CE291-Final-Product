package Team31;

final public class InvalidFileExtensionException extends Exception {
    /**
     * Custom Exception for the entry of an invalid file extension.
     * Calls the superclass constructor with the message for the Exception from the config class.
     */
    public InvalidFileExtensionException() {
        super(Config.INVALID_FILE);
    }

    public InvalidFileExtensionException(String filePath) {
        super(Config.INVALID_FILE + " Path: \"" + filePath + "\"");
    }
}
