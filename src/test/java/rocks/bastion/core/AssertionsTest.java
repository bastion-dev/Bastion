package rocks.bastion.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionsTest {

    @Test
    public void and_notNull_performsBothAssertions() throws Exception {
        Spy spy = new Spy();
        Assertions<Object> touchTwice = (statusCode, response, model) -> {
            spy.touch();
            spy.touch();
        };
        Assertions<Object> touchFourTimes = touchTwice.and(touchTwice);
        touchFourTimes.execute(200, null, null);
        assertThat(spy.getCounter()).isEqualTo(4);
    }

    private static class Spy {
        private int counter;

        public Spy() {
        }

        public synchronized void touch() {
            counter = counter + 1;
        }

        public synchronized int getCounter() {
            return counter;
        }
    }
}