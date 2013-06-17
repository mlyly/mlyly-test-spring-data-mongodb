package fi.zcode.springmongo.repository;

import fi.zcode.springmongo.model.User;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * See: http://www.springsource.org/spring-data/mongodb
 *
 * @author mlyly
 */
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    /**
     * Find lite objects by age less or egual that given value.
     *
     * Uses "custom" finder.
     *
     * @param age
     * @param pageable
     * @return
     */
    @Query(value = "{ 'age' : { $lte : ?0 } }", fields = "{ 'lastname' : 1, 'age' : 1}")
    public Page<User> findByAgeLTLight(int age, Pageable pageable);

    /**
     * Find by firstname, totally generated finder - it's generated based by domain objects "firstnamw" fields.
     *
     * @param name
     * @return
     */
    public Collection<User> findByFirstname(String name);

    @Query(value = "{ 'firstname' : ?0 }", fields = "{ 'lastname' : 1, 'age' : 1}")
    public Collection<User> findByFirstnameLight(String firstname);

    public Collection<User> findByLastname(String name);

    public User findByUsername(String username);
}
