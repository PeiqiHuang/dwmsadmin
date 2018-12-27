$(function() {
    initTable();
    initPartySearch();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
	var $party = $tableForm.find("select[name='partyId']");
    var setting = {
        url: ctx + 'album/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                partyId: $party[0] == undefined ? '' : $party.val().trim(),
                albumName: $tableForm.find("input[name='albumName']").val().trim(),
                timeField: $tableForm.find("input[name='timeField']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim()
            };
        },
        columns: [{
	            checkbox: true
	        },
            /*{
                title: '编号',
                field: 'albumId',
                width: '50px'
            },*/
	        {
	        	title: '相册封面',
	        	field: 'cover',
	        	formatter: function (value, row, index) {
	        		if (!value) return '';
	        		return "<img src='" + value + "' class='img-responsive' style='height:80px;max-width:200px;'>";
	        	}
	        },
            {
                title: '相册名称',
                field: 'albumName'
            },
            {
            	title: '相册描述',
            	field: 'albumDesc',
        		formatter: function (value, row, index) {
            		return "<textarea class='form-control' rows='2' readonly>"+value+"</textarea>";
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
        $MB.n_warning('请勾选需要删除的相册！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].albumId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的相册？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'album/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function openImg() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
	var selected_length = selected.length;
	if (!selected_length || selected_length > 1) {
		$MB.n_warning('请勾选一个活动！');
		return;
	}
	var entityId = selected[0].albumId;
	var albumName = selected[0].albumName;
	var url = "albumImg?albumId=" + entityId;
	openPage(url, albumName + "图片");
}