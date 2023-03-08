package com.postnord.ndm.base.scrolling.model;

import com.postnord.ndm.base.scrolling.util.ScrollerHelper;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@RequestScoped
@Path("/integration")
public class ScrollableResource {

    private static final String SORT_PARAMS = "+eventAt";
    private static final String Q_PARAM = "foo";

    @GET
    @Path("/test/hasnext")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateScrollableWithHasNext() {
        final ScrollableQuery query = new ScrollableQuery("status==ACTIVE", "5", 2, SORT_PARAMS);
        return ScrollerHelper.getScrollableResponse(createPersonList(), query);
    }

    @GET
    @Path("/test/totalcount")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateScrollableWithTotalCount() {
        final ScrollableQuery query = new ScrollableQuery(Q_PARAM, "5", 2, SORT_PARAMS);
        return ScrollerHelper.getScrollableResponse(createPersonList(), 12, query);
    }

    private List<Person> createPersonList() {
        final List<Person> persons = new ArrayList<>();

        persons.add(Person.builder().age(45).name("bosse").build());
        persons.add(Person.builder().age(43).name("anna").build());
        persons.add(Person.builder().age(12).name("calle").build());
        persons.add(Person.builder().age(15).name("stina").build());

        return persons;
    }
}
