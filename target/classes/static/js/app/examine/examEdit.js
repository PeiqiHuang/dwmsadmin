var $addForm = $('#entity-add');
function addEntity() {
	ajaxGetVals();
	statusHidden(true);
	$('#entity-add').modal();
}

function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的考试！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个考试！');
        return;
    }
    var entityId = selected[0].examId;
    $.post(ctx + "exam/getExam", {"examId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改考试');
        	$("#entity-add-button").attr("name", "update");
            var entity = r.msg;
            statusHidden(false);
            showModal(entity);
            if (entity.status == 1) noEdit();
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function noEdit() {
	toggleEdit(true);
}
function toggleEdit(hidden) {
	$addForm.find("input[name='totalScore']").prop("disabled", hidden);
	$addForm.find("input[name='passScore']").prop("disabled", hidden);
	$addForm.find("input[name='timeLength']").prop("disabled", hidden);
	$addForm.find("input[name='beginTime']").prop("disabled", hidden);
	$addForm.find("input[name='endTime']").prop("disabled", hidden);
	$addForm.find("input:radio[name='showType']").prop("disabled", hidden);
	$addForm.find("input[name='pushMsg']").parent().prop("hidden", hidden);
	$addForm.find("input[name='endMsg']").parent().prop("hidden", hidden);
	$addForm.find("input:radio[name='status']").prop("disabled", hidden);
}

function editable() {
	usersSelect.enable();
	previewerSelect.enable();
	toggleEdit(false);
}

function copyEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
	var selected_length = selected.length;
	if (!selected_length) {
		$MB.n_warning('请勾选需要复制的考试！');
		return;
	}
	if (selected_length > 1) {
		$MB.n_warning('一次只能复制一个考试！');
		return;
	}
	var entityId = selected[0].examId;
	$.post(ctx + "exam/getExam", {"examId": entityId}, function (r) {
		if (r.code === 0) {
			$("#entity-add-modal-title").html('复制考试');
			$("#entity-add-button").attr("name", "copy");
			var entity = r.msg;
			entity.examId = '';
			entity.status = 0; //复制的默认待发布
			statusHidden(true);
			showModal(entity, true);
		} else {
			$MB.n_danger(r.msg);
		}
	});
}

function showModal(entity, copy) {
	var forbidden = !copy && entity.status == 1;//已发布的话禁用
	$addForm.modal();
    $addForm.find("input[name='examId']").val(entity.examId);
    $addForm.find("input[name='partyId']").val(entity.partyId);
    $addForm.find("input[name='examName']").val(entity.examName);
    $addForm.find("input[name='examDesc']").val(entity.examDesc);
    $addForm.find("input[name='totalScore']").val(entity.totalScore);
    $addForm.find("input[name='passScore']").val(entity.passScore);
    $addForm.find("input[name='timeLength']").val(entity.timeLength);
    $addForm.find("input[name='quNum']").val(entity.quNum);
    $("input:radio[name='showType'][value='" + entity.showType + "']").prop("checked", true);
    $addForm.find("input[name='showTime']").val(entity.showTime);
    if (entity.showType == 1) $("#showTime").prop("hidden", false);
    $("input:radio[name='status'][value='" + entity.status + "']").prop("checked", true);
    $addForm.find("input[name='beginTime']").val(entity.beginTime);
    $addForm.find("input[name='endTime']").val(entity.endTime);
    setPushMsg(entity.pushMsg);
    setEndMsg(entity.endMsg);
    //previewer
    previewerSelect.selected = entity.previewer;
    //参会党员
    usersSelect.selected = entity.userIds;
    if (forbidden) {
    	previewerSelect.select = false;
    	usersSelect.select = false;
    }
    if (!isAdmin) { // 超管时select_node.jstree事件设置值
    	ajaxGetVals(entity.partyId);
    }
    // 设值party会自动触发select_node.jstree事件
    partyTreeSelect(entity.partyId, forbidden);
    
}