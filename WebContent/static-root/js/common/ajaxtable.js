(function(global, doc) {

	global.onload = function() {

		var goStart = function(pageNumber){
			console.log(pageNumber);
			var searchKey = doc.getElementById('searchKey').value;
			var searchValue = doc.getElementById('searchValue').value;
			var url = doc.getElementById('ajaxUrl').value + "?searchKey="
					+ searchKey + "&searchValue=" + searchValue + "&pageNum="
					+ pageNumber;
	
			console.log(`searchKey:${searchKey},searchValue:${searchValue},pageNum:${pageNum}`);
			
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					var json = JSON.parse(this.responseText);
					console.log(json);
					makeList(json);
					makePagination(json);
				}
				
				if(this.status === 500 && this.readyState == this.DONE){
					alert(this.responseText);
				}
			};
			
			xhttp.open("GET", url, true);
			xhttp.send();
		}
		
		goStart();
		
		var makeList = function(json){
			
			var tBody = doc.getElementById("table_list_template");
			
			if(tBody.hasChildNodes()) {   
		    	tBody.innerHTML = '';
	        }
			
			for ( let i in json.employees) {

				var tr = doc.createElement("tr");

				var empno = doc.createElement("td");
				empno.innerHTML = json.employees[i].empno;
				tr.appendChild(empno);

				var ename = doc.createElement("td");
				ename.innerHTML = json.employees[i].ename;
				tr.appendChild(ename);

				var hiredate = doc.createElement("td");
				
				var date = new Date(json.employees[i].hiredate);
	            var date = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
								
				hiredate.innerHTML = date;
				tr.appendChild(hiredate);

				var dname = doc.createElement("td");
				dname.innerHTML = json.employees[i].dname;
				tr.appendChild(dname);

				tBody.appendChild(tr);
			}
		}
		
		var makePagination = function(json){
			var pageBody = doc.getElementById("table_Page_template");
			var start_page = json.paging.startPageIndex;
			var end_page = json.paging.endPageIndex;
			var total_page = json.paging.totalPageCount;

			end_page = end_page > total_page ? total_page : end_page;

			var pagination_Form = '';
			pagination_Form = pagination_Form.concat('<tr align="center">');
			pagination_Form = pagination_Form.concat('<td colspan="4"> ');
			pagination_Form = pagination_Form
					.concat('	 <input type="hidden" id="totalPageCount" value="'
							+ total_page + '">');
			pagination_Form = pagination_Form
					.concat('	 <span id="first" style="cursor: pointer;">◀◀</span> ');
			pagination_Form = pagination_Form
					.concat('	 <span id="prev" style="cursor: pointer;">◀</span> ');

			for (let i = start_page; i <= end_page; i++) {
				pagination_Form = pagination_Form.concat('<span class="pages" id="page-' + i+ '" page="' + i+ '" style="cursor: pointer;">[' + i 	+ ']</span>');
			}

			pagination_Form = pagination_Form
					.concat('<span id="next" style="cursor: pointer;">▶</span>');
			pagination_Form = pagination_Form
					.concat('<span id="end" style="cursor: pointer;">▶▶</span>');
			pagination_Form = pagination_Form.concat('</td>');
			pagination_Form = pagination_Form.concat('</tr>');

			pageBody.innerHTML = pagination_Form;
			clickEvent();
		}

		var clickEvent = function() {
			var getParameter = function(name) {
				if (name = (new RegExp('[?&]' + encodeURIComponent(name)
						+ '=([^&]*)')).exec(global.location.search)) {
					return decodeURIComponent(name[1]);
				}
				return "";
			};

			var pages = doc.getElementsByClassName('pages');

			for (var i = 0; i < pages.length; i++) {
				pages[i].onclick = function() {
					var pageNum = this.getAttribute('page');
					var toPage = isNaN(parseInt(pageNum)) ? 1 : parseInt(pageNum);
					doc.getElementById('pageNum').value = toPage;
					return goStart(toPage);
				};
			}

			doc.getElementById('first').onclick = function() {
				return goStart(1);
			};

			doc.getElementById('prev').onclick = function() {
				var pageNum = doc.getElementById('pageNum').value;
				var toPage = isNaN(parseInt(pageNum)) ? 1 : parseInt(pageNum);
				toPage = toPage == 1 ? 1 : toPage - 1;
				return goStart(toPage);
			};

			doc.getElementById('next').onclick = function() {
				var pageNum = doc.getElementById('pageNum').value;
				var toPage = isNaN(parseInt(pageNum)) ? 1 : parseInt(pageNum);
				var totalPageCount = doc.getElementById('totalPageCount').value;
				toPage = toPage >= totalPageCount ? totalPageCount : toPage + 1;
				return goStart(toPage);
			};

			doc.getElementById('end').onclick = function() {
				var toPage = doc.getElementById('totalPageCount').value;
				return goStart(toPage);
			};
			
		}

		//======================================================================검색
		doc.getElementById('btn_search').onclick = function() {
			doc.getElementById('searchKey').value = doc.getElementById('select_searchKey').value; 
			doc.getElementById('searchValue').value = doc.getElementById('select_searchValue').value;
			console.log(`검색 데이터 : ${doc.getElementById('searchKey').value}/${doc.getElementById('searchValue').value}`);
			return goStart(1);
		};
		
		//=====================================================================사원등록
		var registerForm = doc.getElementById("employee_register_template");
		doc.getElementById('btn_register').onclick = function(){
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					var registerData = (this.responseText);
					console.log(registerData);
					registerForm.innerHTML = registerData;
					onloadSetName();
					onclickRegisterButton();
				}
			};
			
			xmlhttp.open("POST", "/bbs/ajaxpage.do" , true);
			xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xmlhttp.send("form=" + "register");
			
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
		
		
		//중복확인 , 사원등록  , 취소 ( 사원 등록 )
		var onclickRegisterButton = function(){
			doc.getElementById('duplicate_check').onclick = function(){
				checkDuplicatedEname();
			}
			
			//사원등록
			doc.getElementById('btnRegister').onclick = function(){
				onloadSetName();
				onclickRegisterButton();
				var Employee = makeEmployee();
				
				var xmlhttp = new XMLHttpRequest();
				xmlhttp.onreadystatechange = function() {
					if (this.readyState == 4 && this.status == 200) {
						var result = this.responseText;
						if(result==='success'){
							alert('성공');
							goStart(1);
							registerForm.innerHTML = '';
						}
					}
					
					//에러처리를 위함
					if(this.status === 500 && this.readyState == this.DONE){
						alert(JSON.parse(this.responseText).errorText);
					}
				};
				
				xmlhttp.open("POST", "/bbs/ajaxinsert.do" , true);
				xmlhttp.setRequestHeader("Content-type","application/json");
				//X-XMLHttpRequest는 우리가 만들어줌. 에러 처리등을 위함을 위해서
				xmlhttp.setRequestHeader("X-XMLHttpRequest","true");
				xmlhttp.send(JSON.stringify(Employee));
			}
			
			doc.getElementById('btnRegister_cancel').onclick = function(){
				registerForm.innerHTML = '';
			}
		};
		
		
		var makeEmployee = function(){
			var ename = doc.getElementById('emp_name').value;
			var job = doc.getElementById('emp_job').value;
			var mgr = doc.getElementById('emp_mgr').value;
			var hiredate = doc.getElementById('emp_date').value;
			var sal = doc.getElementById('emp_sal').value;
			var comm = doc.getElementById('emp_comm').value;
			var dept = doc.getElementById('emp_deptno').value;
			
			console.log(`${ename},${job},${mgr},${hiredate},${sal},${comm},${dept}`);
			
			var tempEmployee = new Object();
			tempEmployee.ename = ename;
			tempEmployee.job = job;
			tempEmployee.mgr = mgr;
			tempEmployee.hiredate = hiredate;
			tempEmployee.sal = sal;
			tempEmployee.comm = comm;
			tempEmployee.deptno = dept;
			
			return tempEmployee;
		}
		

	}

}(window, document))