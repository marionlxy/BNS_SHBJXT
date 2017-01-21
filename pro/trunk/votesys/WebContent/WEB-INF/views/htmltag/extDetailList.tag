@var detailList = detailList!'';
@var bxgId;
@for(detail in detailList){
	@bxgId = detail.bxgId;
@}
@var dollar = '$';

<table id="detail-table"></table>




<script type="text/javascript">
	$(function() {
		$("#detail-table").bootstrapTable({
			inconSize : 'sm',
			url : '${dollar!}{ctxPath!}/pt/menuwinconf/getExtDetDataList?groupId=${bxgId!}&projectId=${dollar}{projectId!}',
			cache: false,
			clickToSelect: true,
			columns : [{
				checkbox : true
			},
			{
				field : "id",
				title : '序号',
				formatter : function(value, row, index) {
					return index + 1;
				}
			},
			@for(detail in detailList){
				{
					field:"${detail.enName}",
					title:'${detail.mapCnName}',
					sortable:true
				}
				@if(!detailLP.last){
					,
				@}
			@}
			]
		});
	});
	
	
	
</script>