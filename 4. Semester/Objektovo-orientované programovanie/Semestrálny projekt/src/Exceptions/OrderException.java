package Exceptions;

//Vynimka pri vytvarani novej objednavky
public class OrderException extends Exception {

    public OrderException() {
        super("Treba zvolit typ objednavky");
    }
    public OrderException(String mesage) {
        super(mesage);
    }
}
