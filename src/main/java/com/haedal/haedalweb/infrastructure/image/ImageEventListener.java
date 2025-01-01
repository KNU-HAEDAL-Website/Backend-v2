package com.haedal.haedalweb.infrastructure.image;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class ImageEventListener {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAfterCommit(ImageRemoveEvent event) { // 트랜잭션이 성공한다면, 파일 시스템에 저장한 이미지 삭제 (기존 이미지 삭제 할 때 사용)
        // 트랜잭션 커밋 후 파일 삭제
        ImageUtil.removeImage(event.getUploadPath(), event.getRemoveFile());
        log.info("Successfully removed old image file: {}", event.getRemoveFile());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleAfterRollback(ImageSaveRollbackEvent event) { // 트랜잭션이 실패한다면, 파일 시스템에 저장한 이미지 삭제 (새로운 이미지 저장 할 때 사용)
        ImageUtil.removeImage(event.getUploadPath(), event.getSaveFile());
        log.info("Transaction rolled back, removed newly uploaded file: {}", event.getSaveFile());
    }

}
