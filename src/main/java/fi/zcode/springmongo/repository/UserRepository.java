package fi.zcode.springmongo.repository;

import fi.zcode.springmongo.model.User;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author mlyly
 */
public interface UserRepository extends PagingAndSortingRepository<User, String> {

//    // @Query(value="{ 'firstname' : ?0 }", fields="{ 'firstname' : 1, 'lastname' : 1}")
//    // @Query(value="{ 'oid' : ?0 }")
//    public KoulutusModuli findByOid(String oid);
//    public Page<KoulutusModuli> findByUlkoinenTunniste(String tunniste, Pageable pageable);


    @Query(value="{ 'age' : { $lte : ?0 } }", fields="{ 'lastname' : 1, 'age' : 1}")
    public Page<User> findByAgeLTLight(int age, Pageable pageable);
    
    public Collection<User> findByFirstname(String name);

    @Query(value="{ 'firstname' : ?0 }", fields="{ 'lastname' : 1, 'age' : 1}")
    public Collection<User> findByFirstnameLight(String firstname);

    public Collection<User> findByLastname(String name);

    public User findByUsername(String username);

}
