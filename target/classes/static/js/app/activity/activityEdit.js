var $addForm = $('#entity-add');
function addEntity() {
	ajaxGetVals();
	//statusHidden(true);
	$('#entity-add').modal();
}

function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的活动！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个活动！');
        return;
    }
    var entityId = selected[0].actId;
    $.post(ctx + "activity/getActivity", {"actId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改活动');
        	$("#entity-add-button").attr("name", "update");
            var entity = r.msg;
            //statusHidden(false);
            showModal(entity);
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function noEdit() {
	toggleEdit(true);
}
function toggleEdit(hidden) {
	$addForm.find("input[name='beginTime']").prop("disabled", hidden);
	$addForm.find("input[name='endTime']").prop("disabled", hidden);
	$addForm.find("input[name='applyEndTime']").prop("disabled", hidden);
	$addForm.find("input:radio[name='status']").prop("disabled", hidden);
}

function editable() {
	usersSelect.enable();
	toggleEdit(false);
}

function showModal(entity) {
	var forbidden = (entity.status == 1);//已发布的话禁止修改
	$addForm.modal();
    $addForm.find("input[name='actId']").val(entity.actId);
    $addForm.find("input[name='partyId']").val(entity.partyId);
    $addForm.find("input[name='actName']").val(entity.actName);
    $addForm.find("input[name='actDesc']").val(entity.actDesc);
    $addForm.find("input[name='address']").val(entity.address);
    $("input:radio[name='status'][value='" + entity.status + "']").prop("checked", true);
    $addForm.find("input[name='beginTime']").val(entity.beginTime);
    $addForm.find("input[name='endTime']").val(entity.endTime);
    $addForm.find("input[name='applyEndTime']").val(entity.applyEndTime);
    //编辑器
    myEditor.setData(entity.content);
    //参会党员
    usersSelect.selected = entity.userIds;
    if (forbidden) {
    	usersSelect.select = false;
    }
    if (!isAdmin) { // 超管时select_node.jstree事件设置值
    	ajaxGetVals(entity.partyId);
    }
    // 设值party会自动触发select_node.jstree事件
    partyTreeSelect(entity.partyId, forbidden);
    
    if (forbidden) {
    	noEdit();
    }
}