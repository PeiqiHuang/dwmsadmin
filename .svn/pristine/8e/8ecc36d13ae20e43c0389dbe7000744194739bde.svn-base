$(function() {
    initTable();
    initPartySearch();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
	var $party = $tableForm.find("select[name='partyId']");
    var setting = {
        url: ctx + 'notice/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                partyId: $party[0] == undefined ? '' : $party.val().trim(),
                title: $tableForm.find("input[name='title']").val().trim(),
                timeField: $tableForm.find("input[name='timeField']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim(),
                type: $tableForm.find("select[name='type']").val().trim()
            };
        },
        columns: [{
	            checkbox: true
	        },
            /*{
                title: '编号',
                field: 'ntiId',
                width: '50px'
            },*/
	        {
	        	title: '通告封面',
	        	field: 'cover',
	        	formatter: function (value, row, index) {
	        		if (!value || row.type == 1) return '';
	        		return "<img src='" + value + "' class='img-responsive' style='height:80px;max-width:200px;'>";
	        	}
	        },
            {
                title: '标题',
                field: 'title'
            },
            {
            	title: '副标题',
            	field: 'subTitle'
            },
            {
                title: '类型',
                field: 'type',
                formatter: function (value, row, index) {
                    if (value === 1) return '文字消息';
                    if (value === 2) return '图文消息';
                }
            },
            {
            	title: '状态',
            	field: 'status',
                formatter: function (value, row, index) {
                	if (value === -9) return '<span class="badge badge-danger">已删除</span>';
                    if (value === -1) return '<span class="badge badge-danger">已取消</span>';
                    if (value === 0) return '<span class="badge badge-warning">待发布</span>';
                    if (value === 1) return '<span class="badge badge-success">已发布</span>';
                }
            },
            {
                title: '发布时间',
                field: 'releaseTime'
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
    $MB.calenders('input[name="timeField"]', true, false);
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
        $MB.n_warning('请勾选需要删除的通知！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].ntiId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的通知？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'notice/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}