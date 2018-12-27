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
    var entityId = selected[0].id;
    $.post(ctx + "party/getParty", {"partyId": entityId}, function (r) {
        if (r.code === 0) {
            var $form = $('#entity-add');
            var $entityTree = $('#entityTree');
            $form.modal();
            var entity = r.msg;
            $("#entity-add-modal-title").html('修改党支部');
            $form.find("input[name='partyName']").val(entity.partyName);
            $form.find("input[name='oldPartyName']").val(entity.partyName);
            $form.find("input[name='partyId']").val(entity.partyId);
            $form.find("input[name='address']").val(entity.address);
            $form.find("input[name='contract']").val(entity.contract);
            $form.find("input[name='mobile']").val(entity.mobile);
            $form.find("input[name='email']").val(entity.email);
            setStatus(entity.status);
            $entityTree.jstree('select_node', entity.parentId, true);
            $entityTree.jstree('disable_node', entity.partyId);
            $("#entity-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}