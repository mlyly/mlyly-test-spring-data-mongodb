package fi.zcode.springmongo.model;

/**
 *
 * @author mlyly
 */
public class User {

    private String username;
    private String password;
    private int age;
    private String firstname;
    private String lastname;

    @Override
    public String toString() {
        return "User[username=" + username + ", password=***, age=" + age + ", fn=" + firstname + ", ln=" + lastname + "]";
    }

    public User() {
    }

    public User(String username, String password, int age) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.lastname = "NA";
        this.firstname = "NA";
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String _password) {
        this.password = _password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String _username) {
        this.username = _username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

}

