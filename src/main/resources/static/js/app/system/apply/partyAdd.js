var validator;
var $entityAddForm = $("#entity-add-form");

$(function () {
    validateRule();
    createEntityTree();

    $("#entity-add .btn-save").click(function () {
        getEntity();
        validator = $entityAddForm.validate();
        var flag = validator.form();
        if (flag) {
            $.post(ctx + "apply/pass", $entityAddForm.serialize(), function (r) {
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

function updateEntity() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要审核通过的入驻申请！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能审核通过一个入驻申请！');
        return;
    }
    var entityId = selected[0].applyId;
    $.post(ctx + "apply/getApply", {"applyId": entityId}, function (r) {
        if (r.code === 0) {
            var $form = $('#entity-add');
            var $entityTree = $('#entityTree');
            $form.modal();
            var entity = r.msg;
            $form.find("input[name='partyName']").val(entity.partyName);
            $form.find("input[name='applyId']").val(entity.applyId);
            $form.find("input[name='address']").val(entity.address);
            $form.find("input[name='contract']").val(entity.contract);
            $form.find("input[name='mobile']").val(entity.mobile);
            $form.find("input[name='email']").val(entity.email);
            /*$entityTree.jstree('select_node', entity.parentId, true);
            $entityTree.jstree('disable_node', entity.partyId);*/
        } else {
            $MB.n_danger(r.msg);
        }
    });
}

function closeModal() {
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
                            return null;
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