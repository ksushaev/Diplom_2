package all_for_user;

import org.apache.commons.lang3.RandomStringUtils;

public class CreateUser {
    private String email;
    private String password;
    private String name;


    public CreateUser(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public CreateUser() {
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

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public static CreateUser getUserWithRandomStringUtils() {
        String email = RandomStringUtils.randomAlphabetic(10) + "@mail.ru";
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(10);
        return new CreateUser(email, password, name);
    }

    public static CreateUser getNotRandomUser() {

        return new CreateUser("ksushdfa@yandex.ru", "123456", "KsushaEv");
    }
}


