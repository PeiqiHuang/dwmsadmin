var $tableForm = $(".entity-table-form");
var $partySelect = $tableForm.find("select[name='partyId']");

$(function() {
    initTable();
    initPartySearch();
});

function initTable() {
	var $party = $tableForm.find("select[name='partyId']");
    var setting = {
        url: ctx + 'wish/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                partyId: $party[0] == undefined ? '' : $party.val().trim(),
                fromUserName: $tableForm.find("input[name='fromUserName']").val().trim(),
                toUserName: $tableForm.find("input[name='toUserName']").val().trim(),
                wishYear: $tableForm.find("input[name='wishYear']").val().trim(),
                timeField: $tableForm.find("input[name='timeField']").val().trim(),
                wishType: $tableForm.find("select[name='wishType']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim()
            };
        },
        columns: [{
	            checkbox: true
	        },
            /*{
                title: '编号',
                field: 'wishId',
                width: '50px'
            },*/
	        {
	        	title: '生日党员',
	        	field: 'toUserName'
	        },
	        {
	        	title: '祝福人',
	        	field: 'fromUserName'
	        },
            {
            	title: '生日年份',
            	field: 'wishYear'
            },
            {
            	title: '类型',
            	field: 'wishType',
            	formatter: function (value, row, index) {
            		if (value === 1) return '书记寄语';
            		if (value === 2) return '生日感言';
            		if (value === 3) return '他人祝福';
            	}
            },
            {
            	title: '状态',
            	field: 'status',
            	formatter: function (value, row, index) {
            		if (value === 0) return '<span class="badge badge-warning">屏蔽</span>';
            		if (value === 1) return '<span class="badge badge-success">启用</span>';
            	}
            },
            {
                title: '祝福时间',
                field: 'wishTime'
            },
            {
            	title: '祝福语',
            	field: 'wishText',
            	formatter: function (value, row, index) {
            		return "<textarea class='form-control' rows='2' readonly>"+value+"</textarea>";
            	}
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

function updateEntity() {
    var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的祝福语！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].wishId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定修改选中的祝福语？",
        confirmButtonText: "确定修改"
    }, function() {
        $.post(ctx + 'wish/update', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportEntityExcel(){
	$.post(ctx+"wish/excel",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exportEntityCsv(){
	$.post(ctx+"wish/csv",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}


function openTpl() {
	var url = "wishTpl";
	openPage(url, "祝福语模板");
}