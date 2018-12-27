$(function() {
    initTable();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
    var setting = {
        url: ctx + 'mtgSummary/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                mtgId: $tableForm.find("input[name='mtgId']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim(),
                source: $tableForm.find("select[name='source']").val().trim()
            };
        },
        columns: [{
	            checkbox: true
	        },
            {
                title: '编号',
                field: 'sumId',
                width: '50px'
            },
            {
                title: '纪要标题',
                field: 'sumTitle'
            },
            {
            	title: '撰写人',
            	field: 'operatorName'
            },
            {
            	title: '来源',
            	field: 'source',
            	formatter: function (value, row, index) {
            		if (value === 1) return '后台创建';
            		if (value === 2) return 'app创建';
            	}
            },
            {
            	title: '状态',
            	field: 'status',
            	formatter: function (value, row, index) {
            		if (value === -9) return '<span class="badge badge-danger">已删除</span>';
                    if (value === 0) return '<span class="badge badge-warning">未发布</span>';
                    if (value === 1) return '<span class="badge badge-success">已发布</span>';
                }
            },
            {
                title: '发布时间',
                field: 'releaseTime'
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
        $MB.n_warning('请勾选需要删除的会议纪要！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].sumId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的会议纪要？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'mtgSummary/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}