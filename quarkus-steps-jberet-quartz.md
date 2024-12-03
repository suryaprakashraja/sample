# Building a quarkus application with Jberet & Quartz

Simple batch application.

## Project set-up

```shell
quarkus create my-quartz-jberet-app
```

## Edit the pom.xml file to include the following dependencies

```xml
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-jberet</artifactId>
</dependency>
```

## Create JBeret components

Create following classes:

### Reader

```java
import javax.batch.api.chunk.ItemReader;

public class MyReader implements ItemReader {
    // ... implementation for reading items from a data source
}
```

### Processor

```java
import javax.batch.api.chunk.ItemProcessor;

public class MyProcessor implements ItemProcessor {
    @Override
    public Object processItem(Object item) throws Exception {
        // Process the item here
        return "Processed: " + item;
    }
}
```

### Writer

```java
import javax.batch.api.chunk.ItemWriter;

public class MyWriter implements ItemWriter {
    // ... implementation for writing processed items to a target
}
```

## Configure the JBeret Job

Use annotations to configure the JBeret job:

```java
@Job
public class MyJob {
    @Step
    public Step step1() {
        return StepBuilder.step("step1")
                .<String, String>chunk(10)
                .reader(MyReader.class)
                .processor(MyProcessor.class)
                .writer(MyWriter.class)
                .build();
    }
}
```

## Configure Quartz

Create a Quartz scheduler bean:

```java
@ApplicationScoped
public class QuartzSchedulerConfig {
    @Produces
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        return scheduler;
    }
}
```

## Trigger the JBeret Job with Quartz

Create a job scheduler bean to trigger the JBeret job:

```java
@ApplicationScoped
public class JobScheduler {
    @Inject
    Scheduler scheduler;

    @PostConstruct
    public void scheduleJob() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("myJob", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
}
```

## Build and Run the Application

```shell
./mvnw compile quarkus:dev
```

## In case of building a cli

Append the main method to look like this.

```java
import javax.batch.runtime.BatchRuntime;

public class Main {
    public static void main(String[] args) throws Exception {
        BatchRuntime.getJobOperator().startJob("myJob");
    }
}
```

## References

[GitHub-Sample Link](https://github.com/awiradarma/SampleBatch/tree/master)
