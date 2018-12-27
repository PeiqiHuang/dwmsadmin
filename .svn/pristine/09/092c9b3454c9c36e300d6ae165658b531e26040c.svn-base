/**
 * 根据ajax获取select控件数据
 * $select: select控件
 * url： 获取数据地址
 * value： option value
 * text： option 文本
 */

//单选
function Select($select, url, value, text) {
	this.$select = $select;
	this.url = url;
	this.value = value;
	this.text = text;
	this.select = true;
}

Select.prototype.setVal = function(value){
	this.$select.val(value);
}
Select.prototype.set = function(partyId){
	var ps = this;
    $.post(ctx + ps.url, function (r) {
        if (r.code === 0) {
            var data = r.msg;
            ps.clean();
            $.each(data, function(){
            	ps.$select.append("<option value='"+this[ps.value]+"'>"+this[ps.text]+"</option>");
            })
            if (!ps.select) {
            	ps.disable();
            }
        } else {
            $MB.n_danger(r.msg);
        }
    })
}

Select.prototype.clean = function() {
	this.$select.children().first().siblings().remove();
}

Select.prototype.disable = function() {
	this.$select.prop('disabled', true);
}

Select.prototype.enable = function() {
	this.$select.prop('disabled', false);
	this.select = true;
}