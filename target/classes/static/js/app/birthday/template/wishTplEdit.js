function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的模板！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个模板！');
        return;
    }
    var entityId = selected[0].tplId;
    $.post(ctx + "wishTpl/getWishTpl", {"tplId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改模板');
        	$("#entity-add-button").attr("name", "update");
            var $form = $('#entity-add');
            var entity = r.msg;
            showModal($form, entity);
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function showModal($form, entity) {
	$form.modal();
    $form.find("input[name='tplId']").val(entity.tplId);
    $form.find("input[name='partyId']").val(entity.partyId);
    $form.find("textarea[name='wishText']").text(entity.wishText);
    partyTreeSelect(entity.partyId);
}