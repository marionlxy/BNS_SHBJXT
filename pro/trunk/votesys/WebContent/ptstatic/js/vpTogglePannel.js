// JavaScript Document
//2008-09-04 evan.bian
function getObj(Obj){
	return document.getElementById?document.getElementById(Obj):null;
}

var keyPadStatus = "show";
var keyPadMaxHeight = 112;
var keyPadMinHeight = 0;
var keyPadPixStep = 14;

function toggleKeyPad(){
	
	if(keyPadStatus == "hidden"){
		getObj("vpKeypadClip").style.pixelHeight  = getObj("vpKeypadClip").offsetHeight + keyPadPixStep;
		if(getObj("vpKeypad").style.marginTop == ""){getObj("vpKeypad").style.marginTop = "4px"}
		getObj("vpKeypad").style.marginTop  = parseInt(getObj("vpKeypad").style.marginTop) + keyPadPixStep;
		
		if(getObj("vpKeypadClip").offsetHeight < keyPadMaxHeight){
				setTimeout("toggleKeyPad()",1);
			}
		else{
			 keyPadStatus = "show";
			}
	}else{
		getObj("vpKeypadClip").style.pixelHeight = getObj("vpKeypadClip").offsetHeight - keyPadPixStep;
		if(getObj("vpKeypad").style.marginTop == ""){getObj("vpKeypad").style.marginTop = "4px"}
		getObj("vpKeypad").style.marginTop  = parseInt(getObj("vpKeypad").style.marginTop) - keyPadPixStep;
		if(getObj("vpKeypadClip").offsetHeight > keyPadMinHeight){
			setTimeout("toggleKeyPad()",1);
			}
		else{
			 keyPadStatus = "hidden";
			} 
	}
}


var videoStatus = "show";
var videoMaxHeight = 140;
var videoMinHeight = 0;
var videoPixStep = 14;
function toggleVideo(){
	
	if(videoStatus == "hidden"){
		getObj("vpVideo").style.pixelHeight  = getObj("vpVideo").offsetHeight + videoPixStep;
		
		if(getObj("vpVideo").offsetHeight < videoMaxHeight){
				setTimeout("toggleVideo()",1);
			}
		else{
			 videoStatus = "show";
			}
	}else{
		getObj("vpVideo").style.pixelHeight = getObj("vpVideo").offsetHeight - videoPixStep;
		if(getObj("vpVideo").offsetHeight > videoMinHeight){
			setTimeout("toggleVideo()",1);
			}
		else{
			 videoStatus = "hidden";
			} 
	}
}


function toggleDiv(obj){
	if(getObj(obj).style.display == "none"){getObj(obj).style.display = "block"}
	else{getObj(obj).style.display = "none"}
}


function hideDiv(obj){
	document.getElementById(obj).style.display = "none"
}
