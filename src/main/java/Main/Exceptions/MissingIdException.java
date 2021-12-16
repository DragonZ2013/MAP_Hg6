package Main.Exceptions;

public class MissingIdException extends Exception{
    public MissingIdException(String errorMessage){
        super(errorMessage);
    }
}
