package me.rumenbl.sshsentry.sshsentry.ssh.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SSHLogEntry {
    private String originIP;
    private String username;
    private LocalDateTime timeOfEvent;
    private String serverHostName;
    private String serverIP;
}
