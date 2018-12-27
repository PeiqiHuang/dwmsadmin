var $tableForm = $(".entity-table-form");
var $infoType = $tableForm.find("select[name='infoType']");
var infoType = new Select($infoType, "helpType/getTypes", "typeId", "typeName");
$(function() {
    initTable();
    infoType.set();
});

function initTable() {
    var setting = {
        url: ctx + 'help/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                title: $tableForm.find("input[name='title']").val().trim(),
                timeField: $tableForm.find("input[name='timeField']").val().trim(),
                infoStatus: $tableForm.find("select[name='infoStatus']").val().trim(),
                infoType: $tableForm.find("select[name='infoType']").val().trim()
            };
        },
        columns: [{
	            checkbox: true
	        },
            /*{
                title: '编号',
                field: 'infoId',
                width: '50px'
            },*/
            {
                title: '标题',
                field: 'title'
            },
            {
            	title: '内容',
            	field: 'text',
            	formatter: function (value, row, index) {
            		return "<textarea class='form-control' rows='3' readonly>"+value+"</textarea>";
            	}
            },
            {
                title: '类型',
                field: 'typeName'
            },
            {
            	title: '状态',
            	field: 'infoStatus',
                formatter: function (value, row, index) {
                    if (value === 0) return '<span class="badge badge-warning">禁用</span>';
                    if (value === 1) return '<span class="badge badge-success">正常</span>';
                }
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
        $MB.n_warning('请勾选需要删除的帮助！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].infoId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的帮助？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'help/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function openType() {
	var url = "helpType";
	openPage(url, "帮助类型");
}