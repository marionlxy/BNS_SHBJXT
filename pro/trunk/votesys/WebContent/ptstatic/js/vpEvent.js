// JavaScript Document
//2008-09-04 evan.bian
function getObj(Obj){
	return document.getElementById?document.getElementById(Obj):null;
}

//------------keypad button event start
var vpKey= new Array();
vpKey[0] = "vpKey_1";
vpKey[1] = "vpKey_2";
vpKey[2] = "vpKey_3";
vpKey[3] = "vpKey_4";
vpKey[4] = "vpKey_5";
vpKey[5] = "vpKey_6";
vpKey[6] = "vpKey_7";
vpKey[7] = "vpKey_8";
vpKey[8] = "vpKey_9";
vpKey[9] = "vpKey_0";
vpKey[10] = "vpKey_star";
vpKey[11] = "vpKey_sharp";
vpKey[12] = "vpKey_f1";
vpKey[13] = "vpKey_f2";
vpKey[14] = "vpKey_f3";
vpKey[15] = "vpKey_f4";

vpKey[16] = "vpDialButton";

vpKey[17] = "hangup";
vpKey[18] = "hold";
vpKey[19] = "trans";
vpKey[20] = "mulTrans";
vpKey[21] = "conference";
vpKey[22] = "ivr";

for(i=0;i<vpKey.length;i++){
	var vpK_over = function(i){	return function(){ event.srcElement.className = vpKey[i]+"_hover";}}
	getObj(vpKey[i]).attachEvent("onmouseover",vpK_over(i));
	
	var vpK_down = function(i){	return function(){ event.srcElement.className = vpKey[i]+"_down";}}
	getObj(vpKey[i]).attachEvent("onmousedown",vpK_down(i));
	
	var vpK_up = function(i){	return function(){ event.srcElement.className = vpKey[i]+"_hover";}}
	getObj(vpKey[i]).attachEvent("onmouseup",vpK_up(i));
	
	var vpK_rest = function(i){	return function(){ event.srcElement.className = vpKey[i]+"_rest";}}
	getObj(vpKey[i]).attachEvent("onmouseout",vpK_rest(i));
}
//------------keypad button event end

//float menu start
var menu1= getObj("toolsDiv").getElementsByTagName("div");
for(i=0;i<menu1.length;i++){
	if(i != 2){
	menu1[i].attachEvent("onmouseover",function fmenuHover(){event.srcElement.style.backgroundColor = "#676767"})
	menu1[i].attachEvent("onmouseout",function fmenuOut(){event.srcElement.style.backgroundColor = "#000000"})
	}
}

var menu2= getObj("customerOperation").getElementsByTagName("div");
for(i=0;i<menu2.length;i++){
	if(i != 1 || i != 4){
	menu2[i].attachEvent("onmouseover",function fmenuHover(){event.srcElement.style.backgroundColor = "#676767"})
	menu2[i].attachEvent("onmouseout",function fmenuOut(){event.srcElement.style.backgroundColor = "#000000"})
	}
}
//float menu end


document.body.attachEvent("onclick",hideToolsMenu)
function hideToolsMenu(){
	if(event.srcElement.id != "tools"){
		document.getElementById("toolsDiv").style.display = "none"
	}
	
	if(event.srcElement.id != "customerInfoSetting"){
		document.getElementById("customerOperation").style.display = "none"
	}
}



