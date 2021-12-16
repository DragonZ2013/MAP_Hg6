package Main.Exceptions;

public class ExistentIdException extends Exception{
    public ExistentIdException(String errorMessage){
        super(errorMessage);
    }
}
