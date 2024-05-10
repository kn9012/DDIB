package com.ddib.product.product.util.batch.scheduler;

import com.ddib.product.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductJobScheduler {

    private final Job productJob;

    private final JobLauncher jobLauncher;

    private final ProductService productService;

    //    @Scheduled(cron = "30 * * * * ?") // TEST 시 10초 주기로 스케줄링
    @Scheduled(cron = "0 * * * *")
    public void runAlarmJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(productJob, jobParameters);
    }

    @Scheduled(cron = "0 * * * *")
    public void updateTimeOverProduct(){
        productService.updateTimeOverProduct();
    }
}
