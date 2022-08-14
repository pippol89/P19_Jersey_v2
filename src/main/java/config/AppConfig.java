package config;

import filters.ResourceFilter;
import org.glassfish.jersey.server.ResourceConfig;
import services.UserResource;

public class AppConfig extends ResourceConfig {
    public AppConfig() {
        register(ResourceFilter.class);
        register(UserResource.class);
    }
}
