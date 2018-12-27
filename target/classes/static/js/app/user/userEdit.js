function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要审核通过的入驻申请！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能审核通过一个入驻申请！');
        return;
    }
    var entityId = selected[0].userId;
    $.post(ctx + "user/getUser", {"userId": entityId}, function (r) {
        if (r.code === 0) {
            var $form = $('#entity-add');
            $("#entity-add-modal-title").html('修改党员');
            $("#entity-add-button").attr("name", "update");
            $form.modal();
            var entity = r.msg;
            $form.find("input[name='userId']").val(entity.userId);
            $form.find("input[name='userName']").val(entity.userName);
            $form.find("input[name='mobile']").val(entity.mobile);
            $("input:radio[name='gender'][value='" + entity.gender + "']").prop("checked", true);
            $("input:radio[name='partyType'][value='" + entity.partyType + "']").prop("checked", true);
            $("input:radio[name='workStatus'][value='" + entity.workStatus + "']").prop("checked", true);
            $("input:radio[name='partyPosts'][value='" + entity.partyPosts + "']").prop("checked", true);
            //$form.find("input[name='partyPosts']").val(entity.partyPosts);
            $form.find("input[name='probationaryTime']").val(entity.probationaryTime);
            $form.find("input[name='fullTime']").val(entity.fullTime);
            
        	$("#avatar").attr("src", entity.avatar);
            
            setStatus(entity.status);
            partyTreeSelect(entity.partyId);
        } else {
            $MB.n_danger(r.msg);
        }
    });
}