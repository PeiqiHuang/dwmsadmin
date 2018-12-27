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
        $MB.n_warning('请勾选需要修改的缴费！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个缴费！');
        return;
    }
    var entityId = selected[0].dueId;
    $.post(ctx + "due/getDue", {"dueId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改缴费');
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
	$addForm.find("input[name='due']").prop("disabled", hidden);
	$addForm.find("input[name='endTime']").prop("disabled", hidden);
	$addForm.find("input[name='dueItem']").parent().prop("hidden", hidden);
	$addForm.find("input[name='dueType']").parent().prop("hidden", hidden);
	$addForm.find("input:radio[name='status']").prop("disabled", hidden);
}

function editable() {
	usersSelect.enable();
	toggleEdit(false);
}

function showModal(entity) {
	var forbidden = (entity.status == 1);//已发布的话禁止修改
	$addForm.modal();
    $addForm.find("input[name='dueId']").val(entity.dueId);
    $addForm.find("input[name='partyId']").val(entity.partyId);
    $addForm.find("input[name='title']").val(entity.title);
    $addForm.find("input[name='dueDesc']").val(entity.dueDesc);
    $("input:radio[name='status'][value='" + entity.status + "']").prop("checked", true);
    $addForm.find("input[name='endTime']").val(entity.endTime);
    //1.缴费项目
    setDueItem(entity.dueItem);
    dueItemTrigger(entity.dueItem == 2);
    //2.缴费类型
    setDueType(entity.dueType);
    dueTypeTrigger(entity.dueType == 2);
    //3.缴费金额
    $addForm.find("input[name='due']").val(entity.due);
    //参会党员
    usersSelect.selected = entity.userIds;
    //账号
    accAddSelect.selected = entity.accId;
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