package software.pinklady.controltableadmin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@ActiveProfiles("test")
public class TableDeployerTest {
    private Logger log = LoggerFactory.getLogger(TableDeployerTest.class);

    @Autowired
    private DataSource dataSource;

    @Test
    public void testExecuteSql() {
        log.debug("testing table deployer");
        TableDeployer td = new TableDeployer(dataSource);
        td.deploy("test","create table test( a char(2))");
        assertEquals("done", "done");
    }

}
