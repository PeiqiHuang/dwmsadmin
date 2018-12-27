var $addForm = $('#entity-add');
function addEntity() {
	ajaxGetVals();
	$('#entity-add').modal();
}
function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的祝福语！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个祝福语！');
        return;
    }
    var entityId = selected[0].wishId;
    $.post(ctx + "wish/getWish", {"wishId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改祝福语');
        	$("#entity-add-button").attr("name", "update");
            var entity = r.msg;
            showModal(entity);
            //if (entity.status == 1) noEdit();
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function noEdit() {
	
}

function editable() {
	fromUserSelect.enable();
	toUserSelect.enable();
}

function showModal(entity) {
	$addForm.modal();
    $addForm.find("input[name='wishId']").val(entity.wishId);
    //$addForm.find("input[name='partyId']").val(entity.partyId);
    $addForm.find("textarea[name='wishText']").text(entity.wishText);
    $("input:radio[name='wishType'][value='" + entity.wishType + "']").prop("checked", true);
    setStatus(entity.status);
    //联动设值,当select ajax获取之后自动设值为selected
    fromUserSelect.selected = entity.fromUserId;
    toUserSelect.selected = entity.toUserId;
    //更新禁止选择
	fromUserSelect.select = false;
	toUserSelect.select = false;
    
    if (!isAdmin) { // 超管时select_node.jstree事件设置值
    	ajaxGetVals(entity.partyId);
    }
    
    // 设值party会自动触发select_node.jstree事件
    partyTreeSelect(entity.partyId, true);
   
}