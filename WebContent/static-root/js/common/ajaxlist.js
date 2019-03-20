(function(global,doc){
	var getParameter = function (name){
		   if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(global.location.search)) {
			   return decodeURIComponent(name[1]);
		   }
		   return "";
	};
	
	var goPage = function(pageNum){
		var searchKey = getParameter('searchKey');
		var searchValue = getParameter('searchValue');
		var transportUrl = doc.getElementById('transportUrl').value;
		location.href = transportUrl + "?searchKey=" + searchKey + "&searchValue=" + searchValue + "&pageNum=" + pageNum; 
	}
	
	global.onload = function(){
		var pages = doc.getElementsByClassName('pages');   
 
		for(var i=0;i<pages.length;i++){
			pages[i].onclick = function(){
				var pageNum = this.getAttribute('page');
				var toPage = isNaN(parseInt(pageNum)) ? 1 : parseInt(pageNum);
				return goPage(toPage); 
			};
		}
		
		doc.getElementById('first').onclick = function(){
				return goPage(1);
		};
		
		doc.getElementById('prev').onclick = function(){
				var pageNum = getParameter('pageNum');
				var toPage = isNaN(parseInt(pageNum)) ? 1:parseInt(pageNum);
				toPage = toPage == 1 ? 1 : toPage - 1;
				return goPage(toPage);
		};
		
		doc.getElementById('next').onclick = function(){
				var pageNum = getParameter('pageNum');
				var toPage = isNaN(parseInt(pageNum)) ? 1 : parseInt(pageNum);
				var totalPageCount = doc.getElementById('totalPageCount').value;
				toPage = toPage >= totalPageCount ? totalPageCount : toPage+1;
				return goPage(toPage);
		};
		
		doc.getElementById('end').onclick = function(){
			var toPage = doc.getElementById('totalPageCount').value;
			return goPage(toPage);
		};
	};
	
	
}(window,document));