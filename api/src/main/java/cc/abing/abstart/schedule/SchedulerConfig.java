package cc.abing.abstart.schedule;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@EnableScheduling
@Configuration
public class SchedulerConfig implements SchedulingConfigurer, ApplicationRunner {

    @Getter
    private ScheduledTaskRegistrar taskRegistrar;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = threadPoolTaskScheduler();
        taskRegistrar.setScheduler(taskScheduler);
        this.taskRegistrar=taskRegistrar;
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setRemoveOnCancelPolicy(true);
        scheduler.setThreadNamePrefix("scheduler-");
        scheduler.setPoolSize(Runtime.getRuntime().availableProcessors() / 3 + 1);
        return scheduler;
    }

    /**
     * 实现了 ApplicationRunner 接口，该接口的 run 方法会在 Spring Boot 应用完全启动后自动执行
     * 无需手动调用，只要将该类纳入 Spring 容器管理即可
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (true) {
            return;
        }
        CronTask cronTask = new CronTask(() -> log.info("foo-----------"), "0/3 * * * * ?");
        ScheduledTask fooTask = taskRegistrar.scheduleCronTask(cronTask);

        ExecutorService executor = Executors.newSingleThreadExecutor(Thread::new);
        executor.execute(() -> {
            try {
                // 等10秒
                TimeUnit.SECONDS.sleep(10);
                Runnable runnable = fooTask.getTask().getRunnable();
                // 停止foo任务
                fooTask.cancel();
                // 重新添加，并修改触发时间为每3秒一次
                taskRegistrar.scheduleCronTask(new CronTask(runnable, "0/6 * * * * ?"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}

