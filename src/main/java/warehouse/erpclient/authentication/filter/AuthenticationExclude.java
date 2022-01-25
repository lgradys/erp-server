package warehouse.erpclient.authentication.filter;

public enum AuthenticationExclude {

    H_CONSOLE("/h2-console");

    private final String path;

    AuthenticationExclude(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
