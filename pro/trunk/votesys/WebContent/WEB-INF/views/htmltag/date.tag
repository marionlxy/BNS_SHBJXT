@var islayer = islayer!'true';//是否是弹窗形式,否则是表格toolbar形式
@var isRange = isRange!'false';//是否是日期范围
@var text = text!'';//中文名
@var name = name!'';//name属性
@var require = require!'false'; //是否要校验
@var readonly = readonly!'';
@var value = value!'';//元素值
@var notNull = notNull!'false';//是否非空
@var class = class!'width-100';//自定义样式
@var alterFlag = alterFlag!'';//可更改标记

@if(islayer != 'true'){
	<div class="form-group">
		<span>${text}:</span>
		<input name="${name}" class="Wdate form-control w150" type="text">
	</div>
@}
@if(isRange == 'true'){
	<div class="form-group">
		<span>${text}:</span>
		<input name="${nameStart}" class=" Wdate form-control w70" type="text" 
				id="startDate" onFocus="WdatePicker({maxDate: '#F{$dp.$D(\'endDate\')||\'2020-10-01\'}' })">-
		<input name="${nameEnd}" class="form-control w70" type="text" 
				id="endDate" onFocus="WdatePicker({minDate: '#F{$dp.$D(\'startDate\')||\'%y-%M-%d\'}'})"	>
	</div>
@}else{
	<tr class="FormData">
	    <td class="CaptionTD">${text}:</td>
	    <td class="DataTD ${class}">
	    	@//非空标记
			@if(nullFlag == '0'){
				@//可更改标记
	    	 	@if(alterFlag == '0'){
				     <input type="text" name="${name}" readonly value="${value! }" class="Wdate validate[required]"/>
	    	 	@}else{
				     <input type="text" name="${name}"  value="${value! }" onfocus="WdatePicker()" class="Wdate validate[required]"/>
	    	 	@}
		    @}else{
		    	@if(alterFlag == '0'){
				     <input type="text" name="${name}" readonly value="${value! }" class="Wdate" />
	    	 	@}else{
				     <input type="text" name="${name}"  value="${value! }" onfocus="WdatePicker()" class="Wdate"/>
	    	 	@}
		    @}
	        
	    </td>
	    @if(nullFlag == '0'){
		     <td><font color="red" size="+1">*</font></td>
	    @}
	</tr>
@}