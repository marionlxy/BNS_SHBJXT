@var islayer = islayer!'true';//是否是弹窗形式,否则是表格toolbar形式
@var id = id!"chosen-select";
@var text = text!'';//中文名
@var name = name!;
@var width = width!"100%";
@var class = class!;
@var options = options!'';
@var nullFlag = nullFlag!'1';//是否非空,是否要校验
@var value = value!'';
@var dollar = '$';
@var alterFlag = alterFlag!'';//可更改标记
@var dataType = dataType!'';
@var hiddenValue = hiddenValue!'';//数据类型为03的时候,新增的值:updateObj.xxxxName

@if(islayer != 'true'){
	<div class="form-group">
		<span>${text}:</span>
		<select class=" ${class!}" name="${name!}" id="${id!}">
		<option value="">--请选择--</option>
            @if(options != ""){
            	@for(op in options){
		            <option value="${op.ecode! }">${op.mapName! }</option>
            	@}
            @}
        </select> 
	</div>
@}
@if(islayer == 'true'){
<tr class="FormData">
	<td class="CaptionTD">${text! }:</td>
	<td class="DataTD width-100">
	@//类型为编码的字段的处理方式
	@if(dataType == '03'){
		@if(nullFlag == '0'){
		@//可更改标记
   	 	@if(alterFlag == '0'){
			<select class=" ${class!} validate[funcCall[validSelect]]" name="${name!}Id" id="${id!}" disabled="disabled">
		@}else{
			<select class=" ${class!} validate[funcCall[validSelect]]" name="${name!}Id" id="${id!}">
		@}
	@}else{
		@if(alterFlag == '0'){
			<select class=" ${class!}" name="${name!}Id" id="${id!}" disabled="disabled">
		@}else{
			<select class=" ${class!}" name="${name!}Id" id="${id!}">
		@}
	@}
            <option value="">--请选择--</option>
            @if(options != ""){
            	@for(op in options){
            		@if(value == ''){
            			<option value="${op.ecode! }">${op.mapName! }</option>
            		@}else{
	            		<option value="${op.ecode!}" ${dollar!}{decode("${op.ecode}",${value}Id,"selected","")}>${op.mapName!}</option>
            		@}
            	@}
            @}
        </select>
        <input type="hidden" id="${name!}Name" name="${name!}Name" value="${hiddenValue! }"/> 
    </td>
     @if(nullFlag == '0'){
	     <td><font color="red" size="+1">*</font></td>
    @}
<script>
$(function(){
    $("#${id!}").change(function(){
    	$("#${name!}Name").val($("#${id!}").find("option:selected").text());
    })
});
</script>
@}else{

	@if(nullFlag == '0'){
		@//可更改标记
   	 	@if(alterFlag == '0'){
			<select class=" ${class!} validate[funcCall[validSelect]]" name="${name!}" id="${id!}" disabled="disabled" >
		@}else{
			<select class=" ${class!} validate[funcCall[validSelect]]" name="${name!}" id="${id!}">
		@}
	@}else{
		@if(alterFlag == '0'){
			<select class=" ${class!}" name="${name!}" id="${id!}" disabled="disabled">
		@}else{
			<select class=" ${class!}" name="${name!}" id="${id!}">
		@}
	@}
            <option value="">--请选择--</option>
            @if(options != ""){
            	@for(op in options){
            		@if(value == ''){
            			<option value="${op.ecode! }" >${op.mapName! }</option>
            		@}else{
	            		<option value="${op.ecode!}" ${dollar!}{decode("${op.ecode}",${value},"selected","")}>${op.mapName!}</option>
            		@}
            	@}
            @}
        </select> 
    </td>
     @if(nullFlag == '0'){
	     <td><font color="red" size="+1">*</font></td>
    @}
@}
</tr>
@}
<script type="text/javascript">
	function validSelect(field, rules, i, options){
		var flag = false;
		 $.each(field[0],function(j,obj){
			 if(j != 0 && obj.selected){
				flag = true;
			}
		})
		if(!flag){
			rules.push('required');
		    return options.allrules.validSelect.alertText;
		}
	}
</script>
