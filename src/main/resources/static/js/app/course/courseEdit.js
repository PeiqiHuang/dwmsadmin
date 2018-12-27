var $addForm = $('#entity-add');
function addEntity() {
	ajaxGetVals();
	$('#entity-add').modal();
}
function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的党课！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个党课！');
        return;
    }
    var entityId = selected[0].courseId;
    $.post(ctx + "course/getCourse", {"courseId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改党课');
        	$("#entity-add-button").attr("name", "update");
            var entity = r.msg;
            showModal(entity);
            if (entity.status == 1) noEdit();
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function noEdit() {
	$("#uploadFile").prop("hidden", true);
	$addForm.find("input[name='endTime']").prop("disabled", true);
	$addForm.find("input:radio[name='status']").prop("disabled", true);
	//usersSelect.disable();//这里禁用无效，第一次加载要在党支部选择，触发党员数据加载之后才禁用
	$addForm.find("input[name='courseType']").parent().prop("hidden", true);
	//$("#partyTree").jstree('disable_node',);
}

function editable() {
	$("#uploadFile").prop("hidden", false);
	$addForm.find("input[name='endTime']").prop("disabled", false);
	$addForm.find("input:radio[name='status']").prop("disabled", false);
	usersSelect.enable();
	$addForm.find("input[name='courseType']").parent().prop("hidden", false);
	//$("#partyTree").jstree('hide_checkboxes');
}

function showModal(entity) {
	$addForm.modal();
    $addForm.find("input[name='courseId']").val(entity.courseId);
    $addForm.find("input[name='partyId']").val(entity.partyId);
    $addForm.find("input[name='courseName']").val(entity.courseName);
    $addForm.find("input[name='courseDesc']").val(entity.courseDesc);
    $addForm.find("input[name='chapterNum']").val(entity.chapterNum);
    $("input:radio[name='status'][value='" + entity.status + "']").prop("checked", true);
    $addForm.find("input[name='endTime']").val(entity.endTime);
    setCourseType(entity.courseType);
    //分类联动设值,当分类select ajax获取之后自动设值为selected
    catAddSelect.selected = entity.categoryId;
    //参会党员
    usersSelect.selected = entity.userIds;
    if (entity.status == 1) //已发布的话禁用
    	usersSelect.select = false;
    
    if (!isAdmin) { // 超管时select_node.jstree事件设置值
    	ajaxGetVals(entity.partyId);
    }
    
    // 设值party会自动触发select_node.jstree事件
    partyTreeSelect(entity.partyId, entity.status == 1);
    
    //封面
    $("#coverImg").attr("src", entity.cover);
    //顶部图片
    $("#bannerImg").attr("src", entity.banner);
    //上传文件
    $("#courseFile").attr("href", entity.filePath);
    if (!!entity.filePath) {
    	$("#courseFile").text(entity.courseName);
    }
}