package org.launchcode.capstonebooksbuyback.models.data;

import org.launchcode.capstonebooksbuyback.models.Zip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface ZipDao extends CrudRepository<Zip,Integer> {
}

