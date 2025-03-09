package com.springboot3.template.service;

import com.springboot3.template.model.entity.User;
import com.springboot3.template.model.vo.UserVO;
import com.springboot3.template.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    public List<UserVO> findAll() {
        List<User> userList =
                userRepository.findAll().stream().collect(Collectors.toList());
        List<UserVO> voList = new ArrayList<UserVO>();
        for(User user:userList) {
            UserVO vo = UserVO.builder().no(user.getNo()).mid(user.getMid()).name(user.getName()).createdt(user.getCreatedt()).modifydt(user.getModifydt()).build();
            voList.add(vo);
        }

        return voList;
    }

    public UserVO findUser(Integer no) {
        User user = userRepository.findById(no).get();
        UserVO vo = UserVO.builder().no(user.getNo()).mid(user.getMid()).name(user.getName()).createdt(user.getCreatedt()).modifydt(user.getModifydt()).build();
        return vo;
    }

    public void saveUser(UserVO userVO) {
        User user = new User();
        user.setMid(userVO.mid());
        user.setName(userVO.name());
        user.setPassword(new BCryptPasswordEncoder().encode(userVO.password()));
        userRepository.save(user);
    }

    public void editUser(UserVO userVO) {
        User user = userRepository.findById(userVO.no()).get();
        user.setName(userVO.name());
        userRepository.save(user);
    }

    public void changeUserPwd(UserVO userVO) {
        User user = userRepository.findById(userVO.no()).get();
        user.setPassword(new BCryptPasswordEncoder().encode(userVO.password()));
        userRepository.save(user);
    }

    public void deleteUser(Integer no) {
        userRepository.deleteById(no);
    }

}
