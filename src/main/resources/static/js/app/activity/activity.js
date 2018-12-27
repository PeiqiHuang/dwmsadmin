$(function() {
    initTable();
    initPartySearch();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
	var $party = $tableForm.find("select[name='partyId']");
    var setting = {
        url: ctx + 'activity/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                partyId: $party[0] == undefined ? '' : $party.val().trim(),
                actName: $tableForm.find("input[name='actName']").val().trim(),
                timeField: $tableForm.find("input[name='timeField']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim()
            };
        },
        columns: [{
	            checkbox: true
	        },
            /*{
                title: '编号',
                field: 'actId',
                width: '50px'
            },*/
            {
                title: '活动名称',
                field: 'actName'
            },
            {
            	title: '活动详情',
            	field: 'actDesc'
            },
            {
            	title: '活动地址',
            	field: 'address'
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
            	title: '报名截止时间',
            	field: 'applyEndTime'
            },
            {
            	title: '开始时间',
            	field: 'beginTime'
            },
            {
                title: '结束时间',
                field: 'endTime'
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
        $MB.n_warning('请勾选需要删除的活动！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].actId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的活动？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'activity/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function openData() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
	var selected_length = selected.length;
	if (!selected_length || selected_length > 1) {
		$MB.n_warning('请勾选一个活动！');
		return;
	}
	var entityId = selected[0].actId;
	var actName = selected[0].actName;
	var url = "actUser?actId=" + entityId;
	openPage(url, actName + "活动统计");
}