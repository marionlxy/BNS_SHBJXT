@var islayer = islayer!'true';//是否是弹窗形式,否则是表格toolbar形式
@var text = text!'';//中文名
@var name = name!'';//name属性
@var readonly = readonly!'';
@var value = value!'';//元素值
@var nullFlag = nullFlag!'1';//是否非空,是否要校验
@var class = class!'width-100';//自定义样式
@var alterFlag = alterFlag!'';//可更改标记
@var id = id!'';
@var specFlag = specFlag!'false';
@var dollar = '$';

@if(islayer != 'true'){
	<div class="form-group">
		<span>${text}:</span>
		<input name="${name}" value="${value! }" class="form-control w150" type="text">
	</div>
@}else{
		@if(specFlag == "true"){
			\@if(createDate!0 == 0){
				\@createDate = date();
			\@}
		@}
	<tr class="FormData">
	    <td class="CaptionTD">${text}:</td>
	    <td class="DataTD ${class}">
	    	@//非空标记  	 0:非空
			@if(nullFlag == '0'){
				@//可更改标记 	0:不可更改
	    	 	@if(alterFlag == '0'){
				     <input type="text" id="${id!}" name="${name}" readonly value="${value! }" class="validate[required,custom[invalidChar]]" ${decode(id,"specMettingCode","onblur='mettingCode()'","")}/>
	    	 	@}else{
				     <input type="text" id="${id!}" name="${name}"   value="${value! }" class="validate[required,custom[invalidChar]]" ${decode(id,"specMettingCode","onblur='mettingCode()'","")} />
	    	 	@}
		    @}else{
		    	@if(alterFlag == '0'){
				     <input type="text" id="${id!}" name="${name}" readonly value="${value! }" ${decode(id,"specMettingCode","onblur='mettingCode()'","")}/>
	    	 	@}else{
				     <input type="text" id="${id!}" name="${name}" value="${value! }" ${decode(id,"specMettingCode","onblur='mettingCode()'","")}/>
	    	 	@}
		    @}
	        
	    </td>
	    @if(nullFlag == '0'){
		     <td><font color="red" size="+1">*</font></td>
	    @}
	</tr>
@}

@//准备会议特殊处理
@if(id == 'specMettingCode'){
<script>
var name = "";
function mettingCode(){
	var code = $("#specMettingCode").val();
	if(name == ""){
		name = $("#specMettingTitle").val();
	}
	var arr = code.split("-");
	var newName = name + arr[0] + "年第" + parseInt(arr[1]) + "次会议";
	$("#specMettingTitle").val(newName);
}
$(function(){
	mettingCode();
})
	
</script>
@}