/******************************************************
不支持如下情况：
1.多行标头
2.表格里面嵌套表
3.同一个页面中多个不同样式的表格
*******************************************************/

/*--- define gridTable parament--------------------------*/
var normalColor 	 = ""; 									//normal背景颜色
var normalColor2 	 = ""; 									//normal背景颜色2
var horizonLine		 ="1px solid #b3d3ef";					//横线
var verticalLine	 ="";									//竖线
var tableBorderColor ="#959595";							//表格边框颜色
var overColor   	 = "#f2f8ff";							//mouseover背景颜色
var clickColor  	 = "#dbebff"; 							//按下去背景颜色
var normalFontColor	 = "#000000"; 							//normal文字颜色
var clickFontColor 	 = ""; 									//按下去文字颜色
var cursor		 	 = "default"; 							//鼠标形状
var handleVlineColor = "#f4f4f4"; 							//拖拽手柄的颜色
var handleColor  	 = "#ffffff"; 							//拖拽手柄边缘的颜色
var handleOpacity  	 = "55"; 								//拖拽手柄的透明度(0-100)
var gridWidth		 = "100%";  							//初始化表格的宽度
var cellPadding		 = 3;  									//初始化表格的间隙，单位px

var isTitle	 = true; 										//是否需要表头，true=要
var titleHeight		 = "21";  								//表头的高度
var titleBgColor	 = "#f4f4f4";  							//表头背景颜色
var titleBgImg		 = "../../images/grid_head.gif";  			//表头背景图片
var titlePaddingTop	 = "3px";  								//表头的文字上边距




var eGridRowIndex = null;
var eGridMouseStatus = 0;
var eGridSelectRecords = 0;

/*--- define css--------------------------*/
var cssString = "<style type='text/css' id='EGrid_Sstyle'>"
	cssString +=".EGridTable { }"
	cssString +=".EGrid_ResizeHandle{ position:relative; background-color:"+handleColor+"; filter:alpha(Opacity="+handleOpacity+"); width:2px; height:"+(titleHeight-4)+"px; margin-top:1px; border-left:1px solid gray; z-index:1; display:inline; font-size:0px; left:expression(this.parentElement.offsetWidth-2); cursor:col-resize; float:left;}"
	cssString +=".EGrid_fixedColumn { position:relative; background-color:"+handleColor+"; filter:alpha(Opacity="+handleOpacity+"); width:1px; height:"+(titleHeight-4)+"px; margin-top:1px; border-left:1px solid gray; z-index:1; display:inline; font-size:0px; left:expression(this.parentElement.offsetWidth-1); float:left;}"
	cssString +=".EGrid_titleWord { width:expression(this.parentElement.offsetWidth-7); padding-top:"+titlePaddingTop+"; text-overflow:ellipsis; -o-text-overflow:ellipsis; white-space:nowrap; display:block; overflow:hidden;  float:left;}"
	cssString +=".EGridTable { table-layout:fixed; padding:0px 0px; width:"+gridWidth+"; }"

var gt=document.getElementsByTagName("table");
	if(gt){for (gtl=0;gtl<gt.length;gtl++){
			if(gt[gtl].className == "EGridTable"){
					cssString +="#"+gt[gtl].id+"  td{ text-overflow:ellipsis; -o-text-overflow:ellipsis; white-space:nowrap; display:block; overflow:hidden; border-right:"+verticalLine+"; padding:"+cellPadding+"px; padding-left:6px; border-bottom:"+ horizonLine+"; cursor:"+cursor+";}"
	}}}
	
	cssString +=".EGrid_tHead{ text-overflow:ellipsis; -o-text-overflow:ellipsis; white-space:nowrap; display:block; overflow:hidden; background-color:"+titleBgColor+"; height:"+titleHeight+"px; background-image:url("+titleBgImg+"); cursor:"+cursor+"; }"
	cssString +=".EGrid_pagination { border-top:1px solid #bebebe; background-color:#ddefff; padding:4px;}"
	cssString +=".EGrid_floatLeft { text-overflow:ellipsis; -o-text-overflow:ellipsis; white-space:nowrap; overflow:hidden; float:left;}"
	cssString +=".EGrid_floatRight { text-overflow:ellipsis; -o-text-overflow:ellipsis; white-space:nowrap; overflow:hidden; float:right; }"
	cssString +=".noRecordTd { height:75px; background-color:#f8f8f8; color:#aaaaaa; font-size:14px; text-align:center;font-weight:bold; padding-top:28px; }"
	cssString +=".EGrid_checkbox { height:15px; width:15px;}"
	cssString +=".currentPage { font-weight:bold; color:red;}"
	cssString +="</style>"
document.write(cssString);



function eGridCheckSelectRecords(){
	if(document.getElementById("eGridRecords")){
		document.getElementById("eGridRecords").innerText = eGridSelectRecords;
		}
	}

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
			var newWidth=obj.parentTdW+event.clientX-obj.mouseDownX;	//新宽度
			if(newWidth>0){
				obj.parentElement.style.width = newWidth;
				obj.parentElement.parentElement.parentElement.parentElement.style.width=obj.parentTableW+event.clientX-obj.mouseDownX;
}}
function MouseUpToResize(obj){
	obj.mouseDownX=0;
	obj.releaseCapture();
}


var gt=document.getElementsByTagName("table");

if(gt){for (gtl=0;gtl<gt.length;gtl++){
		if(gt[gtl].className == "EGridTable"){
			gt[gtl].cellPadding = "0";
			gt[gtl].cellSpacing = "0";

var trl=gt[gtl].getElementsByTagName("tr");


/*--- for grid--------------------------*/
window.onload = function(){




/*--- attach drag & drop event--------------------------*/
var thl=trl[0].cells;   //table title
	for(i=0;i<thl.length;i++){
		if(thl[i].className =="eFixed"){	thl[i].innerHTML = "<div class='EGrid_fixedColumn'></div><div class='EGrid_titleWord'>" +thl[i].innerHTML+"</div>";}
		else{								thl[i].innerHTML = "<div class='EGrid_ResizeHandle' onmousedown='MouseDownToResize(this);' onmousemove='MouseMoveToResize(this);' onmouseup='MouseUpToResize(this);'></div><div class='EGrid_titleWord'>" +thl[i].innerHTML+"</div>";}
		
		thl[i].className = "EGrid_tHead";
		//thl[i].style.borderRight = "1px solid "+handleVlineColor;
		thl[i].style.borderTop = "1px solid "+tableBorderColor;
		thl[i].style.borderBottom = "1px solid "+tableBorderColor;
		thl[i].style.padding = "0px";
}


if(trl.length < 2){   // no date, the table has only 1 line as the head
	var TrlNoRecord = document.createElement("TR");;
	gt[gtl].getElementsByTagName("tbody")[0].appendChild(TrlNoRecord);

	var TdlNoRecord = document.createElement("TD");
	TrlNoRecord.appendChild(TdlNoRecord);

	TdlNoRecord.className = "noRecordTd";
	TdlNoRecord.colSpan = thl.length;
	TdlNoRecord.innerHTML = "No Records";
}

else{
	
for (i=1; i<trl.length; i++){
	
		if((i % 2) == 0){trl[i].style.backgroundColor=normalColor;}
		if((i % 2) == 1){trl[i].style.backgroundColor=normalColor2;}
		
	
	for(k=0;k<trl[i].cells.length;k++){ if(trl[i].cells[k].innerText.length >=2){trl[i].cells[k].title = trl[i].cells[k].innerText.substring(0,32);}}	//for cell toolTips 
	
	var inputType = trl[i].getElementsByTagName("input") 	//fix checkbox's size
		for(k=0; k< inputType.length; k++){
			if(inputType[k].type == "checkbox" || inputType[k].type == "radio"){inputType[k].className = "EGrid_checkbox";}}

	trl[i].onmouseover = function eGrid1(){	 //onmouseover
		//if(eGridMouseStatus == 0){
			if (this.style.backgroundColor != clickColor) {
				this.style.backgroundColor = overColor;
			}
		//}
	}
	
	/*
	trl[i].onmouseenter  = function eGrid221(){
			if(eGridMouseStatus == 1){

				this.style.backgroundColor = clickColor;
				var m_Max = Math.max(eGridRowIndex,this.sectionRowIndex);
				var m_Min = Math.min(eGridRowIndex,this.sectionRowIndex);
				for (j=0; j<m_Min; j++)
				{	
					if(trl[j].style.backgroundColor == clickColor)
					{
						if (j%2 == 0){
							trl[j].style.backgroundColor = normalColor;
							}
							else{
							trl[j].style.backgroundColor = normalColor2;	
						}
					}
				}
				for (j=m_Min; j<=m_Max; j++)
				{	
					if(trl[j].style.backgroundColor != clickColor)
					{
						trl[j].style.backgroundColor = clickColor;
					}
				}
				for (j=m_Max+1; j<trl.length; j++)
				{	
					if(trl[j].style.backgroundColor == clickColor)
					{
						if (j%2 == 0){
							trl[j].style.backgroundColor = normalColor;
							}
							else{
							trl[j].style.backgroundColor = normalColor2;	
						}
					}
				}
				eGridSelectRecords = m_Max - m_Min +1;
				eGridCheckSelectRecords();
			}
		}
		*/

	if((i % 2) == 0){	trl[i].onmouseout =  function $eGrid21(){	if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = normalColor;  }}}
	if((i % 2) == 1){	trl[i].onmouseout =  function $eGrid22(){	if (this.style.backgroundColor != clickColor) {this.style.backgroundColor = normalColor2; }}}	

	trl[i].onmousedown = function eGrid3(){	
				
			if(event.ctrlKey == true){								//press ctrl key to multiply select
						if(this.style.backgroundColor == clickColor){ this.style.backgroundColor = overColor; eGridSelectRecords--}
						else{this.style.backgroundColor = clickColor; eGridSelectRecords++}
						
						eGridCheckSelectRecords();
						resetCheckbox();
					}
		
			if(event.shiftKey == true){ 							//press shift key to multiply select
					for (j=0; j<trl.length; j++){
							if((j % 2) == 0){trl[j].style.backgroundColor=normalColor;}
							if((j % 2) == 1){trl[j].style.backgroundColor=normalColor2;}
						}
					this.style.backgroundColor = clickColor;
						
	
					if(eGridRowIndex<this.sectionRowIndex){
							for (j=0; j<=this.sectionRowIndex-eGridRowIndex; j++){
									trl[eGridRowIndex+j].style.backgroundColor = clickColor;
								}
							eGridSelectRecords = this.sectionRowIndex-eGridRowIndex+1;
						}
					if(eGridRowIndex>this.sectionRowIndex){
							for (j=0; j<=eGridRowIndex-this.sectionRowIndex; j++){
									trl[this.sectionRowIndex+j].style.backgroundColor = clickColor;
								}
							eGridSelectRecords = eGridRowIndex-this.sectionRowIndex+1;
						}
						eGridCheckSelectRecords();
						resetCheckbox();
				}

			if(event.ctrlKey == false && event.shiftKey == false ){  		//single select
					for (j=0; j<trl.length; j++){
						if((j % 2) == 0){trl[j].style.backgroundColor=normalColor;}
						if((j % 2) == 1){trl[j].style.backgroundColor=normalColor2;}
					}
					this.style.backgroundColor = clickColor;
					eGridRowIndex = this.sectionRowIndex;
					//eGridMouseStatus = 1;
					eGridSelectRecords = 1;
					eGridCheckSelectRecords();
					resetCheckbox();
				}
		}


		/*
		trl[i].onmouseup = function eGrid42(){	
			if(event.ctrlKey == false && event.shiftKey == false ){ 
				
				for (j=0; j<trl.length; j++){
						if((j % 2) == 0){trl[j].style.backgroundColor=normalColor;}
						if((j % 2) == 1){trl[j].style.backgroundColor=normalColor2;}
					}
					this.style.backgroundColor = clickColor;
				if(eGridRowIndex<this.sectionRowIndex){
						for (j=0; j<=this.sectionRowIndex-eGridRowIndex; j++){
								trl[eGridRowIndex+j].style.backgroundColor = clickColor;
							}
						eGridSelectRecords = this.sectionRowIndex-eGridRowIndex+1;
					}
				if(eGridRowIndex>this.sectionRowIndex){
						for (j=0; j<=eGridRowIndex-this.sectionRowIndex; j++){
								trl[this.sectionRowIndex+j].style.backgroundColor = clickColor;
							}
						 eGridSelectRecords = eGridRowIndex-this.sectionRowIndex+1;
					}
					
				//eGridMouseStatus = 0;
				eGridCheckSelectRecords();
				
			}
		}
		*/

		trl[i].onkeyup  = function eGrid5(){  								//delete items
			if(event.keyCode == 46){
					alert('Are you sure to delete this item(s)?');
				}
		}
		
		trl[i].onkeydown  = function eGrid6(){								//press ctrl+A to multiply select all items
			if( event.ctrlKey == true && event.keyCode == 65){
					for (j=0; j<i; j++){
							trl[j].style.backgroundColor = clickColor;
					}
					 eGridSelectRecords = i-1;
					 eGridCheckSelectRecords();
					 resetCheckbox();
				}
		}


}}}}}}


document.attachEvent("onclick",hideFloat);
function hideFloat(){
	if(document.getElementById("eGridTableSelectRecordsDiv") && event.srcElement.id != "eGridselectOptionIcon"){
		hideDiv("eGridTableSelectRecordsDiv");
	}
}

function sellectPageRecords(){
for (j=0; j<trl.length; j++){	trl[j].style.backgroundColor = clickColor;	}
		if(document.getElementById("eGridselectCheckbox")){
				document.getElementById("eGridselectCheckbox").checked = true;
				document.getElementById("eGridselectCheckbox").disabled = false;
			}
			eGridSelectRecords = trl.length-1;
			eGridCheckSelectRecords();
}

function sellectAllRecords(){
for (j=0; j<trl.length; j++){	trl[j].style.backgroundColor = clickColor;	}

		if(document.getElementById("eGridselectCheckbox")){
				document.getElementById("eGridselectCheckbox").checked = true;
				document.getElementById("eGridselectCheckbox").disabled = true;
			}
			
			eGridSelectRecords = 192;
			eGridCheckSelectRecords();
}

function sellectNoRecords(){
		for (j=0; j<trl.length; j++){
			if((j % 2) == 0){trl[j].style.backgroundColor=normalColor;}
			if((j % 2) == 1){trl[j].style.backgroundColor=normalColor2;}
		}
		if(document.getElementById("eGridselectCheckbox")){
				document.getElementById("eGridselectCheckbox").checked = false;
				document.getElementById("eGridselectCheckbox").disabled = false;
			}
			eGridSelectRecords = 0;
			eGridCheckSelectRecords();
}

function resetCheckbox(){
	if(document.getElementById("eGridselectCheckbox")){
		if(eGridSelectRecords == trl.length-1){
			document.getElementById("eGridselectCheckbox").checked = true;
			}
		else{
			document.getElementById("eGridselectCheckbox").checked = false;
			}
				document.getElementById("eGridselectCheckbox").disabled = false;
			}
	}

/*
developement notes:
Ways of select items :
	1.click to select one item
	2.press ctrl key and click to pick multiply items
	3.press shift key and click to pick multiply items
	4.press ctrl+a to multiple select all items of current page
	5.click mouse and drag to multiple select all the items which mouse move over.
	6.select some item(s) and delete key in keyboard to delete this items

*/





function mouseOverOption(fromObj,selObj){
		var objCollection = document.getElementById(fromObj).getElementsByTagName("td");
		for(i=0;i<objCollection.length;i++){
				objCollection[i].style.backgroundColor = "#ffffff";
				objCollection[i].style.color = "#000000";
			}
			selObj.style.backgroundColor = "#0071ef";
			selObj.style.color = "#ffffff";
	}
	
function toSellectPageRecords(){
		hideDiv('eGridTableSelectRecordsDiv');
		sellectPageRecords();
}
	
function toSellectAllRecords(){
		hideDiv('eGridTableSelectRecordsDiv');
		sellectAllRecords();
}
	
function toSellectNoRecords(){
		hideDiv('eGridTableSelectRecordsDiv');
		sellectNoRecords();
}