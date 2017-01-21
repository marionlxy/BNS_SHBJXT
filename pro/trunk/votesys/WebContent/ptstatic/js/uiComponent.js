// 	2007-05-23
// 	Evan.bian

//obj=object;sh=showHide;
//preLoad:--images/ban_bottom_left02.gif;images/ban_bottom_left03.gif



// for drag & drop --------- start
move = null; 
hmin = 0; 
wmin = 120; 
zmax = 1;
ssize = 4; 
var offsetSize= 48;
var offsetPageBorder = 32;
function Down(obj){
top.document.body.focus();
  move = 1;
  obj.x = event.x; 
  obj.y = event.y;
  obj.l = obj.offsetParent.offsetLeft; 
  obj.t = obj.offsetParent.offsetTop;
  obj.w = obj.offsetParent.offsetWidth;
  obj.h = obj.offsetParent.offsetHeight;
  obj.setCapture(); 
}

function Remove(obj){
if(move != null){
		
		//limit the obj's x position in the window
		if(obj.offsetParent.offsetLeft + obj.w >= offsetPageBorder && obj.offsetParent.offsetLeft <= document.documentElement.clientWidth-offsetPageBorder){ //if obj in the window,obj can be drag
			obj.offsetParent.style.left = event.x - obj.x + obj.l; 
			}
		if(obj.offsetParent.offsetLeft + obj.w < offsetPageBorder){//obj in the left of window,obj go back automatic
				obj.offsetParent.style.left = offsetPageBorder-obj.w; 
			}
		if(obj.offsetParent.offsetLeft > document.documentElement.clientWidth-offsetPageBorder){//obj in the right of window,obj go back automatic
				obj.offsetParent.style.left = document.documentElement.clientWidth-offsetPageBorder; 
			}
		
		//limit the obj's Y position in the window
		if(obj.offsetParent.offsetTop >= 0 && obj.offsetParent.offsetTop <= document.documentElement.clientHeight-obj.h){
			obj.offsetParent.style.top = event.y - obj.y + obj.t;
			}
		if(obj.offsetParent.offsetTop < 0){
				obj.offsetParent.style.top = 0; 
			}
		if(obj.offsetParent.offsetTop > document.documentElement.clientHeight-obj.h){
				obj.offsetParent.style.top = document.documentElement.clientHeight-obj.h; 
			}
}
}

function Resize(obj){
  if(move != null){
   obj.offsetParent.style.width = Math.max(wmin, event.x - obj.x + obj.w); 
   obj.offsetParent.style.height = Math.max(hmin, event.y - obj.y + obj.h -offsetSize);
  }
}

function ResizeH(obj){
  if(move != null){
   obj.offsetParent.style.height = Math.max(hmin, event.y - obj.y + obj.h -offsetSize);
   mainIframe.centerContentFrame.rows = (document.getElementById('popTable1').clientHeight)+',*';
  }
}

 function Hresize(obj){
  if(move != null){
   obj.offsetParent.style.width = Math.max(queue_wmin, event.x - obj.x + obj.w); 
  }
 }

function ResizeVertical(obj){
  if(move != null){
   obj.offsetParent.style.height = Math.max(hmin, event.y - obj.y + obj.h -offsetSize);
  }
}

function ResizePannel(obj){
  if(move != null){
   obj.offsetParent.style.height = Math.max(hmin, event.y - obj.y + obj.h);
  }
}

function Up(obj){
  move = null;
  obj.releaseCapture(); 
}

function Focus(obj){
  zmax = zmax +10; 
 // obj.style.zIndex = zmax; 
}
// for drag & drop --------- end





//----for marquee start---------------------//
 var marqueeFlag = 0; //0 = scrooling£¬1 = stop
 function marqueeDBclick(){
	 if(marqueeFlag == 1){ 
	 document.getElementById("marqueeMessage").start();
	 document.getElementById("marqueeStatSign").src="images/ban_bottom_light_green.gif";
	 marqueeFlag = 0;
	 }
	 else { 
	 document.getElementById("marqueeMessage").stop(); 
	  document.getElementById("marqueeStatSign").src="images/ban_bottom_light.gif";
	 marqueeFlag = 1; }
 }
 function marqueeMouseOut(){
	 if(marqueeFlag == 1){ return false; }
	 else { document.getElementById("marqueeMessage").start(); }
 }
 function marqueeMouseOver(){
	 if(marqueeFlag == 1){ return false; }
	 else { document.getElementById("marqueeMessage").stop(); }
 }
//----for marquee end---------------------// 



//----for hung component start---------------------// 
function openHung(){
	document.getElementById("hungDiv").style.height = "178px";
	document.getElementById("hungDiv").style.marginTop = "-150px";
	document.getElementById("hungIframe").style.height = "150px";
	//document.getElementById("hungIframe").src = url;
	document.getElementById("closeHung").style.display = "block";
}

function openGRSY(){
	openHung();
	document.getElementById("hungIframe").src = "common/11.htm";
	document.getElementById("ownhomepagePic").src = "images/ban_infoW_ownhomepage03.gif";
	document.getElementById("yunyingPic").src = "images/ban_infoW_yunyingM01.gif";
	document.getElementById("konkuPic").src = "images/ban_infoW_konku01.gif";
}
function openYYGL(){
	openHung();
	document.getElementById("hungIframe").src = "common/22.htm";
	document.getElementById("ownhomepagePic").src = "images/ban_infoW_ownhomepage01.gif";
	document.getElementById("yunyingPic").src = "images/ban_infoW_yunyingM03.gif";
	document.getElementById("konkuPic").src = "images/ban_infoW_konku01.gif";
}
function openZSK(){
	openHung();
	document.getElementById("hungIframe").src = "common/33.htm";
	document.getElementById("ownhomepagePic").src = "images/ban_infoW_ownhomepage01.gif";
	document.getElementById("yunyingPic").src = "images/ban_infoW_yunyingM01.gif";
	document.getElementById("konkuPic").src = "images/ban_infoW_konku03.gif";
}
	
function closeHung(){
	document.getElementById("hungDiv").style.height = "28px";
	document.getElementById("hungDiv").style.marginTop = "0px";
	document.getElementById("hungIframe").style.height = "0px";
	document.getElementById("closeHung").style.display = "none";
	document.getElementById("hungIframe").src = "";
	document.getElementById("ownhomepagePic").src = "images/ban_infoW_ownhomepage01.gif";
	document.getElementById("yunyingPic").src = "images/ban_infoW_yunyingM01.gif";
	document.getElementById("konkuPic").src = "images/ban_infoW_konku01.gif";
}
function ocHung(){
	if(document.getElementById("hungIframe").style.height < "100px"){
		document.getElementById("hungDiv").style.height = "178px";
		document.getElementById("hungDiv").style.marginTop = "-150px";
		document.getElementById("hungIframe").style.height = "150px";
		document.getElementById("closeHung").style.display = "block";
		if(document.getElementById("hungIframe").src == ""){
			document.getElementById("hungIframe").src = "common/11.htm";}
	document.getElementById("ownhomepagePic").src = "images/ban_infoW_ownhomepage03.gif";
	document.getElementById("yunyingPic").src = "images/ban_infoW_yunyingM01.gif";
	document.getElementById("konkuPic").src = "images/ban_infoW_konku01.gif";
	}
	else{
		document.getElementById("hungDiv").style.height = "28px";
		document.getElementById("hungDiv").style.marginTop = "0px";
		document.getElementById("hungIframe").style.height = "0px";
		document.getElementById("closeHung").style.display = "none";
		document.getElementById("hungIframe").src = "";
	document.getElementById("ownhomepagePic").src = "images/ban_infoW_ownhomepage01.gif";
	document.getElementById("yunyingPic").src = "images/ban_infoW_yunyingM01.gif";
	document.getElementById("konkuPic").src = "images/ban_infoW_konku01.gif";
	}
}
function outHung(){
	document.getElementById("ownhomepagePic").src = "images/ban_infoW_ownhomepage01.gif";
	document.getElementById("yunyingPic").src = "images/ban_infoW_yunyingM01.gif";
	document.getElementById("konkuPic").src = "images/ban_infoW_konku01.gif";
	event.srcElement.id;
}
//----for hung component end---------------------// 












