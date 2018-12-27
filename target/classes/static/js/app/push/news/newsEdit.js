function updateNews() {
    var selected = $("#newsTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的新闻！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个新闻！');
        return;
    }
    var newsId = selected[0].newsId;
    $.post(ctx + "news/getNews", {"newsId": newsId}, function (r) {
        if (r.code === 0) {
            var $form = $('#news-add');
            $form.modal();
            var news = r.msg;
            $("#news-add-modal-title").html('修改新闻');
            $form.find("input[name='title']").val(news.title);
            $form.find("input[name='newsId']").val(news.newsId);
            $("input:radio[value='" + news.status + "']").prop("checked", true);
            $("#news-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}