function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的类型！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个类型！');
        return;
    }
    var entityId = selected[0].typeId;
    $.post(ctx + "helpType/getType", {"typeId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改类型');
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
    $form.find("input[name='typeId']").val(entity.typeId);
    $form.find("input[name='typeName']").val(entity.typeName);
    setStatus(entity.typeStatus);
}