var validator;
var $entityAddForm = $("#entity-add-form");

//开关控件
var $status_label = $("#statusLabel");
var $status_check = $("#statusCheck");
var $status = $("input[name='status']");

function setStatus(status) {
	if (status || status == 1) {
		$status_check.prop("checked", true);
	    $status_label.html('有效');
	}
	if (!status || status == 0) {
		$status_check.prop("checked", false);
	    $status_label.html('禁用');
	}
	$status.val(status);
}

//A B C...
function getAlpha(index) {
	return String.fromCharCode((65+index));
}
function removeBad(val) {
	//去掉分号、空格
	//return $.trim(val.replace(new RegExp(";","gm"), ""));
	return val.trim();
}

//清空选项答案
function clearQuType() {
	$("#singleItem").siblings().remove();
	$("#multipleItem").siblings().remove();
	$("#fillItem").siblings().remove();
	$("textarea[name='rightKey']").text("");
	$("input[name='maxNum']").val("");
}

function singleAdd() {
	var $copy = $("#singleItem").clone().removeAttr("id").prop("hidden", false);
	$("#singleItem").parent().append($copy);
	return $copy;
}

function multipleAdd() {
	var $copy = $("#multipleItem").clone().removeAttr("id").prop("hidden", false);
	$("#multipleItem").parent().append($copy);
	return $copy;
}

function fillAdd() {
	var $copy = $("#fillItem").clone().removeAttr("id").prop("hidden", false);
	$("#fillItem").parent().append($copy);
	return $copy;
}


$(function () {
    validateRule();
    createPartyTree();
    
	$status_check.change(function () {
        var checked = $(this).is(":checked");
        var status = checked ? 1 : 0;
        setStatus(status);
    });
	
	//切换题型
	$("input[type='radio'][name='quType']").change(function() {
		clearQuType();
		if (this.value == 1) {
			$("#single").prop("hidden", false);
			$("#multiple").prop("hidden", true);
			$("#fill").prop("hidden", true);
			$("#short").prop("hidden", true);
		} else if (this.value == 2) {
			$("#single").prop("hidden", true);
			$("#multiple").prop("hidden", false);
			$("#fill").prop("hidden", true);
			$("#short").prop("hidden", true);
		} else if (this.value == 3) {
			$("#single").prop("hidden", true);
			$("#multiple").prop("hidden", true);
			$("#fill").prop("hidden", false);
			$("#short").prop("hidden", true);
		} else if (this.value == 4) {
			$("#single").prop("hidden", true);
			$("#multiple").prop("hidden", true);
			$("#fill").prop("hidden", true);
			$("#short").prop("hidden", false);
		}
	})
	//单选
	$("#singleAdd").click(function() {
		singleAdd();
	})
	$(document).on("click", ".singleDel", function() {
		$(this).parent().remove();
	})
	//多选
	$("#multipleAdd").click(function() {
		multipleAdd();
	})
	$(document).on("click", ".multipleDel", function() {
		$(this).parent().remove();
	})
	//填空题
	$("#fillAdd").click(function() {
		fillAdd();
	})
	$(document).on("click", ".fillDel", function() {
		$(this).parent().remove();
	})
	
    $("#entity-add .btn-save").click(function () {
    	var name = $(this).attr("name");
        getParty();
        validator = $entityAddForm.validate();
        var flag = validator.form();
        var quType = $("input[type='radio'][name='quType']:checked").val();
        if (flag) {
        	if (quType == 1) {//单选验证
        		var answers = [];
        		var rightKeys = [];
        		$("#singleItem").siblings().find(":text").each(function(i){
        			var answer = removeBad(this.value);
        			if (answer == "") return;
        			answer =  getAlpha(i) + "|||" + answer;
        			answers.push(answer);
        			var checked = $(this).prev().find(":radio").is(":checked");
        			if(checked) {
        				rightKeys.push(answer);
        			}
        		})
        		if (answers.length < 1) {
        			$MB.n_danger("请输入单选题选项！");
        			return;
        		} else if(rightKeys.length != 1) {
        			$MB.n_danger("请勾选单选题答案！");
        			return;
        		} else {
        			$("input[name='answers']").val(answers.join("==="));
        			$("textarea[name='rightKey']").text(rightKeys[0]);
        		}
        	} else if (quType == 2) {//多选验证
        		var answers = [];
        		var rightKeys = [];
        		$("#multipleItem").siblings().find(":text").each(function(i){
        			var answer = removeBad(this.value);
        			if (answer == "") return;
        			answer =  getAlpha(i) + "|||" + answer;
        			answers.push(answer);
        			var checked = $(this).prev().find(":checkbox").is(":checked");
        			if(checked) {
        				rightKeys.push(answer);
        			}
        		})
        		if (answers.length < 1) {
        			$MB.n_danger("请输入多选题选项！");
        			return;
        		} else if(rightKeys.length < 1) {
        			$MB.n_danger("请勾选多选题答案！");
        			return;
        		} else {
        			$("input[name='answers']").val(answers.join("==="));
        			$("textarea[name='rightKey']").text(rightKeys.join("==="));
        		}
        	}  else if (quType == 3) {//填空验证
        		var rightKeys = [];
        		$("#fillItem").siblings().find(":text").each(function(){
        			var rightkey = removeBad(this.value);
        			if(rightkey == "") return;
        			rightKeys.push(rightkey);
        		})
        		if (rightKeys.length < 1) {
        			$MB.n_danger("请输入填空题答案！");
        			return;
        		} else {
        			$("textarea[name='rightKey']").text(rightKeys.join("==="));
        		}
        	}  else if (quType == 4) {//简答验证
        		var txt = $("textarea[name='rightKey']").val();
        		if ($.trim(txt) == "") {
        			$MB.n_danger("请输入正确答案！");
        			return;
        		}
        	} 
        	if (name === "save") {
	            $.post(ctx + "question/add", $entityAddForm.serialize(), function (r) {
	                if (r.code === 0) {
	                    closeModal();
	                    refresh();
	                    $MB.n_success(r.msg);
	                } else $MB.n_danger(r.msg);
	            });
        	}
        	if (name === "update") {
        		$.post(ctx + "question/update", $entityAddForm.serialize(), function (r) {
        			if (r.code === 0) {
        				closeModal();
        				refresh();
        				$MB.n_success(r.msg);
        			} else $MB.n_danger(r.msg);
        		});
        	}
        }
    });

    $("#entity-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
	$("#entity-add-button").attr("name", "save");
	setStatus(1);
	
	clearQuType();
	
    $("#entity-add-modal-title").html('新增题目');
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
    refreshPartyTree();
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
        rules: {
        	title: {
                required: true,
                maxlength: 200
            },
            analysis: {
            	maxlength: 1000
            },
            quType: {
            	required: true
            },
            /*rightKey: {
            	required: true,
            	maxlength: 1000
            },*/
            partyId: {
            	required: true
            }
        },
        errorPlacement: function (error, element) {
            if (element.is(":checkbox") || element.is(":radio")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        },
        messages: {
        	title: {
                required: icon + "请输入题目标题",
                maxlength: icon + "题目标题长度少于200个字符"
            },
            analysis: {
            	maxlength: icon + "答案解析长度少于1000个字符"
            },
            quType: icon + "请选择题目类型",
            //rightKey: icon + "正确答案长度少于2000个字符",
        	partyId: icon + "请选择党支部"
        }
    });
}