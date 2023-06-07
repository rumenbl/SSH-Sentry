package me.rumenbl.sshsentry.sshsentry.ssh.models;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Builder
public class SSHLogEntry {
    private String originIP;
    private String username;
    private LocalDateTime timeOfEvent;
    private String serverHostName;
    private String serverIP;
}
