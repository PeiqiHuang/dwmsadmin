var $tableForm = $(".entity-table-form");
$(function() {
    initTable();
});

function initTable() {
    var setting = {
        url: ctx + 'advice/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                timeField: $tableForm.find("input[name='timeField']").val().trim(),
                adviceText: $tableForm.find("input[name='adviceText']").val().trim(),
                userName: $tableForm.find("input[name='userName']").val().trim()
            };
        },
        columns: [/*{
	            checkbox: true
	        },*/
            /*{
                title: '编号',
                field: 'infoId',
                width: '50px'
            },*/
            {
                title: '反馈内容',
                field: 'adviceText'
            },
            {
            	title: '反馈人',
            	field: 'userName'
            },
            {
            	title: '所属党支部',
            	field: 'partyName'
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