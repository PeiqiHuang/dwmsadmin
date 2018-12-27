$(function() {
    initTable();
    initPartySearch();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
	var $party = $tableForm.find("select[name='partyId']");
    var setting = {
		//id: 'userId',
        url: ctx + 'user/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                partyId: $party[0] == undefined ? '' : $party.val().trim(),
                userName: $tableForm.find("input[name='userName']").val().trim(),
                mobile: $tableForm.find("input[name='mobile']").val().trim(),
                gender: $tableForm.find("select[name='gender']").val().trim(),
                partyType: $tableForm.find("select[name='partyType']").val().trim(),
                workStatus: $tableForm.find("select[name='workStatus']").val().trim(),
                partyPosts: $tableForm.find("select[name='partyPosts']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim(),
            };
        },
        columns: [{
	            checkbox: true
	        },
            /*{
                title: '编号',
                field: 'userId',
                width: '50px'
            },*/
	        {
	        	title: '头像',
	        	field: 'avatar',
	        	formatter: function (value, row, index) {
	        		if (!value) return '';
	        		return "<img src='" + value + "' class='img-responsive' style='height:80px;max-width:200px;'>";
	        	}
	        },
            {
                title: '党员名称',
                field: 'userName'
            },
            {
            	title: '手机',
            	field: 'mobile'
            },
            {
            	title: '性别',
            	field: 'gender',
            	formatter: function (value, row, index) {
            		if (value === 0) return '未知';
            		if (value === 1) return '男';
            		if (value === 2) return '女';
            	}
            },
            {
            	title: '党员类型',
            	field: 'partyType',
                formatter: function (value, row, index) {
                    if (value === 1) return '申请入党人';
                    if (value === 2) return '入党积极分子';
                    if (value === 3) return '发展对象';
                    if (value === 4) return '预备党员';
                    if (value === 5) return '正式党员';
                }
            },
            {
            	title: '工作状态',
            	field: 'workStatus',
                formatter: function (value, row, index) {
                	if (value === 0) return '离职';
                    if (value === 1) return '在岗';
                    if (value === 2) return '在职';
                }
            },
            {
            	title: '党内职务',
            	field: 'partyPosts',
            	formatter: function (value, row, index) {
                    if (value === 1) return '普通党员';
                    if (value === 2) return '支部委员';
                    if (value === 3) return '支部副书记';
                    if (value === 4) return '支部书记';
                }
            },
            {
            	title: '状态',
            	field: 'status',
                formatter: function (value, row, index) {
                    if (value === 1) return '<span class="badge badge-success">正常</span>';
                    if (value === 0) return '<span class="badge badge-danger">禁用</span>';
                }
            },
            {
                title: '创建时间',
                field: 'createTime'
            }
        ]
    };
    $MB.initTable('entityTable', setting);
}

function search() {
	$MB.refreshTable('entityTable');
}

function refresh() {
    $(".entity-table-form")[0].reset();
    search();
}


function exportEntityExcel(){
	$.post(ctx+"user/excel",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exportEntityCsv(){
	$.post(ctx+"user/csv",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function resetPwd() {
    var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要重置密码的党员！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].userId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定选中的党员要重置密码？",
        confirmButtonText: "确定"
    }, function() {
        $.post(ctx + 'user/reset', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}