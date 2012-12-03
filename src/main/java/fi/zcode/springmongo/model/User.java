package fi.zcode.springmongo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author mlyly
 */
@Document(collection="xusers")
public class User {

    @Id
    private String id = UUID.randomUUID().toString();

    private String username;
    private String password;
    private int age;
    private String firstname;
    private String lastname;

    // @DBRef
    // private Collection<Group> groups = new ArrayList<Group>();

    @Override
    public String toString() {
        return "User[username=" + username + ", password=***, age=" + age + ", fn=" + firstname + ", ln=" + lastname + ", id=" + id + "]";
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

//    public void addGroup(Group g) {
//        if (g != null) {
//            getGroups().add(g);
//        }
//    }
//

    public String getId() {
        return id;
    }

//    public Collection<Group> getGroups() {
//        return groups;
//    }
//
//    public void setGroups(Collection<Group> groups) {
//        this.groups = groups;
//    }

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

