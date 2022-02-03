package org.acme.kafka.producer;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.kafka.model.Quote;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;

@Path("/quotes")
public class QuotesResource {

    @Channel("quote-requests")
    Emitter<String> quoteRequestEmitter;

    @Channel("quotes")
    Multi<Quote> quotes;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<Quote> getQuotes() {
        quotes.onItem().invoke((i) -> Log.info("Response recieved - " + i.toString()));
        return quotes;
    }

    @POST
    @Path("/request")
    @Produces(MediaType.TEXT_PLAIN)
    public String createRequest() {
        UUID uuid = UUID.randomUUID();
        quoteRequestEmitter.send(uuid.toString());
        Log.info("Request sended - " + uuid.toString());
        return uuid.toString();
    }
}