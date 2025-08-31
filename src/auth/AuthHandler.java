package auth;

public abstract class AuthHandler {
    private AuthHandler next;

    public AuthHandler setNext(AuthHandler next) {
        this.next = next;
        return next;
    }

    public boolean handle(String username, String password) {
        if (!process(username, password)) {
            return false;
        }
        return next == null || next.handle(username, password);
    }

    protected abstract boolean process(String username, String password);
}
