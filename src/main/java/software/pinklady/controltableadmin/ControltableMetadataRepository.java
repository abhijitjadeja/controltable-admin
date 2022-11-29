package software.pinklady.controltableadmin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource
public interface ControltableMetadataRepository extends CrudRepository<ControltableMetadata, String>{}