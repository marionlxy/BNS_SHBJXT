// JavaScript Document

function showHideDiv(divId){
	if(document.getElementById(divId).style.display == "none"){
		document.getElementById(divId).style.display = "block";
	}
	else{
		document.getElementById(divId).style.display = "none";
	}
}

function showDiv(divId){
	document.getElementById(divId).style.display = "block"
}
function hideDiv(divId){
	document.getElementById(divId).style.display = "none";
}

function closeWin(){
		window.close();
		return false;
	}


function openWin(url,name,width,height){
	window.open(url,name,"toolbar=no,location=no,status=yes,menubar=no,scrollbars=no,resizable=yes,width="+width+",height="+height);
}

function openDiag(url,width,height){
	window.showModalDialog(url,"","center:yes; help:no; minimize:no; maximize:no; border:thin; status:yes; dialogWidth:"+width+"px; dialogHeight:"+height+"px;");
}

/*
window.ClearEvent=function()
{ event.cancelBubble=false;
    var sSrcTagName=event.srcElement.tagName.toLowerCase();
    return (sSrcTagName=="textarea" || sSrcTagName=="input" || sSrcTagName=="select");
}

window.ClearKey=function()
{event.cancelBubble=false;var iKeyCode=event.keyCode;
   return !(iKeyCode==78 && event.ctrlKey);
}

with (window.document)
{oncontextmenu=onselectstart=ondragstart=window.ClearEvent;
   onkeydown=window.ClearKey;
}
*/



// hide all bug html tag,include select menu
window.onbeforeunload = function EZ_fixIeBug(){
	var EZ_IeBugTag_select = document.getElementsByTagName("select");
	var EZ_IeBugTag_menu   = document.getElementsByTagName("menu");
	
	if(EZ_IeBugTag_select){
	for(i=0;i<EZ_IeBugTag_select.length;i++){
			EZ_IeBugTag_select[i].style.display = "none";
		}
	}
	
	if(EZ_IeBugTag_menu){
	for(i=0;i<EZ_IeBugTag_menu.length;i++){
			EZ_IeBugTag_menu[i].style.display = "none";
		}
	}
}
