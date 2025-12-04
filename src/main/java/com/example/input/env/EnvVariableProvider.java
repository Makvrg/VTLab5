package com.example.input.env;

public class EnvVariableProvider implements IEnvironmentProvider {

    private final String envName;

    public EnvVariableProvider(String envName) {
        this.envName = envName;
    }

    @Override
    public String getFileName() {
        return System.getenv(envName);
    }

}
