package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;
import javax.ws.rs.PathParam;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;

@Path("/hello")
public class GreetingResource {

    @Inject
    MeterRegistry registry;

    @GET
    @Path("/{name}")
    public String sayHello(@PathParam(value = "name") String name) {
        registry.counter("greeting_counter", Tags.of("name", name)).increment();

        return "hello " + name;
    }
}