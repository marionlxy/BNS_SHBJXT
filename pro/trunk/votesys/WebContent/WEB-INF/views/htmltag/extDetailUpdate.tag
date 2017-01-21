@var detailList = detailList!'';
@var random = random!'';

@var dollar = '$';
<div id="toolbar" style="padding: 5px 0;text-align: center;">
	        	<button type="button" id="BtAdd${random}" class="btn btn-primary btn-sm" >添加</button>&nbsp;
	        	<button type="button" id="BtDel${random}" class="btn btn-primary btn-sm">删除</button>
		</div>
        <table id="tb${random}"  class="table table-bordered" data-toolbar="#toolbar" width="100%" style="white-space: nowrap;">
				            <tr align="center">
				                <td style="width:5%;">序号</td>
				                
				                <td style="width:5%;">
				                    <input id="CKA${random}" name="CKA${random}" type="checkbox"/>
				                </td>
				                @var width;
				                @var bxgId;//组id
				                @for(detail in detailList){
				                	@var length = detailLP.size;
				                	@width = 90/length;
				                	@bxgId = detail.bxgId;
					                <td style="width:${width!}%;">${detail.mapCnName!}</td>
		            			@}
				            </tr>
				            <tr>
				                <td style="text-align: center"></td>
				                <td style="text-align: center"><input id="CK${random}" type="checkbox" name="CK${random}"/></td>
				                <td style="display:none">
				                	<input type="hidden" name="extendGroupId" value="${bxgId }">
				                </td>
				                  @for(detail in detailList){
				                 	@// 0:文本框
				                 	@if(detail.collectType == '0'){
			                 			<td style="text-align: center;">
			                 				@if(detail.nullFlag == '0'){
				                 				<input  type="text" name="${detail.enName!}" class="validate[required]" />
			                 				@}else{
			                 					<input  type="text" name="${detail.enName!}"/>
			                 				@}
			                 			</td>
				                 	@}
				                 	@// 1:下拉框
				                 	@if(detail.collectType == '1'){
				                 	<td style="text-align: center;">
				                 		
				                 		@if(detail.nullFlag == '0'){
					                 		<select name="${detail.enName!}" class="validate[required]" style="height: 26px;width: 150px;line-height: 26px;border: 1px solid #e0e0e0;"> 
				                 		@}else{
					                 		<select name="${detail.enName!}" style="height: 26px;width: 150px;line-height: 26px;border: 1px solid #e0e0e0;"> 
				                 		@}
				                 			@var groups = detail.selgroups;
			                 				<option>--请选择--</option>
				                 			@for(gro in groups){
				                 				<option value="${gro.ecode!}">${gro.name!}</option>
				                 			@}
				                 		</select>
				                 	</td>
				                 	@}
				                 	@// 2:日期框
				                 	@if(detail.collectType == '2'){
				                 		<td style="text-align: center;">
			                 				@if(detail.nullFlag == '0'){
				                 				<input  type="text" name="${detail.enName!}" onFocus="WdatePicker()" class="validate[required]"/>
			                 				@}else{
			                 					<input  type="text" name="${detail.enName!}" onFocus="WdatePicker()"/>
			                 				@}
			                 			</td>
				                	@}
				                	@// 3:数字框
				                	@if(detail.collectType == '3'){
				                 		<td style="text-align: center;">
			                 				@if(detail.nullFlag == '0'){
					                 				<input  type="text" name="${detail.enName!}" class="validate[required,custom[integer]]" />
				                 				@}else{
				                 					<input  type="text" name="${detail.enName!}"/>
				                 				@}
			                 			</td>
				                	@}
				                	@// 4：金额框
				                	@if(detail.collectType == '4'){
					            		<td style="text-align: center;">
			                 				@if(detail.nullFlag == '0'){
				                 				<input  type="text" name="${detail.enName!}" placeholder="0.00(单位)" class="validate[required]"/>
			                 				@}else{
			                 					<input  type="text" name="${detail.enName!}" placeholder="0.00(单位)"/>
			                 				@}
			                 			</td>
					            	@}
					            @}
				            </tr>
			    		\@if(projectId!0 != 0){
        					\@var groupId = "${bxgId}";
        					\@var extDetDataList = wins.getExtDetDataList(projectId!,groupId);
				            \@for(detailData in extDetDataList){
				            	\@var index = detailDataLP.index-1;
				            	\@var newIndex = index+1;
				            
				           <tr>
			                <td style="text-align: center">${dollar!}{newIndex! }</td>
				                <td style="text-align: center"><input id="CK${random}" type="checkbox" name="CK${random}"/></td>
				                <td style="display:none">
				                	<input type="hidden" name="detailForm[${dollar!}{index! }].extendGroupId" value="${bxgId }">
				                </td>
				                 @for(detail in detailList){
				                 	@// 0:文本框
				                 	@if(detail.collectType == '0'){
			                 			<td style="text-align: center;">
			                 				@if(detail.nullFlag == '0'){
			                 					@//可更改标记
								    	 	@if(detail.alterFlag == '0'){
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" readonly class="validate[required]" value="${dollar!}{detailData.${detail.enName!}! }" />
								    	 	@}else{
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" class="validate[required]" value="${dollar!}{detailData.${detail.enName!}! }"/>
								    	 	@}
									    @}else{
									    	@if(detail.alterFlag == '0'){
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" readonly value="${dollar!}{detailData.${detail.enName!}! }"/>
								    	 	@}else{
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" value="${dollar!}{detailData.${detail.enName!}! }"/>
								    	 	@}
									    @}
			                 			</td>
				                 	@}
				                 	@// 1:下拉框
				                 	@if(detail.collectType == '1'){
				                 	<td style="text-align: center;">
				                 		@if(detail.nullFlag == '0'){
											@//可更改标记
									   	 	@if(detail.alterFlag == '0'){
												<select name="detailForm[${dollar!}{index! }].${detail.enName!}" class="validate[required]" disabled="disabled" style="height: 26px;width: 150px;line-height: 26px;border: 1px solid #e0e0e0;"> 
											@}else{
												<select name="detailForm[${dollar!}{index! }].${detail.enName!}" class="validate[required]" style="height: 26px;width: 150px;line-height: 26px;border: 1px solid #e0e0e0;"> 
											@}
										@}else{
											@if(detail.alterFlag == '0'){
												<select name="detailForm[${dollar!}{index! }].${detail.enName!}" disabled="disabled"  style="height: 26px;width: 150px;line-height: 26px;border: 1px solid #e0e0e0;"> 
											@}else{
												<select name="detailForm[${dollar!}{index! }].${detail.enName!}"  style="height: 26px;width: 150px;line-height: 26px;border: 1px solid #e0e0e0;"> 
											@}
										@}
								            <option value="">--请选择--</option>
								            @var groups = detail.selgroups;
								            @var value = 'detailData.'+detail.enName;
				                 			@for(gro in groups){
			                 					<option value="${gro.ecode!}" ${dollar!}{decode("${gro.ecode}",detailData.${detail.enName!}!,"selected","")}>${gro.name!}</option>
				                 			@}
								        </select> 
				                 	</td>
				                 	@}
				                 	@// 2:日期框
				                 	@if(detail.collectType == '2'){
				                 		<td style="text-align: center;">
				                 			@if(detail.nullFlag == '0'){
			                 					@//可更改标记
								    	 	@if(detail.alterFlag == '0'){
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" readonly  class="Wdate validate[required]" value="${dollar!}{detailData.${detail.enName!}! }"/>
								    	 	@}else{
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" class="Wdate" onFocus="WdatePicker()" class="validate[required]" value="${dollar!}{detailData.${detail.enName!}! }"/>
								    	 	@}
									    @}else{
									    	@if(detail.alterFlag == '0'){
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" class="Wdate" readonly  value="${dollar!}{detailData.${detail.enName!}! }"/>
								    	 	@}else{
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" class="Wdate" onFocus="WdatePicker()" value="${dollar!}{detailData.${detail.enName!}! }"/>
								    	 	@}
									    @}
			                 			</td>
				                	@}
				                	@// 3:数字框
				                	@if(detail.collectType == '3'){
				                 		<td style="text-align: center;">
				                 			@if(detail.nullFlag == '0'){
			                 					@//可更改标记
									    	 	@if(detail.alterFlag == '0'){
												     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" readonly class="validate[custom[integer]]" value="${dollar!}{detailData.${detail.enName!}! }"/>
									    	 	@}else{
												     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" class="validate[custom[integer]]" value="${dollar!}{detailData.${detail.enName!}! }"/>
									    	 	@}
										    @}else{
										    	@if(detail.alterFlag == '0'){
												     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" readonly  value="${dollar!}{detailData.${detail.enName!}! }"/>
									    	 	@}else{
												     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" value="${dollar!}{detailData.${detail.enName!}! }"/>
									    	 	@}
										    @}
			                 			</td>
				                	@}
				                	@// 4：金额框
				                	@if(detail.collectType == '4'){
				                 		<td style="text-align: center;">
				                 			@if(detail.nullFlag == '0'){
			                 					@//可更改标记
									    	 	@if(detail.alterFlag == '0'){
												     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" placeholder="0.00(单位)" readonly class="validate[custom[integer]]" value="${dollar!}{detailData.${detail.enName!}! }"/>
									    	 	@}else{
												     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" placeholder="0.00(单位)" class="validate[custom[integer]]" value="${dollar!}{detailData.${detail.enName!}! }"/>
									    	 	@}
										    @}else{
										    	@if(detail.alterFlag == '0'){
												     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" placeholder="0.00(单位)" readonly  value="${dollar!}{detailData.${detail.enName!}! }"/>
									    	 	@}else{
												     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" placeholder="0.00(单位)" value="${dollar!}{detailData.${detail.enName!}! }"/>
									    	 	@}
										    @}
			                 			</td>
				                 	@}
				                	
		            			 @}
				            </tr>
				          \@}  
						\@}     
		</table>
		
		<script type="text/javascript">
        $(document).ready(function() {
        	var index = layer.index;
            //隐藏模板tr
            $("#tb${random} tr").eq(1).hide();
            var i = trs = $("#tb${random} tr").length-2;
            
            /* $("#BtAdd").click(function() {
　　　　　			//复制一行
         		var trs = $("#tb tr");
    　　　　　		var index = trs.length-2;
                    var tr = $("#tb tr").eq(1).clone();
    　　　　　			var seq = ++i;
                    tr.find("td").get(0).innerHTML = seq;
                    var tds = tr.find("td");
                    $.each(tds,function(index1){
    					var td =tds.eq(index1);
    					var input = td.find("input,select").eq(0);
    					if(index1 <= 2){
    						var name = input.attr("name");
	    					var value = input.attr("value");
	    					
	    					if(name != "CK"){
	    						input.attr("name",name+"["+index+"]");
	    					}
    					}
    					//前面三个隐藏项不参与判断
    					if(index1 > 2){
	    					if(input[0].tagName.toLowerCase() == 'input'){
		    					var name = input.attr("name");
		    					var value = input.attr("value");
	    						input.attr("name",name+"["+index+"]");
		    					input.val("");
	    					}
	    					if(input[0].tagName.toLowerCase() == 'select'){
	    						var name = input.attr("name");
		    					var value = input.attr("value");
		    					input.attr("name",name+"["+index+"]");
		    					input.val("");
	    					}
    					}
    				});
                    tr.show();
                    tr.appendTo("#tb");
            }); */
            $("#BtAdd${random}").click(function() {
    　　　　　		//复制一行
         		var trs = $("#tb${random} tr");
    　　　　　		var index = trs.length-2;
                    var tr = $("#tb${random} tr").eq(1).clone();
    　　　　　			var seq = ++i;
                    tr.find("td").get(0).innerHTML = seq;
                    var tds = tr.find("td");
                    $.each(tds,function(index1){
    					var td =tds.eq(index1);
    					var input = td.find("input,select").eq(0);
    					var name = input.attr("name");
    					if(name != "CK${random}"){
    						input.attr("name","detailForm["+index+"]."+name);
    					}
    				});
                    tr.show();
                    tr.appendTo("#tb${random}");
                });
            $("#BtDel${random}").click(function() {
            	var rows=$("input[name='CK${random}']:checked");
        		if(rows.length==0||rows.length==null){
        			layer.msg("请选择要删除的数据！");
        		}else{
	                $("#tb${random} tr:gt(1)").each(function() {
	                    if ($(this).find("#CK${random}").get(0).checked == true) {
	                        $(this).remove();
	                    }
	                });
	                i = 0;
	                $("#tb${random} tr:gt(1)").each(function() {
	                    $(this).find("td").get(0).innerHTML = ++i;
	                });
	                $("#CKA${random}").attr("checked", false);
        		}
            });
            $("#CKA${random}").click(function() {
                $("#tb${random} tr:gt(1)").each(function() {
                    $(this).find("#CK${random}").get(0).checked = $("#CKA${random}").get(0).checked;
                });
            });
        })    
            
        
    </script>