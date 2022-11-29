package software.pinklady.controltableadmin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

@SpringBootTest
@ActiveProfiles("test")
public class JsonToSqlConverterTest {
    private DataSource mockDataSource = Mockito.mock(DataSource.class);
    @Autowired
    private DataSource datasource;

    ObjectMapper mapper = new ObjectMapper();
    private Logger log = LoggerFactory.getLogger(JsonToSqlConverter.class);

    @Test
    public void testCreateInsertStatement() throws Exception {
        JsonToSqlConverter converter = new JsonToSqlConverter(mockDataSource);
        JsonNode node = mapper.readTree("{\"code\":\"C\",\"description\":\"current\"}");

        String insert = converter.createInsertStatement("test", node);
        log.info(insert);
        assertEquals("INSERT INTO test (code,description) values (:code,:description)", insert);
    }

    @Test
    @Sql("/country.sql")
    public void testInsertAll() throws Exception {
        String country = TestUtils.readTestResource("/country.json");
        JsonToSqlConverter converter = new JsonToSqlConverter(datasource);
        converter.insertAll("country", country);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
        final int count = JdbcTestUtils.countRowsInTable(jdbcTemplate,"country");
        assertEquals(243,count);
    }

}
