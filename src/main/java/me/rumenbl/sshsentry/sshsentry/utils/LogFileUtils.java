package me.rumenbl.sshsentry.sshsentry.utils;

import lombok.extern.slf4j.Slf4j;
import me.rumenbl.sshsentry.sshsentry.ssh.models.SSHLogEntry;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

@Component
@Slf4j
public class LogFileUtils {
    private LogFileUtils() {
    }

    public static SSHLogEntry parseSSHLogEntryFromString(final String str) {
        final String[] arr = str.split(" ");
        final String username = arr[9];
        final String originIP = arr[11];
        final String serverIP = pubIP();
        final String serverHostname = arr[4];

        return SSHLogEntry.builder()
                .originIP(originIP)
                .username(username)
                .timeOfEvent(LocalDateTime.now())
                .serverHostName(serverHostname)
                .serverIP(serverIP).build();
    }

    private static String pubIP() {
        URL url = null;
        try {
            url = new URL("https://checkip.amazonaws.com/");
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            return "";
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return br.readLine();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return "";
    }
}
