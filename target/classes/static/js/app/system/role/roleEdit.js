function updateRole() {
    var selected = $("#roleTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的角色！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个角色！');
        return;
    }
    var roleId = selected[0].roleId;
    $.post(ctx + "role/getRole", {"roleId": roleId}, function (r) {
        if (r.code === 0) {
            var $form = $('#role-add');
            var $menuTree = $('#menuTree');
            $form.modal();
            var role = r.msg;
            $("#role-add-modal-title").html('修改角色');
            $form.find("input[name='roleName']").val(role.roleName);
            $form.find("input[name='oldRoleName']").val(role.roleName);
            $form.find("input[name='roleId']").val(role.roleId);
            $form.find("input[name='remark']").val(role.remark);
            var menuArr = [];
            for (var i = 0; i < role.menuIds.length; i++) {
                menuArr.push(role.menuIds[i]);
            }
            $menuTree.jstree('select_node', menuArr, true);
            //全选再取消没有的权限,否则只有部分子菜单选中的情况会显示选择所有子菜单
            /*$menuTree.jstree().select_all();
            $menuTree.jstree('deselect_node', menuArr, true);*/
            //$menuTree.jstree().close_all();
            $menuTree.jstree().open_all();
            $("#role-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}