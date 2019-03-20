(function(global,doc){
	
	global.onload = function(){
		
		var url = doc.getElementById('ajaxUrl').value;
		var searchKey = doc.getElementById('searchKey').value;
		var searchValue = doc.getElementById('searchValue').value;
		var pageNum = doc.getElementById('pageNum').value;
		
		var data = JSON.stringify({
				"searchKey" : searchKey,
				"searchValue" : searchValue,
				"pageNum" : pageNum
		});
		
		
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				doc.getElementById('table_list_template').innerHTML = this.responseText;
			}
		};
		xhttp.open("POST", url , true);
		xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xhttp.send("data=" +  data);
		
	}
	
}(window,document))