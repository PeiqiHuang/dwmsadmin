var validator;
var $entityAddForm = $("#entity-add-form");

// status控件
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
    createEntityTree();
    
    $status_check.change(function () {
        var checked = $(this).is(":checked");
        var status = checked ? 1 : 0;
        setStatus(status);
    });

    $("#entity-add .btn-save").click(function () {
        var name = $(this).attr("name");
        getEntity();
        validator = $entityAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "party/add", $entityAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "party/update", $entityAddForm.serialize(), function (r) {
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
    $("#entity-add-modal-title").html('新增党支部');
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
    $MB.refreshJsTree("entityTree", createEntityTree());
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
        rules: {
            partyName: {
                required: true,
                maxlength: 100,
                remote: {
                    url: "party/checkPartyName",
                    type: "get",
                    dataType: "json",
                    data: {
                        partyName: function () {
                            return $("input[name='partyName']").val().trim();
                        },
                        oldPartyName: function () {
                            return $("input[name='oldPartyName']").val().trim();
                        }
                    }
                }
            },
            email: {
                email: true
            },
            mobile: {
                checkPhone: true
            },
            address: {
            	maxlength: 500,
            }
        },
        messages: {
            partyName: {
                required: icon + "请输入党支部名称",
                minlength: icon + "党支部名称长度少于100个字符",
                remote: icon + "该党支部名称已经存在"
            }
        }
    });
}

function createEntityTree() {
    $.post(ctx + "party/tree", {}, function (r) {
        if (r.code === 0) {
            var data = r.msg;
            $('#entityTree').jstree({
                "core": {
                    'data': data.children,
                    'multiple': false
                },
                "state": {
                    "disabled": true
                },
                "checkbox": {
                    "three_state": false
                },
                "plugins": ["wholerow", "checkbox"]
            });
        } else {
            $MB.n_danger(r.msg);
        }
    })

}

function getEntity() {
    var ref = $('#entityTree').jstree(true);
    var entityIds = ref.get_checked();
    $("[name='parentId']").val(entityIds[0]);
}