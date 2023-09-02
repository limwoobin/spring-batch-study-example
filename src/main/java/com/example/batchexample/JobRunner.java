package com.example.batchexample;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobRunner implements ApplicationRunner {
  private final JobLauncher jobLauncher;
  private final Job job;

  public JobRunner(JobLauncher jobLauncher, Job job) {
    this.jobLauncher = jobLauncher;
    this.job = job;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    JobParameters parameters = new JobParametersBuilder()
      .addString("name", "user1")
      .addDate("birth", new Date())
      .addLong("seq", 2L)
      .addDouble("length", 16.5)
      .toJobParameters();

    jobLauncher.run(job, parameters);
  }
}
