var validator;

var $entityAddForm = $("#entity-add-form");

$(function () {
    validateRule();
    
    //file upload
    $("input[name='imgs']").fileinput({
    	language: "zh",
    	showUpload: false, 
    	showCaption: false, 
    	dropZoneEnabled: false,
    	allowedFileExtensions: ['jpeg', 'jpg', 'gif', 'png', 'bmp']
	});
    
    $("#entity-add .btn-save").click(function () {
        validator = $entityAddForm.validate();
        var flag = validator.form();
        if (flag) {
			var options = {
	            url : ctx + "albumImg/add",
	            dataType : "json",
	            type : "post",
	            success : function (r) {
	            	closeLoading();
	            	if (r.code === 0) {
	    				closeModal();
	    				$MB.n_success(r.msg);
	    				init();
	    			} else $MB.n_danger(r.msg);
	            }
	        };
			openLoading();
			$entityAddForm.ajaxSubmit(options);
        }
    });

    $("#entity-add .btn-close").click(function () {
        closeModal();
    });

});

function refresh() {
	//var url = "albumImg?albumId=" + $entityAddForm.find("input[name='albumId']").val();
	//var albumName = $("#albumName").val();
	//openPage(url, albumName + "图片", true);
	init();
}

function closeModal() {
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
    	ignore: ".no-need",
        rules: {
            imgs: {
            	required: true
            }
        },
        errorPlacement: function (error, element) {
        	if ($(element).attr("name") == "imgs") {
        		error.appendTo(element.parent().parent().parent());
        	}
        },
        messages: {
        	imgs: {
            	required: icon + "请选择图片"
            },
        }
    });
}