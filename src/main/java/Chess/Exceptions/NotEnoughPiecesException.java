package Chess.Exceptions;

public class NotEnoughPiecesException extends Exception {
    public NotEnoughPiecesException(String errormessage) {
        super(errormessage);
    }
}
