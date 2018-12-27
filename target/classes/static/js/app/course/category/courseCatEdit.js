function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的分类！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个分类！');
        return;
    }
    var entityId = selected[0].categoryId;
    $.post(ctx + "courseCat/getCourseCat", {"categoryId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改分类');
        	$("#entity-add-button").attr("name", "update");
            var $form = $('#entity-add');
            var entity = r.msg;
            showModal($form, entity);
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function showModal($form, entity) {
	$form.modal();
    $form.find("input[name='categoryId']").val(entity.categoryId);
    $form.find("input[name='partyId']").val(entity.partyId);
    $form.find("input[name='categoryName']").val(entity.categoryName);
    $form.find("input[name='weight']").val(entity.weight);
    setStatus(entity.status);
    partyTreeSelect(entity.partyId);
}