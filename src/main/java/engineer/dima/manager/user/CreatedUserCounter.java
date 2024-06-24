package engineer.dima.manager.user;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class CreatedUserCounter {
    private final static String CREATED_USER_COUNTER_NAME = "created_user_total";

    private final Counter counter;

    public CreatedUserCounter(MeterRegistry meterRegistry) {
        this.counter = Counter.builder(CREATED_USER_COUNTER_NAME)
                .description("Total number of created users")
                .register(meterRegistry);
    }

    public void increment() {
        counter.increment();
    }
}
