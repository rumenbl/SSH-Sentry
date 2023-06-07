package me.rumenbl.sshsentry.sshsentry.ssh.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class SSHLogEntry {
    private String originIP;
    private LocalDateTime timeOfEvent;
    private String serverHostName;
    private String serverIP;
}
