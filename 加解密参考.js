importScripts("https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js","https://cdn.bootcdn.net/ajax/libs/cryptico/0.0.1343522940/cryptico.min.js")
//参考resources/static/test.html
//生成公钥发送，拿到DES密钥用于后续加密
function verify(){
    let sth = Math.random().toString()
    let k = cryptico.generateRSAKey(sth,1024)
    let modulus = String(k.n)
    let exponent = String(k.e)
    console.log(modulus)
    console.log(exponent)
    $.ajax({
        url:"http://localhost:8082/Verify/getDesKey",//url
        type:"POST",
        headers:{"Content-Type":"application/json","Access-Control-Allow-Origin":"*"},
        data:JSON.stringify({"exponent":exponent,"modulus":modulus}),//发送公钥
        success:function (response) {
            let des_key_s = JSON.parse(response.data).key
            console.log(des_key_s)
            let des_key = JSON.parse(k.decrypt(cryptico.b64to16(des_key_s)))//私钥解密
            //DES密钥
            console.log(des_key)
        }
    })
}