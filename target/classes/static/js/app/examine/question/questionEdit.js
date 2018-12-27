function addEntity() {
	$('#entity-add').modal();
}

function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的题目！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个题目！');
        return;
    }
    var entityId = selected[0].quId;
    $.post(ctx + "question/getQuestion", {"quId": entityId}, function (r) {
        if (r.code === 0) {
        	$("#entity-add-modal-title").html('修改题目');
        	$("#entity-add-button").attr("name", "update");
            var $form = $('#entity-add');
            var entity = r.msg;
            showModal($form, entity);
            //已发布已开始的题目不能编辑
            //if (entity.progress > 0 && entity.status == 1) $("#entity-add-button").prop("hidden", true);
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function showModal($form, entity) {
	$form.modal();
    $form.find("input[name='quId']").val(entity.quId);
    $form.find("input[name='partyId']").val(entity.partyId);
    $form.find("input[name='title']").val(entity.title);
    $form.find("input[name='analysis']").val(entity.analysis);
    $form.find("input[name='rightKey']").val(entity.rightKey);
    //$("input:radio[name='quType'][value='" + entity.quType + "']").prop("checked", true);
    $("input:radio[name='quType'][value='" + entity.quType + "']").click();//触发选择类型
    setQuType(entity);
    
    setStatus(entity.status);
    // 设值party
    partyTreeSelect(entity.partyId);
    
}

function setQuType(entity) {
	if (entity.quType == 1) {
    	$.each(entity.answers.split("==="), function() {
    		$item = singleAdd();
    		var val = this;
    		if (val.trim() == entity.rightKey.trim()) {
    			$item.find(":radio").prop("checked", true);
    		}
    		val = val.substring(4);//去掉编号
    		$item.find(":text").val(val);
    		
    	})
    } else if (entity.quType == 2) {
    	$.each(entity.answers.split("==="), function() {
    		$item = multipleAdd();
    		var val = this;
    		$.each(entity.rightKey.split(";"), function() {
    			if (this.trim()  == val.trim() ) {
    				$item.find(":checkbox").prop("checked", true);
    			}
    		})
    		val = val.substring(4);//去掉编号
    		$item.find(":text").val(val);
    	})
    } else if (entity.quType == 3) {
    	$.each(entity.rightKey.split("==="), function() {
    		$item = fillAdd();
    		$item.find(":text").val(this);
    	})
    } else if (entity.quType == 4) {
    	$("textarea[name='rightKey']").text(entity.rightKey);
    	$("input[name='maxNum']").val(entity.maxNum);
    }
}