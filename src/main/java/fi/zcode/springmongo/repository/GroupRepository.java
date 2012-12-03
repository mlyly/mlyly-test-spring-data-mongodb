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

    // TODO This does not work like this....
    @Query(value="{ 'name[fi]' : ?0 }")
    public Collection<Group> findByNameFI(String name);

}
