package com.haedal.haedalweb.domain.board.service;

import com.haedal.haedalweb.constants.ErrorCode;
import com.haedal.haedalweb.domain.activity.model.Activity;
import com.haedal.haedalweb.domain.board.model.Board;
import com.haedal.haedalweb.domain.board.model.Participant;
import com.haedal.haedalweb.domain.user.model.Role;
import com.haedal.haedalweb.domain.user.model.User;
import com.haedal.haedalweb.domain.user.model.UserStatus;
import com.haedal.haedalweb.web.board.dto.UpdateBoardRequestDto;
import com.haedal.haedalweb.application.board.dto.BoardResponseDto;
import com.haedal.haedalweb.application.board.dto.ParticipantResponseDto;
import com.haedal.haedalweb.exception.BusinessException;
import com.haedal.haedalweb.domain.activity.repository.ActivityRepository;
import com.haedal.haedalweb.domain.board.repository.BoardRepository;
import com.haedal.haedalweb.domain.post.repository.PostRepository;
import com.haedal.haedalweb.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    private final UserService userService;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;


    @Override
    public void registerBoard(List<User> participants, Board board) {
        addParticipantsToBoard(board, participants);
        boardRepository.save(board);
    }

    @Override
    public Board getBoard(Long activityId, Long boardId) {
        return boardRepository.findBoardWithImageAndParticipants(activityId, boardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));
    }

    @Override
    public Page<Board> getBoardPage(Long activityId, Pageable pageable) {
        return boardRepository.findBoardsByActivityId(activityId, pageable);
    }

    @Transactional
    public void deleteBoard(Long activityId, Long boardId) {
        Board board = boardRepository.findByActivityIdAndId(activityId, boardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));

        User loggedInUser = userService.getLoggedInUser();
        User creator = board.getUser();

        validateAuthorityOfBoardManagement(loggedInUser, creator);
        validateDeleteBoardRequest(boardId);

        boardRepository.delete(board);
    }

    @Transactional
    public void updateBoardImage(Long activityId, Long boardId, String newImageUrl) {
        Board board = boardRepository.findByActivityIdAndId(activityId, boardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));

        User loggedInUser = userService.getLoggedInUser();
        User creator = board.getUser();

        validateAuthorityOfBoardManagement(loggedInUser, creator);

//        s3Service.deleteObject(board.getImageUrl());
//        board.setImageUrl(newImageUrl);
        boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(Long activityId, Long boardId, UpdateBoardRequestDto updateBoardRequestDto) {
        Board board = boardRepository.findByActivityIdAndId(activityId, boardId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOARD_ID));

        User loggedInUser = userService.getLoggedInUser();
        User creator = board.getUser();

        validateAuthorityOfBoardManagement(loggedInUser, creator);

        List<String> participantIds = new ArrayList<>(updateBoardRequestDto.getParticipants());
        List<User> participants = userService.getUsersByIds(participantIds);

        validateParticipants(participants, participantIds);

        board.setName(updateBoardRequestDto.getBoardName());
        board.setIntro(updateBoardRequestDto.getBoardIntro());
        board.setParticipants(new ArrayList<>());
        addParticipantsToBoard(board, participants);

        boardRepository.save(board);
    }

    @Override
    public boolean hasBoardsByActivityId(Long activityId) {
        return boardRepository.existsByActivityId(activityId);
    }

    private void addParticipantsToBoard(Board board, List<User> participants) {
        for (User user : participants) {
            Participant participant = Participant.builder()
                    .board(board)
                    .user(user)
                    .build();
            board.addParticipant(participant);
        }
    }

    @Override
    public void validateParticipants(List<User> users, List<String> userIds) {
        if (users.size() != userIds.size()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_USER_ID);
        }

        users.forEach(user -> {
            UserStatus userStatus = user.getUserStatus();
            if (userStatus != UserStatus.ACTIVE) {
                throw new BusinessException(ErrorCode.NOT_FOUND_USER_ID);
            }
        });
    }

    private BoardResponseDto convertToBoardDTO(Board board, Long activityId) {
        return BoardResponseDto.builder()
                .activityId(activityId)
                .boardId(board.getId())
                .boardName(board.getName())
                .boardIntro(board.getIntro())
                .participants(convertParticipants(board.getParticipants())) // List<Participants>로 List<participantDTO> 만들기
                .build();
    }

    private List<ParticipantResponseDto> convertParticipants(List<Participant> participants) {
        return participants.stream()
                .map(this::convertToParticipantDTO)
                .collect(Collectors.toList());
    }

    private ParticipantResponseDto convertToParticipantDTO(Participant participant) {
        return ParticipantResponseDto.builder()
                .participantId(participant.getId())
                .userId(participant.getUser().getId())
                .userName(participant.getUser().getName())
                .build();
    }

    private void validateAuthorityOfBoardManagement(User loggedInUser, User creator) {
        if (loggedInUser.getRole() == Role.ROLE_TEAM_LEADER && !loggedInUser.getId().equals(creator.getId())) {
            throw new BusinessException(ErrorCode.FORBIDDEN_UPDATE);
        }
    }

    private void validateDeleteBoardRequest(Long boardId) {
        if (postRepository.existsByBoardId(boardId)) {
            throw new BusinessException(ErrorCode.EXIST_POST);
        }
    }
}
