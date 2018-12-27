var validator;
var $entityAddForm = $("#entity-add-form");
$(function () {
    validateRule();
    
    $("#entity-add .btn-save").click(function () {
        validator = $entityAddForm.validate();
        var flag = validator.form();
        if (flag) {
            $.post(ctx + "examQuUser/judgeScore", $entityAddForm.serialize(), function (r) {
                if (r.code === 0) {
                    closeModal();
                    refresh();
                    $MB.n_success(r.msg);
                } else $MB.n_danger(r.msg);
            });
        }
    });

    $("#entity-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
        rules: {
        	equId: {
                required: true
            },
            score: {
            	required: true,
            	digits: true,
            	min: 1
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
        	equId: {
                required: icon + "试题ID不能为空"
            },
            score: {
            	required: icon + "请输入试题得分",
            	digits: icon + "请输入整数",
            	min: icon + "请输入大于0的整数"
            }
        }
    });
}