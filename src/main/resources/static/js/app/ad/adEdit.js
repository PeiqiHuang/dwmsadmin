var $addForm = $('#entity-add');
function addEntity() {
	//statusHidden(true);
	$('#entity-add').modal();
}

function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的广告！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个广告！');
        return;
    }
    var entityId = selected[0].adId;
    $.post(ctx + "ad/getAdvert", {"adId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改广告');
        	$("#entity-add-button").attr("name", "update");
            var entity = r.msg;
            //statusHidden(false);
            showModal(entity);
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function showModal(entity) {
	$addForm.modal();
    $addForm.find("input[name='adId']").val(entity.adId);
    $addForm.find("input[name='partyId']").val(entity.partyId);
    $addForm.find("input[name='name']").val(entity.name);
    $addForm.find("input[name='summary']").val(entity.summary);
    $addForm.find("input[name='beginTime']").val(entity.beginTime);
    $addForm.find("input[name='endTime']").val(entity.endTime);
    $addForm.find("input[name='weight']").val(entity.weight);
    $addForm.find("input[name='action']").val(entity.action);
    setStatus(entity.status);
    adKeyAdd.setVal(entity.adKey);
    partyTreeSelect(entity.partyId);
    
    $("#cover").attr("src", entity.cover);
}