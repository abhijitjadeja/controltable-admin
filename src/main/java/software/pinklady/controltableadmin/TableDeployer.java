package software.pinklady.controltableadmin;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jdbc.core.JdbcTemplate;

public class TableDeployer {
    private final Logger log = LoggerFactory.getLogger(TableDeployer.class);
    private final JdbcTemplate jdbcTemplate;

    public TableDeployer(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void deploy(String tableName,String createTableSql) {
        try {
            //drop table if already exists
            jdbcTemplate.execute(String.format ("DROP TABLE %s", tableName));
        }
        catch(Exception e) {
            log.info("DROP table was not executed");
        }
        jdbcTemplate.execute(createTableSql);
    }   
}
