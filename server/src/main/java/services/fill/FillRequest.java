package services.fill;

public class FillRequest {
    private int generations;
    private String username;

    /**
     * This will just construct the request into a data structure
     * so that the Fill Service can easily gather the information.
     * @param generations
     * @param username
     */
    public FillRequest(int generations, String username) {
        this.generations = generations;
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public String getUsername() {
        return username;
    }
}
