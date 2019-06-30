package com.wayn.controller.system;

import com.baomidou.mybatisplus.plugins.Page;
import com.wayn.commom.base.BaseControlller;
import com.wayn.commom.exception.BusinessException;
import com.wayn.commom.util.Response;
import com.wayn.domain.Role;
import com.wayn.enums.Operator;
import com.wayn.framework.annotation.Log;
import com.wayn.framework.util.ShiroUtil;
import com.wayn.service.MenuService;
import com.wayn.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseControlller {
	private static final String PREFIX = "system/role";
	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	@RequiresPermissions("sys:role:role")
	@GetMapping
	public String roleIndex(Model model) {
		return PREFIX + "/role";
	}

	@Log(value = "角色管理")
	@RequiresPermissions("sys:role:role")
	@ResponseBody
	@PostMapping("/list")
	public Page<Role> list(Model model, Role role) {
		Page<Role> page = getPage();
		return roleService.listPage(page, role);
	}

	@RequiresPermissions("sys:role:add")
	@GetMapping("/add")
	public String add(Model model) {
		return PREFIX + "/add";
	}

	@RequiresPermissions("sys:role:edit")
	@GetMapping("/edit")
	public String edit(Model model, String id) {
		Role role = roleService.selectById(id);
		model.addAttribute("role1", role);
		return PREFIX + "/edit";
	}

	@Log(value = "角色管理", operator = Operator.ADD)
	@RequiresPermissions("sys:role:add")
	@ResponseBody
	@PostMapping("/addSave")
	public Response addSave(Model model, Role role, String menuIds) {
		role.setCreateTime(new Date());
		roleService.save(role, menuIds);
		ShiroUtil.clearCachedAuthorizationInfo();
		return Response.success("新增角色成功");
	}

	@Log(value = "角色管理", operator = Operator.UPDATE)
	@RequiresPermissions("sys:role:edit")
	@ResponseBody
	@PostMapping("/editSave")
	public Response editSave(Model model, Role role, String menuIds) throws Exception {
		roleService.update(role, menuIds);
		ShiroUtil.clearCachedAuthorizationInfo();
		return Response.success("修改角色成功");
	}

	@Log(value = "角色管理", operator = Operator.DELETE)
	@RequiresPermissions("sys:role:remove")
	@ResponseBody
	@DeleteMapping("/remove/{roleId}")
	public Response remove(Model model, @PathVariable("roleId") String roleId) throws BusinessException {
		roleService.remove(roleId);
		return Response.success("删除角色成功");
	}

	@Log(value = "角色管理", operator = Operator.DELETE)
	@RequiresPermissions("sys:role:remove")
	@ResponseBody
	@PostMapping("/batchRemove")
	public Response batchRemove(Model model, @RequestParam("ids[]") String[] ids) throws BusinessException {
		roleService.batchRemove(ids);
		return Response.success("批量删除角色成功");
	}
}
