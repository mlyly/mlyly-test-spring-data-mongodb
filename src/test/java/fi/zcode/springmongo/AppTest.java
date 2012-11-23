package fi.zcode.springmongo;

import fi.zcode.springmongo.model.User;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for simple App.
 */
@ContextConfiguration(locations = {"classpath:test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest {

    private static final Logger LOG = LoggerFactory.getLogger(AppTest.class);

    public static final String USERNAME = "theusername";
    public static final String NEW_PASSWORD = "the new password";
    public static final String PASSWORD = "password123";
    public static final String DB_USERS = "users";
    private String[] fnames = new String[]{
        "James", "Janis", "Elvis", "Amadeus", "Bob"
    };
    private String[] lnames = new String[]{
        "Brown", "Joplin", "Costello", "Mozart", "Squarepants"
    };
    @Autowired
    private MongoOperations mongoTemplate;

    @Test
    public void testApp() {
        // assertTrue( true );
        LOG.info("testApp)()...");

        LOG.info("  mongoTemplate: {}", mongoTemplate);

        User user = new User(USERNAME, PASSWORD, 42);

        LOG.info("save...");
        mongoTemplate.save(user, DB_USERS);

        LOG.info("find...");
        User savedUser = mongoTemplate.findOne(new Query(Criteria.where("username").is(USERNAME)), User.class, DB_USERS);
        LOG.info("  found: {}", savedUser);

        assertNotNull(savedUser);
        assertEquals(savedUser.getAge(), user.getAge());
        assertEquals(savedUser.getUsername(), user.getUsername());
        assertEquals(savedUser.getPassword(), user.getPassword());

        LOG.info("update...");
        mongoTemplate.updateMulti(new Query(Criteria.where("username").is(USERNAME)), Update.update("password", NEW_PASSWORD), DB_USERS);

        LOG.info("find...");
        savedUser = mongoTemplate.findOne(new Query(Criteria.where("username").is(USERNAME)), User.class, DB_USERS);
        LOG.info("  found: {}", savedUser);

        assertNotNull(savedUser);
        assertEquals(savedUser.getAge(), user.getAge());
        assertEquals(savedUser.getUsername(), user.getUsername());
        assertEquals(savedUser.getPassword(), NEW_PASSWORD);

        LOG.info("findAll...");
        List<User> listUser = mongoTemplate.findAll(User.class, DB_USERS);

        LOG.info("findAll... number of users: {}", listUser.size());
        for (User u : listUser) {
            LOG.info("  user: {}", u);
        }

        LOG.info("delete...");
        mongoTemplate.remove(new Query(Criteria.where("username").is(USERNAME)), DB_USERS);

        LOG.info("findAll...");
        listUser = mongoTemplate.findAll(User.class, DB_USERS);
        LOG.info("findAll... number of users: {}", listUser.size());


        LOG.info("Create 1000 users...");
        String toBeDeletedUsername = "" + System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            User u = createDummyUser();
            u.setUsername(toBeDeletedUsername);
            mongoTemplate.save(u, DB_USERS);
        }

        LOG.info("Find all age <= 2...");
        listUser = mongoTemplate.find(new Query(Criteria.where("age").lte(2)), User.class, DB_USERS);
        LOG.info("  found number of users where age <= 2: {}", listUser.size());

        for (User u : listUser) {
            LOG.info("  user: {}", u);
        }

        LOG.info("Delete all generated users...");
        mongoTemplate.remove(new Query(Criteria.where("username").is(toBeDeletedUsername)), DB_USERS);

        LOG.info("findAll...");
        listUser = mongoTemplate.findAll(User.class, DB_USERS);
        LOG.info("findAll... number of users: {}", listUser.size());

        LOG.info("testApp)()... done.");
    }

    private User createDummyUser() {
        Random r = new Random();

        User u = new User();
        u.setAge(r.nextInt(100));
        u.setPassword("" + r.nextLong());
        u.setUsername("" + r.nextLong());

        u.setFirstname(fnames[r.nextInt(fnames.length)]);
        u.setLastname(lnames[r.nextInt(lnames.length)]);

        return u;
    }
}
