package fi.zcode.springmongo.repository;

import fi.zcode.springmongo.model.Group;
import fi.zcode.springmongo.model.User;
import java.util.Collection;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author mlyly
 */
public interface GroupRepository extends PagingAndSortingRepository<Group, String> {

//    // @Query(value="{ 'firstname' : ?0 }", fields="{ 'firstname' : 1, 'lastname' : 1}")
//    // @Query(value="{ 'oid' : ?0 }")
//    public KoulutusModuli findByOid(String oid);
//    public Page<KoulutusModuli> findByUlkoinenTunniste(String tunniste, Pageable pageable);

    @Query(value="{ 'name[fi]' : ?0 }")
    public Collection<Group> findByNameFI(String name);


}
