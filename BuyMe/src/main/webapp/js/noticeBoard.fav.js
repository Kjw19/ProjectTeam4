$(function() {

	//좋아요 선택 여부와 선택한 총 개수 읽기
	function selectFav(noti_num) {
		$.ajax({
			url:'getFav.do',
			type:'post',
			data:{noti_num:noti_num},
			dataType:'json',
			success:function(param) {
				displayFav(param);
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	
	//좋아요 등록
	$('#output_fav').click(function(){
		$.ajax({
			url:'writeFav.do',
			type:'post',
			data:{noti_num:$('board_num').val()},
			datType:'json',
			success:function(param){
				if(param.result == 'logout') {
					alert('로그인 후 좋아요를 눌러주세요.');
				}
				else if(param.result == 'success') {
					displayFav(param);
				} 
				else {
					alert('좋아요 등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		}); 
	});  //end of click - 좋아요 등록
	
	//좋아요 표시
	function displayFav(param) {
		let output;
		if(param.status == 'noFav') {
			output = '../images/fav01.gif';
		}
		else {
			output = '../images/fav02.gif';
		}
		
		$('#output_fav').attr('src',output);
		$('#output_fcount').text(param.count);
	} //end of displayFav
	
	//초기 데이터 표시
	selectFav($('#board_num').val());
	
});