var validator;
var $entityAddForm = $("#entity-add-form");

$(function () {
    validateRule();
    createPartyTree();
    
    $("#entity-add .btn-save").click(function () {
    	var name = $(this).attr("name");
        getParty();
        validator = $entityAddForm.validate();
        var flag = validator.form();
        if (flag) {
        	if (name === "save") {
	            $.post(ctx + "album/add", $entityAddForm.serialize(), function (r) {
	                if (r.code === 0) {
	                    closeModal();
	                    refresh();
	                    $MB.n_success(r.msg);
	                } else $MB.n_danger(r.msg);
	            });
        	}
        	if (name === "update") {
        		$.post(ctx + "album/update", $entityAddForm.serialize(), function (r) {
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
    $("#entity-add-modal-albumName").html('新增相册');
    $("textarea[name='albumDesc']").text("");
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
    refreshPartyTree();
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
        rules: {
        	albumName: {
                required: true,
                maxlength: 200
            },
            albumDesc: {
            	maxlength: 500
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
        	albumName: {
                required: icon + "请输入相册名称",
                maxlength: icon + "相册名称长度少于200个字符"
            },
            albumDesc: {
            	maxlength: icon + "相册描述长度少于500个字符"
            },
        	status: icon + "请选择状态",
        	partyId: icon + "请选择党支部"
        }
    });
}