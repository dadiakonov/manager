package engineer.dima.manager.user;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CreatedUserCounterTest {
    private CreatedUserCounter createdUserCounter;
    private Counter counter;

    @BeforeEach
    public void setUp() {
        MeterRegistry meterRegistry = mock(MeterRegistry.class);
        counter = mock(Counter.class);

        Counter.Builder counterBuilder = mock(Counter.Builder.class);
        when(counterBuilder.description(anyString())).thenReturn(counterBuilder);
        when(counterBuilder.register(any(MeterRegistry.class))).thenReturn(counter);

        try (MockedStatic<Counter> mockedCounter = mockStatic(Counter.class)) {
            mockedCounter.when(() -> Counter.builder("created_user_total")).thenReturn(counterBuilder);

            createdUserCounter = new CreatedUserCounter(meterRegistry);
        }
    }

    @Test
    public void testIncrement() {
        createdUserCounter.increment();
        createdUserCounter.increment();

        verify(counter, times(2)).increment();
    }
}
