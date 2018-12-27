var validator;
var $entityAddForm = $("#entity-add-form");

//开关控件
var $status_label = $("#statusLabel");
var $status_check = $("#statusCheck");
var $status = $("input[name='status']");

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
    createPartyTree();
    
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
                $.post(ctx + "courseCat/add", $entityAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });        		
        	}
        	if (name === "update") {
        		$.post(ctx + "courseCat/update", $entityAddForm.serialize(), function (r) {
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
    $("#entity-add-modal-title").html('新增分类');
    $("#entity-add-button").prop("hidden", false);
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
    refreshPartyTree();
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
    	ignore: ".no-need",
        rules: {
        	categoryName: {
                required: true,
                maxlength: 100
            },
            weight: {
            	required: true,
            	digits: true,
            	min: 1
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
        	categoryName: {
                required: icon + "请输入名称",
                maxlength: icon + "分类名称长度少于100个字符"
            },
            weight: {
            	required: icon + "请输入权重",
            	digits: icon + "请输入整数",
            	min: icon + "请输入大于0的整数"
            },
        	partyId: icon + "请选择党支部"
        }
    });
}