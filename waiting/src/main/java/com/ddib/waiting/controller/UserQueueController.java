package com.ddib.waiting.controller;

import com.ddib.waiting.dto.RankNumberResponse;
import com.ddib.waiting.service.UserQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/queue")
@Slf4j
public class UserQueueController { // UserQueueController 클래스 선언

    private final UserQueueService userQueueService; // UserQueueService 주입

    @GetMapping("")
    public Mono<?> waitingRoomPage(@RequestParam(name = "queue", defaultValue = "default") String queue,
                                   @RequestParam(name = "user_id") Long userId) {

        // 입장이 허용되어 페이지 리다이렉트 가능한지 확인
        return userQueueService.isAllowedByToken(queue, userId).filter(allowed -> allowed) // 허용되었다면
                .flatMap(allowed -> Mono.empty()) // 렌더링 없이 빈 Mono 반환
                .switchIfEmpty(
                        // 대기열 등록. 이미 대기열에 있으면서 오류가 발생할 경우, 해당 큐에서의 사용자 랭크 가져오기
                        userQueueService.registerWaitQueue(queue, userId).onErrorResume(ex -> userQueueService.getRank(queue, userId)).then() // Mono<Void> 반환
                );
    }

    @GetMapping("/rank") // GET 요청 매핑
    public Mono<RankNumberResponse> getRankUser(@RequestParam(name = "queue", defaultValue = "default") String queue, // 대기열 이름 받아옴 (기본값: default)
                                                @RequestParam(name = "user_id") Long userId) { // 사용자 ID 받아옴

        return userQueueService.getRank(queue, userId) // 사용자의 순위 조회
                .map(RankNumberResponse::new); // 순위 응답으로 매핑
    }

    @GetMapping("/{userId}")
    public Mono<?> waitingRoomPage2(@RequestParam(name = "queue", defaultValue = "default") String queue, @PathVariable Long userId) {

        log.info("userId : {}", userId);

        // 입장이 허용되어 페이지 리다이렉트 가능한지 확인
        return userQueueService.isAllowedByToken(queue, userId).filter(allowed -> allowed) // 허용되었다면
                .flatMap(allowed -> Mono.empty()) // 렌더링 없이 빈 Mono 반환
                .switchIfEmpty(
                        // 대기열 등록. 이미 대기열에 있으면서 오류가 발생할 경우, 해당 큐에서의 사용자 랭크 가져오기
                        userQueueService.registerWaitQueue(queue, userId).onErrorResume(ex -> userQueueService.getRank(queue, userId)).then() // Mono<Void> 반환
                );
    }

}
