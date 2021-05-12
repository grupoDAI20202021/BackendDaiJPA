package daibackend.demo;

import java.io.IOException;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContextListener;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@SpringBootApplication
@EntityScan(basePackageClasses = { DaiProjectApplication.class, Jsr310JpaConverters.class })
public class DaiProjectApplication implements ServletContextListener {

    protected final Logger log = Logger.getLogger(String.valueOf(this.getClass()));



    public DaiProjectApplication() {
        try {
            Properties p = new Properties();
            p.load(getClass().getResourceAsStream("/application.properties"));
          //  Ssh forwarding is used if the configuration file contains the ssh.forward.enabled attribute
            if(p.getProperty("ssh.forward.enabled")!=null){
                log.info("ssh forward is opened.");
                log.info("ssh init ……");
                Session session = new JSch().getSession(p.getProperty("ssh.forward.username"),p.getProperty("ssh.forward.host"),Integer.valueOf(p.getProperty("ssh.forward.port")));
                session.setConfig("StrictHostKeyChecking", "no");
                session.setPassword(p.getProperty("ssh.forward.password"));
                session.connect();
                session.setPortForwardingL(p.getProperty("ssh.forward.from_host"),Integer.valueOf(p.getProperty("ssh.forward.from_port")) ,p.getProperty("ssh.forward.to_host") ,Integer.valueOf(p.getProperty("ssh.forward.to_port")) );
            }else{
                log.info("ssh forward is closed.");
            }
        } catch (IOException e) {
            log.info("ssh IOException failed.");
        } catch (JSchException e) {
            log.info("ssh JSchException failed.");
        } catch (Exception e) {
            log.info("ssh settings is failed. skip!");
        }
    }
    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Lisbon"));
    }
    public static void main(String[] args) {
        SpringApplication.run(DaiProjectApplication.class, args);
    }

}

