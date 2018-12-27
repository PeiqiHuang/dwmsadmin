var $addForm = $('#entity-add');
function addEntity() {
	//statusHidden(true);
	$('#entity-add').modal();
}

function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的通知！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个通知！');
        return;
    }
    var entityId = selected[0].ntiId;
    $.post(ctx + "notice/getNotice", {"ntiId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改通知');
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
	$addForm.find("input:radio[name='status']").prop("disabled", hidden);
}

function editable() {
	toggleEdit(false);
}

function showModal(entity) {
	var forbidden = (entity.status == 1);//已发布的话禁止修改
	$addForm.modal();
    $addForm.find("input[name='ntiId']").val(entity.ntiId);
    $addForm.find("input[name='partyId']").val(entity.partyId);
    $addForm.find("input[name='title']").val(entity.title);
    $addForm.find("input[name='subTitle']").val(entity.subTitle);
    $("input:radio[name='status'][value='" + entity.status + "']").prop("checked", true);
    setType(entity.type);
    //编辑器
    myEditor.setData(entity.content);
    // 设值party会自动触发select_node.jstree事件
    partyTreeSelect(entity.partyId, forbidden);
    
    if (entity.type == 2) {
    	$("#coverImg").attr("src", entity.cover);
    }
    
    if (forbidden) {
    	noEdit();
    }
}