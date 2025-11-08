package com.jconfperu.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus on Kubernetes!";
    }
    
    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public AppInfo info() {
        return new AppInfo(
            "Quarkus Kubernetes Demo",
            "1.0.0-SNAPSHOT",
            "JConf Peru 2025"
        );
    }
    
    public record AppInfo(String name, String version, String event) {}
}
