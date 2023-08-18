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

/**
 * ella_isBlank(null)    = true
 * ella_isBlank('')      = true
 * ella_isBlank(' ')     = true
 * ella_isBlank(0)       = true
 * @param data
 * @returns
 */
function ella_isBlank(data) {
    if (typeof(data) === 'undefined') {
        return true;
    } else if (typeof(data) === 'number') {
        return (0==data);
    } else if (typeof(data) === 'object') {
        if (JSON.stringify(data) === '{}' || JSON.stringify(data) === '[]') {
            return true;
        } else if (!data) {
            return true;
        }
        return false;
    } else if (typeof(data) === 'string') {
        if (!data.trim()) {
            return true;
        } else if(!isNaN(data)){
            let result=true;
            if(data.trim().length == data.trim().replace(/[^0-9]/gi,'').length){
                result = (0==Number.parseInt(data));
            }else{
                result = false;
            }
            return result;
        } else {
            return false;
        }
    } else {
        return false;
    }
}

// ella_isNotBlank
function ella_isNotBlank(data) {
    return !ella_isBlank(data);
}

// 돈 comma 구분하기
function ella_comma(p){
    let v = 0;

    if(ella_isBlank(p)){
        return 0;
    }

    p=p.toString().replace(/[^0-9\.]/gi,'');

    if(ella_isNotBlank(p)){
        v = Number(p);
    }

    const parts = String(v).split('.');
    return parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',') + (parts[1] ? '.' + parts[1] : '');
}

// 숫자만 반환
function ella_toNumber(p){
    let result = 0;
    if(ella_isNotBlank(p)){
        //p = Number(p);
        if(typeof p ==='number'){
            result = p;
        }else{
            result = parseFloat( p.toString().replace(/[^0-9]/gi,'') );
        }
    }
    return result;
}

// 전화번호 - 구분
function ella_toTel(p){
    p = p.toString().replace(/[^0-9]/gi,'')
    return p.toString().replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, '$1-$2-$3');;
}

// LocalDateTime 날짜 반환
function ella_getDate(p){
    if(ella_isBlank(p)){
        return "";
    }

    return String(p).split('T')[0];
}
