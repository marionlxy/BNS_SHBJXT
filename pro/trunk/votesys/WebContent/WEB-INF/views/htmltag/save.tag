@var formId = formId!'save-form'; //form表单id
@var reloadUrl = reloadUrl!'false'; //是否url刷新
@var subBtnId = subBtnId!(formId+'-save');
@var okText = okText!'确 定';
@var callBack = callBack!'';
@var style = style!'bottom: 0px;left:35%;position:absolute;';
@var url = url!'';

@var length = strutil.length(okText);
@var css ="btn-primary";	//btn-primary样式固定按钮宽度
 @if(length>4){
 	 @css = "btn-success";	//btn-success按钮长度随文字自动扩展，适应于按钮文本较长的情况
 @}
<div class="clearfix" tag-save-btn style="${style}">
	<span class="btn ${css } btn-sm bigger-110"  id="${subBtnId}" onclick="save('${formId}','${url!}')">
		${okText}
	</span>&nbsp;&nbsp;
	<span class="btn btn-primary btn-sm bigger-110" id="${formId}-cancel">
		关 闭
	</span>
</div>

<script type="text/javascript">
var index = layer.index;
$(function(){
	$("#${formId}-cancel").click(function(){
		layer.close(index);
		return false;
	});
	

});
</script>