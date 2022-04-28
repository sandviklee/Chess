package Chess.Exceptions;

public class GameEndedException extends Exception{
    public GameEndedException(String errormessage) {
        super(errormessage);
    }
}
