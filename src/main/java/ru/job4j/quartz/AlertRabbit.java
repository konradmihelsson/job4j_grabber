package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {

    private static Properties config;

    public static void main(String[] args) {
        AlertRabbit.init();
        try {
            Class.forName(config.getProperty("driver-class-name"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(
                config.getProperty("url"),
                config.getProperty("username"),
                config.getProperty("password"))) {
            List<Long> store = new ArrayList<>();
            JobDataMap data = new JobDataMap();
            data.put("store", store);
            data.put("connection", connection);
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = newJob(Rabbit.class).usingJobData(data).build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(Integer.parseInt(config.getProperty("rabbit.interval")))
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println(store);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void init() {
        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            config = new Properties();
            config.load(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs here ...");
            List<Long> store = (List<Long>) context.getJobDetail().getJobDataMap().get("store");
            Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            long currentTime = System.currentTimeMillis();
            store.add(currentTime);
            String query = "insert into rabbit (created_date) values (?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setLong(1, currentTime);
                ps.executeUpdate();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
    }
}
