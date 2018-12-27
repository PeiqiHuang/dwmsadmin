var validator;
var $entityAddForm = $("#entity-add-form");

//开关控件
var $pushMsg_label = $("#pushMsgLabel");
var $pushMsg_check = $("#pushMsgCheck");
var $pushMsg = $("input[name='pushMsg']");
var $endMsg_label = $("#endMsgLabel");
var $endMsg_check = $("#endMsgCheck");
var $endMsg = $("input[name='endMsg']");
//签到负责人
var $previewerSelect = $entityAddForm.find("select[name='previewer']");
var previewerSelect = new PartySelect($previewerSelect, "user/getUsers", "userId", "userName");
//党员多选
var $usersSelect = $entityAddForm.find("select[name='usersSelect']");
var $users = $entityAddForm.find("input[name='users']");
var usersSelect = new PartyMulSelect($usersSelect, $users, "user/getUsers", "userId", "userName");

function setPushMsg(pushMsg) {
	if (pushMsg || pushMsg == 1) {
		$pushMsg_check.prop("checked", true);
	    $pushMsg_label.html('是');
	}
	if (!pushMsg || pushMsg == 0) {
		$pushMsg_check.prop("checked", false);
	    $pushMsg_label.html('否');
	}
	$pushMsg.val(pushMsg);
}
function setEndMsg(endMsg) {
	if (endMsg || endMsg == 1) {
		$endMsg_check.prop("checked", true);
		$endMsg_label.html('是');
	}
	if (!endMsg || endMsg == 0) {
		$endMsg_check.prop("checked", false);
		$endMsg_label.html('否');
	}
	$endMsg.val(endMsg);
}

$(function () {
    validateRule();
    createPartyTree();
    
    $MB.calenders('input[name="showTime"]', false, true);
    $MB.calenders('input[name="beginTime"]', false, true);
    $MB.calenders('input[name="endTime"]', false, true);

	$pushMsg_check.change(function () {
        var checked = $(this).is(":checked");
        var pushMsg = checked ? 1 : 0;
        setPushMsg(pushMsg);
    });
	
	$endMsg_check.change(function () {
		var checked = $(this).is(":checked");
		var endMsg = checked ? 1 : 0;
		setEndMsg(endMsg);
	});
	
	//显示答案时间
	$entityAddForm.find("input[name='showType']").change(function(){
		var hidden = this.value < 1;
		$("#showTime").prop("hidden", hidden);
		if (hidden) {
			$entityAddForm.find("input[name='showTime']").val("");
		}
	});
	
	//选党支部切换党员数据
    $('#partyTree').on("select_node.jstree", function (e, data) {
    	getParty();
    	var partyId = $("input[name='partyId']").val();
    	ajaxGetVals(partyId);
    })
    
    $("#entity-add .btn-save").click(function () {
    	var name = $(this).attr("name");
        getParty();
        if (name === "save") {
        	$("input:radio[name='status'][value='0']").prop("checked", true);//新建时不用验证状态，默认为待发布
        }
        validator = $entityAddForm.validate();
        var flag = validator.form();
        if (flag) {
        	if (name === "save") {
	            $.post(ctx + "exam/add", $entityAddForm.serialize(), function (r) {
	                if (r.code === 0) {
	                    closeModal();
	                    refresh();
	                    $MB.n_success(r.msg);
	                } else $MB.n_danger(r.msg);
	            });
        	}
        	if (name === "copy") {
        		$.post(ctx + "exam/copy", $entityAddForm.serialize(), function (r) {
        			if (r.code === 0) {
        				closeModal();
        				refresh();
        				$MB.n_success(r.msg);
        			} else $MB.n_danger(r.msg);
        		});
        	}
        	if (name === "update") {
        		$.post(ctx + "exam/update", $entityAddForm.serialize(), function (r) {
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
	setPushMsg(1);
	setEndMsg(1);
	
	editable();
	previewerSelect.clean();
	usersSelect.clean();
	
	$("#showTime").prop("hidden", true);
    $("#entity-add-modal-title").html('新增考试');
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
    refreshPartyTree();
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
        rules: {
        	examName: {
                required: true,
                maxlength: 200
            },
            examDesc: {
            	maxlength: 500
            },
            totalScore: {
            	required: true,
            	digits: true,
            	min: 1
            },
            passScore: {
            	required: true,
            	digits: true,
            	min: 1
            },
            timeLength: {
            	required: true,
            	digits: true,
            	min: 1
            },
            showType: {
            	required: true
            },
            previewer: {
            	required: true
            },
            users: {
            	required: true
            },
            beginTime: {
            	required: true,
            	date: true,
            	gtCurrent: true
            },
            endTime: {
            	required: true,
            	date: true,
            	gtCurrent: true,
            	compareTime: "input[name='beginTime']"
            },
            status: {
            	required: true
            },partyId: {
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
        	examName: {
                required: icon + "请输入试卷标题",
                maxlength: icon + "试卷标题长度少于200个字符"
            },
            examDesc: {
            	maxlength: icon + "试卷说明长度少于500个字符"
            },
            totalScore: {
            	required: icon + "请输入总分",
            	digits: icon + "请输入整数",
            	min: icon + "请输入大于0的整数"
            },
            passScore: {
            	required: icon + "请输入及格分数",
            	digits: icon + "请输入整数",
            	min: icon + "请输入大于0的整数"
            },
            timeLength: {
            	required: icon + "请输入考试时长",
            	digits: icon + "请输入整数",
            	min: icon + "请输入大于0的整数"
            },
        	beginTime: {
        		required: icon + "请选择开始时间"
        	},
        	endTime: {
        		required: icon + "请选择结束时间"
        	},
        	showType: icon + "请选择显示答案时间",
        	previewer: icon + "请选择签到负责人",
        	users: icon + "请选择考试人员",
        	status: icon + "请选择考试状态",
        	partyId: icon + "请选择党支部",
        	
        }
    });
}

function ajaxGetVals(partyId) {
	previewerSelect.set(partyId);
	usersSelect.set(partyId);
}

function statusHidden(hidden) {
	$("#status").prop("hidden", hidden);
}