package me.rumenbl.sshsentry.sshsentry.ssh;

import lombok.RequiredArgsConstructor;
import me.rumenbl.sshsentry.sshsentry.client.DiscordRestClient;
import me.rumenbl.sshsentry.sshsentry.utils.LogFileUtils;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SSHService implements ApplicationRunner {
    private final DiscordRestClient discordRestClient;
    @Value("${sshd.log-file-path}")
    private String sshdLogPath;
    private ScheduledExecutorService executor;
    private Tailer tailer;
    private boolean loopedOver = false;

    @Override
    public void run(ApplicationArguments args) {


        File file = new File(sshdLogPath);

        final String lastLineOfFile = getLastLineOfFile(file);


        executor = Executors.newSingleThreadScheduledExecutor();
        TailerListener listener = new TailerListenerAdapter() {
            @Override
            public void handle(String line) {
                if (line.equals(lastLineOfFile)) {
                    loopedOver = true;
                }

                if (loopedOver && line.contains("Accepted")) {
                    System.out.println(line);
                    discordRestClient.notifyForSSHLogin(LogFileUtils.parseSSHLogEntryFromString(line));
                }
            }
        };

        tailer = Tailer.create(file, listener);
        executor.schedule(tailer, 0, TimeUnit.MILLISECONDS);
    }

    private String getLastLineOfFile(final File file) {
        String lastLine = "";
        String sCurrentLine;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((sCurrentLine = br.readLine()) != null) {
                lastLine = sCurrentLine;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lastLine;
    }
}
