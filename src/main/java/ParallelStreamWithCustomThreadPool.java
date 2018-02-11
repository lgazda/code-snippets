import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ParallelStreamWithCustomThreadPool {
    /**
     * Java parallel steam with custom thread pool.
     * @link https://www.codementor.io/nitinpuri/controlling-parallelism-of-java-8-collection-streams-umex0qbt1
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Default behavior. Using main and commonPool, this may lead block the app.");
        IntStream.range(1, 10).boxed()
                .parallel()
                .peek(process())
                .count();

        System.out.println("Using fork join pool.");
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        forkJoinPool.submit(() -> IntStream.range(1, 10).boxed()
                .parallel()
                .peek(process())
                .collect(toList()))
                .get();
    }

    private static Consumer<Integer> process() {
        return i -> {
            System.out.println("Processing " + i + " by: " + Thread.currentThread().getName());
            delay();
        };
    }

    private static void delay() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
