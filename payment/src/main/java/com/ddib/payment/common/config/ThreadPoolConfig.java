//package com.ddib.payment.common.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.Executor;
//import java.util.concurrent.ThreadPoolExecutor;
//
//@Configuration
//public class ThreadPoolConfig {
//
//    private static final int CORE_POOL_SIZE = 200;
//    private static final int QUEUE_CAPACITY = Integer.MAX_VALUE;
//    private static final int MAX_POOL_SIZE = 250;
//
//    /**
//     * 비동기 처리를 위한 ThreadPoolTaskExecutor 설정
//     * - setCorePoolSize : 동시에 실행 할 기본 스레드의 수 설정 (default : 1)
//     * - setQueueCapacity : 이벤트 대기 큐 크기 (default : Integer.MAX_VALUE(약 21억))
//     * - setMaxPoolSize : 스레드 풀의 최대 스레드 수 설정 (default : Integer.MAX_VALUE(약 21억))
//     * - setRejectedExecutionHandler : max size만큼 스레드를 생성하고, 설정한 queue가 가득 찬 상태에서 추가 작업이 들어올 경우 RejectedExecutionException 예외 발생 -> 더 이상 처리할 수 없다는 오류!
//     * - ThreadPoolExecutor.CallerRunsPolicy : shutdown 상태가 아니라면 ThreadPoolTaskExecutor에 요청한 Caller 스레드에서 직접 처리한다. (태스크 유실 최소화)
//     * - setWaitForTasksToCompleteOnShutdown : true로 설정하면 애플리케이션 종료 요청 시 queue에 남아있는 모든 작업들이 완료될 때까지 기다린 후 종료된다.
//     */
//    @Bean(name = "taskExecutor")
//    public Executor taskExecutor() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//
//        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
//        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
//        taskExecutor.setCorePoolSize(MAX_POOL_SIZE);
//        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
//
//        // ThreadPoolTaskExecutor를 생성하고 사용할 수 있도록 initialize() 호출
//        // 명시적으로 적지 않아도 빈으로 등록될 때 initialize 한다.
//        taskExecutor.initialize();
//
//        return taskExecutor;
//    }
//
//}
