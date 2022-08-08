package config;

import org.glassfish.jersey.server.ResourceConfig;
import services.UserResource;

public class AppConfig extends ResourceConfig {
    public AppConfig() {
        register(UserResource.class);
    }
}
