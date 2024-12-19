package com.haedal.haedalweb.application.board.service;

import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.domain.activity.service.ActivityService;
import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.board.model.BoardImage;
import com.haedal.haedalweb.domain.board.service.BoardService;
import com.haedal.haedalweb.application.board.dto.CreateBoardRequestDto;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.service.UserService;
import com.haedal.haedalweb.security.service.SecurityService;
import com.haedal.haedalweb.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final ActivityService activityService;
    private final SecurityService securityService;
    private final UserService userService;
    private final String uploadPath;

    @Autowired
    public BoardAppServiceImpl(BoardService boardService, ActivityService activityService, SecurityService securityService, UserService userService, @Value("${file.path.upload-board-images}")String uploadPath) {
        this.boardService = boardService;
        this.activityService = activityService;
        this.securityService = securityService;
        this.userService = userService;
        this.uploadPath = uploadPath;
    }

    @Override
    @Transactional
    public void registerBoard(Long activityId, MultipartFile boardImageFile, CreateBoardRequestDto createBoardRequestDto) {
        Activity activity = activityService.getActivity(activityId);
        User user = securityService.getLoggedInUser();

        // 게시판 참여자 검증
        List<String> participantIds = new ArrayList<>(createBoardRequestDto.getParticipants());;
        List<User> participants = userService.getUsersByIds(participantIds);
        userService.validateActiveUsers(participants, participantIds);

        // 파일 저장
        String originalFile = boardImageFile.getOriginalFilename();
        log.warn(originalFile + " original File Name");
        String saveFile = UUID.randomUUID() + originalFile.substring(originalFile.lastIndexOf('.'));
        log.warn(saveFile + " save File Name");
        FileUtil.uploadFile(boardImageFile, uploadPath, saveFile);

        Board board = Board.builder()
                .name(createBoardRequestDto.getBoardName())
                .intro(createBoardRequestDto.getBoardIntro())
                .user(user)
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
}
