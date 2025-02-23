package com.haedal.haedalweb.application.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.haedal.haedalweb.application.board.dto.BoardRequestDto;
import com.haedal.haedalweb.application.board.dto.BoardResponseDto;
import com.haedal.haedalweb.application.board.mapper.BoardMapper;
import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.domain.activity.service.ActivityService;
import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.board.model.BoardImage;
import com.haedal.haedalweb.domain.board.service.BoardService;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.service.UserService;
import com.haedal.haedalweb.infrastructure.image.ImageRemoveEvent;
import com.haedal.haedalweb.infrastructure.image.ImageSaveRollbackEvent;
import com.haedal.haedalweb.infrastructure.image.ImageUtil;
import com.haedal.haedalweb.security.service.SecurityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardAppServiceImpl implements BoardAppService {
	private final BoardService boardService;
	private final ActivityService activityService;
	private final SecurityService securityService;
	private final UserService userService;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final String uploadPath;
	private final String uploadUrl;

	@Autowired
	public BoardAppServiceImpl(BoardService boardService, ActivityService activityService,
		SecurityService securityService, UserService userService, ApplicationEventPublisher applicationEventPublisher,
		@Value("${file.path.upload-board-images}") String uploadPath,
		@Value("${file.url.upload-board-images}") String uploadUrl) {
		this.boardService = boardService;
		this.activityService = activityService;
		this.securityService = securityService;
		this.userService = userService;
		this.applicationEventPublisher = applicationEventPublisher;
		this.uploadPath = uploadPath;
		this.uploadUrl = uploadUrl;
	}

	@Override
	@Transactional
	public void registerBoard(Long activityId, MultipartFile boardImageFile, BoardRequestDto boardRequestDto) {
		Activity activity = activityService.getActivity(activityId);
		User loggedInUser = securityService.getLoggedInUser();

		// 게시판 참여자 검증
		List<String> participantIds = new ArrayList<>(boardRequestDto.getParticipants());
		List<User> participants = userService.getUsersByIds(participantIds);
		userService.validateActiveUsers(participantIds, participants);

		// 파일 저장
		String originalFile = boardImageFile.getOriginalFilename();
		String saveFile = UUID.randomUUID() + "." + ImageUtil.getExtension(originalFile);
		ImageUtil.uploadImage(boardImageFile, uploadPath, saveFile);
		applicationEventPublisher.publishEvent(new ImageSaveRollbackEvent(uploadPath, saveFile));

		Board board = Board.builder()
			.name(boardRequestDto.getBoardName())
			.intro(boardRequestDto.getBoardIntro())
			.user(loggedInUser)
			.activity(activity)
			.build();

		BoardImage boardImage = BoardImage.builder()
			.saveFile(saveFile)
			.originalFile(originalFile)
			.board(board)
			.build();

		board.setBoardImage(boardImage);
		boardService.addParticipantsToBoard(participants, board);

		boardService.registerBoard(board);
	}

	@Override
	@Transactional(readOnly = true)
	public BoardResponseDto getBoard(Long activityId, Long boardId) {
		Board board = boardService.getBoardWithParticipants(activityId, boardId);

		String imageUrl = ImageUtil.generateImageUrl(uploadUrl, board.getBoardImage().getSaveFile());

		return BoardMapper.toDto(imageUrl, board);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BoardResponseDto> getBoardPage(Long activityId, Pageable pageable) {
		Page<Board> boardPage = boardService.getBoardPage(activityId, pageable);

		Page<BoardResponseDto> boardResponsePage = boardPage.map(board -> {
			String imageUrl = ImageUtil.generateImageUrl(uploadUrl, board.getBoardImage().getSaveFile());

			return BoardMapper.toDto(imageUrl, board);
		});

		return boardResponsePage;
	}

	@Override
	@Transactional
	public void updateBoardImage(Long activityId, Long boardId, MultipartFile boardImageFile) {
		Board board = boardService.getBoardWithUser(activityId, boardId);

		User loggedInUser = securityService.getLoggedInUser();
		User creator = board.getUser();
		// BoardImage 업데이트 권한 유효성 검사
		boardService.validateAuthorityOfBoardManagement(loggedInUser, creator);

		// 새 이미지 파일 저장
		String originalFile = boardImageFile.getOriginalFilename();
		String saveFile = UUID.randomUUID() + "." + ImageUtil.getExtension(originalFile);
		ImageUtil.uploadImage(boardImageFile, uploadPath, saveFile);

		// ROLLBACK 발생시 저장한 이미지 파일 삭제
		applicationEventPublisher.publishEvent(new ImageSaveRollbackEvent(uploadPath, saveFile));

		BoardImage boardImage = board.getBoardImage();
		String removeFile = boardImage.getSaveFile();

		// 이미지 테이블 수정
		boardImage.setOriginalFile(originalFile);
		boardImage.setSaveFile(saveFile);

		// 모든작업이 Commit 될 시에 이전 이미지 파일 삭제
		applicationEventPublisher.publishEvent(new ImageRemoveEvent(uploadPath, removeFile));
	}

	@Override
	@Transactional
	public void updateBoard(Long activityId, Long boardId, BoardRequestDto boardRequestDto) { // 여기 bulk insert 최적화 해야함
		Board board = boardService.getBoardWithUserAndParticipants(activityId, boardId);

		User loggedInUser = securityService.getLoggedInUser();
		User creator = board.getUser();
		// Board 업데이트 권한 유효성 검사
		boardService.validateAuthorityOfBoardManagement(loggedInUser, creator);

		// 게시판 참여자 검증
		List<String> participantIds = new ArrayList<>(boardRequestDto.getParticipants());
		List<User> participants = userService.getUsersByIds(participantIds);
		userService.validateActiveUsers(participantIds, participants);

		// 게시판 메타 데이터 수정
		board.setName(boardRequestDto.getBoardName());
		board.setIntro(boardRequestDto.getBoardIntro());
		board.getParticipants().clear();
		boardService.addParticipantsToBoard(participants, board);
	}

	@Override
	@Transactional
	public void removeBoard(Long activityId, Long boardId) {
		Board board = boardService.getBoardWithUser(activityId, boardId);

		User loggedInUser = securityService.getLoggedInUser();
		User creator = board.getUser();
		// Board 삭제 권한 유효성 검사
		boardService.validateAuthorityOfBoardManagement(loggedInUser, creator);

		// 저장된 BoardImage 파일 이름 저장
		BoardImage boardImage = board.getBoardImage();
		String removeFile = boardImage.getSaveFile();

		// Board 삭제
		boardService.removeBoard(board);

		// BoardImage 파일 삭제
		applicationEventPublisher.publishEvent(new ImageRemoveEvent(uploadPath, removeFile));
	}
}
