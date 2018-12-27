var validator;

var $entityAddForm = $("#entity-add-form");
var $fromUserSelect = $entityAddForm.find("select[name='fromUserId']");
var fromUserSelect = new PartySelect($fromUserSelect, "user/getUsers", "userId", "userName");
var $toUserSelect = $entityAddForm.find("select[name='toUserId']");
var toUserSelect = new PartySelect($toUserSelect, "user/getUsers", "userId", "userName");


//status控件
var $status_label = $("#statusLabel");
var $status_check = $("#statusCheck");
var $status = $("input[name='status']");

function setStatus(status) {
	if (status || status == 1) {
		$status_check.prop("checked", true);
	    $status_label.html('启用');
	}
	if (!status || status == 0) {
		$status_check.prop("checked", false);
	    $status_label.html('屏蔽');
	}
	$status.val(status);
}

$(function () {
    validateRule();
    createPartyTree();
    
    //选党支部切换分类/党员数据
    $('#partyTree').on("select_node.jstree", function (e, data) {
    	getParty();
    	var partyId = $("input[name='partyId']").val();
    	ajaxGetVals(partyId);
    })

	$status_check.change(function () {
        var checked = $(this).is(":checked");
        var status = checked ? 1 : 0;
        setStatus(status);
    });
    
    $("#entity-add .btn-save").click(function () {
    	var name = $(this).attr("name");
        getParty();
        validator = $entityAddForm.validate();
        var flag = validator.form();
        if (flag) {
        	if (name === "save") {
        		var options = {
		            url : ctx + "wish/add",
		            dataType : "json",
		            type : "post",
		            success : function (r) {
		            	$("#loadingModal").modal('hide');
		            	if (r.code === 0) {
	        				closeModal();
	        				refresh();
	        				$MB.n_success(r.msg);
	        			} else $MB.n_danger(r.msg);
		            }
		        };
        		$("#loadingModal").modal('show');
        		$entityAddForm.ajaxSubmit(options);
        	}
        	if (name === "update") {
        		var options = {
		            url : ctx + "wish/update",
		            dataType : "json",
		            type : "post",
		            success : function (r) {
		            	$("#loadingModal").modal('hide');
		            	if (r.code === 0) {
	        				closeModal();
	        				refresh();
	        				$MB.n_success(r.msg);
	        			} else $MB.n_danger(r.msg);
		            }
		        };
        		$("#loadingModal").modal('show');
        		$entityAddForm.ajaxSubmit(options);
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
	
	editable();
	fromUserSelect.clean();
	toUserSelect.clean();
	
    $("#entity-add-modal-title").html('新增祝福语');
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
    refreshPartyTree();
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
    	ignore: ".no-need",
        rules: {
        	wishText: {
                required: true,
                maxlength: 500
            },
            wishType: {
            	required: true
            },
            partyId: {
            	required: true
            },
            fromUserId: {
            	required: true
            },
            toUserId: {
            	required: true
            }
        },
        errorPlacement: function (error, element) {
        	if ($(element).attr("name") == "cover" 
        		|| $(element).attr("name") == "banner"
        		|| $(element).attr("name") == "file") {
        		error.appendTo(element.parent().parent().parent());
        	} else if (element.is(":checkbox") || element.is(":radio")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        },
        messages: {
        	wishText: {
                required: icon + "请输入祝福语",
                maxlength: icon + "祝福语长度少于500个字符"
            },
            wishType: icon + "请选择祝福类型",
        	partyId: icon + "请选择党支部",
        	fromUserId: icon + "请选择祝福人",
        	fromUserId: icon + "请选择生日党员"
        	
        }
    });
}


function ajaxGetVals(partyId) {
	fromUserSelect.set(partyId);
	toUserSelect.set(partyId);
}