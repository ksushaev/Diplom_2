package all_for_user;

public class DataForLoginUser {
    private String email;
    private String password;

    public DataForLoginUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public DataForLoginUser() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static DataForLoginUser from(CreateUser createUser) {
        return new DataForLoginUser(createUser.getEmail(), createUser.getPassword());
    }
}
