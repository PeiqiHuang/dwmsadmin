$(function() {
    initTable();
    initPartySearch();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
	var $party = $tableForm.find("select[name='partyId']");
    var setting = {
        url: ctx + 'dueAccount/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                dueId: $tableForm.find("input[name='dueId']").val().trim(),
                partyId: $party[0] == undefined ? '' : $party.val().trim(),
                status: $tableForm.find("select[name='status']").val().trim()
            };
        },
        columns: [{
	            checkbox: true
	        },
            /*{
                title: '编号',
                field: 'dueId',
                width: '50px'
            },*/
	        {
	            title: '党费账户名称',
	            field: 'accName'
	        },
	        {
	        	title: '微信支付图片',
	        	field: 'wechatFilePath',
	        	formatter: function (value, row, index) {
	        		if (!value) return '';
	        		return "<img src='" + value + "' class='img-responsive' style='height:80px;max-width:200px;'>";
	        	}
	        },
	        {
	        	title: '支付宝支付图片',
	        	field: 'alipayFilePath',
	        	formatter: function (value, row, index) {
	        		if (!value) return '';
	        		return "<img src='" + value + "' class='img-responsive' style='height:80px;max-width:200px;'>";
	        	}
	        },
            {
            	title: '收款方姓名',
            	field: 'payeeName'
            },
            {
            	title: '收款方开户银行',
            	field: 'payeeBank'
            },
            {
            	title: '收款方开户地址',
            	field: 'payeeAddress'
            },
            {
            	title: '收款方账号',
            	field: 'payeeAccount'
            },
            {
            	title: '状态',
            	field: 'status',
            	formatter: function (value, row, index) {
                    if (value === 0) return '<span class="badge badge-warning">屏蔽</span>';
                    if (value === 1) return '<span class="badge badge-info">开启</span>';
                }
            },
            {
                title: '修改时间',
                field: 'updateTime'
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


function deleteEntity() {
    var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的账户！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].accId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的账户？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'dueAccount/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}