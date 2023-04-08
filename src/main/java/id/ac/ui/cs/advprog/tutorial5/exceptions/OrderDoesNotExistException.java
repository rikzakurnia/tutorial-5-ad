package id.ac.ui.cs.advprog.tutorial5.exceptions;

public class OrderDoesNotExistException extends RuntimeException {
    public OrderDoesNotExistException(Integer id) {
        super("Order with id " + id + " does not exist");
    }
}
