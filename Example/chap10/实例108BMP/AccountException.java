package Account;
public class AccountException extends Exception {
    public AccountException() {
        super();
    }
    public AccountException(Exception e) {
        super(e.toString());
    }
    public AccountException(String s) {
        super(s);
    }
}
