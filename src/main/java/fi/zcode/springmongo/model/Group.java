package fi.zcode.springmongo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author mlyly
 */
@Document(collection="xgroups")
public class Group {

    private Map<String, String> name = new HashMap<String, String>();

    @Id
    private String id = UUID.randomUUID().toString();

    @DBRef
    private Collection<User> users = new ArrayList<User>();

    @Override
    public String toString() {
        return "Group[name=" + name + ", id=" + id + "]";
    }

    public void setName(String key, String value) {
        name.put(key, value);
    }

    public String getName(String key) {
        return name.get(key);
    }

    public void addUser(User u) {
        if (u != null) {
            getUsers().add(u);
        }
    }

    public String getId() {
        return id;
    }

    public Map<String, String> getName() {
        return name;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

}
