var $addForm = $('#entity-add');
function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的帮助！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个帮助！');
        return;
    }
    var entityId = selected[0].infoId;
    $.post(ctx + "help/getHelp", {"infoId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改帮助');
        	$("#entity-add-button").attr("name", "update");
            var entity = r.msg;
            showModal(entity);
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function showModal(entity) {
	var forbidden = (entity.infoStatus == 1);//已发布的话禁止修改
	$addForm.modal();
    $addForm.find("input[name='infoId']").val(entity.infoId);
    $addForm.find("input[name='title']").val(entity.title);
    $addForm.find("textarea[name='text']").text(entity.text);
    setStatus(entity.infoStatus);
    infoTypeAdd.setVal(entity.infoType);
}