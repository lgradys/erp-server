package warehouse.erpclient.authentication.filter;

public enum AuthorizationExclude {

    LOGIN("/login", false),
    OPEN_API("/v3/api-docs",true),
    OPEN_API_UI("/swagger-ui", true);

    private final String path;
    private final boolean batch;

    AuthorizationExclude(String path, boolean batch) {
        this.path = path;
        this.batch = batch;
    }

    public String getPath() {
        return path;
    }

    public boolean isBatch() {
        return batch;
    }

}
