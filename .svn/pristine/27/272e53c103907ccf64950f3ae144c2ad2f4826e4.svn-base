function updateEntity() {
    var selected = $("#entityTable").bootstrapTreeTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的党支部！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个党支部！');
        return;
    }
    var partyId = selected[0].id;
    $.post(ctx + "party/getDept", {"partyId": partyId}, function (r) {
        if (r.code === 0) {
            var $form = $('#entity-add');
            var $entityTree = $('#entityTree');
            $form.modal();
            var dept = r.msg;
            $("#entity-add-modal-title").html('修改党支部');
            $form.find("input[name='partyName']").val(dept.deptName);
            $form.find("input[name='oldPartyName']").val(dept.deptName);
            $form.find("input[name='partyId']").val(dept.partyId);
            $entityTree.jstree('select_node', dept.parentId, true);
            $entityTree.jstree('disable_node', dept.partyId);
            $("#entity-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}