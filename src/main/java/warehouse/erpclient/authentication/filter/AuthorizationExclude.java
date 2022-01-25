package warehouse.erpclient.authentication.filter;

public enum AuthorizationExclude {

    LOGIN("/login"),
    H_CONSOLE("/h2-console");

    private final String path;

    AuthorizationExclude(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
