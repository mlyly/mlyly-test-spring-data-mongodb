package fi.zcode.springmongo.repository;

import fi.zcode.springmongo.model.User;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    public User findByUsername(String username);

    public Collection<User> findByLastname(String name);

    public Collection<User> findByFirstname(String name);


}
