package org.example.expert.domain.manager.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.manager.dto.ManagerRequestDto;
import org.example.expert.domain.manager.dto.ManagerResponseDto;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.manager.repository.ManagerRepository;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.example.expert.global.exception.ApiException;
import org.example.expert.global.exception.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public ManagerResponseDto.Create saveManager(AuthUser authUser, long todoId, ManagerRequestDto.Create managerSaveRequest) {
        // 일정을 만든 유저
        User user = User.fromAuthUser(authUser);
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER, "Todo not found"));

        if(todo.getUser() == null){
            throw new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER, "담당자를 등록하려고 하는 유저가 일정을 만든 유저가 유효하지 않습니다.");
        }

        if (!ObjectUtils.nullSafeEquals(user.getId(), todo.getUser().getId())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER, "담당자를 등록하려고 하는 유저가 일정을 만든 유저가 유효하지 않습니다.");
        }

        User managerUser = userRepository.findById(managerSaveRequest.getManagerUserId())
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER, "등록하려고 하는 담당자 유저가 존재하지 않습니다."));

        if (ObjectUtils.nullSafeEquals(user.getId(), managerUser.getId())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER, "일정 작성자는 본인을 담당자로 등록할 수 없습니다.");
        }

        Manager newManagerUser = new Manager(managerUser, todo);
        Manager savedManagerUser = managerRepository.save(newManagerUser);

        return new ManagerResponseDto.Create(
                savedManagerUser.getId(),
                new UserResponse(managerUser.getId(), managerUser.getEmail())
        );
    }

    @Transactional(readOnly = true)
    public List<ManagerResponseDto.Get> getManagers(long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER, "Todo not found"));

        List<Manager> managerList = managerRepository.findByTodoIdWithUser(todo.getId());

        List<ManagerResponseDto.Get> dtoList = new ArrayList<>();
        for (Manager manager : managerList) {
            User user = manager.getUser();
            dtoList.add(new ManagerResponseDto.Get(
                    manager.getId(),
                    new UserResponse(user.getId(), user.getEmail())
            ));
        }
        return dtoList;
    }

    @Transactional
    public void deleteManager(long userId, long todoId, long managerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER, "User not found"));

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER, "Todo not found"));

        if (todo.getUser() == null || !ObjectUtils.nullSafeEquals(user.getId(), todo.getUser().getId())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER, "해당 일정을 만든 유저가 유효하지 않습니다.");
        }

        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER, "Manager not found"));

        if (!ObjectUtils.nullSafeEquals(todo.getId(), manager.getTodo().getId())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_PARAMETER, "해당 일정에 등록된 담당자가 아닙니다.");
        }

        managerRepository.delete(manager);
    }
}
