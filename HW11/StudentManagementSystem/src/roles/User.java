package roles;

/**
 * This is the User class.
 * @author Yicheng Xia
 */
public abstract class User {

    /**
     * The ID of User.
     */
    private String ID;

    /**
     * The name of User.
     */
    private String name;

    /**
     * The username of User.
     */
    private String username;
    
    /**
     * The password of User.
     */
    private String password;

    /**
     * The constructor of User.
     * @param ID ID of User
     * @param name name of User
     * @param username username of User
     * @param password password of User
     */
    public User(String ID, String name, String username, String password) {
        this.ID = ID;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    /**
     * Get the ID of User.
     * @return ID of User
     */
    public String getID() {
        return this.ID;
    }

    /**
     * Get the name of User.
     * @return name of User
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the username of User.
     * @return username of User
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get the password of User.
     * @return password of User
     */
    public String getPassword() {
        return this.password;
    }
}
