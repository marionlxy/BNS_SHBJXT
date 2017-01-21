@var id = id!"";
@var text = text!'';//中文名
@var name = name!;
@var options = options!'';
@var nullFlag = nullFlag!'1';//是否非空,是否要校验
@var values = values!'';
@var dollar = '$';
@var alterFlag = alterFlag!'';//可更改标记
@var dataType = dataType!'';
@var hiddenValue = hiddenValue!'';//数据类型为03的时候,新增的值:updateObj.xxxxName


		\@var ${name}Arr = [];
@if(values != ''){
	@if(dataType == '03'){
		\@if(updateObj.${name!}Id!0 != 0){
			\@${name}Arr = strutil.split(updateObj.${name!}Id!,",");
		\@}
	@}else{
		\@if(${values!}!0 != 0){
			\@${name}Arr = strutil.split(${values!},",");
		\@}
	@}
@}


<tr class="FormData">
	<td class="CaptionTD">${text!}:</td>
	<td class="DataTD width-100">
	     @if(nullFlag == '0'){
	     	@if(alterFlag == '0'){
	         <select id="${id }" class="multiple" multiple="multiple" disabled>
				\@var ${id }flag = '0';//0：默认不选中
					@for(op in options){
						@//判断有没有values，用来区分新增和更新，新增没有values属性
	            		@if(values == ''){
	            			<option value="${op.ecode! }">${op.name! }</option>
	            		@}else{
	            		@if(dataType == '03'){
	            			\@if(updateObj.${name!}Id!0 != 0){
	            		@}else{
	            			\@if(${values!}!0 != 0){
	            		@}
		            			\@for(var i=0;i<${name}Arr.~size;i++){
			            			\@if("${op.ecode}" == ${name}Arr[i]){
			            				\@${id }flag = '1'; //1：有值选中
									\@}
								\@}
								\@if(${id }flag == '0'){
				            		<option value="${op.ecode!}">${op.name!}</option>
								\@}else{
				            		<option value="${op.ecode!}" selected>${op.name!}</option>
								\@}
								\@${id }flag = '0';
		            		\@}else{
		            			<option value="${op.ecode!}">${op.name!}</option>
		            		\@}
	            		@}
	            	@}
			</select>
            @}else{
            	<select id="${id }" class="multiple" multiple="multiple">
           			\@var ${id }flag = '0';//0：默认不选中
					@for(op in options){
	            		@if(values == ''){
	            			<option value="${op.ecode! }">${op.name! }</option>
	            		@}else{
	            			@if(dataType == '03'){
	            			\@if(updateObj.${name!}Id!0 != 0){
	            		@}else{
	            			\@if(${values!}!0 != 0){
	            		@}
	            				\@for(var i=0;i<${name}Arr.~size;i++){
			            			\@if("${op.ecode}" == ${name}Arr[i]){
			            				\@${id }flag = '1'; //1：有值选中
									\@}
								\@}
								\@if(${id }flag == '0'){
				            		<option value="${op.ecode!}">${op.name!}</option>
								\@}else{
				            		<option value="${op.ecode!}" selected>${op.name!}</option>
								\@}
								\@${id }flag = '0';
		            		\@}else{
		            			<option value="${op.ecode!}">${op.name!}</option>
		            		\@}
	            		@}
	            	@}
			</select>
            @}
	     @}else{
	     	@if(alterFlag == '0'){
	         <select id="${id }" class="multiple" multiple="multiple" disabled>
				\@var ${id }flag = '0';//0：默认不选中
					@for(op in options){
	            		@if(values == ''){
	            			<option value="${op.ecode! }">${op.name! }</option>
	            		@}else{
	            			@if(dataType == '03'){
	            			\@if(updateObj.${name!}Id!0 != 0){
	            		@}else{
	            			\@if(${values!}!0 != 0){
	            		@}
		            			\@for(var i=0;i<${name}Arr.~size;i++){
			            			\@if("${op.ecode}" == ${name}Arr[i]){
			            				\@${id }flag = '1'; //1：有值选中
									\@}
								\@}
								\@if(${id }flag == '0'){
				            		<option value="${op.ecode!}">${op.name!}</option>
								\@}else{
				            		<option value="${op.ecode!}" selected>${op.name!}</option>
								\@}
								\@${id }flag = '0';
		            		\@}else{
		            			<option value="${op.ecode!}">${op.name!}</option>
		            		\@}
	            		@}
	            	@}
			</select>
            @}else{
            	<select id="${id }" class="multiple" multiple="multiple">
           			\@var ${id }flag = '0';//0：默认不选中
					@for(op in options){
	            		@if(values == ''){
	            			<option value="${op.ecode! }">${op.name! }</option>
	            		@}else{
	            			@if(dataType == '03'){
	            			\@if(updateObj.${name!}Id!0 != 0){
	            		@}else{
	            			\@if(${values!}!0 != 0){
	            		@}
		            			\@for(var i=0;i<${name}Arr.~size;i++){
			            			\@if("${op.ecode}" == ${name}Arr[i]){
			            				\@${id }flag = '1'; //1：有值选中
									\@}
								\@}
								\@if(${id }flag == '0'){
				            		<option value="${op.ecode!}">${op.name!}</option>
								\@}else{
				            		<option value="${op.ecode!}" selected>${op.name!}</option>
								\@}
								\@${id }flag = '0';
		            		\@}else{
		            			<option value="${op.ecode!}">${op.name!}</option>
		            		\@}
	            		@}
	            	@}
			</select>
            @}
	     @}
\@//===============================================================================================================	     
        @if(dataType == '03'){
			<input type="hidden" name="${name!}Id" id="${id }Hidden" value="${dollar}{updateObj.${name!}Id!}">
			<input type="hidden" name="${name!}Name" id="${id }HiddenValue" value="${hiddenValue!}">
		@}else{
			<input type="hidden" name="${name!}" id="${id }Hidden" value="${dollar}{updateObj.${name!}!}"> 
		@}			
	</td>
       @if(nullFlag == '0'){
		<td><font color="red" size="+1">*</font></td>
	   @}
</tr>
          		
          		
          		
<script>
	$('#${id}').multiselect({
		onChange : function(element, checked) {
			$('#${id }Hidden').val();//先清空值
			$('#${id }Hidden').val($('#${id}').val());//赋值
			var selectedText = '';
			var selectedOptions = $('#${id} option:selected');
			if(selectedOptions.length > 0){
				selectedOptions.each(function(i,obj) {
					selectedText += obj.text + ',';
		        });
			}
			selectedText = selectedText.substr(0, selectedText.length -1) + ' ';
			$('#${id }HiddenValue').val();//先清空值
			$('#${id }HiddenValue').val(selectedText);//赋值
		}
	});
	
</script>