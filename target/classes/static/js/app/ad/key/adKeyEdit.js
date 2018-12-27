var $addForm = $('#entity-add');

function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的广告位置！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个广告位置！');
        return;
    }
    var entityId = selected[0].adKey;
    $.post(ctx + "adKey/getAdKey", {"adKey": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改广告位置');
        	$("#entity-add-button").attr("name", "update");
            var entity = r.msg;
            showModal(entity);
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function showModal(entity) {
	$addForm.modal();
    $addForm.find("input[name='adKey']").val(entity.adKey);
    $addForm.find("input[name='keyName']").val(entity.keyName);
    setStatus(entity.status);
}