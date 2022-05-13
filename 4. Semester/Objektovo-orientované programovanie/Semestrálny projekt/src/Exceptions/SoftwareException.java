package Exceptions;

//Vynimka pri instalacii softwareu do PC
public class SoftwareException extends Exception {

    public SoftwareException(String mesage) {
        super(mesage);
    }
}
