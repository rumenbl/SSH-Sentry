package me.rumenbl.sshsentry.sshsentry.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rumenbl.sshsentry.sshsentry.ssh.models.SSHLogEntry;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;

@Component
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogFileUtils {
    public static SSHLogEntry parseSSHLogEntryFromString(final String str) {
        final String[] arr = str.split(" ");
        final String username = arr[8];
        final String originIP = arr[10];
        final String serverIP = getPublicIP();
        final String serverHostname = arr[3];

        return SSHLogEntry.builder()
                .originIP(originIP)
                .username(username)
                .timeOfEvent(LocalDateTime.now())
                .serverHostName(serverHostname)
                .serverIP(serverIP).build();
    }

    private static String getPublicIP() {
        String publicIP = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://checkip.amazonaws.com/").openStream()))) {
            publicIP = reader.readLine();
        } catch (IOException e) {
            log.error("Failed to retrieve public IP: " + e.getMessage());
        }
        return publicIP;
    }
}
