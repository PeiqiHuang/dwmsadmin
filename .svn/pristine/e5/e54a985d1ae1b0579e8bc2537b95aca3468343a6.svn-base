var validator;
var $entityAddForm = $("#entity-add-form");
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
	    $status_label.html('禁用');
	}
	$status.val(status);
}

$(function () {
    validateRule();
    
    $status_check.change(function () {
        var checked = $(this).is(":checked");
        var status = checked ? 1 : 0;
        setStatus(status);
    });
    
    $("#entity-add .btn-save").click(function () {
    	var name = $(this).attr("name");
        validator = $entityAddForm.validate();
        var flag = validator.form();
        if (flag) {
        	if (name === "save") {
	            var options = {
		            url : ctx + "adKey/add",
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
        				url : ctx + "adKey/update",
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
	
    $("#entity-add-modal-title").html('新增广告位置');
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
        rules: {
        	adKey: {
                required: true,
                maxlength: 30
            },
            keyName: {
            	required: true,
            	maxlength: 30
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
        	adKey: {
                required: icon + "请输入广告位置key",
                maxlength: icon + "key长度少于30个字符"
            },
            keyName: {
            	required: icon + "请输入广告位置名称",
            	maxlength: icon + "名称长度少于30个字符"
            }
        }
    });
}