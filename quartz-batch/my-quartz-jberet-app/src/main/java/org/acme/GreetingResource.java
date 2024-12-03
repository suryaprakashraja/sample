package org.acme;

import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Properties;

@ApplicationScoped
@Path("/hello")
public class GreetingResource {

    @Inject
    QuarkusJobOperator quarkusJobOperator;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        var executionId = quarkusJobOperator.start("demo-job", new Properties());
        return "ExecutionId for started job: "+executionId;
    }
}
