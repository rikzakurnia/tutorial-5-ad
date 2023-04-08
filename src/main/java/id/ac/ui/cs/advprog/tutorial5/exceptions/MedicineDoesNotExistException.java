package id.ac.ui.cs.advprog.tutorial5.exceptions;

public class MedicineDoesNotExistException extends RuntimeException {

    public MedicineDoesNotExistException(Integer id) {
        super("Medicine with id " + id + " does not exist");
    }


}
