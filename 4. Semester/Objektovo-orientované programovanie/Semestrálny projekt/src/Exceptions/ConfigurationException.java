package Exceptions;

//Vynimka pri konfiguracii PC
public class ConfigurationException extends Exception {

    public ConfigurationException(String mesage) {
        super(mesage);
    }
}
