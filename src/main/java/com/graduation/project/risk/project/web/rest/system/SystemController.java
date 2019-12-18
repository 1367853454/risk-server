package com.graduation.project.risk.project.web.rest.system;

import com.graduation.project.risk.common.core.biz.RestBusinessTemplate;
import com.graduation.project.risk.common.model.CommonRestResult;
import com.graduation.project.risk.project.web.form.system.*;
import com.graduation.project.risk.project.biz.enums.CommonStatus;
import com.graduation.project.risk.project.biz.manager.SystemManager;
import com.graduation.project.risk.project.web.vo.KeyValueVO;
import com.graduation.project.risk.project.web.vo.system.GetUserInfoVO;
import io.swagger.annotations.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/admin/system")
@Api(value = "API - SystemController", description = "SystemController")
public class SystemController {

    private Logger logger = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private SystemManager systemManager;

    /*
    *用户管理页面
    * @return
     */
    @GetMapping("/page")
    public ModelAndView page(){
        return new ModelAndView("system/user");
    }
    /*
    *新增、编辑、查看页面
    * @param model
    * @param account 有account时编辑，无account时新增
    * @param edit 1 = 编辑、查看
    * #return
     */
    @GetMapping("/editPage")
    public ModelAndView editPage(Model model, @RequestParam(required = false) String account, @RequestParam Integer edit){

        ModelAndView mv = new ModelAndView("system/user-edit");

        model.addAttribute("account",account);
        model.addAttribute("edit",edit);

        if (account != null && account.length() > 0){
            UserInfoForm userInfoForm = new UserInfoForm();
            userInfoForm.setAccount(account);
            GetUserInfoVO userInfo = systemManager.userCheckAndEdit(userInfoForm);
            model.addAttribute("userInfo",userInfo);
        }

        return mv;
    }
    /*
    *修改密码页面
     */
    @GetMapping("/editpassword")
    public ModelAndView editPassword() {
        return new ModelAndView("system/changepassword");
    }

    /*
    *增加角色
    *
    * @param AddRoleForm
    *
     */
    @ApiOperation(value = "增加角色", notes = "notes", httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "value", dataType = "LONG")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/addrole")
    //@RequiresPermissions("")
    public CommonRestResult addRole(@RequestBody @Valid AddRoleForm addRoleForm){

        return RestBusinessTemplate.transaction(() ->{

            systemManager.addRole(addRoleForm);
            return true;
        });
    }
    /*
    *修改密码
    *
    * @param ChangePasswordForm
     */
    @ApiOperation(value = "修改密码", notes = "notes", httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "value", dataType = "LONG")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/changepassword")
    //@RequiresPermissions("")
    public CommonRestResult changePassword(@RequestBody @Valid ChangePasswordForm changePasswordForm){

        return RestBusinessTemplate.transaction(() ->{

            systemManager.changePassword(changePasswordForm);
            return true;
        });
    }
    /*
    *左侧菜单栏
    * 应该没用的
    * @param MenuForm
     */
    @ApiOperation(value = "左侧菜单栏 应该没用的", notes = "notes", httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "value", dataType = "LONG")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/rolemenuadd")
    //@RequiresPermissions("")
    public CommonRestResult roleMenuAdd(@RequestBody @Valid MenuForm menuForm){

        return RestBusinessTemplate.transaction(() ->{

            systemManager.roleMenuAdd(menuForm);
            return true;
        });
    }
    /*
    *重置密码
    * @param ResetForm
     */
    @ApiOperation(value = "重置密码", notes = "notes", httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "value", dataType = "LONG")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/reset")
    //@RequiresPermissions("")
    public CommonRestResult resetPassword(@RequestBody @Valid ResetForm resetForm){

        return RestBusinessTemplate.transaction(() ->{

            systemManager.resetPassword(resetForm);
            return true;
        });
    }
    /*
     *状态设置为启用
     * @param PackageStartAndStopForm
     */
    @ApiOperation(value = "状态设置为启用", notes = "notes", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/startusing")
    //@RequiresPermissions("")
    public CommonRestResult startUsing(@RequestBody @Valid StartAndStopForm startAndStopForm){

        return RestBusinessTemplate.transaction(() ->{

            systemManager.startUsing(startAndStopForm);
            return true;
        });
    }
    /*
     *状态设置为停用
     * @param PackageStartAndStopForm
     */
    @ApiOperation(value = "状态设置为停用", notes = "notes", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/stopusing")
    //@RequiresPermissions("")
    public CommonRestResult stopUsing(@RequestBody @Valid StartAndStopForm startAndStopForm){

        return RestBusinessTemplate.transaction(() ->{

            systemManager.stopUsing(startAndStopForm);
            return true;
        });
    }

    /*
    *增加用户
    * @param AddUserForm
    * @return
     */
    @ApiOperation(value = "增加用户", notes = "notes", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/adduser")
    //@RequiresPermissions("")
    public CommonRestResult addUser(@RequestBody @Valid AddUserForm addUserForm){

        return RestBusinessTemplate.transaction(() ->{

            systemManager.addUser(addUserForm);
            return true;
        });
    }
    /*
    *查看用户信息
    * @param PackageStartAndStopForm : account 唯一标识号
    * @return
     */
    @ApiOperation(value = "查看用户信息", notes = "notes", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/usercheckandedit")
    //@RequiresPermissions("")
    public CommonRestResult userCheckAndEdit(@Valid UserInfoForm userInfoForm){

        return RestBusinessTemplate.execute(() ->{

            return systemManager.userCheckAndEdit(userInfoForm);
        });
    }
    /*
    *编辑用户
    * @parma EditUserForm
    * @return
     */
    @ApiOperation(value = "编辑用户信息", notes = "notes", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/edituser")
    //@RequiresPermissions("")
    public CommonRestResult editUser(@RequestBody @Valid EditUserForm editUserForm){

        return RestBusinessTemplate.transaction(() ->{

            systemManager.editUser(editUserForm);
            return true;
        });
    }
    /*
    *获取角色id
    *
    * @return
     */
    @ApiOperation(value = "获取角色id", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/roleid")
    public CommonRestResult getRoleId(){
        return RestBusinessTemplate.execute(() ->{

            return systemManager.getRoleId();
        });
    }
    /*
     * 获取状态
     * @return
     */
    @ApiOperation(value = "获取状态", notes = "获取状态", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/status")
    public CommonRestResult getStatus(){
        return RestBusinessTemplate.execute(() ->{

            Map<String, List<KeyValueVO>> result = new HashMap<>();
            result.put("statusList", CommonStatus.list());

            return result;
        });
    }

    /*
    *用户查询
    * @param SysUserSearchForm
    * @return
     */
    @ApiOperation(value = "用户查询", notes = "notes", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/usersearch")
    //@RequiresPermissions("")
    public CommonRestResult sysUserSearch(SysUserSearchForm sysUserSearchForm){

        return RestBusinessTemplate.execute(() ->{

            return systemManager.sysUserSearch(sysUserSearchForm);
        });
    }

    /*
    *角色查询
    * @param SysRoleForm
    * @return
     */
    @ApiOperation(value = "角色查询", notes = "notes", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/rolesearch")
    //@RequiresPermissions("")
    public CommonRestResult sysRoleSearch(@Valid SysRoleForm sysRoleForm){

        return RestBusinessTemplate.execute(() ->{

            return systemManager.sysRoleSearch(sysRoleForm);
        });
    }
    /*
    *一个角色所有的权限
    *
    * @param SysRolePermissionForm : id 角色id
    * @return
     */
    @ApiOperation(value = "一个角色所有的权限", notes = "notes", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/rolepermission")
    //@RequiresPermissions("")
    public CommonRestResult rolePermission(@Valid SysRolePermissionForm sysRolePermissionForm){

        return RestBusinessTemplate.execute(() ->{

            return systemManager.rolePermission(sysRolePermissionForm);
        });
    }
    /*
    *所有的权限 排列好的顺序
    * @return
     */
    @ApiOperation(value = "所有的权限 排列好的顺序", notes = "notes", httpMethod = "GET")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @GetMapping("/permissiontree")
    //@RequiresPermissions("")
    public CommonRestResult permissionTree(){

        return RestBusinessTemplate.execute(() ->{

            return systemManager.permissionTree();
        });
    }
    /*
    *授权提交
    * @param EditRolePermissiomForm
     */
    @ApiOperation(value = "授权提交", notes = "notes", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message"), @ApiResponse(code = 401, message = "unauthenticated")})
    @PostMapping("/editrolepermission")
    //@RequiresPermissions("")
    public CommonRestResult editRolePermission(@RequestBody @Valid EditRolePermissiomForm editRolePermissiomForm){

        return RestBusinessTemplate.transaction(() ->{

            systemManager.editRolePermission(editRolePermissiomForm);
            return true;
        });
    }

}
