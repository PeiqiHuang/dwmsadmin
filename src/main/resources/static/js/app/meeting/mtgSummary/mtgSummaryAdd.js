var validator;
var $entityAddForm = $("#entity-add-form");
var myEditor = null;//编辑器
var partyId = $entityAddForm.find("input[name='partyId']").val();

//status控件
var $status_label = $("#statusLabel");
var $status_check = $("#statusCheck");
var $status = $("input[name='status']");

function setStatus(status) {
	if (status || status == 1) {
		$status_check.prop("checked", true);
	    $status_label.html('已发布');
	}
	if (!status || status == 0) {
		$status_check.prop("checked", false);
	    $status_label.html('未发布');
	}
	$status.val(status);
}

//党员多选
var $usersSelect = $entityAddForm.find("select[name='usersSelect']");
var $users = $entityAddForm.find("input[name='users']");
var usersSelect = new PartyMulSelect($usersSelect, $users, "user/getUsers", "userId", "userName");

$(function () {
    validateRule();
    //createUserMulSelect($usersSelect);
    usersSelect.set(partyId);
    //editor
    ClassicEditor
	.create(document.querySelector("#editor"), {
        language:"zh-cn",
        toolbar: ["heading","|","bold","italic","link","blockQuote","imageUpload","imageStyle:full","imageStyle:side","numberedList","bulletedList","insertTable","|","undo","redo"],
        //removePlugins: [ 'mediaEmbed' ],
        ckfinder: {
        	uploadUrl: "common/upload?subDir=mtgSummary" 
        }
    }) 
	.then(editor => { 
		//console.info(Array.from( editor.ui.componentFactory.names() ))
		myEditor = editor; 
	}) 
	.catch(error => { console.error(error); });
    
    //file upload
    $("input[name='file']").fileinput({
    	language: "zh",
    	showUpload: false, 
    	showCaption: false, 
    	dropZoneEnabled: false,
    	maxFileSize: 10240//单位为kb，如果为0表示不限制文件大小
    	//allowedFileExtensions: ['txt','pdf','doc','docx']
    });
    
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
        	//设置editor内容
        	$("textarea[name='content']").text(myEditor.getData());
        	if (name === "save") {
	            var options = {
		            url : ctx + "mtgSummary/add",
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
		            url : ctx + "mtgSummary/update",
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
	//resetMulUsers($usersSelect);
	usersSelect.clean();
	setStatus(0);
    $("#entity-add-modal-title").html('新增会议纪要');
    $("#entity-add-button").prop("hidden", false);
    $("#sumFile").siblings().remove();//清空附件
    myEditor.setData("");
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
        rules: {
        	sumTitle: {
                required: true,
                maxlength: 200
            },
            users: {
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
        	sumTitle: {
                required: icon + "请输入标题",
                maxlength: icon + "标题长度少于200个字符"
            },
            users: icon + "请选择参会党员"
        	
        }
    });
}