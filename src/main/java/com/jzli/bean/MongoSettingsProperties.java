package com.jzli.bean;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Component
@Validated
public class MongoSettingsProperties {

    @NotBlank
    private String database;

    @NotEmpty
    private List<String> hosts;

    @NotEmpty
    private List<Integer> ports;

    private String replicaSet;
    private String username;
    private String password;
    private String authenticationDatabase;
    private Integer minConnectionsPerHost = 2;
    private Integer maxConnectionsPerHost = 10;

    public MongoSettingsProperties() {

    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public List<Integer> getPorts() {
        return ports;
    }

    public void setPorts(List<Integer> ports) {
        this.ports = ports;
    }

    public String getReplicaSet() {
        return replicaSet;
    }

    public void setReplicaSet(String replicaSet) {
        this.replicaSet = replicaSet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthenticationDatabase() {
        return authenticationDatabase;
    }

    public void setAuthenticationDatabase(String authenticationDatabase) {
        this.authenticationDatabase = authenticationDatabase;
    }

    public Integer getMinConnectionsPerHost() {
        return minConnectionsPerHost;
    }

    public void setMinConnectionsPerHost(Integer minConnectionsPerHost) {
        this.minConnectionsPerHost = minConnectionsPerHost;
    }

    public Integer getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public void setMaxConnectionsPerHost(Integer maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
    }
}