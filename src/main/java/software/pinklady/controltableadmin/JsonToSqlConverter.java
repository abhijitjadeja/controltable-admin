package software.pinklady.controltableadmin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToSqlConverter {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_STRING = "INSERT INTO %s (%s) values (%s)";

    private ObjectMapper mapper = new ObjectMapper();

    public JsonToSqlConverter(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    public void insertAll(String tableName,String json) throws JsonMappingException, JsonProcessingException {
        JsonNode array =mapper.readTree(json);
        boolean first = true;
        String sql = null;
        for (JsonNode node:array) {
            if (first) {
                sql = createInsertStatement(tableName, node);
            }
            else {
                first = false;
            }
            insert(sql,node);
        }
    }


    public void insert(String insertSql,JsonNode jsonNode) {
        Map<String, Object> parameters = new HashMap<>();
        jsonNode.fields().forEachRemaining(e -> {
            parameters.put(e.getKey(),e.getValue().asText());
        });
        namedParameterJdbcTemplate.update(insertSql,parameters);
    }

    public String createInsertStatement(String tableName, JsonNode jsonNode) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        Iterator<String> iterator = jsonNode.fieldNames();
        boolean first = true;
        while (iterator.hasNext()) {
            if (!first) {
                columns.append(",");
                values.append(",");

            } else {
                first = false;
            }
            String column = iterator.next();
            columns.append(column);
            values.append(":")
                .append(column);

        }
        return String.format(INSERT_STRING,tableName,columns,values);    }

}
