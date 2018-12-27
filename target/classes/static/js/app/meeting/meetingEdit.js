function addEntity() {
	ajaxGetVals();
	$('#entity-add').modal();
}

function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的会议！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个会议！');
        return;
    }
    var entityId = selected[0].mtgId;
    $.post(ctx + "meeting/getMeeting", {"mtgId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改会议');
        	$("#entity-add-button").attr("name", "update");
            var $form = $('#entity-add');
            var entity = r.msg;
            showModal($form, entity);
            //已发布已开始的会议不能编辑
            if (entity.progress > 0 && entity.status == 1) $("#entity-add-button").prop("hidden", true);
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function copyEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
	var selected_length = selected.length;
	if (!selected_length) {
		$MB.n_warning('请勾选需要复制的会议！');
		return;
	}
	if (selected_length > 1) {
		$MB.n_warning('一次只能复制一个会议！');
		return;
	}
	var entityId = selected[0].mtgId;
	$.post(ctx + "meeting/getMeeting", {"mtgId": entityId}, function (r) {
		if (r.code === 0) {
			$("#entity-add-modal-title").html('复制会议');
			$("#entity-add-button").attr("name", "save");//保存相当于新建
			var $form = $('#entity-add');
			var entity = r.msg;
			entity.mtgId = '';
			entity.status = 0; //复制的默认待发布
			showModal($form, entity);
		} else {
			$MB.n_danger(r.msg);
		}
	});
}

function showModal($form, entity) {
	$form.modal();
    $form.find("input[name='mtgId']").val(entity.mtgId);
    $form.find("input[name='partyId']").val(entity.partyId);
    $form.find("input[name='mtgName']").val(entity.mtgName);
    $form.find("input[name='mtgTitle']").val(entity.mtgTitle);
    $form.find("input[name='mtgDesc']").val(entity.mtgDesc);
    $form.find("input[name='address']").val(entity.address);
    $("input:radio[name='status'][value='" + entity.status + "']").prop("checked", true);
    $form.find("input[name='beginTime']").val(entity.beginTime);
    $form.find("input[name='endTime']").val(entity.endTime);
    setRemind(entity.remind);
    setSignIn(entity.signIn);
    setPushMsg(entity.pushMsg);
    setSignHelp(entity.signHelp);
    //signer,recorder,hoster
    signerSelect.selected = entity.signer;
    recorderSelect.selected = entity.recorder;
    hosterSelect.selected = entity.hoster;
    //参会党员
    usersSelect.selected = entity.userIds;
    if (!isAdmin) { // 超管时select_node.jstree事件设置值
    	ajaxGetVals(entity.partyId);
    }
    // 设值party会自动触发select_node.jstree事件
    partyTreeSelect(entity.partyId);
    
}