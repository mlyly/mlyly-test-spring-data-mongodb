package fi.zcode.springmongo;

import com.mongodb.Mongo;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import fi.zcode.springmongo.model.Group;
import fi.zcode.springmongo.model.User;
import fi.zcode.springmongo.repository.GroupRepository;
import fi.zcode.springmongo.repository.UserRepository;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private String[] fnames = new String[]{
        "James", "Janis", "Elvis", "Amadeus", "Bob"
    };
    private String[] lnames = new String[]{
        "Brown", "Joplin", "Costello", "Mozart", "Squarepants"
    };

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MongoOperations mongoTemplate;


    //
    // https://github.com/flapdoodle-oss/embedmongo.flapdoodle.de -- it seems that start&stop in @Before/@After causes half of tests to fail... weird.
    //

    // Start stop mongo
    private static MongodExecutable _mongodExe;
    private static MongodProcess _mongod;
    private Mongo _mongo;

    @BeforeClass
    public static void setUp() throws Exception {
        LOG.info("setUp()...");
        MongodStarter runtime = MongodStarter.getDefaultInstance();
        _mongodExe = runtime.prepare(new MongodConfig(Version.Main.PRODUCTION, 12345, Network.localhostIsIPv6()));
        _mongod = _mongodExe.start();

//        mongoTemplate = new MongoTemplate(mongoDbFactory);

        LOG.info("setUp()... done.");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        LOG.info("tearDown()...");
        _mongod.stop();
        _mongod.waitFor();
        _mongodExe.stop();

        LOG.info("tearDown()... done.");
    }

    @Before
    public void doBefore() throws UnknownHostException {
        _mongo = new Mongo("localhost", 12345);
    }

    @After
    public void doAfter() {
        _mongo.dropDatabase("springmongo");
    }


    @Test
    public void testWithMongoTemplate() {
        assertNotNull(mongoTemplate);

        int NUM = 123;

        for (int i = 0; i < NUM; i++) {
            User u = createDummyUser();
            u.setLastname("testWithMongoTemplate");
            u.setAge(i);
            mongoTemplate.save(u);
        }
        assertEquals(NUM, mongoTemplate.count(new Query(Criteria.where("lastname").is("testWithMongoTemplate")), User.class));

        LOG.info("Find all age <= 2...");
        List<User> listUser = mongoTemplate.find(new Query(Criteria.where("age").lte(2)), User.class);
        assertEquals(3, listUser.size());

        mongoTemplate.updateMulti(
                new Query(Criteria.where("age").lte(2)),
                Update.update("password", NEW_PASSWORD),
                User.class);

        listUser = mongoTemplate.find(new Query(Criteria.where("age").lte(2)), User.class);
        for (User user : listUser) {
            assertEquals(user.getPassword(), NEW_PASSWORD);
        }

        mongoTemplate.remove(new Query(Criteria.where("lastname").is("testWithMongoTemplate")), User.class);
        assertEquals(0, mongoTemplate.count(new Query(Criteria.where("lastname").is("TOBEDELETED")), User.class));
    }

    @Test
    public void testApp() throws InterruptedException {
        // assertTrue( true );
        LOG.info("testApp)()...");

        User user = new User(USERNAME, PASSWORD, 42);

        LOG.info("save...");
        userRepository.save(user);

        LOG.info("find...");
        User savedUser = userRepository.findOne(user.getId());
        LOG.info("  found: {}", savedUser);

        assertNotNull(savedUser);
        assertEquals(savedUser.getAge(), user.getAge());
        assertEquals(savedUser.getUsername(), user.getUsername());
        assertEquals(savedUser.getPassword(), user.getPassword());

        LOG.info("update...");
        savedUser.setPassword(NEW_PASSWORD);
        userRepository.save(savedUser);

        LOG.info("find...");
        savedUser = userRepository.findOne(user.getId());
        LOG.info("  found: {}", savedUser);

        assertNotNull(savedUser);
        assertEquals(savedUser.getAge(), user.getAge());
        assertEquals(savedUser.getUsername(), user.getUsername());
        assertEquals(savedUser.getPassword(), NEW_PASSWORD);

        LOG.info("findAll... (count={})", userRepository.count());
        for (User u : userRepository.findAll()) {
            LOG.info("  user: {}", u);
        }

        assertTrue("There should be only ONE user", userRepository.count() == 1);

        LOG.info("deleteAll... (count={})", userRepository.count());
        userRepository.deleteAll();

        assertTrue("There should be NO users", userRepository.count() == 0);

        LOG.info("Create 2 groups AND 1000 users...");

        Group groupOdd = new Group();
        groupOdd.setName("en", "ODD");
        groupOdd.setName("fi", "Pariton");
        groupRepository.save(groupOdd);

        Group groupEven = new Group();
        groupEven.setName("en", "Event");
        groupEven.setName("fi", "Parillinen");
        groupRepository.save(groupEven);


        // THIS DOES NOT WORK YET...

        {
            Collection<Group> nameGroup = groupRepository.findByNameFI("Pariton");
            LOG.info("PARITON: {}", nameGroup);
        }
        {
            Collection<Group> nameGroup = groupRepository.findByNameFI("Parillinen");
            LOG.info("PARILLINEN: {}", nameGroup);
        }

        String toBeDeletedUsername = "" + System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            User u = createDummyUser();
            u.setUsername(toBeDeletedUsername);

            if (u.getAge() % 2 == 0) {
                groupEven.addUser(u);
            } else {
                groupOdd.addUser(u);
            }

            userRepository.save(u);
        }

        // Update groups
        groupRepository.save(groupEven);
        groupRepository.save(groupOdd);

        Thread.sleep(2000);

        LOG.info("Find all age <= 2...");
        List<User> listUser = mongoTemplate.find(new Query(Criteria.where("age").lte(2)), User.class);
        LOG.info("  *** found number of users where age <= 2: {}", listUser.size());

        for (User u : listUser) {
            LOG.info("  user: {}", u);
        }


        LOG.info("Paged lite finder...");

        Page<User> page = userRepository.findByAgeLTLight(2, new PageRequest(0, 10));
        LOG.info("  page.number = {}", page.getNumber());
        LOG.info("  page.numberOfElements = {}", page.getNumberOfElements());
        LOG.info("  page.size = {}", page.getSize());
        LOG.info("  page.totalElements = {}", page.getTotalElements());
        LOG.info("  page.totalPages = {}", page.getTotalPages());

        // Check "lightness"
        for (User u : page.getContent()) {
            LOG.info("  Lite user: {}", u);
            assertNull("Users first name is not fetched, should be null!", u.getFirstname());
        }

        // Check size
        assertEquals(page.getTotalElements(), listUser.size());


        LOG.info("Test paging...");
        page = userRepository.findAll(new PageRequest(0, 10));
        assertEquals(page.getContent().size(), 10);

        LOG.info("List groups...");
        for (Group group : groupRepository.findAll()) {
            LOG.info("GROUP: {}", group.getName());
            for (User u : group.getUsers()) {
                LOG.info("  USER: {}", u);
            }
        }


        LOG.info("Delete all generated users...");
        mongoTemplate.remove(new Query(Criteria.where("username").is(toBeDeletedUsername)), User.class);

        LOG.info("Delete all generated groups...");
        groupRepository.delete(groupOdd);
        groupRepository.delete(groupEven);

        LOG.info("findAll...");
        listUser = mongoTemplate.findAll(User.class);
        LOG.info("findAll... number of users: {}", listUser.size());

        LOG.info("  num groups {}", groupRepository.count());
        LOG.info("  num users {}", userRepository.count());

        LOG.info("testApp)()... done.");
    }

    private String createRandom(String prefix) {
        return createRandom(prefix, null);
    }

    private String createRandom(String prefix, String suffix) {
        StringBuilder sb = new StringBuilder();
        if (prefix != null) {
            sb.append(prefix);
        }

        sb.append(new Random().nextLong());

        if (suffix != null) {
            sb.append(suffix);
        }

        return sb.toString();
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
