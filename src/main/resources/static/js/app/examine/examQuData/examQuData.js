$(function() {
    initTable();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
    var setting = {
        url: ctx + 'examQuData/list',
        pagination: false,
        //pageSize: 10,
        queryParams: function(params) {
            return {
                //pageSize: params.limit,
                //pageNum: params.offset / params.limit + 1,
                examId: $tableForm.find("input[name='examId']").val().trim(),
                title: $tableForm.find("input[name='title']").val().trim(),
                quType: $tableForm.find("select[name='quType']").val().trim(),
                answers: $tableForm.find("input[name='answers']").val().trim(),
                rightKey: $tableForm.find("input[name='rightKey']").val().trim()
            };
        },
        columns: [/*{
	            checkbox: true
	        },*/
            /*{
                title: '编号',
                field: 'examId',
                width: '50px'
            },*/
	        {
	            title: '试题序号',
	            field: 'quNo',
	            width: '50px'
	        },
	        {
	        	title: '题目类型',
	        	field: 'quType',
	        	formatter: function (value, row, index) {
	        		if (value === 1) return '单选题';
	        		if (value === 2) return '多选题';
	        		if (value === 3) return '填空题';
	        		if (value === 4) return '简答题';
	        	}
	        },
	        {
	            title: '试题题目',
	            field: 'title'
	        },
	        {
	        	title: '正确答案',
	        	field: 'rightKey'
	        },
	        {
	        	title: '题目分数',
	        	field: 'score'
	        },
            {
            	title: '答案分析',
            	field: 'answers',
            	formatter: function (value, row, index) {
            		if (row.quType != 4) {
	            		console.info(row.answersList)
	            		var answersArr = [];
	            		$.each(row.answersList, function() {
	            			var data = this.answer + ' -> ' + this.selectNum + '人 / ' + this.rate + '% 选择了此答案';
	            			if (row.rightKey.indexOf(this.answer) > -1) {
	            				data = "<span style='color:red'>" + data + "</span>";
	            			}
	            			answersArr.push(data);
	            		})
	            		return answersArr.join("<br>");
            		} else {
            			var data = row.answersList[0];
            			return data.selectNum + '人作答，平均分：' + data.rate;
            		}
            	}
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