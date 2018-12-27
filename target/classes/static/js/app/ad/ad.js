var $tableForm = $(".entity-table-form");
var $adKey = $tableForm.find("select[name='adKey']");
adKey = new Select($adKey, "adKey/getAdKeys", "adKey", "keyName");

$(function() {
    initTable();
    initPartySearch();
    adKey.set();
});

function initTable() {
	var $party = $tableForm.find("select[name='partyId']");
    var setting = {
        url: ctx + 'ad/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                partyId: $party[0] == undefined ? '' : $party.val().trim(),
                name: $tableForm.find("input[name='name']").val().trim(),
                timeField: $tableForm.find("input[name='timeField']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim(),
                adKey: $tableForm.find("select[name='adKey']").val().trim()
            };
        },
        columns: [{
	            checkbox: true
	        },
            /*{
                title: '编号',
                field: 'adId',
                width: '50px'
            },*/
            {
                title: '广告名称',
                field: 'name'
            },
            {
	        	title: '广告封面',
	        	field: 'cover',
	        	formatter: function (value, row, index) {
	        		if (!value) return '';
	        		return "<img src='" + value + "' class='img-responsive' style='height:80px;max-width:200px;'>";
	        	}
	        },
            {
            	title: '广告简介',
            	field: 'summary'
            },
            {
            	title: '广告位置',
            	field: 'keyName',
	        	formatter: function (value, row, index) {
	        		if (!value) return row.adKey;
	        		return value;
	        	}
            },
            {
            	title: '状态',
            	field: 'status',
                formatter: function (value, row, index) {
                    if (value === 0) return '<span class="badge badge-warning">禁用</span>';
                    if (value === 1) return '<span class="badge badge-success">启用</span>';
                }
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
        $MB.n_warning('请勾选需要删除的广告！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].adId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的广告？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'advert/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
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
        ids += selected[i].adId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的广告？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'ad/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function openAdKey() {
	var url = "adKey";
	openPage(url, "广告位置");
}