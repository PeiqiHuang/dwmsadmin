var validator;
var $entityAddForm = $("#entity-add-form");
var $parent = $entityAddForm.find("input[name='parentId']");
//类型控件
var $type_label = $("#typeLabel");
var $type_check = $("#typeCheck");
var $type = $("input[name='chapterType']");

function setType(type) {
	if (type || type == 1) {
		$type_check.prop("checked", false);
	    $type_label.html('章');
	}
	if (!type || type == 2) {
		$type_check.prop("checked", true);
	    $type_label.html('节');
	}
	$type.val(type);
}

$(function () {
    validateRule();
    createEntityTree();
    
    $type_check.change(function () {
        var checked = $(this).is(":checked");
        var type = checked ? 2 : 1;
        setType(type);
        //类型是节才能选择上一级
        $("#parent").prop("hidden", !checked);
    });
    
    $("#entity-add .btn-save").click(function () {
        var name = $(this).attr("name");
        getEntity();
        //选择类型是章不用验证选择上一级
        if ($type.val() == 1) {
        	$parent.addClass("no-need");
        } else {
        	$parent.removeClass("no-need");
        }
        validator = $entityAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "courseChapter/add", $entityAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        refresh();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "courseChapter/update", $entityAddForm.serialize(), function (r) {
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
    setType(1);
    $("#entity-add-modal-title").html('新增章节');
    
    //上级
    $("#parent").prop("hidden", true);
    //类型
    $type.parent().prop("hidden", false);
    
    $parent.removeClass("no-need");
    validator.resetForm();
    $entityAddForm.find("textarea").text("");
    $MB.closeAndRestModal("entity-add");
    $MB.refreshJsTree("entityTree", createEntityTree());
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
    	ignore: ".no-need",
        rules: {
        	chapterTitle: {
                required: true,
                maxlength: 100
            },
            chapterNo: {
            	required: true,
            	digits: true,
            	min: 1
            },
            parentId: {
            	required: true
            },
            content: {
            	required: true
            },
            timeLength: {
            	required: true,
            	digits: true,
            	min: 1
            }
        },
        messages: {
            partyName: {
                required: icon + "请输入章节名称",
                minlength: icon + "章节名称长度少于100个字符"
            },
            chapterNo: {
            	required: icon + "请输入章节序号",
            	digits: icon + "请输入整数",
            	min: icon + "请输入大于0的整数"
            },
            parentId: {
            	required: icon + "请选择所属章"
            },
            content: {
            	required: icon + "请输入内容"
            },
            timeLength: {
            	required: icon + "请输入时长",
            	digits: icon + "请输入整数",
            	min: icon + "请输入大于0的整数"
            }
        }
    });
}

function createEntityTree() {
    $.post(ctx + "courseChapter/tree", {}, function (r) {
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