# Correlating Quartz and JBeret Execution Contexts

## Understanding the Problem

In a Quarkus application using Quartz and JBeret, we often need to correlate the execution of Quartz jobs with the corresponding JBeret jobs. This correlation can be crucial for various reasons, such as:  

1. Logging and Monitoring: Tracking the execution of both jobs together.  
2. Error Handling: Propagating errors from one context to the other.  
3. Job Management: Controlling the lifecycle of both jobs.  
4. Proposed Solution: Leveraging Quartz's JobDataMap

Quartz's JobDataMap provides a flexible mechanism to store arbitrary data associated with a job. We can use this to store the JBeret job ID or other relevant information.

## Implementation Steps

### Store JBeret Job ID in Quartz JobDataMap

When scheduling the Quartz job, add the JBeret job ID to the *JobDataMap*:

```java
JobDetail jobDetail = JobBuilder.newJob(MyJBeretJob.class)
    .withIdentity("myJBeretJob", "group1")
    .setJobDataMap(new JobDataMap()
        .put("jberetJobId", "myJBeretJobId"))
    .build();
```

### Access JBeret Job ID in Quartz Job Execution

In the execute method of the Quartz job, retrieve the JBeret job ID from the *JobExecutionContext*:

```java
@Override
public void execute(JobExecutionContext context) throws JobExecutionException {
    String jberetJobId = context.getJobDetail().getJobDataMap().getString("jberetJobId");
    // Use the jberetJobId to correlate with the JBeret job execution
}
```

## Correlate in JBeret Job Execution

While JBeret doesn't provide a direct mechanism to access the Quartz job context, you can use the following approaches to correlate the two:  

- Logging: Log the JBeret job ID and other relevant information, using a consistent format with the Quartz job logs.  
- External Storage: Store the correlation information in a shared database or file system, accessible to both Quartz and JBeret.  
- Custom JobContext: Extend the JBeret *JobContext* to store additional information, such as the Quartz job ID. However, this approach requires careful consideration and might have limitations.
