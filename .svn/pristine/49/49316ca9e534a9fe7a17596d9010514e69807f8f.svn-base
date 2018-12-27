var validator;
var $entityAddForm = $("#entity-add-form");
//status控件
var $status_label = $("#statusLabel");
var $status_check = $("#statusCheck");
var $status = $("input[name='status']");

function setStatus(status) {
	if (status || status == 1) {
		$status_check.prop("checked", true);
	    $status_label.html('开启');
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
    
    //file upload
    $("input[name='wechat']").fileinput({
    	language: "zh",
    	showUpload: false, 
    	showCaption: false, 
    	dropZoneEnabled: false,
    	allowedFileExtensions: ['jpeg', 'jpg', 'gif', 'png', 'bmp']
	});
    $("input[name='alipay']").fileinput({
    	language: "zh",
    	showUpload: false, 
    	showCaption: false, 
    	dropZoneEnabled: false,
    	allowedFileExtensions: ['jpeg', 'jpg', 'gif', 'png', 'bmp']
    });
    
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
		            url : ctx + "dueAccount/add",
		            dataType : "json",
		            type : "post",
		            success : function (r) {
		            	if (r.code === 0) {
	        				closeModal();
	        				refresh();
	        				$MB.n_success(r.msg);
	        			} else $MB.n_danger(r.msg);
		            }
		        };
	    		$entityAddForm.ajaxSubmit(options);
        	}
        	if (name === "update") {
        		var options = {
        				url : ctx + "dueAccount/update",
        				dataType : "json",
        				type : "post",
        				success : function (r) {
        					if (r.code === 0) {
        						closeModal();
        						refresh();
        						$MB.n_success(r.msg);
        					} else $MB.n_danger(r.msg);
        				}
        		};
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
	
    $("#entity-add-modal-title").html('新增账户');
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
    refreshPartyTree();
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
        rules: {
        	accName: {
                required: true,
                maxlength: 500
            },
            payeeName: {
            	required: true,
            	maxlength: 100
            },
            payeeBank: {
            	required: true,
            	maxlength: 100
            },
            payeeAddress: {
            	required: true,
            	maxlength: 100
            },
            payeeAccount: {
            	required: true,
            	maxlength: 100,
            	number: true
            },
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
        	accName: {
                required: icon + "请输入账户名称",
                maxlength: icon + "账户名称长度少于500个字符"
            },
            payeeName: {
            	required: icon + "请输入收款方姓名",
            	maxlength: icon + "收款方姓名长度少于100个字符"
            },
            payeeBank: {
            	required: icon + "请输入收款方开户银行",
            	maxlength: icon + "收款方开户银行长度少于100个字符"
            },
            payeeAddress: {
            	required: icon + "请输入收款方开户地址",
            	maxlength: icon + "收款方开户地址长度少于100个字符"
            },
            payeeAccount: {
            	required: icon + "请输入收款方账号",
            	maxlength: icon + "收款方账号长度少于100个字符",
            	number: icon + "请输入正确银行账号"
            },
            due: {
            	required: icon + "请输入账户金额",
            	digits: icon + "请输入非负整数",
            	min: icon + "请输入非负整数"
            },
        	partyId: icon + "请选择党支部"
        	
        }
    });
}