package software.pinklady.controltableadmin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.*;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Deployer {
    private final Logger log = LoggerFactory.getLogger(Deployer.class);

    private final Path deployLocationPath;
    private final Path undeployLocationPath;
    private final Path jsonLocationPath;
    private final ControltableMetadataRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final TableDeployer tableDeployer;

    public Deployer(String deployLocation, String undeployLocation, String jsonLocation,
            ControltableMetadataRepository repository, TableDeployer tableDeployer) {
        this.deployLocationPath = Path.of(deployLocation);
        this.undeployLocationPath = Path.of(undeployLocation);
        this.jsonLocationPath = Path.of(jsonLocation);
        this.repository = repository;
        this.tableDeployer = tableDeployer;
    }

    public void deploy() throws IOException {
        try (Stream<Path> files = Files.list(deployLocationPath)) {
            files.filter(path -> !Files.isDirectory(path))
                    .filter(f -> f.getFileName().toString()
                            .endsWith("-deploy.json"))
                    .forEach(this::deployFile);
        }
    }

    public Deployment fileToDeployment(Path file) throws IOException {
        String jsonDeployment = Files.readString(file, StandardCharsets.UTF_8);
        return parseDeployment(jsonDeployment);
    }

    public void deployFile(Path file) {
        try {
            Deployment deployment = fileToDeployment(file);
            ControltableMetadata metadata = new ControltableMetadata(
                    deployment.getName(), deployment.getType(),
                    null, deployment.getVersion());
            repository.save(metadata);

            if (metadata.isLargeControlTable()) {
                // lookfordeploy.sql
                // Path sqlFile = Path.of(metadata.getName()+"-deploy.sql");
                deployment.getOptionalLargeTableSql()
                        .ifPresent(largeTableSql -> tableDeployer.deploy(metadata.getName(), largeTableSql));
                // load json to relational table
            } else {
                Path fileName = file.getFileName();
                Path destination = jsonLocationPath.resolve(fileName);
                log.info("deploying file " + file + " to " + destination);
                Files.move(file, destination);
                log.info("deployed file " + file + " to " + destination);
            }
        } catch (Exception e) {
            log.error("deployment failed for " + file, e);
        }

    }

    Deployment parseDeployment(String jsonDeployment) {
        try {
            return objectMapper.readValue(jsonDeployment, Deployment.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("invalid json deployment file", e);
        }
    }

    public void undeploy() throws IOException {
        try (Stream<Path> files = Files.list(undeployLocationPath)) {
            files.filter(path -> !Files.isDirectory(path))
                    .filter(f -> f.getFileName().toString()
                            .endsWith("-undeploy.json"))
                    .forEach(this::undeployFile);
        }
    }

    public void undeployFile(Path file) {
        try {
            Deployment deployment = fileToDeployment(file);
            ControltableMetadata metadata = new ControltableMetadata(
                    deployment.getName(), deployment.getType(),
                    null, deployment.getVersion());
            repository.delete(metadata);

        } catch (Exception e) {
            log.error("undeploy failed for " + file, e);
        }
    }

}
