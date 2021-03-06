$(function() {
    initTable();
    initPartySearch();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
	var $party = $tableForm.find("select[name='partyId']");
    var setting = {
        url: ctx + 'due/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                partyId: $party[0] == undefined ? '' : $party.val().trim(),
                title: $tableForm.find("input[name='title']").val().trim(),
                timeField: $tableForm.find("input[name='timeField']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim(),
                dueItem: $tableForm.find("select[name='dueItem']").val().trim(),
                dueType: $tableForm.find("select[name='dueType']").val().trim()
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
                title: '缴费标题',
                field: 'title'
            },
            {
            	title: '缴费描述',
            	field: 'dueDesc'
            },
            {
            	title: '缴费项目',
            	field: 'dueItem',
            	formatter: function (value, row, index) {
            		if (value ===1) return "党费";
            		if (value === 2) return "特殊党费";
            	}
            },
            {
            	title: '缴费类型',
            	field: 'dueType',
            	formatter: function (value, row, index) {
            		if (value ===1) return "固定金额";
            		if (value === 2) return "自由金额";
            	}
            },
            {
            	title: '缴费金额',
            	field: 'due',
            	formatter: function (value, row, index) {
            		if (row.dueType === 2) {
            			return "不限";
            		} else {
            			return value;
            		}
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
                title: '结束时间',
                field: 'endTime'
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
        $MB.n_warning('请勾选需要删除的缴费！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].dueId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的缴费？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'due/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function openAccount() {
	var url = "dueAccount";
	openPage(url, "收费账户");
}
function openData() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
	var selected_length = selected.length;
	if (!selected_length || selected_length > 1) {
		$MB.n_warning('请勾选一个缴费！');
		return;
	}
	var entityId = selected[0].dueId;
	var title = selected[0].title;
	var url = "dueUser?dueId=" + entityId;
	openPage(url, title + "缴费统计");
}