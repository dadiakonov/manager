package engineer.dima.manager.externalproject;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class CreatedExternalProjectCounter {
    private static final String CREATED_EXTERNAL_PROJECT_COUNTER_NAME = "created_external_project_total";

    private final Counter counter;

    public CreatedExternalProjectCounter(MeterRegistry meterRegistry) {
        this.counter = Counter.builder(CREATED_EXTERNAL_PROJECT_COUNTER_NAME)
                .description("Total number of created external projects")
                .register(meterRegistry);
    }

    public void increment() {
        counter.increment();
    }
}
