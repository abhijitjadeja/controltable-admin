package software.pinklady.controltableadmin;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@SpringJUnitConfig(ApplicationConfiguration.class)
public class DeployerTest {

    private final Logger log = LoggerFactory.getLogger(DeployerTest.class);    

    private static ControltableMetadataRepository repository;

    private static TableDeployer tableDeployer;
    
    @BeforeAll
    public static void initRepository() {
        repository = Mockito.mock(ControltableMetadataRepository.class);
        tableDeployer = Mockito.mock(TableDeployer.class);
    }
    
    @Test
    public void testDeployer() throws Exception{
        Deployer deployer = new Deployer("/home/jadejaan/controltable-admin/deploy","/home/jadejaan/controltable-admin/undeploy","/home/jadejaan/controltable-admin/json",repository,tableDeployer);
        deployer.deploy();
        deployer.undeploy();
    }

    @Test
    public void testParseDeployment() throws Exception{
        Deployer deployer = new Deployer("/home/jadejaan/controltable-admin/deploy","/home/jadejaan/controltable-admin/undeploy","/home/jadejaan/controltable-admin/json",repository,tableDeployer);
        String testDeployment = TestUtils.readTestResource("/sample-deploy.json");
        Deployment d = deployer.parseDeployment(testDeployment);
        assertNotNull(d);
        log.debug(d.toString());
    }

}
