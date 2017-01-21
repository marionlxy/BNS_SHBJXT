//add by colin.liu 

function isNull(obj){
	try{
		if(obj==null || obj == undefined || obj==''){
			return true;
		}
	}catch(e){
		//alert(e.description);
	}
	return false;
}



var strContextPath="..";
var strTabIndex = '0,0';
var strAllTabId = '0,0';
var strCurrentTabId = "";
function TabObject (tabId,text,url,isRefresh,disableClose,leftUrl,workUrl){
	this.tabId =tabId;
	this.text =text;
	this.url = url;
	this.leftUrl = leftUrl;
	this.workUrl = workUrl;
	this.isRefresh = isRefresh;
	this.disableClose = disableClose;
};

var tabArray = new TabArray();
function TabArray(){
	this.strTabIndex = '0,0';
	this.strAllTabId = '0,0';
	this.strCurrentTabId = "";
	this.tabObjects = {};
};

TabArray.prototype.addTabObject = function(tabId,text,url,isRefresh,disableClose,leftUrl,workUrl){
	if(isNull(this.tabObjects[tabId])){
		this.tabObjects[tabId] = new TabObject(tabId,text,url,isRefresh,disableClose,leftUrl,workUrl);
		if(isRefresh==true){
			this.strTabIndex +=','+tabId;
		}
		this.strAllTabId +=','+tabId;
		this.strCurrentTabId = tabId;
	}
};

TabArray.prototype.getTabObject = function(tabId){
	if(!isNull(this.tabObjects[tabId])){
		return this.tabObjects[tabId];
	}
	return null;
};

TabArray.prototype.removeTabObject = function(tabId){
	this.tabObjects[tabId] = null;
	
	var rnt = "";
	if(this.strTabIndex.indexOf(tabId)>-1){
		var arr = this.strTabIndex.split(',');
		for(var i=0;i<arr.length;i++){
			if(arr[i]==tabId){
				
			}else{
				rnt+=','+arr[i];
			}
		}
		this.strTabIndex = rnt.substring(1,rnt.length);
	}
	
	rnt="";
	if(this.strAllTabId.indexOf(tabId)>-1){
		var arr = this.strAllTabId.split(',');
		for(var i=0;i<arr.length;i++){
			if(arr[i]==tabId){
				
			}else{
				rnt+=','+arr[i];
			}
		}
		this.strAllTabId = rnt.substring(1,rnt.length);
	}
	//alert('this.strAllTabId:'+this.strAllTabId);
};

TabArray.prototype.getTabIndex = function(tabId){
	var index = 1;
	if(this.strTabIndex.indexOf(tabId)>-1){
		var arr = this.strTabIndex.split(',');
		for(var i=0;i<arr.length;i++){
			if(arr[i]==tabId){
				index = i;
				break;
			}
		}
	}
	return index;
}

TabArray.prototype.getNextTabId = function(tabId){
	var rnt = "";
	if(this.strAllTabId.indexOf(tabId)>-1){
		var arr = this.strAllTabId.split(',');
		for(var i=0;i<arr.length;i++){
			if(arr[i]==tabId){
				if(i+1<arr.length){
					rnt = arr[i+1];
				}else{
					if(arr.length>2&&i!=2){
						rnt = arr[2];
					}
				}
				break;
			}
		}
	}
	return rnt;
};


TabArray.prototype.getContentsFrameNewRows = function(strRows,index){
	var arrRows = strRows.split(',');
	var newRows = arrRows[0];
	for(var i=1;i<index;i++){
		newRows +=','+'0';
	}
	newRows +=','+'*';
	return newRows;
};
TabArray.prototype.toString = function(){
	var rnt ="{[strTabIndex:"+this.strTabIndex +"]\n";
		rnt +="[strAllTabId:"+this.strAllTabId+"]\n";
		rnt +="[strCurrentTabId:"+this.strCurrentTabId+"]\n";
		//rnt +="["++"]\n";
		
	return rnt;
}

function addTab(tabId,text,url,isRefresh,disableClose,isBusiness,leftUrl,workUrl,isFocus){
	if(isBusiness)
	{
	   url=url+"?tabId="+tabId;
	}   

	if((tabId+'').indexOf(',')>-1){
		return ;
	}
	if(isRefresh==true || isRefresh=='true'){
		isRefresh==true ; 
	}else{
		isRefresh = false;
	}
	
	if(disableClose==true || disableClose=='true'){
		disableClose==true;
	}else{
		disableClose = false;
	}
	
	if(isFocus==true || isFocus == 'true'){
		isFocus = true;
	}else{
		isFocus = false;
	}	
	var TabAreaTr = top.mainIframe.mainCenter.tabFrame.document.getElementById("TabAreaTr");
	if(top.mainIframe.mainCenter.tabFrame.document.getElementById(tabId)){
		//alert('tabId is exist');		
		focusTab(tabId);
		//return false;
	}
	else{
		tabArray.addTabObject(tabId,text,url,isRefresh,disableClose,leftUrl,workUrl);
		if(!isFocus){
		for(i=0;i<TabAreaTr.getElementsByTagName("img").length;i++){//?????????
		
				if(i % 3 == 0){TabAreaTr.getElementsByTagName("img")[i].src =strContextPath+"/images/tab_un_l1.gif";}
				if(i % 3 == 1){TabAreaTr.getElementsByTagName("img")[i].src =strContextPath+"/images/tab_close2.gif";}
				if(i % 3 == 2){TabAreaTr.getElementsByTagName("img")[i].src =strContextPath+"/images/tab_un_l3.gif";}
			}

		for(i=0;i<TabAreaTr.getElementsByTagName("td").length;i++){//?????????
				if(TabAreaTr.getElementsByTagName("td")[i].style.backgroundImage == "url("+strContextPath+"/images/tab_cu_l2.gif)"){
					TabAreaTr.getElementsByTagName("td")[i].style.backgroundImage = "url("+strContextPath+"/images/tab_un_l2.gif)";
				}
			}
		}
	var strHtml ="<td><table  border='0' cellspacing='0' cellpadding='0' title='"+text+"' id='"+tabId+"'>";
        strHtml +="<tr style='cursor:pointer' onclick='top.focusTab(\""+tabId+"\");'  ";
        if(disableClose==true){
        }else{
        	strHtml+= " onDblClick='top.closeTab(\""+tabId+"\");'";
        }
        strHtml +=">";
        strHtml +="<td width='4'>";
        if(!isFocus){
        strHtml +="<img src='"+strContextPath+"/images/tab_cu_l1.gif' width='4' height='27' border='0'>";
        }else{
        strHtml +="<img src='"+strContextPath+"/images/tab_un_l1.gif' width='4' height='27' border='0'>";
        }
        strHtml +="</td>";
        if(!isFocus){
        strHtml +="<td align='center' style='padding:0px 3px 0px 8px;white-space:nowrap;color:black;background-image:url("+strContextPath+"/images/tab_cu_l2.gif)'> ";
        }else{
        strHtml +="<td align='center' style='padding:0px 3px 0px 8px;white-space:nowrap;color:black;background-image:url("+strContextPath+"/images/tab_un_l2.gif)'> ";
        }
        strHtml +=text;
        strHtml +=" </td>";
        if(!isFocus){
        strHtml +=" <td width='7' align='center' style='padding-bottom:2px;background-image:url("+strContextPath+"/images/tab_cu_l2.gif)'>";
        }else{
        strHtml +=" <td width='7' align='center' style='padding-bottom:2px;background-image:url("+strContextPath+"/images/tab_un_l2.gif)'>";
        }
        
        if(disableClose==true){
        	strHtml +=" <img src='"+strContextPath+"/images/tab_close2.gif' border='0' ";
        }else if(!isFocus){
       		strHtml +=" <img src='"+strContextPath+"/images/tab_close1.gif' border='0' ";
        }else{
        	strHtml +=" <img src='"+strContextPath+"/images/tab_close2.gif' border='0' ";
        }
        if(disableClose==true){
        	strHtml +=" disableClose=true ";
        }else{
        	 strHtml +=" disableClose=false onclick='top.closeTab(\""+tabId+"\");'";
        }
        strHtml +=">";
        strHtml +="</td><td width='4'>";
        if(!isFocus){
        strHtml +="<img src='"+strContextPath+"/images/tab_cu_l3.gif' width='4' height='27' border='0'>";
        }else{
        strHtml +="<img src='"+strContextPath+"/images/tab_un_l3.gif' width='4' height='27' border='0'>";
        }
        strHtml +="</td></tr></table></td>";

	   TabAreaTr.insertCell().innerHTML += strHtml;
	   //alert(isRefresh);
	   if(isRefresh){
	   		if(!isFocus){
			top.mainIframe.mainCenter.contentsFrameSet.rows = tabArray.getContentsFrameNewRows(top.mainIframe.mainCenter.contentsFrameSet.rows,tabArray.getTabIndex(tabId));
			}
			var newContactFrame = mainIframe.mainCenter.document.createElement("frame");
			newContactFrame.id = "contentsFrame_"+tabId;
			newContactFrame.name = "contentsFrame_"+tabId;
			top.mainIframe.mainCenter.contentsFrameSet.appendChild(newContactFrame);
			top.mainIframe.mainCenter.document.all("contentsFrame_"+tabId).src=url; 
			//alert(mainIframe.mainCenter.contentsFrameSet.rows);
			//alert(tabArray.getTabIndex(tabId));
	   }else{
	   		if(!isFocus){
	   		//top.leftUrl = leftUrl;
			//top.workUrl = workUrl;
	   		top.mainIframe.mainCenter.contentFrame.location.href=url;
	   		top.mainIframe.mainCenter.contentsFrameSet.rows = tabArray.getContentsFrameNewRows(top.mainIframe.mainCenter.contentsFrameSet.rows,1);
			
	   		}
	   }
	   if(!isFocus){
		   	tabArray.strCurrentTabId=tabId;
	   }
		//alert(mainIframe.mainCenter.contentsFrameSet.rows);
		//strCurrentTabId = tabId ;
	}
	
	
//----------------this is the new sscript   ---start---
//----------------this is the new sscript
	
//alert(top.mainIframe.mainCenter.tabFrame.document.getElementById("TabAreaTable").offsetWidth);

		//alert(top.mainIframe.mainCenter.tabFrame.document.getElementById(tabId).offsetWidth)
		//alert(top.mainIframe.mainCenter.tabFrame.document.getElementById("TabAreaTable").offsetWidth);
		//alert(top.mainIframe.mainCenter.tabFrame.document.getElementById("TabAreaTable").offsetWidth);
		
		top.mainIframe.mainCenter.tabFrame.moveLR(top.mainIframe.mainCenter.tabFrame.document.getElementById(tabId).offsetWidth,true);
		top.mainIframe.mainCenter.tabFrame.checkShowButton();
// alert(top.mainIframe.mainCenter.tabFrame.document.getElementById(tabId).offsetWidth   +    "  ----   "+    top.mainIframe.mainCenter.tabFrame.document.getElementById("TabAreaTable").offsetWidth);

//----------------this is the new sscript
//----------------this is the new sscript   ---end---


}



	
//??tab
function closeTab(tabId){
	
	if(tabArray.strCurrentTabId==tabId){
		
	}else{
		return false;
	}
	var objTab = top.mainIframe.mainCenter.tabFrame.document.getElementById(tabId);
	if(objTab){
		top.mainIframe.mainCenter.tabFrame.moveLR(objTab.offsetWidth,false);
		var nextTabId ="";
		if(tabArray.strCurrentTabId==tabId){
			nextTabId = tabArray.getNextTabId(tabId);
			//???????
			if(nextTabId==""){
				return;
			}
		}else{
			nextTabId = tabArray.strCurrentTabId;
		}
		objTab.removeNode(true);
		if(tabArray.getTabObject(tabId)!=null && tabArray.getTabObject(tabId).isRefresh==true){
			var objFrm = top.mainIframe.mainCenter.document.all("contentsFrame_"+tabId);
			top.mainIframe.mainCenter.contentsFrameSet.removeChild(objFrm);
		}else{
			//mainIframe.mainCenter.contentFrame.location.href='blank.jsp';
		}
		//alert(mainIframe.mainCenter.contentsFrameSet.innerHTML);
		tabArray.removeTabObject(tabId);
		if(nextTabId!=""){
			focusTab(nextTabId);
		}
		if(top.tempDir) top.tempDir = "";
		top.mainIframe.mainCenter.tabFrame.checkShowButton();
	}
}



//??tab
function focusTab(tabId){
	var TabAreaTr = top.mainIframe.mainCenter.tabFrame.document.getElementById("TabAreaTr");//?????tr
	var objTab = top.mainIframe.mainCenter.tabFrame.document.getElementById(tabId);
	if(objTab){//?????????????????????????????????ok?
		//mainIframe.mainCenter.contentFrame.location.href=url; 
		//alert(1);
		//objTab.firstChild.firstChild.childNodes[1].innerText = text;
		//alert(2);
				for(i=0;i<TabAreaTr.getElementsByTagName("img").length;i++){//?????????
						if(i % 3 == 0){TabAreaTr.getElementsByTagName("img")[i].src =strContextPath+"/images/tab_un_l1.gif";}
						if(i % 3 == 1){TabAreaTr.getElementsByTagName("img")[i].src =strContextPath+"/images/tab_close2.gif";}
						if(i % 3 == 2){TabAreaTr.getElementsByTagName("img")[i].src =strContextPath+"/images/tab_un_l3.gif";}
					}
				for(i=0;i<TabAreaTr.getElementsByTagName("td").length;i++){//?????????
						if(TabAreaTr.getElementsByTagName("td")[i].style.backgroundImage == "url("+strContextPath+"/images/tab_cu_l2.gif)"){
							TabAreaTr.getElementsByTagName("td")[i].style.backgroundImage = "url("+strContextPath+"/images/tab_un_l2.gif)";
						}
					}
				objTab.getElementsByTagName("img")[0].src =strContextPath+"/images/tab_cu_l1.gif";
				if(objTab.getElementsByTagName("img")[1].getAttribute("disableClose")=='true'){
					
				}else{
					objTab.getElementsByTagName("img")[1].src =strContextPath+"/images/tab_close1.gif";
				}
				objTab.getElementsByTagName("img")[2].src =strContextPath+"/images/tab_cu_l3.gif";
				objTab.getElementsByTagName("td")[1].style.backgroundImage = "url("+strContextPath+"/images/tab_cu_l2.gif)";
				objTab.getElementsByTagName("td")[2].style.backgroundImage = "url("+strContextPath+"/images/tab_cu_l2.gif)";

		if(tabArray.getTabObject(tabId)!=null && tabArray.getTabObject(tabId).isRefresh==true){
			top.mainIframe.mainCenter.contentsFrameSet.rows = tabArray.getContentsFrameNewRows(top.mainIframe.mainCenter.contentsFrameSet.rows,tabArray.getTabIndex(tabId));
		}else{
			//alert(tabArray.getTabObject(tabId).url);
			//alert(mainIframe.mainCenter.frames['contentFrame'].location);
			//top.leftUrl = tabArray.getTabObject(tabId).leftUrl;
			//top.workUrl = tabArray.getTabObject(tabId).workUrl;

			top.mainIframe.mainCenter.frames['contentFrame'].location=tabArray.getTabObject(tabId).url;
			
	   		top.mainIframe.mainCenter.contentsFrameSet.rows = tabArray.getContentsFrameNewRows(top.mainIframe.mainCenter.contentsFrameSet.rows,1);
		}
		tabArray.strCurrentTabId = tabId;
		
		if(top.tempDir) top.tempDir = "";
	}
	
}


function updateTab(tabId,text,url){
	var TabAreaTr = top.mainIframe.mainCenter.tabFrame.document.getElementById("TabAreaTr");
	var objTab = top.mainIframe.mainCenter.tabFrame.document.getElementById(tabId);
	if(objTab){

				for(i=0;i<TabAreaTr.getElementsByTagName("img").length;i++){
						if(i % 3 == 0){TabAreaTr.getElementsByTagName("img")[i].src =strContextPath+"/images/tab_un_l1.gif";}
						if(i % 3 == 1){TabAreaTr.getElementsByTagName("img")[i].src =strContextPath+"/images/tab_close2.gif";}
						if(i % 3 == 2){TabAreaTr.getElementsByTagName("img")[i].src =strContextPath+"/images/tab_un_l3.gif";}
					}
				for(i=0;i<TabAreaTr.getElementsByTagName("td").length;i++){
						if(TabAreaTr.getElementsByTagName("td")[i].style.backgroundImage == "url("+strContextPath+"/images/tab_cu_l2.gif)"){
							TabAreaTr.getElementsByTagName("td")[i].style.backgroundImage = "url("+strContextPath+"/images/tab_un_l2.gif)";
						}
					}
				objTab.getElementsByTagName("img")[0].src =strContextPath+"/images/tab_cu_l1.gif";
				if(objTab.getElementsByTagName("img")[1].getAttribute("disableClose")=='true'){
					
				}else{
					objTab.getElementsByTagName("img")[1].src =strContextPath+"/images/tab_close1.gif";
				}
				objTab.getElementsByTagName("img")[2].src =strContextPath+"/images/tab_cu_l3.gif";
				objTab.getElementsByTagName("td")[1].style.backgroundImage = "url("+strContextPath+"/images/tab_cu_l2.gif)";
				objTab.getElementsByTagName("td")[2].style.backgroundImage = "url("+strContextPath+"/images/tab_cu_l2.gif)";

		if(tabArray.getTabObject(tabId)!=null && tabArray.getTabObject(tabId).isRefresh==true){
			top.mainIframe.mainCenter.contentsFrameSet.rows = tabArray.getContentsFrameNewRows(top.mainIframe.mainCenter.contentsFrameSet.rows,tabArray.getTabIndex(tabId));
		}else{

			top.mainIframe.mainCenter.frames['contentFrame'].location=tabArray.getTabObject(tabId).url;
			
	   		top.mainIframe.mainCenter.contentsFrameSet.rows = tabArray.getContentsFrameNewRows(top.mainIframe.mainCenter.contentsFrameSet.rows,1);
		}
		tabArray.strCurrentTabId = tabId;
		
		if(top.tempDir) top.tempDir = "";
	}
	
}
/**
 * ?????????????????????????????????????????????ActivityExecution???tabId
 * top.mainIframe.mainCenter.contentsFrame_ActivityExecution
 */
function getTabFrame(tabId){
	var obj = null;
	if(tabArray.getTabObject(tabId)!=null && tabArray.getTabObject(tabId).isRefresh==true){
		obj = eval('top.mainIframe.mainCenter.contentsFrame_'+tabId);
	}else{
		obj = top.mainIframe.mainCenter.contentFrame;
	}
}

//????????????
function view1(imgId,divId,content){
	//document.getElementsByName("detail").innerHTML = "";
	if(document.getElementById(divId).innerHTML != ""){
		document.getElementById(divId).innerHTML = "";
		document.getElementById(divId).style.display = "none";
		document.getElementById(imgId).src = "images/title_icon1.gif";
	}
	else{
		document.getElementById(divId).innerHTML = content;
		document.getElementById(divId).style.padding = "4px 0px 18px 32px";
		document.getElementById(divId).style.color = "#666666";
		document.getElementById(divId).style.display = "block";
		document.getElementById(imgId).src = "images/title_icon2.gif";
	}
}

function loadModule(tabId ,name,diretory,lefturl,workurl)
{
	var objTab = top.mainIframe.mainCenter.tabFrame.document.getElementById(tabId);
	var TabAreaTr = top.mainIframe.mainCenter.tabFrame.document.getElementById("TabAreaTr");
	if(objTab){		
		try{			
			if(top.mainIframe.mainCenter.tabFrame.document.getElementById(tabId)){				
				//focusTab(tabId);			
			
				for(i=0;i<TabAreaTr.getElementsByTagName("img").length;i++)
				{
					if(i % 3 == 0){TabAreaTr.getElementsByTagName("img")[i].src =strContextPath+"/images/tab_un_l1.gif";}
					if(i % 3 == 1){TabAreaTr.getElementsByTagName("img")[i].src =strContextPath+"/images/tab_close2.gif";}
					if(i % 3 == 2){TabAreaTr.getElementsByTagName("img")[i].src =strContextPath+"/images/tab_un_l3.gif";}
				}
				for(i=0;i<TabAreaTr.getElementsByTagName("td").length;i++){
					if(TabAreaTr.getElementsByTagName("td")[i].style.backgroundImage == "url("+strContextPath+"/images/tab_cu_l2.gif)")
					{
						TabAreaTr.getElementsByTagName("td")[i].style.backgroundImage = "url("+strContextPath+"/images/tab_un_l2.gif)";
					}
				}
				objTab.getElementsByTagName("img")[0].src =strContextPath+"/images/tab_cu_l1.gif";
				objTab.getElementsByTagName("img")[1].src =strContextPath+"/images/tab_close1.gif";
				objTab.getElementsByTagName("img")[2].src =strContextPath+"/images/tab_cu_l3.gif";
				objTab.getElementsByTagName("td")[1].style.backgroundImage = "url("+strContextPath+"/images/tab_cu_l2.gif)";
				objTab.getElementsByTagName("td")[2].style.backgroundImage = "url("+strContextPath+"/images/tab_cu_l2.gif)";
				//alert(tabArray.getTabObject(tabId).isRefresh);
				if(tabArray.getTabObject(tabId)!=null && tabArray.getTabObject(tabId).isRefresh==true){
					//alert("--- 1 ---" + tabArray.getContentsFrameNewRows(mainIframe.mainCenter.contentsFrameSet.rows,tabArray.getTabIndex(tabId)));
					top.mainIframe.mainCenter.contentsFrameSet.rows = tabArray.getContentsFrameNewRows(top.mainIframe.mainCenter.contentsFrameSet.rows,tabArray.getTabIndex(tabId));
				}else{
					//alert("--- 2 ---" + tabArray.getContentsFrameNewRows(mainIframe.mainCenter.contentsFrameSet.rows,1));
					//top.leftUrl = tabArray.getTabObject(tabId).leftUrl;
					//top.workUrl = tabArray.getTabObject(tabId).workUrl;
					//mainIframe.mainCenter.frames['contentFrame'].location=tabArray.getTabObject(tabId).url;
					top.mainIframe.mainCenter.contentsFrameSet.rows = tabArray.getContentsFrameNewRows(top.mainIframe.mainCenter.contentsFrameSet.rows,1);
				}
				tabArray.strCurrentTabId = tabId;
			}
			
			var objFrm
			if(tabArray.getTabObject(tabId)!=null && tabArray.getTabObject(tabId).isRefresh==true){
				objFrm = top.mainIframe.mainCenter.document.all("contentsFrame_"+tabId);			
			}else{				
				objFrm = top.mainIframe.mainCenter.contentFrame;
			}
			objFrm.loadModule(name,diretory,lefturl,workurl);
			var workMenu = objFrm.document.getElementById("workMenu");
			if(workMenu)
				workMenu.style.display = "none";
			//alert(mainIframe.mainCenter.contentsFrameSet.innerHTML);		
		}catch(e)
		{
		}
	}	
}

