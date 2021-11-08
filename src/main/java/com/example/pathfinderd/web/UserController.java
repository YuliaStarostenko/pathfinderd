package com.example.pathfinderd.web;

import com.example.pathfinderd.model.binding.UserLoginBindingModel;
import com.example.pathfinderd.model.binding.UserRegisterBinding;
import com.example.pathfinderd.model.service.UserServiceModel;
import com.example.pathfinderd.model.view.UserViewModel;
import com.example.pathfinderd.service.UserService;
import com.example.pathfinderd.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;


    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;

    }


    @ModelAttribute
    public UserRegisterBinding userRegisterBinding(){
        return new UserRegisterBinding();
    }

    @ModelAttribute
    public UserLoginBindingModel userLoginBindingModel(){
        return new UserLoginBindingModel();
    }

    @GetMapping("/register")
    public String register(Model model){
//        if(!model.containsAttribute("userRegisterBinding")){
//            model.addAttribute("userRegisterBinding",
//                    new UserRegisterBinding());
//        }
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterBinding userRegisterBinding,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors() || !userRegisterBinding.getPassword()
                .equals(userRegisterBinding.getConfirmPassword())){
            redirectAttributes
                    .addFlashAttribute("userRegisterBinding", userRegisterBinding);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBinding",
                            bindingResult);
            return "redirect:register";
        }

        boolean isNameExists = userService.isNameExists(userRegisterBinding.getUsername());

        if (isNameExists){
            //TODO... redirect with message
        }

        userService
                .registerUser(modelMapper.map(userRegisterBinding, UserServiceModel.class));

        return "redirect:login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("isExists", true);

        return "login";
    }

    @PostMapping("/login")
    public String loginConfirm(@Valid UserLoginBindingModel userLoginBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            redirectAttributes
                    .addFlashAttribute("userLoginBindingModel", userLoginBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel",
                            bindingResult);

            return "redirect:login";
        }
        UserServiceModel user = userService
                .findByUsernameAndPassword(userLoginBindingModel.getUsername(), userLoginBindingModel.getPassword());

        if (user == null){
            redirectAttributes
                    .addFlashAttribute("isExists", false)
                    .addFlashAttribute("userLoginBindingModel", userLoginBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel",
                            bindingResult);

            return "redirect:login";

        }

        userService.loginUser(user.getId(), user.getUsername());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(){
        userService.logout();
        return "redirect:/";
    }

    @GetMapping("/profile/{id}")
    private String profile(@PathVariable Long id, Model model){
        model
                .addAttribute("user", modelMapper
                        .map(userService.findById(id), UserViewModel.class));

        return "profile";
    }

}
