var validator;
var $newsAddForm = $("#news-add-form");

$(function () {
    validateRule();

    $("#news-add .btn-save").click(function () {
        var name = $(this).attr("name");
        validator = $newsAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "news/add", $newsAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "news/update", $newsAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
        }
    });

    $("#news-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#news-add-button").attr("name", "save");
    $("#news-add-modal-title").html('新增新闻');
    validator.resetForm();
    $MB.closeAndRestModal("news-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $newsAddForm.validate({
        rules: {
            title: {
                required: true,
                maxlength: 100
            },
            status: {
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
                required: icon + "请输入新闻标题",
                maxlength: icon + "新闻标题最大长度100个字符"
            },
            status: {
                required: icon + "请选择状态"
            }
        }
    });
}
