package pro.sky.course3lesson5scratch.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class InfoController {

    @Value("${server.port}")
    private int port;

    @GetMapping(value = "info")
    int getPort() {
        return port;
    }

    @GetMapping(path = "/get-sum")
    String getSum() {
        int sum;
        int lim = 1_000_000;
        long startTime = System.currentTimeMillis();
        sum = Stream.iterate(1, a -> a + 1)
                .limit(lim)
                .reduce(0, (a, b) -> a + b);
        long timeConsumedSequential = System.currentTimeMillis() - startTime;

        int sum2;
        long startTime2 = System.currentTimeMillis();
        sum2 = Stream
                .iterate(1, a -> a + 1).parallel()
                .limit(lim)
                .reduce(0, Integer::sum);

        long timeConsumedParallel = System.currentTimeMillis() - startTime2;

        startTime = System.currentTimeMillis();
        int sumSimple = 0;
        for (int i = 0; i <= lim; i++) {
            sumSimple = sumSimple + i;
        }
        long timeConsumedSimple = System.currentTimeMillis() - startTime;


        return "lim: " + lim + ". Sum: " + sum +
                ". Time consumed sequential: " + timeConsumedSequential + "ms. Time consumed parallel: " + timeConsumedParallel + "ms. Sum2: "
                + sum2 + ". Time simplest: " + timeConsumedSimple +
                "ms. sum simple: " + sumSimple;
    }

}
