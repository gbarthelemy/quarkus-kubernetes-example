package com.gbarthelemy.kubernetes;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class MessageProducer {
    private Random random = new Random();

    @Outgoing("generated-price")
    @Broadcast
    public Flowable<Integer> generate() {
        return Flowable.interval(1, TimeUnit.SECONDS)
                .map(tick -> random.nextInt(1000));
    }
}
