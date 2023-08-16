// 로그아웃
function signout(){
    $.ajax({
        url: '/signout',
        type: 'POST',
        async: false,
        success: function(response) {
            location.href = "/JIUJA/main";
        },
        error: function(request, status, error) {
            alert('로그아웃을 재시도해주십시오.');
        }
    });
}

// 로그인 여부에 따른 view 제어
function loginTf(result){
    if (result) {
        // 로그인 O
        $('.okVali').css('display','block');
        $('.noVali').css('display','none');
    } else {
        // 로그인 X
        $('.okVali').css('display','none');
        $('.noVali').css('display','block');
    }
}
