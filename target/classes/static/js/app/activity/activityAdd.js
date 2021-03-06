var validator;
var $entityAddForm = $("#entity-add-form");
var myEditor = null;//编辑器

//党员多选
var $usersSelect = $entityAddForm.find("select[name='usersSelect']");
var $users = $entityAddForm.find("input[name='users']");
var usersSelect = new PartyMulSelect($usersSelect, $users, "user/getUsers", "userId", "userName");


$(function () {
    validateRule();
    createPartyTree();
    
    $MB.calenders('input[name="beginTime"]', false, true);
    $MB.calenders('input[name="endTime"]', false, true);
    $MB.calenders('input[name="applyEndTime"]', false, true);
    
    //editor
    ClassicEditor
	.create(document.querySelector("#editor"), {
        language:"zh-cn",
        toolbar: ["heading","|","bold","italic","link","blockQuote","imageUpload","imageStyle:full","imageStyle:side","numberedList","bulletedList","insertTable","|","undo","redo"],
        //removePlugins: [ 'mediaEmbed' ],
        ckfinder: {
        	uploadUrl: "common/upload?subDir=activity" 
        }
    }) 
	.then(editor => { 
		//console.info(Array.from( editor.ui.componentFactory.names() ))
		myEditor = editor; 
	}) 
	.catch(error => { console.error(error); });
    
	//选党支部切换党员数据
    $('#partyTree').on("select_node.jstree", function (e, data) {
    	getParty();
    	var partyId = $("input[name='partyId']").val();
    	ajaxGetVals(partyId);
    })
    
    $("#entity-add .btn-save").click(function () {
    	var name = $(this).attr("name");
        getParty();
        /*if (name === "save") {
        	$("input:radio[name='status'][value='0']").prop("checked", true);//新建时不用验证状态，默认为待发布
        }*/
        validator = $entityAddForm.validate();
        var flag = validator.form();
        if (flag) {
        	//设置editor内容
        	$("textarea[name='content']").text(myEditor.getData());
        	if (name === "save") {
	            $.post(ctx + "activity/add", $entityAddForm.serialize(), function (r) {
	                if (r.code === 0) {
	                    closeModal();
	                    refresh();
	                    $MB.n_success(r.msg);
	                } else $MB.n_danger(r.msg);
	            });
        	}
        	if (name === "update") {
        		$.post(ctx + "activity/update", $entityAddForm.serialize(), function (r) {
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
	
	editable();
	usersSelect.clean();
	myEditor.setData("");
	
    $("#entity-add-modal-title").html('新增活动');
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
    refreshPartyTree();
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
        rules: {
        	actName: {
                required: true,
                maxlength: 200
            },
            actDesc: {
            	maxlength: 500
            },
            address: {
            	required: true,
            	maxlength: 200
            },
            users: {
            	required: true
            },
            beginTime: {
            	required: true,
            	date: true,
            	gtCurrent: true
            },
            endTime: {
            	required: true,
            	date: true,
            	gtCurrent: true,
            	compareTime: "input[name='beginTime']"
            },
            applyEndTime: {
            	required: true
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
        	actName: {
                required: icon + "请输入活动名称",
                maxlength: icon + "活动名称长度少于200个字符"
            },
            actDesc: {
            	maxlength: icon + "活动详情长度少于500个字符"
            },
            address: {
            	required: icon + "请输入活动地址",
            	maxlength: icon + "活动地址长度少于200个字符"
            },
            beginTime: {
        		required: icon + "请选择开始时间"
        	},
        	endTime: {
        		required: icon + "请选择结束时间"
        	},
        	applyEndTime: icon + "请选择报名截止时间",
        	users: icon + "请选择活动人员",
        	status: icon + "请选择状态",
        	partyId: icon + "请选择党支部",
        	
        }
    });
}

function ajaxGetVals(partyId) {
	usersSelect.set(partyId);
}

/*function statusHidden(hidden) {
	$("#status").prop("hidden", hidden);
}*/