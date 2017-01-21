/******************************************************
不支持如下情况：
1.多行标头
2.表格里面嵌套表
3.同一个页面中多个不同样式的表格
*******************************************************/

/*--- define gridTable parament--------------------------*/
var normalColor 	 = ""; 									//normal背景颜色
var normalColor2 	 = "#EEF5FE"; 							//normal背景颜色2
var overColor   	 = "#B2CFEE";							//mouseover背景颜色
var clickColor  	 = "#3d80df"; 							//按下去背景颜色
var normalFontColor	 = "#000000"; 							//normal文字颜色
var clickFontColor 	 = "#ffffff"; 							//按下去文字颜色
var cursor		 	 = "pointer"; 							//鼠标形状
var handleColor  	 = "#ffffff"; 							//拖拽手柄的颜色
var handleOpacity  	 = "45"; 								//拖拽手柄的透明度(0-100)
var gridWidth		 = "100%";  							//初始化表格的宽度
var cellPadding		 = 3;  									//初始化表格的间隙，单位px

var isTitle	 = true; 										//是否需要表头，true=要
var titleHeight		 = "21px";  							//表头的高度
var titleBgColor	 = "#CAD9EC";  							//表头背景颜色
var titleBgImg		 = "../../images/grid_head.gif";  								//表头背景图片
var titlePaddingTop	 = "3px";  								//表头的文字上边距





/*--- define css--------------------------*/
var cssString = "<style type='text/css' id='EGrid_Sstyle'>"
	cssString +=".EGridTable {border:1px solid #959595; border-right-style:none; }"
	cssString +=".EGrid_ResizeHandle{ position:relative; background-color:"+handleColor+"; filter:alpha(Opacity="+handleOpacity+"); width:3px; height:"+titleHeight+"; z-index:1; display:inline; font-size:0px; left:expression(this.parentElement.offsetWidth-2); cursor:col-resize; float:left;}"
	cssString +=".EGrid_fixedColumn{ position:relative; background-color:"+handleColor+"; filter:alpha(Opacity="+handleOpacity+"); width:1px; height:"+titleHeight+"; z-index:1; display:inline; font-size:0px; left:expression(this.parentElement.offsetWidth-1); float:left;}"
	cssString +=".EGrid_titleWord { width:expression(this.parentElement.offsetWidth-7); padding-top:"+titlePaddingTop+"; text-overflow:ellipsis; -o-text-overflow:ellipsis; white-space:nowrap; display:block; overflow:hidden;  float:left;}"
	cssString +=".EGrid_interleaveColor { background-color:"+normalColor2+"}"
	cssString +=".EGridTable { table-layout:fixed; padding:0px 0px; width:"+gridWidth+"; }"

var gt=document.getElementsByTagName("table");
	if(gt){for (gtl=0;gtl<gt.length;gtl++){
			if(gt[gtl].className == "EGridTable"){
					cssString +="#"+gt[gtl].id+"  td{ text-overflow:ellipsis; -o-text-overflow:ellipsis; white-space:nowrap; display:block; overflow:hidden; border-right:1px solid #b3d3ef; padding:"+cellPadding+"px; }"
	}}}
	
	cssString +=".EGrid_tHead{ text-overflow:ellipsis; -o-text-overflow:ellipsis; white-space:nowrap; display:block; overflow:hidden; font-weight:bold; border-bottom:1px solid #959595; background-color:"+titleBgColor+"; height:"+titleHeight+"; background-image:url("+titleBgImg+"); cursor:default;}"
	cssString +=".EGrid_pagination { border-top:1px solid #bebebe; background-color:#ddefff; padding:4px;}"
	cssString +=".EGrid_floatLeft { text-overflow:ellipsis; -o-text-overflow:ellipsis; white-space:nowrap; overflow:hidden; float:left;}"
	cssString +=".EGrid_floatRight { text-overflow:ellipsis; -o-text-overflow:ellipsis; white-space:nowrap; overflow:hidden; float:right; }"
	cssString +=".noRecordTd { height:75px; background-color:#f5f5f5; color:#aaaaaa; font-size:18px; text-align:center;font-weight:bold; padding-top:28px; }"
	cssString +=".EGrid_checkbox { height:15px; width:15px;}"
	cssString +="</style>"
document.write(cssString);



/*--- define drag & drop js--------------------------*/
function MouseDownToResize(obj){
	obj.mouseDownX=0;
	obj.mouseDownX=event.clientX;  //记住按下去时鼠标位置
	obj.parentTdW=obj.parentElement.offsetWidth;	//记住按下去时所在列的宽度
	obj.parentTableW=obj.parentElement.parentElement.parentElement.parentElement.offsetWidth;  //记住按下去时整个表格宽度
	obj.setCapture();
}
function MouseMoveToResize(obj){
		if(!obj.mouseDownX) return false;
			var newWidth=obj.parentTdW+event.clientX-obj.mouseDownX;	//新宽度 = 
			if(newWidth>0){
				obj.parentElement.style.width = newWidth;
				obj.parentElement.parentElement.parentElement.parentElement.style.width=obj.parentTableW+event.clientX-obj.mouseDownX;
}}
function MouseUpToResize(obj){
	obj.mouseDownX=0;
	obj.releaseCapture();
}

/*--- for grid--------------------------*/
window.onload = function(){

var gt=document.getElementsByTagName("table");

if(gt){for (gtl=0;gtl<gt.length;gtl++){
		if(gt[gtl].className == "EGridTable"){
			gt[gtl].cellPadding = "0";
			gt[gtl].cellSpacing = "0";

var trl=gt[gtl].getElementsByTagName("tr");


/*--- attach drag & drop event--------------------------*/
var thl=trl[0].cells;   //table title
	for(i=0;i<thl.length;i++){
		if(thl[i].className =="eFixed"){	thl[i].innerHTML = "<div class='EGrid_fixedColumn'></div><div class='EGrid_titleWord'>" +thl[i].innerHTML+"</div>";}
		else{								thl[i].innerHTML = "<div class='EGrid_ResizeHandle' onmousedown='MouseDownToResize(this);' onmousemove='MouseMoveToResize(this);' onmouseup='MouseUpToResize(this);'></div><div class='EGrid_titleWord'>" +thl[i].innerHTML+"</div>";}
		
		thl[i].className = "EGrid_tHead";
		thl[i].style.borderRight = "1px solid #4d8ac0";
		thl[i].style.padding = "0px";
}

if(trl.length < 2){   // no date, the table has only 1 line as the head
	var TrlNoRecord = document.createElement("TR");;
	gt[gtl].getElementsByTagName("tbody")[0].appendChild(TrlNoRecord);

	var TdlNoRecord = document.createElement("TD");
	TrlNoRecord.appendChild(TdlNoRecord);

	TdlNoRecord.className = "noRecordTd";
	TdlNoRecord.colSpan = thl.length;
	TdlNoRecord.innerHTML = "无记录";
}

else{
for (i=1; i<trl.length -1; i++){
	
	if((i % 2) == 1){trl[i].className="EGrid_interleaveColor";}
	
	for(k=0;k<trl[i].cells.length;k++){ if(trl[i].cells[k].innerText.length >=2){trl[i].cells[k].title = trl[i].cells[k].innerText.substring(0,32);}	}	//title文字
	
	var inputType = trl[i].getElementsByTagName("input") 	//处理checkbox的大小
		for(k=0; k< inputType.length; k++){
			if(inputType[k].type == "checkbox" || inputType[k].type == "radio"){inputType[k].className = "EGrid_checkbox";}}

	trl[i].onmouseover = function (){	 if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = overColor;this.style.cursor = cursor;}}//onmouseover

	if((i % 2) == 0){	trl[i].onmouseout =  function (){	if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = normalColor;  }}}
	if((i % 2) == 1){	trl[i].onmouseout =  function (){	if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = normalColor2; }}}	
	
	/*trl[i].onclick = function (){	
		if (this.style.backgroundColor == clickColor){ //标记状态
			this.style.backgroundRepeat = "no-repeat";
		}
		for (j=0; j<trl.length; j++){
				trl[j].style.backgroundColor = normalColor;
				for(k=0;k<trl[j].cells.length;k++){trl[j].cells[k].style.color = normalFontColor;}
		}
			if (this.style.backgroundRepeat == "no-repeat"){ 
					this.style.backgroundColor = normalColor; 
					this.style.backgroundRepeat = "";
					for(k=0;k<this.cells.length;k++){this.cells[k].style.color = normalFontColor;}
			}
			else{	 this.style.backgroundColor = clickColor; 
					 for(k=0;k<this.cells.length;k++){this.cells[k].style.color = clickFontColor;}
			}
			return true;
	}
	*/
}}}}}}