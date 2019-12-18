package com.graduation.project.risk.project.web.rest.user;

import com.graduation.project.risk.common.core.biz.RestBusinessTemplate;
import com.graduation.project.risk.common.model.CommonRestResult;
import com.graduation.project.risk.project.biz.manager.LogManager;
import com.graduation.project.risk.project.biz.manager.UserManager;
import com.graduation.project.risk.project.dal.jpa.dataobject.sys.SysUserDO;
import com.graduation.project.risk.project.web.form.user.UserAddForm;
import com.graduation.project.risk.project.web.vo.menu.MenuVO;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/user")
@Api(value = "API - UserController", description = "user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserManager userManager;

    @Autowired
    private LogManager logManager;


    /**
     * @Description add system user
     * @Param [userAddForm]
     * @return
     **/
    @ApiOperation(value = " add system user", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/add")
    public CommonRestResult addUser(@RequestBody @Valid UserAddForm userAddForm) {
        return RestBusinessTemplate.transaction(()  ->{
            logger.info("addUser Request");
            userManager.addUser(userAddForm);
            return true;
        });
    }

//    @ApiOperation(value = "interface name", httpMethod = "POST")
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
//    @PostMapping("/getuser")
//    public CommonRestResult getUser(@RequestBody @Valid UserAccountForm userAccountForm) {
//
//        return RestBusinessTemplate.execute(()  ->{
//            logger.info("addUser Request");
//            return userManager.getUserByAccount(userAccountForm.getAccount());
//        });
//    }


    /**
     * @Description login
     * @Param [userInfo, response]
     * @return org.springframework.web.servlet.ModelAndView
     **/
    @RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView ajaxLogin(SysUserDO userInfo, HttpServletResponse response, HttpSession httpSession) {
        Map<String,String> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getAccount(), userInfo.getPassword());
        String JSESSIONID = null;
        try {
            subject.login(token);
            JSESSIONID = (String) SecurityUtils.getSubject().getSession().getId();
            response.setHeader("JSESSIONID", JSESSIONID);
        }catch (UnknownAccountException e) {
            map.put("loginInfo","User does not exist");
            return new ModelAndView("login",map);
        }catch (IncorrectCredentialsException e) {
            map.put("loginInfo","Error username or password");
            return new ModelAndView("login",map);
        }catch (Exception e) {
            e.printStackTrace();
            map.put("loginInfo","Login failed, please try again");
            return new ModelAndView("login",map);
        }

        //get user's menu
        List<MenuVO> menuVOList = userManager.getUserMenu(((SysUserDO) SecurityUtils.getSubject().getPrincipal()).getId());
//        Map<String, Object> model = new HashMap<>();
//        model.put("userInfo",((SysUserDO) SecurityUtils.getSubject().getPrincipal()));

        httpSession.setAttribute("menuVOList", menuVOList);
        httpSession.setAttribute("user", ((SysUserDO) SecurityUtils.getSubject().getPrincipal()));

        logManager.loginLog();

        return new ModelAndView("index");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/index")
    public ModelAndView index() {
        List<MenuVO> menuVOList = userManager.getUserMenu(((SysUserDO) SecurityUtils.getSubject().getPrincipal()).getId());
        Map<String, Object> model = new HashMap<>();
        model.put("userInfo",((SysUserDO) SecurityUtils.getSubject().getPrincipal()));
        return new ModelAndView("index",model);
    }

    @GetMapping("/error")
    public ModelAndView error() {
        return new ModelAndView("error");
    }

    /**
     * @Description get user list for selected
     * @return
     **/
    @ApiOperation(value = "user list", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/getuserlist")
    public CommonRestResult getUserList(){
        return RestBusinessTemplate.execute(() ->{
            return userManager.getUserList();
        });
    }


//    @GetMapping("/logout")
//    public ModelAndView logout() {
//        return new ModelAndView("login");
//    }

}
