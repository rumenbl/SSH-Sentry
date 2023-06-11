# SSH Sentry

The SSH Sentry is a Java-based application designed to monitor SSH activity on a system. It continuously checks the SSH log file for new accepted logins and sends out notifications to a Discord webhook.

## Features

- Monitors SSH log file for new accepted logins.
- Sends notifications to a Discord webhook.
- Includes information about the accepted login:
    - Username
    - Origin IP
    - Time of Event
    - Hostname and Public IP address of server

## Installation

#### 1. Clone the repository

`git clone https://github.com/rumenbl/SSH-Sentry.git`

#### 2. Edit the application.yaml file

- The sshd log file path is the default one, only change it if yours is different
- Update the `discord.webhook-url` with your own Discord webhook URL.

#### 3. Build the project

`gradlew build`

#### 4. Copy the generated JAR file to a directory of your choice
`build/libs/SSH-Sentry-0.0.1-SNAPSHOT.jar`

#### 5. Run the application
You can use `screen` to continue running the application after exiting your terminal

`screen -S SSHSentry java -jar SSH-Sentry-0.0.1-SNAPSHOT.jar`

You can make `screen` run in the background with `CTRL + ALT + D`

To return to that terminal you can use
`screen -r SSHSentry`

## Contributing
Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.