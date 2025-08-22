package cc.abing.abstart.api.controller;

import cc.abing.abstart.schedule.SchedulerConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/scheduler")
public class SchedulerController {

    private final SchedulerConfig schedulerConfig;
    private final Map<String, ScheduledTaskInfo> scheduledTasks = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取所有的任务
     */
    @PostMapping("/getAllTasks")
    public ResponseEntity<String> getAllTasks(@RequestBody @Valid TaskIdRequest request) {
        try {
            return ResponseEntity.ok(objectMapper.writeValueAsString(scheduledTasks.keySet()));
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize task list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve tasks: " + e.getMessage());
        }
    }


    /**
     * 设置周期性提醒任务
     */
    @PostMapping("/add")
    public ResponseEntity<String> setPeriodicRemind(@RequestBody @Valid ScheduleTaskRequest request) {
        // 1. 参数校验已在DTO中通过注解完成
        
        try {
            // 2. 先取消已存在的同名任务
            cancelScheduledTask(request.getTaskId());

            // 3. 创建新任务
            ScheduledTaskRegistrar taskRegistrar = schedulerConfig.getTaskRegistrar();
            Runnable runnable = createTaskRunnable(request);
            CronTask cronTask = new CronTask(runnable, request.getCronExpression());
            ScheduledTask scheduledTask = taskRegistrar.scheduleCronTask(cronTask);

            // 4. 存储任务引用和原始参数
            ScheduledTaskInfo taskInfo = new ScheduledTaskInfo();
            taskInfo.setScheduledTask(scheduledTask);
            taskInfo.setTaskRequest(request);
            scheduledTasks.put(request.getTaskId(), taskInfo);

            log.info("Scheduled task created - ID: {}, Cron: {}", 
                    request.getTaskId(), request.getCronExpression());

            return ResponseEntity.ok("Task scheduled successfully");
        } catch (Exception e) {
            log.error("Failed to schedule task with ID: {}", request.getTaskId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to schedule task: " + e.getMessage());
        }
    }

    /**
     * 取消周期性任务
     */
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelPeriodicTask(@RequestBody @Valid TaskIdRequest request) {
        // 参数校验已在DTO中通过注解完成

        boolean cancelled = cancelScheduledTask(request.getTaskId());
        if (cancelled) {
            return ResponseEntity.ok("Task cancelled successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Task not found or already cancelled");
        }
    }

    // 创建任务执行的Runnable
    private Runnable createTaskRunnable(ScheduleTaskRequest request) {
        return () -> {
            try {
                log.info("Executing reminder task - ID: {}, Message: {}", request.getTaskId(), request.getMessage());

                // 这里可以添加实际的提醒逻辑，例如：
                // 1. 使用模板引擎渲染消息（如果指定了模板）
                // 2. 发送通知（邮件、短信、站内信等）
                // 3. 根据 remindWithButton 决定是否添加功能按钮

                // 示例：模拟发送提醒
                if (request.isRemindWithButton()) {
                    log.info("This reminder includes action buttons for task ID: {}", request.getTaskId());
                    // 实际项目中这里可能会调用消息服务，附带按钮配置
                }
            } catch (Exception e) {
                log.error("Error executing scheduled task - ID: {}", request.getTaskId(), e);
            }
        };
    }


    // 取消任务的辅助方法
    private boolean cancelScheduledTask(String taskId) {
        ScheduledTaskInfo taskInfo = scheduledTasks.get(taskId);
        if (taskInfo != null) {
            taskInfo.getScheduledTask().cancel();
            scheduledTasks.remove(taskId);
            log.info("Cancelled scheduled task - ID: {}", taskId);
            return true;
        }
        log.warn("Attempted to cancel non-existent task - ID: {}", taskId);
        return false;
    }

    // 任务信息实体类
    @Data
    static class ScheduledTaskInfo {
        private ScheduledTask scheduledTask;
        private ScheduleTaskRequest taskRequest;
    }

}


@Data
class TaskIdRequest {
    @NotBlank(message = "Task ID must not be blank")
    private String taskId;
}

@Data
class ScheduleTaskRequest {
    @NotBlank(message = "Task ID must not be blank")
    private String taskId;          // 任务唯一标识（用于后续取消）
    
    @NotBlank(message = "Cron expression must not be blank")
    private String cronExpression;  // Cron表达式
    
    @NotBlank(message = "Message must not be blank")
    private String message;         // 提醒内容
    
    private String templateId;      // 模板ID（可选）
    
    private boolean remindWithButton = false; // 是否带有功能按钮
}