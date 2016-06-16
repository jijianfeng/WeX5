/**
 * easyloader.js 模块加载器
 * 
 */
(function(){
	var modules = {
		draggable:{
			js:'jquery.draggable.js'
		},
		droppable:{
			js:'jquery.droppable.js'
		},
		resizable:{
			js:'jquery.resizable.js'
		},
		linkbutton:{
			js:'jquery.linkbutton.js'
			//css:'linkbutton.css'
		},
		progressbar:{
			js:'jquery.progressbar.js'
			//css:'progressbar.css'
		},
		tooltip:{
			js:'jquery.tooltip.js'
			//css:'tooltip.css'
		},
		pagination:{
			js:'jquery.pagination.js',
			//css:'pagination.css',
			dependencies:['linkbutton']
		},
		datagrid:{
			js:'jquery.datagrid.js',
			//css:'datagrid.css',
			dependencies:['panel','resizable','linkbutton','pagination']
		},
		treegrid:{
			js:'jquery.treegrid.js',
			//css:'tree.css',
			dependencies:['datagrid']
		},
		propertygrid:{
			js:'jquery.propertygrid.js',
			//css:'propertygrid.css',
			dependencies:['datagrid']
		},
		panel: {
			js:'jquery.panel.js'
			//css:'panel.css'
		},
		window:{
			js:'jquery.window.js',
			//css:'window.css',
			dependencies:['resizable','draggable','panel']
		},
		dialog:{
			js:'jquery.dialog.js',
			//css:'dialog.css',
			dependencies:['linkbutton','window']
		},
		messager:{
			js:'jquery.messager.js',
			//css:'messager.css',
			dependencies:['linkbutton','window','progressbar']
		},
		layout:{
			js:'jquery.layout.js',
			//css:'layout.css',
			dependencies:['resizable','panel']
		},
		form:{
			js:'jquery.form.js'
		},
		ajaxform:{
			js:'jquery.ajaxform.js'
		},
		menu:{
			js:'jquery.menu.js'
			//css:'menu.css'
		},
		tabs:{
			js:'jquery.tabs.js',
			//css:'tabs.css',
			dependencies:['panel','linkbutton']
		},
		splitbutton:{
			js:'jquery.splitbutton.js',
			//css:'splitbutton.css',
			dependencies:['linkbutton','menu']
		},
		menubutton:{
			js:'jquery.menubutton.js',
			//css:'menubutton.css',
			dependencies:['linkbutton','menu']
		},
		accordion:{
			js:'jquery.accordion.js',
			//css:'accordion.css',
			dependencies:['panel']
		},
		calendar:{
			js:'jquery.calendar.js'
			//css:'calendar.css'
		},
		fileinput:{
			js:'jquery.fileinput.js',
			//css:'calendar.css'
			dependencies:['validatebox']
		},
		combo:{
			js:'jquery.combo.js',
			//css:'combo.css',
			dependencies:['panel','validatebox']
		},
		combobox:{
			js:'jquery.combobox.js',
			//css:'combobox.css',
			dependencies:['combo']
		},
		combotree:{
			js:'jquery.combotree.js',
			dependencies:['combo','tree']
		},
		combogrid:{
			js:'jquery.combogrid.js',
			dependencies:['combo','datagrid']
		},
		validatebox:{
			js:'jquery.validatebox.js'
			//css:'validatebox.css'
		},
		numberbox:{
			js:'jquery.numberbox.js',
			dependencies:['validatebox']
		},
		searchbox:{
			js:'jquery.searchbox.js',
			//css:'searchbox.css',
			dependencies:['menubutton']
		},
		spinner:{
			js:'jquery.spinner.js',
			//css:'spinner.css',
			dependencies:['validatebox']
		},
		numberspinner:{
			js:'jquery.numberspinner.js',
			dependencies:['spinner','numberbox']
		},
		timespinner:{
			js:'jquery.timespinner.js',
			dependencies:['spinner']
		},
		tree:{
			js:'jquery.tree.js',
			//css:'tree.css',
			dependencies:['draggable','droppable']
		},
		datebox:{
			js:'jquery.datebox.js',
			//css:'datebox.css',
			dependencies:['calendar','combo']
		},
		datetimebox:{
			js:'jquery.datetimebox.js',
			dependencies:['datebox','timespinner']
		},
		slider:{
			js:'jquery.slider.js',
			dependencies:['draggable']
		}
	};
	
	var locales = {
		'en':'easyui-lang-en.js',
		'zh_CN':'easyui-lang-zh_CN.js',
		'zh_TW':'easyui-lang-zh_TW.js'
	};
	
	var queues = {};
	
	function loadJs(url, callback){
		var done = false;
		var script = document.createElement('script');
		script.type = 'text/javascript';
		script.language = 'javascript';
		script.src = url;
		//onreadystatechange和script.readyState属性事件是IE特有的，IE不支持script.onload   
    	//firefox,chrome等其它浏览器支持只script.onload事件 
		script.onload = script.onreadystatechange = function(){
			if (!done && (!script.readyState || script.readyState == 'loaded' || script.readyState == 'complete')){
				done = true;
				script.onload = script.onreadystatechange = null;
				if (callback){
					callback.call(script);
				}
			}
		}
		document.getElementsByTagName("head")[0].appendChild(script);
	}
	
	function runJs(url, callback){
		loadJs(url, function(){
			document.getElementsByTagName("head")[0].removeChild(this);
			if (callback){
				callback();
			}
		});
	}
	
	function loadCss(url, callback){
		var link = document.createElement('link');
		link.rel = 'stylesheet';
		link.type = 'text/css';
		link.media = 'screen';
		link.href = url;
		document.getElementsByTagName('head')[0].appendChild(link);
		if (callback){
			callback.call(link);
		}
	}
	
	function loadSingle(name, callback){
		queues[name] = 'loading';
		
		var module = modules[name];
		var jsStatus = 'loading';
		var cssStatus = (easyloader.css && module['css']) ? 'loading' : 'loaded';
		
		if (easyloader.css && module['css']){
			if (/^http/i.test(module['css'])){
				var url = module['css'];
			} else {
				var url = easyloader.base + 'themes/' + easyloader.theme + '/' + module['css'];
			}
			loadCss(url, function(){
				cssStatus = 'loaded';
				if (jsStatus == 'loaded' && cssStatus == 'loaded'){
					finish();
				}
			});
		}
		
		if (/^http/i.test(module['js'])){
			var url = module['js'];
		} else {
			var url = easyloader.base + 'plugins/' + module['js'];
		}
		loadJs(url, function(){
			jsStatus = 'loaded';
			if (jsStatus == 'loaded' && cssStatus == 'loaded'){
				finish();
			}
		});
		
		function finish(){
			queues[name] = 'loaded';
			easyloader.onProgress(name);
			if (callback){
				callback();
			}
		}
	}
	
	function loadModule(name, callback){
		var mm = [];
		var doLoad = false;
		
		if (typeof name == 'string'){
			add(name);
		} else {
			for(var i=0; i<name.length; i++){
				add(name[i]);
			}
		}
		
		function add(name){
			if (!modules[name]) return;
			var d = modules[name]['dependencies'];
			if (d){
				for(var i=0; i<d.length; i++){
					add(d[i]);
				}
			}
			mm.push(name);
		}
		
		function finish(){
			if (callback){
				callback();
			}
			easyloader.onLoad(name);
		}
		
		var time = 0;
		function loadMm(){
			if (mm.length){
				var m = mm[0];	// the first module
				if (!queues[m]){
					doLoad = true;
					loadSingle(m, function(){
						mm.shift();
						loadMm();
					});
				} else if (queues[m] == 'loaded'){
					mm.shift();
					loadMm();
				} else {
					if (time < easyloader.timeout){
						time += 10;
						setTimeout(arguments.callee, 10);
					}
				}
			} else {
				if (easyloader.locale && doLoad == true && locales[easyloader.locale]){
					var url = easyloader.base + 'locale/' + locales[easyloader.locale];
					runJs(url, function(){
						finish();
					});
				} else {
					finish();
				}
			}
		}
		
		loadMm();
	}
	
	easyloader = {
		modules:modules,
		locales:locales,
		
		base:'.',
		theme:'default',
		css:true,
		locale:null,
		timeout:2000,
	
		load: function(name, callback){
			if (/\.css$/i.test(name)){
				if (/^http/i.test(name)){
					loadCss(name, callback);
				} else {
					loadCss(easyloader.base + name, callback);
				}
			} else if (/\.js$/i.test(name)){
				if (/^http/i.test(name)){
					loadJs(name, callback);
				} else {
					loadJs(easyloader.base + name, callback);
				}
			} else {
				loadModule(name, callback);
			}
		},
		
		onProgress: function(name){},
		onLoad: function(name){}
	};

	var scripts = document.getElementsByTagName('script');
	for(var i=0; i<scripts.length; i++){
		var src = scripts[i].src;
		if (!src) continue;
		var m = src.match(/easyloader\.js(\W|$)/i);
		if (m){
			easyloader.base = src.substring(0, m.index);
		}
	}

	window.using = easyloader.load;
	loadCss(easyloader.base + 'themes/' + easyloader.theme + '/' + 'plugins.css');
	
})();

/**
 * parser
 */
(function($){
	$.parser = {
		auto: true,
		onComplete: function(context){},
		plugins:['draggable','droppable','resizable','pagination',
		         'linkbutton','menu','menubutton','splitbutton','progressbar','tooltip',
				 'tree','combobox','combotree','combogrid','numberbox','validatebox','searchbox',
				 'numberspinner','timespinner','calendar','fileinput','datebox','datetimebox','slider',
				 'layout','panel','datagrid','propertygrid','treegrid','tabs','accordion','window','dialog'
		],
		parse: function(context){
			var aa = [];
			for(var i=0; i<$.parser.plugins.length; i++){
				var name = $.parser.plugins[i];
				var r = $('.jq-' + name, context);
				if (r.length){
					if (r[name]){
						r[name]();
					} else {
						aa.push({name:name,jq:r});
					}
				}
			}
			if (aa.length && window.easyloader){
				var names = [];
				for(var i=0; i<aa.length; i++){
					names.push(aa[i].name);
				}
				easyloader.load(names, function(){
					for(var i=0; i<aa.length; i++){
						var name = aa[i].name;
						var jq = aa[i].jq;
						jq[name]();
					}
					$.parser.onComplete.call($.parser, context);
				});
			} else {
				$.parser.onComplete.call($.parser, context);
			}
		},
		
		/**
		 * parse options, including standard 'data-options' attribute.
		 * 
		 * calling examples:
		 * $.parser.parseOptions(target);
		 * $.parser.parseOptions(target, ['id','title','width',{fit:'boolean',border:'boolean'},{min:'number'}]);
		 */
		parseOptions: function(target, properties){
			var t = $(target);
			var options = {};
			
			var s = $.trim(t.attr('data-options'));
			if (s){
				var first = s.substring(0,1);
				var last = s.substring(s.length-1,1);
				if (first != '{') s = '{' + s;
				if (last != '}') s = s + '}';
				options = (new Function('return ' + s))(); //json字符串转换成json对象
			}
				
			if (properties){
				var opts = {};
				for(var i=0; i<properties.length; i++){
					var pp = properties[i];
					if (typeof pp == 'string'){
						if (pp == 'width' || pp == 'height' || pp == 'left' || pp == 'top'){
							opts[pp] = parseInt(target.style[pp]) || undefined;
						} else {
							opts[pp] = t.attr(pp);
						}
					} else {
						for(var name in pp){
							var type = pp[name];
							if (type == 'boolean'){
								opts[name] = t.attr(name) ? (t.attr(name) == 'true') : undefined;
							} else if (type == 'number'){
								opts[name] = t.attr(name)=='0' ? 0 : parseFloat(t.attr(name)) || undefined;
							}
						}
					}
				}
				$.extend(options, opts);
			}
			return options;
		}
	};
	$(function(){
		if (!window.easyloader && $.parser.auto){
			$.parser.parse();
		}
	});
	
	/**
	 * extend plugin to set box model width
	 */
	$.fn._outerWidth = function(width){
		if (width == undefined){
			if (this[0] == window){
				return this.width() || document.body.clientWidth;
			}
			return this.outerWidth()||0;
		}
		return this.each(function(){
			if (!$.support.boxModel && navigator.userAgent.toLowerCase().indexOf('msie')>0){
				$(this).width(width);
			} else {
				$(this).width(width - ($(this).outerWidth() - $(this).width()));
			}
		});
	};
	
	/**
	 * extend plugin to set box model height
	 */
	$.fn._outerHeight = function(height){
		if (height == undefined){
			if (this[0] == window){
				return this.height() || document.body.clientHeight;
			}
			return this.outerHeight()||0;
		}
		return this.each(function(){
			if (!$.support.boxModel && navigator.userAgent.toLowerCase().indexOf('msie')>0){
				$(this).height(height);
			} else {
				$(this).height(height - ($(this).outerHeight() - $(this).height()));
			}
		});
	};
	
	$.fn._scrollLeft = function(left){
		if (left == undefined){
			return this.scrollLeft();
		} else {
			return this.each(function(){$(this).scrollLeft(left)});
		}
	}
	
	$.fn._propAttr = $.fn.prop || $.fn.attr;
	
	/**
	 * set or unset the fit property of parent container, return the width and height of parent container
	 */
	$.fn._fit = function(fit){
		fit = fit == undefined ? true : fit;
		var p = this.parent()[0];
		var t = this[0];
		var fcount = p.fcount || 0;
		if (fit){
			if (!t.fitted){
				t.fitted = true;
				p.fcount = fcount + 1;
				$(p).addClass('panel-noscroll');
				if (p.tagName == 'BODY'){
					$('html').addClass('panel-fit');
				}
			}
		} else {
			if (t.fitted){
				t.fitted = false;
				p.fcount = fcount - 1;
				if (p.fcount == 0){
					$(p).removeClass('panel-noscroll');
					if (p.tagName == 'BODY'){
						$('html').removeClass('panel-fit');
					}
				}
			}
		}
		return {
			width: $(p).width(),
			height: $(p).height()
		}
	}
	
})(jQuery);

if (window.jQuery){
	jQuery(function(){
		jQuery.parser.parse();
	});
}