var validator;
var $entityAddForm = $("#entity-add-form");

//开关控件
var $dueItem_label = $("#dueItemLabel");
var $dueItem_check = $("#dueItemCheck");
var $dueItem = $("input[name='dueItem']");
var $dueType_label = $("#dueTypeLabel");
var $dueType_check = $("#dueTypeCheck");
var $dueType = $("input[name='dueType']");
//党员多选
var $usersSelect = $entityAddForm.find("select[name='usersSelect']");
var $users = $entityAddForm.find("input[name='users']");
var usersSelect = new PartyMulSelect($usersSelect, $users, "user/getUsers", "userId", "userName");
//账号单选
var $accAddSelect = $entityAddForm.find("select[name='accId']");
var accAddSelect = new PartySelect($accAddSelect, "dueAccount/getAccounts", "accId", "accName");

function setDueItem(dueItem) {
	if (dueItem || dueItem == 2) {
		$dueItem_check.prop("checked", true);
		$dueItem_label.html('特殊党费');
	}
	if (!dueItem || dueItem == 1) {
		$dueItem_check.prop("checked", false);
	    $dueItem_label.html('党费');
	}
	$dueItem.val(dueItem);
}
function setDueType(dueType) {
	if (dueType || dueType == 2) {
		$dueType_check.prop("checked", true);
		$dueType_label.html('自由金额');
	}
	if (!dueType || dueType == 1) {
		$dueType_check.prop("checked", false);
	    $dueType_label.html('固定金额');
	}
	$dueType.val(dueType);
}

function dueItemTrigger(checked) {
	if (checked) {
    	$("#dueType").prop("hidden", false);
    } else {
    	$("#dueType").prop("hidden", true);
    	setDueType(1);//党费只能固定金额
    }
	$dueType_check.change();//触发dueTypeTrigger
}

function dueTypeTrigger(checked) {
	if (checked) {
    	$entityAddForm.find("input[name='due']").val(0);
    	$("#due").prop("hidden", true);//自由金额，金额设为0
    } else {
    	$entityAddForm.find("input[name='due']").val(null);
    	$("#due").prop("hidden", false);
    }
}

$(function () {
    validateRule();
    createPartyTree();
    
    $MB.calenders('input[name="endTime"]', false, true);
    
    $dueItem_check.change(function () {
        var checked = $(this).is(":checked");
        var dueItem = checked ? 2 : 1;
        setDueItem(dueItem);
        dueItemTrigger(checked);
    });
    $dueType_check.change(function () {
        var checked = $(this).is(":checked");
        var dueType = checked ? 2 : 1;
        setDueType(dueType);
        dueTypeTrigger(checked);
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
        /*if (name === "save") {
        	$("input:radio[name='status'][value='0']").prop("checked", true);//新建时不用验证状态，默认为待发布
        }*/
        validator = $entityAddForm.validate();
        var flag = validator.form();
        if (flag) {
        	if (name === "save") {
	            $.post(ctx + "due/add", $entityAddForm.serialize(), function (r) {
	                if (r.code === 0) {
	                    closeModal();
	                    refresh();
	                    $MB.n_success(r.msg);
	                } else $MB.n_danger(r.msg);
	            });
        	}
        	if (name === "update") {
        		$.post(ctx + "due/update", $entityAddForm.serialize(), function (r) {
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
	setDueItem(1);
	setDueType(1);
	$("#dueType").prop("hidden", true);
	$("#due").prop("hidden", false);
	
	editable();
	usersSelect.clean();
	
    $("#entity-add-modal-title").html('新增缴费');
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
                maxlength: 100
            },
            dueDesc: {
            	maxlength: 500
            },
            due: {
            	required: true,
            	digits: true,
            	min: 0
            },
            users: {
            	required: true
            },
            accId: {
            	required: true
            },
            endTime: {
            	required: true,
            	gtCurrent: true
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
        	title: {
                required: icon + "请输入缴费标题",
                maxlength: icon + "缴费标题长度少于100个字符"
            },
            dueDesc: {
            	maxlength: icon + "缴费说明长度少于200个字符"
            },
            due: {
            	required: icon + "请输入缴费金额",
            	digits: icon + "请输入非负整数",
            	min: icon + "请输入非负整数"
            },
        	endTime: {
        		required: icon + "请选择截止时间"
        	},
        	users: icon + "请选择缴费人员",
        	accId: icon + "请选择收费账户",
        	status: icon + "请选择状态",
        	partyId: icon + "请选择党支部",
        	
        }
    });
}

function ajaxGetVals(partyId) {
	accAddSelect.set(partyId);
	usersSelect.set(partyId);
}

/*function statusHidden(hidden) {
	$("#status").prop("hidden", hidden);
}*/