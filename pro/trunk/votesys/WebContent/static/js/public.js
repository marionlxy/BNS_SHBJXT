// 声明一个全局对象Namespace，用来注册命名空间
var Namespace = new Object();
// 全局对象仅仅存在register函数，参数为名称空间全路径，如"Grandsoft.GEA"
Namespace.register = function(fullNS){
    // 将命名空间切成N部分, 比如Grandsoft、GEA等
    var nsArray = fullNS.split('.');
    var sEval = "";
    var sNS = "";
    for (var i = 0; i < nsArray.length; i++)
    {
        if (i != 0) sNS += ".";
        sNS += nsArray[i];
        // 依次创建构造命名空间对象（假如不存在的话）的语句
        // 比如先创建Grandsoft，然后创建Grandsoft.GEA，依次下去
        sEval += "if (typeof(" + sNS + ") == 'undefined') " + sNS + " = new Object();"
    }
    if (sEval != "") eval(sEval);
};
Namespace.register("Angel");
var isIe8 = !(typeof(ie8) == "undefined");

var $curmenu,lastIndex;//最后弹窗索引
var webHistory = Webit.history;



function tableLoadSuccess(data){
	var ps = $('#table').bootstrapTable.defaults.pageSize * 40;
	$('#table').bootstrapTable( 'resetView' , {height: ps} );
}
function operatorBtn(value, row){
	return $("#rowBtn_edit").html()
		.replace(new RegExp("{row.id}","g"),row.id);
}

$(function(){
	
	/*$("html").niceScroll({autohidemode:false,zindex:1100,cursorwidth:"9px",
		 cursorcolor:"rgb(243, 129, 149)",cursorborderradius:"0px"});*/
	
	var aMenu = $("#sidebar-menu a[id]");
	var tab = $("#breadcrumb");
	//init tab
	var uText = $("#sidebar-menu li").find("[href='#"+webHistory.get()+"']").find(">span").text();
	if(webHistory.get()!=null){
		tab.append("<li class='active' data-toggle='context' data-target='#tab-menu' h='#"
				+webHistory.get()+"'>"+uText
				+"<i class='fa fa-times close'></i></li>");
	}
	//menu点击
	aMenu.on("click",function(){
		var hash = webHistory.get(),href = $(this).attr("href");
		if( ("#"+hash) == href ){
			webHistory.justShow("#");
			webHistory.go(hash);
		}
	});
	//tab切换
	tab.on("click","li",function(){
		webHistory.go($(this).attr("h"));
	});


	$("#historyMenu").on("click","li",function(){
		webHistory.go($(this).attr("h"));
	});

	//tab关闭
	tab.on("click","i",function(){
		var curHash = webHistory.get(),hash = $(this).parent().attr("h");
		if(("#"+curHash) == hash){
			var nhash = $(this).parent().next().attr("h");
			var phash = $(this).parent().prev().attr("h");
			if(nhash != undefined){
				$(this).parent().next().addClass("active");
				hash = nhash;
			}else if(phash != undefined){
				$(this).parent().prev().addClass("active");
				hash = phash;
			}else{
				location.href = "index.html";
			}
			webHistory.go(hash);
		}
		$(this).parent().remove();
		return false;
	});
	//tab右键
	$('#breadcrumb').contextmenu({
		scopes:'li',
		target:'#tab-menu',
	    onItem: function(context, e) {
	    	var t = $(e.target).data("right-menu");
	    	if(t == "all_close"){
	    		location.href = "index.html";
	    	}else if(t == "other_close"){
	    		$(context).addClass("active").siblings().remove();
	    		webHistory.go($(context).attr("h"));
	    	}
	    }
	});
	
	var $main_content = $("#fill-main-content");
	webHistory.add("ajax", function(str, action, token) {
	   $main_content.html(loadHtmlPage(str));
	   var curMenu = $("#sidebar-menu li").find("a[href='#"+token+"']");
	   changeMenu(curMenu);
	});
	webHistory.init();
	
	//排序
	$(document).on("click",".table-sort th[class*='sorting']",function(){
		var formId = $(this).parent().parent().parent().attr("formId");
		if(formId == undefined || formId == null){
			formId = "search-form";
		}
		var sort = null;
		if($(this).is(".sorting")){
			sort = "asc";
		}else if($(this).is(".sorting_asc")){
			sort = "desc";
		}else if($(this).is(".sorting_desc")){
			sort = "asc";
		}
		
		var $column = $(".table-sort th[class*='sorting']");
		$column.removeClass("sorting")
		.removeClass("sorting_asc")
		.removeClass("sorting_desc")
		.addClass("sorting");
		
		
		$(this).removeClass("sorting")
		.addClass("sorting_"+sort);
		
		var column = $(this).attr("sort");
		if(column.length == 0){
			alert("请配置排序字段")
		}else{
			var $form = $("#"+formId);
			$form.find('input[name="sortColumn"]').val(column);
			$form.find('input[name="sortWay"]').val(sort);
			paging(formId, 1);
		}
		
	});
});

function loadHtmlPage(path) {
    var result;
    $.ajax({
        url: path,
        dataType: "text",
        async: false,
        success: function(data) {
            result = data;
        }
    });
    return result;
};

function changeMenu(obj) {
	$this = $curmenu = obj, pli = $this.parents("li");
	var $sibling = $this.closest("li[data-level='1']").siblings("li.open");		
	if ($sibling.size() > 0) {
		$sibling.removeClass("open").find("li.open").removeClass("open");
		$sibling.find("ul.nav-show").attr("class", "submenu nav-hide").hide();
	}													//alert($this.attr('haschild'));
	if ($this.attr('haschild') == "false") {				
		$this.closest("li[data-level='1']").addClass("open");
		var pul = $this.parents("ul.submenu");		//所有一级菜单的子菜单对象		
		pul.attr("class", "submenu nav-show").show();
		$("#sidebar-menu").find("li").removeClass("active");
		pli.addClass("active");

		var menuArray = [];
		var level = $this.parents("li").attr("data-level");
		if(level == 2){
			var cur = $this.find(".menu-text").text();
			menuArray.push(cur);
		}else{
			var cur = $this.find(".menu-text").text();
			menuArray.push(cur);
		}
		
		pli = $this.parents("li").parents("li");
		if(pli.attr("data-level") == 2){
			pli = $this.closest("li[data-level='2']");
		}
		var array = [];
		while (pli.data("level") == 1) {
			cur = pli.find(".dropdown-toggle .menu-text");			
			$.each(cur,function(i,obj){
				array.push(obj.innerHTML);
			})
			pli = pli.parents("li");
			menuArray.push(array[0]);
			cur = $this.find(".menu-text").text();	
		}
		if (pli.data("level") > 1) {
			cur = pli.find(".dropdown-toggle .menu-text").text();			
			menuArray.push(cur);
			pli = pli.parents("li");
			cur = pli.find(".dropdown-toggle .menu-text");			
			$.each(cur,function(i,obj){
				array.push(obj.innerHTML);
			})
			menuArray.push(array[0]);
		}
		
		var crumb = $("#breadcrumb");
		crumb.html("");
		crumb.append('<li><i class="ace-icon fa fa-home home-icon"></i><a href="index.html" >首页</a></li>')
		for (var i = menuArray.length - 1; i >= 0; i--) {
			if (i == 0) {
				crumb.append('<li class="active"> ' + menuArray[i] + ' </li>');
			} else {
				crumb.append('<li> <a href="#">' + menuArray[i] + '</a> </li>');
			}
		}

		//最近访问
		var $historyM = $("#historyMenu");
		$historyM.find("li").show();

		var href = $this.attr("href");
		var $li = $historyM.find("li[h='"+href+"']");
		if($li.length > 0){
			$li.remove();
		}

		var text = '';
		if(level == 2){
			text = $this.find(".menu-text_2").text();
		}else{
			text = $this.find(".menu-text").text();
		}
		var html = '<li h="'+href +'" >'+
						'<a href="javascript:void(0)" >'+
							'<span class="mail-tag badge badge-success "></span>'+
							'<span class="success">'+text+'</span>'+
						'</a>'+
					'</li>'

		$li = $historyM.find("li");
		if($li.length <= 0){
			$historyM.append(html);
		}else{
			$historyM.find("li:first").before(html);
		}
		$historyM.find("li:first").hide();
		if($historyM.find("li").length > 1){
			$historyM.parents(".widget-toolbar").show();
		}else{
			$historyM.parents(".widget-toolbar").hide();
		}


		// $historyM.insert(html);

		// tab
		/*
		 * var tab = $("#breadcrumb"),href = $this.attr("href"); var tabTxt =
		 * $this.find(">span").text(); var tabli = tab.find("li[h='"+href+"']");
		 * tab.find("li").removeClass("active"); if(tabli.size() == 0){
		 * tab.append("<li class='active'  h='"+href+"'>" +tabTxt+"<i
		 * class='fa fa-times close'></i></li>"); }else{
		 * tabli.addClass("active"); }
		 */
	}
}

;(function($){
	var cuslayer = function(params){
		var defaults = {
			mode:'normal',
			type:1, //0：信息框（默认），1：页面层，2：iframe层，3：加载层，4：tips层。
			title:false,
			shade: [0.5, '#000'], //[遮罩透明度, 遮罩颜色]
			border:[3, 0.5, '#666'],
			closeBtn:[0, true],
			url:undefined, //请求回来弹窗的url
			data:{}, //请求弹窗携带的参数
			maxmin:true, //是否输出窗口最小化/全屏/还原按钮。 
			width:'0',
			height:'0',
			btns:2,
			btn:['确 定','取 消'],
			msg:'',
			reloadurl:false //是否url刷新,默认false当前右侧刷新
		};
		
		params = $.extend(defaults, params);
		
		var mode = params.mode;
		if(undefined != params.closebtn){
			params.closeBtn = params.closebtn;
		}
		
		if(mode == 'del' || mode == 'delete' || mode == 'page'){
			if(undefined == params.url) {
				alert("请求url未填写");
				return false;
			}
		}
		if (mode == 'delSelect') {
			var rows = $('#' + params.table).bootstrapTable(
					"getAllSelections");
			if (rows.length <= 0) {
				layer.msg('<span class="red bigger-120">请先选择数据</span>');
				return;
			} else {
				var ids = [];
				for (var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				params.data = {
					ids : ids
				};
			}
		}
		if (mode == 'del' || mode == 'delete' || mode == 'delSelect') {
			if (mode == 'delSelect') {
				var rows = $('#' + params.table).bootstrapTable(
						"getAllSelections");
				if (rows.length <= 0) {
					layer.msg('<span class="red bigger-120">请先选择数据</span>');
					return;
				}if (rows.length > 1) {
					layer.msg('<span class="red bigger-120">请选择一条数据</span>');
					return;
				}  else {
					var id = [];
					for (var i = 0; i < rows.length; i++) {
						id.push(rows[i].id);
					}
					params.data = {
						id : id
					};
				}
			}
			var loadi;
			layer.confirm(params.msg,function(index){
				$.ajax({
					url:params.url,
					data:params.data,
					type:'post',
					beforeSend:function(){
						loadi = layer.load(5,0);
					}
				}).done(function(data){
					layer.close(loadi);
        			if(data<0) {
        				if(params.success == undefined){
        					layer.msg('<span class="red bigger-120">删除成功111</span>', 1, 1,function(){
            					if(params.reloadurl){
            						location.reload();
            					}else{
            						$curmenu.trigger('click');
            						if(params.callback != undefined) {
            							if(typeof params.callback === "string"){
            								eval(params.callback)
            							}else{
            								params.callback();
            							}
            						}
            					}
            				});
        				}else{
        					params.success();
        				}
        			}else if(data<0){
        				layer.alert('<span class="red bigger-120">删除失败，数据正在被使用！</span>', 8, !1);
        			}
        		}).fail(function(error){
        			layer.close(loadi);
        			$('#' + params.table).bootstrapTable('remove', {field: 'id', values: [parseInt(params.data.id)]});
        			layer.msg('<span class="red bigger-120">删除成功</span>', 1, 1);
        		});
			},params.title);
			return false;
		}
//============================================================================================================================
		if (mode == 'add') {
			var treeObj = $.fn.zTree.getZTreeObj(''+params.table);
			var name = '';
			var nodes = treeObj.getSelectedNodes();
			$.each(nodes,function(i,obj){
				name = obj.name;
			})
			if(name == "全部" || name == ''){
				layer.msg('<span class="red bigger-120">请先点击左侧流程</span>');
				return;
			}
			params.data = {
				name : name
			};
			var loadi; // 加载窗
			var oheight, owidth;
			$.ajax({
					url : params.url,
					data : params.data,
					type : 'post',
					dataType : 'html',
					beforeSend : function() {
						loadi = layer.load(5, 0);
					}
				})
				.done(
						function(data) {
							var layerObj; // 弹窗
							var increment = 36, dheight = params.height, dwidth = params.width;
							var _scrollHeight = $(document).scrollTop();
							var _windowHeight = $(window).height();
							var _windowWidth = $(window).width();
							var borderWidth = params.borderwidth == undefined ? 4
									: params.borderwidth;
							layer.close(loadi); // 关闭加载框
							lastIndex = $
									.layer({
										type : 1,
										title : params.title,
										maxmin : params.maxmin,
										closeBtn : params.closeBtn,
										// area : ['99%','100%'],
										border : [ borderWidth, 0.5, '#888' ],
										page : {
											html : data
										},
										success : function(layero) {
											layerObj = layero;
										},
										full : function(layero) {
											layero
													.find('.xuboxPageHtml')
													.css(
															{
																'height' : _windowHeight
																		- increment
																		- (borderWidth * 2)
															});
										},
										restore : function(layero) {
											layerObj.css({
												'width' : owidth,
												'height' : oheight
											});
											layerObj.find(".xubox_main")
													.css({
														'width' : owidth,
														'height' : oheight
													});
											layero
													.find('.xuboxPageHtml')
													.css(
															{
																'height' : oheight
																		- increment
															});
											layerObj.find(".xubox_border")
													.width(owidth + 8)
													.height(oheight + 8);
										},
										close : function(index) {
											layer.closeTips();
										}
									});
							var saveTag = layerObj
									.find('div[tag-save-btn]');
							if (saveTag.length > 0)
								increment = 36 * 2;
							oheight = layerObj.find("div.layer")
									.outerHeight()
									+ increment;
							owidth = layerObj.find("div.layer")
									.outerWidth();
							if (oheight > _windowHeight)
								oheight = _windowHeight;
							if (owidth > _windowWidth)
								owidth = _windowWidth;
							// 默认设置
							if (dheight != 0 && dheight != "") { // 显式的指定高度情况
								var bf = dheight.indexOf('%');
								if (bf != -1) { // 百分比
									oheight = parseInt($.trim(dheight
											.substring(0, bf)))
											/ 100.0 * _windowHeight;
								} else { // px
									var px = dheight.indexOf('px');
									oheight = parseInt($.trim(dheight
											.substring(0, px)));
								}
							}
							if (dwidth != 0 && dwidth != "") {// 显式的指定宽度情况
								var bf = dwidth.indexOf('%');
								if (bf != -1) {
									owidth = parseInt($.trim(dwidth
											.substring(0, bf)))
											/ 100.0 * _windowWidth;
								} else {
									var px = dwidth.indexOf('px');
									owidth = parseInt($.trim(dwidth
											.substring(0, px)));
								}
							} else {
								// owidth = 0.46 * _windowWidth;
							}
							var _posiTop = _posiLeft = 0;
							if (oheight != _windowHeight) {
								_posiTop = (_windowHeight - oheight - 8) / 2;
							} else {
								oheight = _windowHeight - 8;
							}
							if (owidth != _windowWidth) {
								_posiLeft = (_windowWidth - owidth - 8) / 2;
							} else {
								owidth = _windowWidth - 8;
							}

							layer.area(lastIndex, {
								width : owidth,
								height : oheight,
								top : _posiTop,
								left : _posiLeft
							});

							var bottom = '0px';
							if (saveTag.length > 0) {
								bottom = '-36px';
							}
							layerObj.find('.xuboxPageHtml').css({
								'overflowY' : 'auto',
								'height' : layerObj.height() - increment,
							});
							saveTag.css({
								'bottom' : bottom
							});
							layerObj.find(".xubox_page").css({
								width : '100%'
							});

						}).fail(function(err) {
					layer.msg('操作失败', 2, 8);
				});
	}
//============================================================================================================================
		if(mode == 'page' || mode == 'detail'){
			var loadi; //加载窗
			var oheight,owidth;
			$.ajax({
				url:params.url,
				data:params.data,
				type:'post',
				dataType:'html',
				beforeSend:function(){
					loadi = layer.load(5,0);
				}
			}).done(function(data){
				var layerObj; //弹窗
				var increment = 36,dheight = params.height,dwidth = params.width;
				var _scrollHeight = $(document).scrollTop();
				var _windowHeight = $(window).height();
				var _windowWidth = $(window).width();
				var borderWidth = params.borderwidth==undefined?4:params.borderwidth;
				layer.close(loadi); //关闭加载框
				lastIndex = $.layer({
				    type : 1,
				    title : params.title,
				    maxmin: params.maxmin,
				    closeBtn: params.closeBtn,
				    //area : ['99%','100%'],
				    border:[borderWidth, 0.5, '#888'],
				    page : {html:data},
				    success:function(layero){
				    	layerObj = layero;
				    },
				    full:function(layero){
				    	layero.find('.xuboxPageHtml').css({'height':_windowHeight-increment-(borderWidth*2)});
				    },
				    restore: function(layero){
				    	layerObj.css({
				    		'width':owidth,
				    		'height':oheight
				    	});
				    	layerObj.find(".xubox_main").css({
				    		'width':owidth,
				    		'height':oheight
				    	});
				    	layero.find('.xuboxPageHtml').css({'height':oheight-increment});
				    	layerObj.find(".xubox_border").width(owidth+8).height(oheight+8);
				    },
				    close: function(index){
				    	layer.closeTips();
				    }
				});
				var saveTag = layerObj.find('div[tag-save-btn]');
				if(saveTag.length > 0) increment = 36*2;
				oheight = layerObj.find("div.layer").outerHeight()+increment;
				owidth = layerObj.find("div.layer").outerWidth();
				if(oheight>_windowHeight) oheight = _windowHeight;
				if(owidth>_windowWidth) owidth = _windowWidth;
				//默认设置
				if(dheight!=0 && dheight!="") { //显式的指定高度情况
					var bf = dheight.indexOf('%');
					if(bf != -1) { //百分比
						oheight = parseInt($.trim(dheight.substring (0,bf)))/100.0 * _windowHeight;
					}else{ //px
						var px = dheight.indexOf('px');
						oheight = parseInt($.trim(dheight.substring (0,px)));
					}
				}
				if(dwidth!=0 && dwidth!="") {//显式的指定宽度情况
					var bf = dwidth.indexOf('%');
					if(bf != -1){
						owidth = parseInt($.trim(dwidth.substring (0,bf)))/100.0 * _windowWidth;
					}else{
						var px = dwidth.indexOf('px');
						owidth = parseInt($.trim(dwidth.substring (0,px)));
					}
				}else{
					//owidth = 0.46 * _windowWidth;
				}
				var _posiTop = _posiLeft = 0;
				if(oheight != _windowHeight){
					_posiTop = (_windowHeight - oheight-8)/2;
				}else{
					oheight = _windowHeight-8;
				}
				if(owidth != _windowWidth){
					_posiLeft = (_windowWidth - owidth-8)/2;
				}else{
					owidth = _windowWidth-8;
				}
				
				layer.area(lastIndex, {width:owidth,height:oheight,top:_posiTop,left:_posiLeft});
		    	
				var bottom = '0px';
				if(saveTag.length > 0) {
					bottom = '-36px';
				}
		    	layerObj.find('.xuboxPageHtml').css({
		    		'overflowY':'auto',
		    		'height':layerObj.height()-increment,
		    	});
		    	saveTag.css({'bottom':bottom});
		    	layerObj.find(".xubox_page").css({width:'100%'});
		    	
			}).fail(function(err){
				layer.msg('操作失败', 2, 8);
			});
		}
		
	};
	$.cuslayer = cuslayer;
})(jQuery);

$(function(){
	//数组操作
	Array.prototype.indexOf = function(val) {              
	    for (var i = 0; i < this.length; i++) {  
	        if (this[i] == val) return i;  
	    }  
	    return -1;  
	};
	Array.prototype.remove = function(val) {  
	    var index = this.indexOf(val);  
	    if (index > -1) {  
	        this.splice(index, 1);  
	    }  
	};
	
	//document.oncontextmenu=function(){return false;}//屏蔽右键 
	
	// 禁用Enter键表单自动提交
	document.onkeydown = function(event) {
		var target, code, tag;
		if (!event) {
			event = window.event; // 针对ie浏览器
			target = event.srcElement;
			code = event.keyCode;
			if (code == 13) {
				tag = target.tagName;
				if (tag == "TEXTAREA") {
					return true;
				} else {
					return false;
				}
			}
		} else {
			target = event.target; // 针对遵循w3c标准的浏览器，如Firefox
			code = event.keyCode;
			if (code == 13) {
				tag = target.tagName;
				if (tag == "INPUT") {
					return false;
				} else {
					return true;
				}
			}
		}
	};
	
	//属性模式
	$(document).on('click','[data-mode]',function(){
		var data = $(this).data();
		if(undefined != data['data'] && typeof data['data'] != "object") {
			data['data'] = eval("("+data.data+")");
		}
		$.cuslayer(data);
	});
	
});


//得到url的参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
};

//刷新url
function reloadUrl(){
	window.location.href = (window.location.href).split("?")[0]+"?menuid="+$curmenu.attr("id");
}

//分页
function paging(formId,pageNo){
	var $form = $("#"+formId),$target = $("#"+$form.attr('target')),spinner;
	var pageNoInput = $form.find('input[name="page"]');
	var pageSize = $form.find('input[name="pageSize"]');
	if(pageNoInput.size() == 0){
		$form.append("<input type='hidden'  name = 'page' value='1'/>");
		pageNoInput = $form.find('input[name="page"]');
	}
	if(pageSize.size() == 0){
		$form.append("<input type='hidden'  name = 'pageSize' value='10'/>");
	}
	pageNoInput.val(pageNo);
	$.ajax({
		url:$form.attr('action'),
		type:'post',
		dataType:'html',
		data:$form.serialize(),
		beforeSend:function(){
			spinner = new Spinner({color: '#3d9bce',width:20,radius:20}).spin($target[0]);
		}
	}).done(function(data){
		if ($target) {
			$target.stop();
		}
		$target.html(data);
		//如果是排序的表格，需要回显排序列
		var c = $form.find('input[name="sortColumn"]').val();
		var s = $form.find('input[name="sortWay"]').val();
		if(c != undefined && s != undefined && c.length > 0 && s.length > 0){
			var t = $target.find(".table-sort th[sort='"+c+"']");
			t.removeClass("sorting").addClass("sorting_"+s);
		}
	}).fail(function(error){
		if ($target) {
			$target.stop();
		}
		alert("请求错误!");
	})
	return false;
};

//条件查询分页
;(function($){
	$.fn.getPageList = function(settings){
		return this.each(function(){
			var $this = $(this);
			this.opt = $.extend({},$.fn.getPageList.defaults,settings);
			$("#"+this.opt.submitBtnId).on('click',function(){
				paging($this.attr("id"),1);
				return false;
			});
			if(this.opt.trigger) $("#"+this.opt.submitBtnId).trigger('click');
		});
	}
	
	$.fn.getPageList.defaults = {
		submitBtnId:"", //提交按钮
		trigger:true 
	}
})(jQuery);

//提示tip
var tip={
	errorTip:function(msg,obj,style){
		style = style == undefined?['background-color:#F26C4F; color:#fff','#F26C4F' ]:style;
		layer.tips(msg, obj, {
			guide:0,
			time: 4,
			style : style
		});
	}
};

Angel.downloadFile = function(formid,action){
   var curForm=$("#"+formid);
   var queryParams=curForm.serialize();
   queryParams = decodeURIComponent(queryParams,true);//将中文解码进行还原
   var $tempForm=$("<form style='display:none;'></form>");
   var paramArr=queryParams.split("&");
   for(var i=0;i<paramArr.length;i++){
	   var paramValue=paramArr[i].split("=");
	   var paramName=paramValue[0];
	   var paramValue=paramValue[1];
	   var $input=$("<input name='"+paramName+"' value='"+paramValue+"'/>");
		   $tempForm.append($input);
   }
   $("body").append($tempForm);
   $tempForm.attr("action",action);
   $tempForm.attr("method","post");
   $tempForm.submit();
   $tempForm.remove();
};

Angel.uploadFile = {
	init:function(fileInput){
		if(!fileInput.parent().is("form")){
			var url = fileInput.data("url");
			fileInput.wrap("<form action='"+url+
			"' method='post' enctype='multipart/form-data'></form>"); 
		}
	},
	excel:function(target){
		var $this = $(target),url = $this.data("url"),
			progress = $($this.data("progressid")),oldTxt = $this.closest(".btn").find("span").text();
		this.init($this);
		var form = $this.parent();
		form.ajaxSubmit({ 
            dataType: 'html', 
            beforeSend: function() { //开始上传 
            	$this.css({"top":"-1000px"});
            	progress.attr("data-percent","0%");
                progress.children().eq(0).width("0%");
                $this.closest(".btn").find("span").text("处理中,请稍后…");
                if(!isIe8){
                	progress.show();
                }
            }, 
            uploadProgress: function(event, position, total, percentComplete) { 
                var percentVal = percentComplete + '%'; //获得进度 
                progress.attr("data-percent",percentVal);
                progress.children().eq(0).width(percentVal);
            }, 
            success: function(data) { //成功 
            	$this.css({"top":"0px"});
            	progress.hide();
            	$this.closest(".btn").find("span").text(oldTxt);
            	$this.replaceWith($this.clone());
            	layer.alert(data, 1,function(index){
            		layer.close(index);
            		$curmenu.trigger('click');
            	});
            }, 
            error:function(xhr){ //上传失败 
            	$this.css({"top":"0px"});
            	progress.hide();
            	$this.closest(".btn").find("span").text(oldTxt);
            	$this.replaceWith($this.clone());
                //alert(xhr.responseText); //返回失败信息 
            } 
        }); 
	}
};

// Find the right method, call on correct element
function launchFullscreen(element) {

    if (!$("body").hasClass("full-screen")) {

        $("body").addClass("full-screen");

        if (element.requestFullscreen) {
            element.requestFullscreen();
        } else if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen();
        } else if (element.webkitRequestFullscreen) {
            element.webkitRequestFullscreen();
        } else if (element.msRequestFullscreen) {
            element.msRequestFullscreen();
        }

    } else {

        $("body").removeClass("full-screen");

        if (document.exitFullscreen) {
            document.exitFullscreen();
        } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
        } else if (document.webkitExitFullscreen) {
            document.webkitExitFullscreen();
        }

    }

}


/***********************************crud ztree*******************************************/
(function($) {
	
	var setting = {};
	var treeObj;	
	var opts;
	var key = null,nodeList = [];
	$.fn.krmZtree = function(options) {
		opts = $.extend( {}, $.fn.krmZtree.defaults, options);
		init();
	}
	
	function init(){
		setting = {
			view:{
				expandSpeed:100,
				selectedMulti : false,
				addHoverDom:addHoverDom,
				removeHoverDom: removeHoverDom,
				fontCss:function(treeId, treeNode) {
					return (!!treeNode.highlight) ? {"font-weight":"bold","color":"red"} : {"font-weight":"normal","color":"#333"};
				}
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: function(treeId,treeNode){
					if(treeNode.type != undefined && treeNode.type == "flow"){
						return false;
					}
					return treeNode.level == 0 ? false:true;
				},
				showRenameBtn: function(treeId,treeNode){
					if(treeNode.type != undefined && treeNode.type == "flow"){
						return false;
					}
					return treeNode.level == 0 ? false:true;
				},
				removeTitle : "删除",
				renameTitle : "编辑"
			},
			data : {
				simpleData : {
					enable : true,
					idKey : opts.idKey,
					pIdKey :opts.pIdKey,
				},
				key:{
					name:opts.nameKey
				}
			},
			callback: {
				onClick: onClickNode,
				beforeRemove:beforeRemove,
				beforeEditName: beforeEditName,
				beforeDrag: function(){return false;}
			}
		};
		
		key = $("#"+opts.searchInput);
		
		//树结构初始化
		nodeList=[]; //清除缓存
		var treeData = opts.treeData;
		
		var root = {open:true};
		root[opts.idKey] = 0;
		root[opts.nameKey] = opts.rootNodeName;
		
		treeData.splice(0, 0, root); 
	
		$.fn.zTree.init($("#"+opts.id), setting,treeData);
		treeObj = $.fn.zTree.getZTreeObj(""+opts.id);
		// 默认展开一级节点
		var nodes = treeObj.getNodesByParam("level", 0);
		for(var i=0; i<nodes.length; i++) {
			treeObj.expandNode(nodes[i], true, false, false);
		}
		
		$("#"+opts.searchAllBtn).click(function(){
			$("#"+opts.form).find("input[name=id]").val("");
			$("#"+opts.form).find("input[name=name]").val("");
			//联动查询
			//paging(opts.form,1);
			$('#'+opts.tableId).bootstrapTable( 'refresh');
			var node = treeObj.getNodeByParam(opts.idKey, 0);
			treeObj.selectNode(node,false);
			if(undefined != nodeList) {
				for(var i=0, l=nodeList.length; i<l; i++) {
					nodeList[i].highlight = false;				
					treeObj.updateNode(nodeList[i]);
				}
			}
		}).trigger("click");
		
		$("#"+opts.searchBtn).click(function(e){
			$("#"+opts.form).find("input[name=id]").val("");
			var treeObj = $.fn.zTree.getZTreeObj("treeMenu");
			treeObj.cancelSelectedNode();
			//paging(opts.form,1);
			$('#'+opts.tableId).bootstrapTable( 'refresh');
			searchNode(e);
		});
		
	}
	
	//编辑
	function beforeEditName(treeId, treeNode) {
		$.cuslayer({
			mode:'page',
			title:eval("treeNode."+opts.nameKey)+'编辑',
			height:opts.height,
			width:opts.width,
			url:opts.editUrl,
			data:{"id":treeNode.id,"parentId":treeNode.getParentNode().id}
		});
		return false;
	}
	
	//删除
	function beforeRemove(treeId, treeNode){
		var id = treeNode.id;
		$.cuslayer({
			mode:'del',
			msg:'<span class="red bigger-120">你确定删除<'+treeNode.name+'>吗?</span>',
			title:'删除操作',
			url:ctxPath +"/"+opts.delUrl,
			data:{"id":id},
			reloadurl:opts.reloadUrl
		});
		return false;
	}
	
	//划过显示添加按钮,添加
	function addHoverDom(treeId, treeNode) {
		if(treeNode.type != undefined && treeNode.type == "flow"){
			return;
		}
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='添加' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		
		if (btn) btn.bind("click", function(){
			$.cuslayer({
				mode:'page',
				title:opts.addDialogTitle,
				height:opts.height,
				width:opts.width,
				url:opts.addUrl,
				data:{"parentId":eval("treeNode."+opts.idKey)}
			});
			return false;
		});
	};
	
	//移除添加按钮
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	};
	
	//节点点击事件
	function onClickNode(event, treeId, treeNode) {
		if(treeNode.type != undefined && treeNode.type == "flow"){
			$("#flowIframe").attr("src",'./static/draw/read.html?xml='+treeNode.id);
		}else{
			$("#"+opts.form).find("input[name=name]").val("");
			var treeObj = $.fn.zTree.getZTreeObj("treeMenu");
			$("#"+opts.form).find("input[name=id]").val(eval("treeNode."+opts.idKey));

			//paging(opts.form,1); //刷新表单
			$('#'+opts.tableId).bootstrapTable( 'refresh');

			for(var i=0, l=nodeList.length; i<l; i++) {
				nodeList[i].highlight = false;
				treeObj.updateNode(nodeList[i]);
			}

		}

	}
	
	
	
	function searchNode(e) {
		// 取得输入的关键字的值
		var value = $.trim(key.get(0).value);
		
		// 按名字查询
		var keyType = opts.nameKey;
		if (key.hasClass("empty")) {
			value = "";
		}
		
		// 如果要查空字串，就退出不查了。
		if (value === "") {
			return;
		}
		updateNodes(false);
		nodeList = treeObj.getNodesByParamFuzzy(keyType, value);
		updateNodes(true);
	}
	
	function updateNodes(highlight) {
		for(var i=0, l=nodeList.length; i<l; i++) {
			nodeList[i].highlight = highlight;				
			treeObj.updateNode(nodeList[i]);
			treeObj.expandNode(nodeList[i].getParentNode(), true, false, false);
		}
	}
	
	$.fn.krmZtree.defaults = {
		readyOnly:false,
		editUrl:'',
		delUrl:'',
		addUrl:'',
		treeData:{},
		addDialogTitle:'添加',
		form:'search-form',
		searchBtn:'search-btn',
		searchInput:'search-input',
		searchAllBtn:'search-all-btn',
		height:'0',
		width:'50%',
		idKey:'id',
		pIdKey:'parentId',
		nameKey:'name',
		tableId:'table',
		reloadUrl:false,
		rootNodeName:"全部",
		id:'treeMenu'
	}
	
		
})(jQuery);


/***********************************krmPullDownTree*******************************************/
(function($) {

	var treeData = [];
	var treeObj = null;
	var setting = null;
	var list = [];
	var pullDownTreeList = [];

	$.fn.krmPullDownTree = function(options) {
		opts = $.extend( {}, $.fn.krmPullDownTree.defaults, options);
		
		init();
	}

	function init(){
		
		$("div.dropdown-menu").on("click", ".ztree .switch,#pullDownTreeSearch"+opts.sort+",.scroll-track", function(e) {e.stopPropagation(); });

		$('.scrollable').each(function () {
			var $this = $(this);
			$(this).ace_scroll({
				size: $this.data('height') || 250
			});
		});

		setting = {
			view:{
				expandSpeed:100,
				selectedMulti : false,
				fontCss:function(treeId, treeNode) {
					return (!!treeNode.highlight) ? {"font-weight":"bold","color":"red"} : {"font-weight":"normal","color":"#333"};
				}
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pid"
				},
				key:{name:"name"}
			},
			callback:{
				onClick: selectClick,
				onExpand:onExpand
			}
		};

		$.ajax({
			url: opts.url,
			dataType: "json",
			async: false,
			success: function(data) {
				treeData = data;
				if(opts.isRoot){
					var root = {id:0,name:"全部",open:true};
					treeData[treeData.length] = root;
				}
				treeObj = $.fn.zTree.init($("#"+opts.id), setting, treeData);
				if(opts.isRoot){
					var nodes = treeObj.getNodesByParam("level", 0);
					for(var i=0; i<nodes.length; i++) {
						treeObj.expandNode(nodes[i], true, false, false);
					}
				}
				if(opts.value != 0){
					var curNode = treeObj.getNodesByParam("id",opts.value )[0];
					getParentNames(curNode);
				}
			}
		});

		$("#pullDownTreeSearch"+opts.sort).bind("change keydown cut input propertychange", searchNode);

	}

	function selectClick(e, treeId, treeNode){
		$("#pullDownTreeCurId"+opts.sort).val(treeNode.id);
		getParentNames(treeNode);
	}

	function onExpand(){
		$('.scrollable').ace_scroll('reset');
	}
	function getParentNames(treeNode){
		var pids = [];


		var p = treeNode.getParentNode();
		var pnames = "";
		while(p != undefined){
			if(p.id != 0){
				pnames = p.name + "-"+ pnames;
			}
			p = p.getParentNode();
		}

		$("#pullDownTreeCurName"+opts.sort).text( pnames+treeNode.name);
	}

	function searchNode() {
		// 取得输入的关键字的值
		var value = $.trim($("#pullDownTreeSearch"+opts.sort).get(0).value);

		// 按名字查询
		var keyType = "name";

		// 如果要查空字串，就退出不查了。
		if (value === "") {
			for(var i=0, l=pullDownTreeList.length; i<l; i++) {
				pullDownTreeList[i].highlight = false;
				treeObj.updateNode(pullDownTreeList[i]);
			}
			return;
		}
		updateNodes(false);
		pullDownTreeList = treeObj.getNodesByParamFuzzy(keyType, value);
		updateNodes(true);
	}
	function updateNodes(highlight) {
		for(var i=0, l=pullDownTreeList.length; i<l; i++) {
			pullDownTreeList[i].highlight = highlight;
			treeObj.updateNode(pullDownTreeList[i]);
			treeObj.expandNode(pullDownTreeList[i].getParentNode(), true, false, false);
		}
	}
	$.fn.krmPullDownTree.defaults = {
			id:"",
			sort:0,
			url:'',
			value:0,
			isRoot:false,
			rootNodeName:'请选择归属机构'
	}

})(jQuery);

