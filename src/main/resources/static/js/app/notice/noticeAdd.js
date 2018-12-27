var validator;
var $entityAddForm = $("#entity-add-form");
var myEditor = null;//编辑器
//开关控件
var $type_label = $("#typeLabel");
var $type_check = $("#typeCheck");
var $type = $("input[name='type']");

function setType(type) {
	if (type || type == 2) {
		$type_check.prop("checked", true);
		$type_label.html('图文消息');
		//trigger cover
		$("#upload").prop("hidden", false);
	}
	if (!type || type == 1) {
		$type_check.prop("checked", false);
	    $type_label.html('文字消息');
	    //trigger cover
	    $("#upload").prop("hidden", true);
	}
	$type.val(type);
}

$(function () {
    validateRule();
    createPartyTree();
    
    //file upload
    $("input[name='coverImg']").fileinput({
    	language: "zh",
    	showUpload: false, 
    	showCaption: false, 
    	dropZoneEnabled: false,
    	allowedFileExtensions: ['jpeg', 'jpg', 'gif', 'png', 'bmp']
	});
    
    //editor
    ClassicEditor
	.create(document.querySelector("#editor"), {
        language:"zh-cn",
        toolbar: ["heading","|","bold","italic","link","blockQuote","imageUpload","imageStyle:full","imageStyle:side","numberedList","bulletedList","insertTable","|","undo","redo"],
        //removePlugins: [ 'mediaEmbed' ],
        ckfinder: {
        	uploadUrl: "common/upload?subDir=notice" 
        }
    }) 
	.then(editor => { 
		//console.info(Array.from( editor.ui.componentFactory.names() ))
		myEditor = editor; 
	}) 
	.catch(error => { console.error(error); });
    
    $type_check.change(function () {
        var checked = $(this).is(":checked");
        var type = checked ? 2 : 1;
        setType(type);
    });
    
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
        		var options = {
		            url : ctx + "notice/add",
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
        				url : ctx + "notice/update",
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
	
	editable();
	myEditor.setData("");
	setType(1);
	$("#upload").prop("hidden", true);
	$("#coverImg").attr("src", "");
	
    $("#entity-add-modal-title").html('新增通知');
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
    refreshPartyTree();
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
        rules: {
        	title: {
                required: true,
                maxlength: 50
            },
            subTitle: {
            	maxlength: 50
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
        	title: {
                required: icon + "请输入标题",
                maxlength: icon + "标题长度少于50个字符"
            },
            subTitle: {
            	maxlength: icon + "副标题长度少于50个字符"
            },
        	status: icon + "请选择状态",
        	partyId: icon + "请选择党支部",
        	
        }
    });
}

/*function statusHidden(hidden) {
	$("#status").prop("hidden", hidden);
}*/