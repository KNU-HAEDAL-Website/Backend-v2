package com.haedal.haedalweb.infrastructure.image;

import com.haedal.haedalweb.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class ImageEventListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAfterCommit(ImageRemoveEvent event) {
        // 트랜잭션 커밋 후 파일 삭제
        ImageUtil.removeImage(event.getUploadPath(), event.getRemoveFile());
        log.info("Successfully removed old image file: {}", event.getRemoveFile());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleAfterRollback(ImageSaveRollbackEvent event) {
        ImageUtil.removeImage(event.getUploadPath(), event.getSaveFile());
        log.info("Transaction rolled back, removed newly uploaded file: {}", event.getSaveFile());
    }

}
