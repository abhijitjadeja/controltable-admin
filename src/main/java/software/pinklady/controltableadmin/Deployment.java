package software.pinklady.controltableadmin;

public class Deployment {
    private String name;
    private String type;
    private String version;

    public void setName(String name) {
        this.name=name;
    }
    public String getName() {
        return name;
    }
    public void setType(String type) {
        this.type=type;
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

    public Deployment() {
    }

    public Deployment(String name, String type, String version) {
        this.name = name;
        this.type = type;
        this.version = version;
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