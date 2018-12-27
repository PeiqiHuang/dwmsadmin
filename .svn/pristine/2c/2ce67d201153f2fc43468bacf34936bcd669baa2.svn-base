/**
 * 根据partyId选择，ajax获取select控件数据
 * $select: select控件
 * url： 获取数据地址
 * value： option value
 * text： option 文本
 * $input: 多选时保存值的hidden input
 */

//单选
function PartySelect($select, url, value, text) {
	this.partyId = null;
	this.$select = $select;
	this.url = url;
	this.value = value;
	this.text = text;
	this.selected = null;
	this.select = true;
}

PartySelect.prototype.set = function(partyId){
	this.partyId = partyId;
	var ps = this;
	param = {partyId : partyId};
    $.post(ctx + ps.url, param, function (r) {
        if (r.code === 0) {
            var data = r.msg;
            ps.clean();
            $.each(data, function(){
            	ps.$select.append("<option value='"+this[ps.value]+"'>"+this[ps.text]+"</option>");
            })
            if (!!ps.selected) {
            	ps.$select.val(ps.selected);
            }
            ps.selected = null;
            if (!ps.select) {
            	ps.disable();
            }
        } else {
            $MB.n_danger(r.msg);
        }
    })
}

PartySelect.prototype.clean = function() {
	this.$select.children().first().siblings().remove();
}

PartySelect.prototype.disable = function() {
	this.$select.prop('disabled', true);
}

PartySelect.prototype.enable = function() {
	this.$select.prop('disabled', false);
	this.select = true;
}

//多选
function PartyMulSelect($select, $input, url, value, text) {
	this.partyId = null;
	this.$select = $select;
	this.$input = $input;
	this.url = url;
	this.value = value;
	this.text = text;
	this.selected = [];
	this.select = true;
}
PartyMulSelect.prototype.set = function(partyId) {
	var pm = this;
	pm.partyId = partyId;
	var param = {partyId : partyId};
	$.post(ctx + pm.url, param, function (r) {
        var data = r.msg;
        var option = "";
        $.each(data, function(){
        	option += "<option value='" + this[pm.value] + "'>"+this[pm.text] + "</option>";
        })
        pm.$select.html("").append(option);
        
        pm.setMulSelect();
        if(pm.selected.length > 0) {
        	$usersSelect.multipleSelect('setSelects', pm.selected);
        	pm.$input.val(pm.$select.val());
        	pm.selected = [];
        }
        if (!pm.select) {
        	pm.disable();
        }
    });
}
PartyMulSelect.prototype.clean = function() {
	this.$select.html("");
	this.setMulSelect();
}

PartyMulSelect.prototype.setMulSelect = function() {
	var pm = this;
	var options = {
        selectAllText: '所有',
        allSelected: '所有',
        width: '100%',
        onClose: function () {
            pm.$input.val(pm.$select.val());
            //validator.element(pm.$input.selector);
        }
    };

    this.$select.multipleSelect(options);
}

PartyMulSelect.prototype.disable = function() {
	this.$select.multipleSelect('disable');
}

PartyMulSelect.prototype.enable = function() {
	this.$select.multipleSelect('enable');
	this.select = true;
}