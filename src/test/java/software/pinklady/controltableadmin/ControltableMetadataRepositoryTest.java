package software.pinklady.controltableadmin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//@SpringJUnitConfig(ApplicationConfiguration.class)
@SpringBootTest
@ActiveProfiles("test")
public class ControltableMetadataRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(ControltableMetadataRepositoryTest.class);    

    @Autowired
    private ControltableMetadataRepository metadataRepo;

    @Test
    public void testMetadata() {
        ControltableMetadata metadata = new ControltableMetadata("CTRY","L","XXX-YYY-ZZZ","1.0");
        metadataRepo.save(metadata);
        log.debug("id ="+metadata.id);
        metadata = new ControltableMetadata("STATE","S","XXX-YYY-ZZZ","1.0");
        metadataRepo.save(metadata);
        log.debug("id ="+metadata.id);
    }

}