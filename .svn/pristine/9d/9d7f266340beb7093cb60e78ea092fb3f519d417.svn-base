// 全局变量loginPartyId
// 单选user
function createUserSingleSelect(idArr, url, param) {
	if (!url)
		url = "user/getUsers";
	if (!param)
		param = {partyId : loginPartyId};
    $.post(ctx + url, param, function (r) {
        if (r.code === 0) {
            var data = r.msg;
            $.each(idArr, function(){
            	var $select = $("select[name='"+this+"']");
            	$.each(data, function(){
            		$select.append("<option value='"+this.userId+"'>"+this.userName+"</option>");
            	})
            })
        } else {
            $MB.n_danger(r.msg);
        }
    })
}

/*var userSelectArr = [];//修改状态时设置勾选
function createUserMulSelect(partyId, $usersSelect, url, param) {
	if (!url)
		url = "user/getUsers";
	//if (!param)
	//	param = {partyId : loginPartyId};
	var param = {partyId : partyId};
    $.post(ctx + url, param, function (r) {
        var data = r.msg;
        var option = "";
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].userId + "'>" + data[i].userName + "</option>"
        }
        $usersSelect.html("").append(option);
        
        setMulSelect();
        if(userSelectArr.length > 0) {
        	$usersSelect.multipleSelect('setSelects', userSelectArr);
        	$users.val($usersSelect.val());
        	userSelectArr = [];
        }
    });
}

function setMulSelect() {
	var options = {
        selectAllText: '所有党员',
        allSelected: '所有党员',
        width: '100%',
        onClose: function () {
            $users.val($usersSelect.val());
            validator.element("input[name='users']");
        }
    };

    $usersSelect.multipleSelect(options);
}

function resetMulUsers($usersSelect) {
	//$usersSelect.multipleSelect('setSelects', []);
    //$usersSelect.multipleSelect("refresh");
	$usersSelect.html("");
	setMulSelect();
}*/