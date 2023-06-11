package me.rumenbl.sshsentry.sshsentry.client;

import lombok.extern.slf4j.Slf4j;
import me.rumenbl.sshsentry.sshsentry.ssh.models.SSHLogEntry;
import me.rumenbl.sshsentry.sshsentry.utils.ResourceUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class DiscordRestClient {
    @Value("${discord.webhook-url}")
    private String webhookURL;
    @Value("classpath:discord/accepted_ssh_login_notification_embed.json")
    private Resource discordEmbedPayloadResource;

    public void notifyForAcceptedSSHLogin(final SSHLogEntry sshLogEntry) {
        final String payload = ResourceUtil.resourceToString(discordEmbedPayloadResource)
                .replace("<SERVER_NAME>", sshLogEntry.getServerHostName())
                .replace("<SERVER_IP>", sshLogEntry.getServerIP())
                .replace("<ORIGIN_IP>", sshLogEntry.getOriginIP())
                .replace("<USERNAME>", sshLogEntry.getUsername())
                .replace("<TIME_OF_EVENT>", sshLogEntry.getTimeOfEvent().toString());

        sendContentToWebhook(payload);
    }

    private void sendContentToWebhook(final String payload) {
        WebClient webClient = WebClient.builder()
                .baseUrl(webhookURL)
                .build();

        try {
            webClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception e) {
            log.error("Failed to send content to webhook: " + e.getMessage());
        }
    }
}
