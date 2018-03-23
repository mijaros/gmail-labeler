/*
 * Copyright 2016-2017 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.openshift.booster.service;

import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Path("/")
@Component
public class GreetingEndpoint {
    private final GreetingProperties properties;
    public static List<User> users = new ArrayList<>(
        Arrays.asList(new User("Ahoj", "Svete", "aSvete"), new User("Test1", "test2", "tt"))
    );

    public GreetingEndpoint(GreetingProperties properties) {
        this.properties = properties;
    }

    @GET
    @Path("/greeting")
    @Produces("application/json")
    public Greeting greeting(@QueryParam("name") @DefaultValue("World") String name) {
        String message = String.format(properties.getMessage(), name);
        return new Greeting(message);
    }
    
    @GET
    @Path("/test")
    @Produces("application/json")
    public User getUsers(@QueryParam("name") String name) {
        Optional<User> u = users.stream().filter(x -> x.getName() == name).findFirst();
        if (!u.isPresent()) {
            return null;
        }
        
        return u.get();
    }
}
