$(function() {
    initTable();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
    var setting = {
        url: ctx + 'adKey/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                adKey: $tableForm.find("input[name='adKey']").val().trim(),
                keyName: $tableForm.find("input[name='keyName']").val().trim(),
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
	            title: '广告位置key',
	            field: 'adKey'
	        },
	        {
	        	title: '广告位置名称',
	        	field: 'keyName'
	        },
            {
            	title: '状态',
            	field: 'status',
            	formatter: function (value, row, index) {
                    if (value === 0) return '<span class="badge badge-warning">禁用</span>';
                    if (value === 1) return '<span class="badge badge-info">启用</span>';
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
        $MB.n_warning('请勾选需要删除的广告位置！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].adKey;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的广告位置？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'adKey/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}