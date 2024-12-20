package com.haedal.haedalweb.application.board.service;

import com.haedal.haedalweb.application.board.dto.BoardImageResponseDto;
import com.haedal.haedalweb.application.board.mapper.BoardImageMapper;
import com.haedal.haedalweb.application.board.mapper.BoardMapper;
import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.domain.activity.service.ActivityService;
import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.board.model.BoardImage;
import com.haedal.haedalweb.domain.board.service.BoardImageService;
import com.haedal.haedalweb.domain.board.service.BoardService;
import com.haedal.haedalweb.application.board.dto.CreateBoardRequestDto;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.service.UserService;
import com.haedal.haedalweb.infrastructure.image.ImageRemoveEvent;
import com.haedal.haedalweb.infrastructure.image.ImageSaveRollbackEvent;
import com.haedal.haedalweb.security.service.SecurityService;
import com.haedal.haedalweb.util.ImageUtil;
import com.haedal.haedalweb.application.board.dto.BoardResponseDto;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class BoardAppServiceImpl implements BoardAppService {
    private final BoardService boardService;
    private final BoardImageService boardImageService;
    private final ActivityService activityService;
    private final SecurityService securityService;
    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final String uploadPath;
    private final String uploadUrl;

    @Autowired
    public BoardAppServiceImpl(BoardService boardService, BoardImageService boardImageService, ActivityService activityService, SecurityService securityService, UserService userService, ApplicationEventPublisher applicationEventPublisher, @Value("${file.path.upload-board-images}")String uploadPath, @Value("${file.url.upload-board-images}")String uploadUrl) {
        this.boardService = boardService;
        this.boardImageService = boardImageService;
        this.activityService = activityService;
        this.securityService = securityService;
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.uploadPath = uploadPath;
        this.uploadUrl = uploadUrl;
    }

    @Override
    @Transactional
    public void registerBoard(Long activityId, MultipartFile boardImageFile, CreateBoardRequestDto createBoardRequestDto) {
        Activity activity = activityService.getActivity(activityId);
        User loggedInUser = securityService.getLoggedInUser();

        // 게시판 참여자 검증
        List<String> participantIds = new ArrayList<>(createBoardRequestDto.getParticipants());;
        List<User> participants = userService.getUsersByIds(participantIds);
        userService.validateActiveUsers(participants, participantIds);

        // 파일 저장
        String originalFile = boardImageFile.getOriginalFilename();
        log.warn(originalFile + " original File Name");
        String saveFile = UUID.randomUUID() + originalFile.substring(originalFile.lastIndexOf('.'));
        log.warn(saveFile + " save File Name");
        ImageUtil.uploadImage(boardImageFile, uploadPath, saveFile);
        applicationEventPublisher.publishEvent(new ImageSaveRollbackEvent(uploadPath, saveFile));

        Board board = Board.builder()
                .name(createBoardRequestDto.getBoardName())
                .intro(createBoardRequestDto.getBoardIntro())
                .user(loggedInUser)
                .participants(new ArrayList<>())
                .activity(activity)
                .build();

        BoardImage boardImage = BoardImage.builder()
                .saveFile(saveFile)
                .originalFile(originalFile)
                .board(board)
                .build();


        board.setBoardImage(boardImage);

        boardService.registerBoard(participants, board);
    }

    @Override
    @Transactional(readOnly = true)
    public BoardResponseDto getBoard(Long activityId, Long boardId) {
        Board board = boardService.getBoardWithImageAndParticipants(activityId, boardId);
        BoardImageResponseDto boardImageResponseDto = BoardImageMapper.toDto(board.getBoardImage());
        String imageUrl = ImageUtil.generateImageUrl(uploadUrl, boardImageResponseDto.getBoardImageSaveFile());

        return BoardMapper.toDto(activityId, imageUrl, board);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponseDto> getBoardPage(Long activityId, Pageable pageable) {
        Page<Board> boardPage = boardService.getBoardPage(activityId, pageable);

        Page<BoardResponseDto> boardResponsePage = boardPage.map(board -> {
            BoardImageResponseDto boardImageResponseDto = BoardImageMapper.toDto(board.getBoardImage());
            String imageUrl = ImageUtil.generateImageUrl(uploadUrl, boardImageResponseDto.getBoardImageSaveFile());

            return BoardMapper.toDto(activityId, imageUrl, board);
        });

        return boardResponsePage;
    }

    @Override
    @Transactional
    public void updateBoardImage(Long activityId, Long boardId, MultipartFile boardImageFile) {
        Board board = boardService.getBoardWithImageAndUser(activityId, boardId);

        // 생성자와 로그인한 유저가 같은지 검증
        User loggedInUser = securityService.getLoggedInUser();
        User creator = board.getUser();
        boardService.validateAuthorityOfBoardManagement(loggedInUser, creator);

        // 새 이미지 파일 저장
        String originalFile = boardImageFile.getOriginalFilename();
        String saveFile = UUID.randomUUID() + originalFile.substring(originalFile.lastIndexOf('.'));
        ImageUtil.uploadImage(boardImageFile, uploadPath, saveFile);

        // ROLLBACK 발생시 저장한 이미지 파일 삭제
        applicationEventPublisher.publishEvent(new ImageSaveRollbackEvent(uploadPath, saveFile));

        BoardImage boardImage = board.getBoardImage();
        String removeFile = boardImage.getSaveFile();

        // 이미지 테이블 수정
        boardImage.setOriginalFile(originalFile);
        boardImage.setSaveFile(saveFile);

        // 이전 이미지 삭제
        ImageUtil.removeImage(uploadPath, removeFile);

        // 모든작업이 Commit 될 시에 이전 이미지 파일 삭제
        applicationEventPublisher.publishEvent(new ImageRemoveEvent(uploadPath, removeFile));
    }
}
