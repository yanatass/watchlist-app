package movie;

public class MovieNotFound extends RuntimeException {
    public MovieNotFound(String message) {
        super(message);
    }
}
