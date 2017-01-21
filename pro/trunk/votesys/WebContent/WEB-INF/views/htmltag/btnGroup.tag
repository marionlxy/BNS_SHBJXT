	@var btninfo = btninfo!'';
	<div class="widget-body">
				<div id="list-page" class="widget-main padding-8">
				<div id="toolbar">
					<div class="form-inline" role="form">
				   @var size = btninfo.~size;
					 @for(btn in btninfo!){
						   @var length = strutil.length(btn.commtext);
						   @var css ="btn-primary";	//btn-primary样式固定按钮宽度
						   @if(length>4){
						   	 @css = "btn-success";	//btn-success按钮长度随文字自动扩展，适应于按钮文本较长的情况
						   @}
						   @if(size > 8){	//当前会议按钮较多，以8个分隔一行
							   @if(btnLP.index == 1 || btnLP.index == 9){
							   	<div class="form-group width-95">
							   @}
						   @}
							   <a id='${btn.functionCode}' class="btn ${css } btn-xs">
							   <i class="${btn.icon}"></i>
								<span>${btn.commtext}</span>
							   </a>
						   @if(size > 8){
							   @if(btnLP.index == 8 || btnLP.last){
							   	</div>
							   @}
						   @}
					 @}
					</div>
				</div>
				<table id="resList"></table>
				</div>
		</div>