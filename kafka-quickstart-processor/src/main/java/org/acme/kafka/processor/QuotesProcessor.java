package org.acme.kafka.processor;

import java.util.Random;

import org.acme.kafka.model.Quote;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.annotations.Blocking;

public class QuotesProcessor {
    private Random random = new Random();

    @Incoming("requests")
    @Outgoing("quotes")
    @Blocking
    public Quote process(String quoteRequest) throws InterruptedException {
        // simulate some hard working task
        Log.info("Request received: " + quoteRequest);
        Thread.sleep(200);
        Quote quote = new Quote(quoteRequest, random.nextInt(100));
        Log.info("Quote processed: " + quote.toString());
        return quote;
    }
}
