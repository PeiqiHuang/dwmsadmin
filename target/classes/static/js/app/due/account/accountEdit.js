var $addForm = $('#entity-add');

function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的账户！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个账户！');
        return;
    }
    var entityId = selected[0].accId;
    $.post(ctx + "dueAccount/getAccount", {"accId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改账户');
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
    $addForm.find("input[name='accId']").val(entity.accId);
    $addForm.find("input[name='partyId']").val(entity.partyId);
    $addForm.find("input[name='accName']").val(entity.accName);
    $addForm.find("input[name='payeeName']").val(entity.payeeName);
    $addForm.find("input[name='payeeBank']").val(entity.payeeBank);
    $addForm.find("input[name='payeeAddress']").val(entity.payeeAddress);
    $addForm.find("input[name='payeeAccount']").val(entity.payeeAccount);
    $addForm.find("textarea[name='remark']").text(entity.remark);
    $addForm.find("textarea[name='tips']").text(entity.tips);
    setStatus(entity.status);
    // 设值party会自动触发select_node.jstree事件
    partyTreeSelect(entity.partyId);
    
    $("#wechat").attr("src", entity.wechatFilePath);
    $("#alipay").attr("src", entity.alipayFilePath);
}