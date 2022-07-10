package fa.training;

import fa.training.exception.ServerInternalException;

import java.io.IOException;

public class ConsoleCleaner {
    public static void clear() {
        try {
            ProcessBuilder pb = new ProcessBuilder("clear");
            Process startProcess = pb.inheritIO().start();
            startProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new ServerInternalException("Server has problem please try again");
        }
    }
}
