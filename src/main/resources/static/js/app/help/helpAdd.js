var validator;
var $entityAddForm = $("#entity-add-form");
var $infoTypeAdd = $entityAddForm.find("select[name='infoType']");
var infoTypeAdd = new Select($infoTypeAdd, "helpType/getTypes", "typeId", "typeName");
//status控件
var $status_label = $("#statusLabel");
var $status_check = $("#statusCheck");
var $status = $("input[name='infoStatus']");

function setStatus(status) {
	if (status || status == 1) {
		$status_check.prop("checked", true);
	    $status_label.html('正常');
	}
	if (!status || status == 0) {
		$status_check.prop("checked", false);
	    $status_label.html('禁用');
	}
	$status.val(status);
}

$(function () {
    validateRule();
    infoTypeAdd.set();
    
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
		            url : ctx + "help/add",
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
        				url : ctx + "help/update",
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
	$entityAddForm.find("textarea[name='text']").text("");
    $("#entity-add-modal-title").html('新增帮助');
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
        rules: {
        	title: {
                required: true,
                maxlength: 30
            },
            text: {
            	required: true,
            	maxlength: 500
            },
            infoType: {
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
                required: icon + "请输入标题",
                maxlength: icon + "标题长度少于30个字符"
            },
            text: {
            	maxlength: icon + "内容长度少于500个字符"
            },
            infoType: icon + "请选择类型"
        	
        }
    });
}