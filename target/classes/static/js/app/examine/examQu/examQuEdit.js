function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的试题！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个试题！');
        return;
    }
    var entityId = selected[0].eqId;
    $.post(ctx + "examQu/getExamQu", {"eqId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改试题');
        	$("#entity-add-button").attr("name", "update");
            var $form = $('#entity-add');
            var entity = r.msg;
            showModal($form, entity);
            //已发布已开始的试题不能编辑
            //if (entity.progress > 0 && entity.status == 1) $("#entity-add-button").prop("hidden", true);
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function showModal($form, entity) {
	$form.modal();
	$form.find("input[name='eqId']").val(entity.eqId);
	$form.find("input[name='quId']").val(entity.quId);
    $form.find("input[name='examId']").val(entity.examId);
    $form.find("input[name='score']").val(entity.score);
    $form.find("input[name='quNo']").val(entity.quNo);
    $form.find("input[id='title']").val(entity.title);
    $form.find("input[id='answers']").val(entity.answers);
    $form.find("input[id='rightKey']").val(entity.rightKey);
    $form.find("input[id='quType']").val(entity.quType == 1 ? "单选题" : entity.quType == 2 ? "多选题" : entity.quType == 3 ? "填空题" : "简单题");
    hiddenQu(false);
    
}