package software.pinklady.controltableadmin;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.*;
import java.sql.Timestamp;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Table("CONTROLTABLE_METADATA")
public class ControltableMetadata {

    private static final Logger log = LoggerFactory.getLogger(ControltableMetadata.class);

    @Id
    @Column("TABLE_ID")
    public long id;
    @Column("TABLE_NAME")
    String name;
    @Column("TABLE_TYPE")
    String type;
    @Column("TABLE_HASH")
    String hash;
    @Column("TABLE_VERSION")
    String version;
    @Column("LAST_UPDATED")
    Timestamp lastUpdated;


    public ControltableMetadata(String name,String type,String hash,String version) {
        this.name=name;
        this.type=type;
        this.hash=hash;
        this.version=version;
        this.lastUpdated=Timestamp.from(Instant.now());
        log.debug(String.format("name %s type %s",name,type));
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getHash() {
        return hash;
    }

    public String getVersion() {
        return version;
    }

    public boolean isLargeControlTable() {
        return "L".equals(type);
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }
}