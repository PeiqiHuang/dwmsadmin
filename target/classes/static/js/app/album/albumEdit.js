var $addForm = $('#entity-add');
function addEntity() {
	$('#entity-add').modal();
}

function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的相册！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个相册！');
        return;
    }
    var entityId = selected[0].albumId;
    $.post(ctx + "album/getAlbum", {"albumId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-albumName").html('修改相册');
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
    $addForm.find("input[name='albumId']").val(entity.albumId);
    $addForm.find("input[name='partyId']").val(entity.partyId);
    $addForm.find("input[name='albumName']").val(entity.albumName);
    $addForm.find("textarea[name='albumDesc']").text(entity.albumDesc);
    $("input:radio[name='status'][value='" + entity.status + "']").prop("checked", true);
    
    partyTreeSelect(entity.partyId);
}