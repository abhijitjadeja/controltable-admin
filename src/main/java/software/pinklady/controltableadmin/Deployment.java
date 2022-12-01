package software.pinklady.controltableadmin;

import java.util.Optional;

public class Deployment {
    private String name;
    private String type;
    private String version;
    private String largeTableSql;

    public void setName(String name) {
        this.name=name;
    }
    public String getName() {
        return name;
    }
    public void setType(String type) {
        this.type=type;
    }
    public String getLargeTableSql() {
        return largeTableSql;
    }
    public void setLargeTableSql(String largeTableSql) {
        this.largeTableSql = largeTableSql;
    }
    public String getType() {
        return type;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getVersion() {
        return version;
    }
    public Optional<String> getOptionalLargeTableSql() {
        return Optional.ofNullable(largeTableSql);
    }

    public Deployment() {
    }

    public Deployment(String name, String type, String version, String largeTableSql) {
        this.name = name;
        this.type = type;
        this.version = version;
        this.largeTableSql = largeTableSql;
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", version='" + getVersion() + "'" +
            "}";
    }

}