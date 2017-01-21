// JavaScript Document
function MouseDownToResize(obj){
	obj.mouseDownX=event.clientX;
	obj.parentTdW=obj.parentElement.offsetWidth;
	obj.parentTableW=document.getElementById("dragAbleTable").offsetWidth;
	obj.setCapture();
}
function MouseMoveToResize(obj){
		if(!obj.mouseDownX) return false;
			var newWidth=obj.parentTdW*1+event.clientX*1-obj.mouseDownX;
			if(newWidth>25){
				obj.parentElement.style.width = newWidth;
				document.getElementById("dragAbleTable").style.width=obj.parentTableW*1+event.clientX*1-obj.mouseDownX;
		}
}
function MouseUpToResize(obj){
	obj.mouseDownX=0;
	obj.releaseCapture();
}


window.onload = function(){

var trl=document.getElementById("dataArea").getElementsByTagName("tr");

var normalColor = "";
var normalColor2 = "#EEF5FE";
var overColor   = "#B2CFEE";
var clickColor  = "#dbebff";
var normalFontColor  = "#000000";
var clickFontColor  = "#ffffff";


for (i=0; i<trl.length; i++){
		
	for(k=0;k<trl[i].cells.length;k++){ if(trl[i].cells[k].innerText.length >=2){trl[i].cells[k].title = trl[i].cells[k].innerText.substring(0,100);}}
	
	trl[i].onclick = function eGrid3(){	
		if (this.style.backgroundColor == clickColor){
			this.style.backgroundRepeat = "no-repeat";
		}

		if(event.ctrlKey == true){
					if (this.style.backgroundRepeat == "no-repeat"){ this.style.backgroundColor = normalColor;  this.style.backgroundRepeat = ""; }
					else{	this.style.backgroundColor = clickColor; }
			}

		if(event.shiftKey == true){
					/*for (j=0; j<trl.length; j++){	trl[j].style.backgroundColor = normalColor;trl[j].style.backgroundRepeat = "";}
					if (this.style.backgroundRepeat == "no-repeat"){ this.style.backgroundColor = normalColor; this.style.backgroundRepeat = ""; }
					else{	this.style.backgroundColor = clickColor; }*/
			}

		if(event.ctrlKey == false && event.shiftKey == false ){
					for (j=0; j<trl.length; j++){	trl[j].style.backgroundColor = normalColor;trl[j].style.backgroundRepeat = "";}
					if (this.style.backgroundRepeat == "no-repeat"){ this.style.backgroundColor = normalColor; this.style.backgroundRepeat = ""; }
					else{this.style.backgroundColor = clickColor; }
		}
			return true;
	}
}

var thl=document.getElementById("dragAbleTable").getElementsByTagName("th");
	for(i=0;i<thl.length;i++){
		thl[i].innerHTML = "<font class='resizeDivClass' onmousedown='MouseDownToResize(this);' onmousemove='MouseMoveToResize(this);' onmouseup='MouseUpToResize(this);'></font>" +thl[i].innerHTML;
	}
}