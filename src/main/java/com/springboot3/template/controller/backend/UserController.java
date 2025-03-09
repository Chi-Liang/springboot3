package com.springboot3.template.controller.backend;

import com.springboot3.template.model.vo.UserVO;
import com.springboot3.template.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/${template.entry-point.auth}/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("userList", userService.findAll());
		return "backend/user/list";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		UserVO user = UserVO.builder().build();
		model.addAttribute("user", user);
		return "backend/user/add";
	}
	
	@PostMapping("/addSubmit")
	public String addSubmit(@ModelAttribute UserVO userDTO) {	
		userService.saveUser(userDTO);
		return "redirect:/template/backend/user/list";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam Integer no, Model model) {
		model.addAttribute("user", userService.findUser(no));
		return "backend/user/edit";
	}
	
	@PostMapping("/editSubmit")
	public String editSubmit(@ModelAttribute UserVO userDTO) {	
		userService.editUser(userDTO);
		return "redirect:/template/backend/user/list";
	}
	
	@GetMapping("/changePwd")
	public String changePwd(@RequestParam Integer no, Model model) {
		model.addAttribute("user", userService.findUser(no));
		return "backend/user/changePwd";
	}
	
	@PostMapping("/changePwdSubmit")
	public String changePwdSubmit(@ModelAttribute UserVO userDTO) {
		userService.changeUserPwd(userDTO);
		return "redirect:/template/backend/user/list";
	}
	
	@PostMapping("/delSubmit")
	public String delSubmit(@RequestParam Integer no) {
		userService.deleteUser(no);
		return "redirect:/template/backend/user/list";
	}
}
