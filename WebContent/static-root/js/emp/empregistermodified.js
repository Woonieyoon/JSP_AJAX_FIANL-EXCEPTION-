(function(global, doc) {
	global.onload = function() {
		
		var check = function(){
			var mgrValue = doc.getElementById('emp_mgr').value;
			var salValue = doc.getElementById('emp_sal').value;
			var commValue = doc.getElementById('emp_comm').value;
			if(isNaN(mgrValue) || isNaN(salValue) || isNaN(commValue)){
				alert('값을 다시 입력하세요');
				return ;
			}
		}
		
		
		var onloadSetName = function() {
			console.log("onloadSetName");
			localStorage.setItem('checkedName',doc.getElementById("emp_name").value);
			console.log(localStorage.getItem('checkedName'));
			if (doc.getElementById("form").getAttribute('value') === 'register') {
				localStorage.setItem('check', 'false');
			} else if (doc.getElementById("form").getAttribute('value') === 'update') {
				localStorage.setItem('pass', 'true');
			}
		};

		var sendForm = function() {
			console.log("sendForm");
			var checkedName = localStorage.getItem('checkedName');
			var ename = doc.getElementById("emp_name").value;
			if (!(localStorage.getItem('check') == 'true' && checkedName == ename)) {
				alert("이름 중복확인이 필요합니다.");
				return;
			}
			var f = doc.empForm;
			var value = doc.getElementById("form").getAttribute('value');
			
			check();
			if (value == "register") {
				f.action = "/bbs/empinsert.do";
			} else {
				f.action = "/bbs/empupdate.do";
			}
			f.submit();
		};

		var checkDuplicatedEname = function() {
			console.log("checkDuplicatedEname");
			var ename = doc.getElementById("emp_name").value;

			if (ename === "") {
				alert("이름을 입력해 주세요.");
				return;
			}
			var pass = localStorage.getItem('pass');
			var checkedName = localStorage.getItem('checkedName');
			var xhttp = new XMLHttpRequest();

			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					if (this.responseText == 'notexists' || (pass == 'true' && checkedName == ename)) {
						var nameConfirm = confirm("사용 가능한 이름입니다. 사용하시겠습니까?");
						if (nameConfirm) {
							localStorage.setItem('check', 'true');
							localStorage.setItem('checkedName', ename);
						}
					} else {
						localStorage.setItem('check', 'false');
						alert("중복되는 이름입니다. 다시 입력해 주세요.")
						doc.getElementById("emp_name").value = "";
						doc.getElementById("emp_name").focus();
					}
				}
			};
			xhttp.open("POST", "/bbs/duplicateempname.do", true);
			xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xhttp.send("ename=" + ename);
		};
		
		onloadSetName();
		doc.getElementById('duplicate_check').onclick = checkDuplicatedEname;
		doc.getElementById('btnRegister').onclick = sendForm;
	};

}(window, document));